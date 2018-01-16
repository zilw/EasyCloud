package com.pdwu.easycloud.file.service.impl;

import com.pdwu.easycloud.common.bean.ResultBean;
import com.pdwu.easycloud.common.bean.ResultCode;
import com.pdwu.easycloud.common.config.AppConfig;
import com.pdwu.easycloud.file.bean.FileInfoBean;
import com.pdwu.easycloud.file.constant.FileInfoConstant;
import com.pdwu.easycloud.file.dao.FileInfoDao;
import com.pdwu.easycloud.file.service.IFileService;
import com.pdwu.easycloud.file.service.IShareService;
import com.pdwu.easycloud.file.util.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by pdwu on 2017/12/19.
 */
@Service
public class FileServiceImpl implements IFileService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private FileInfoDao fileInfoDao;

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private IShareService shareService;

    @Transactional
    public ResultBean insertFileInfo(FileInfoBean bean) {
        if (bean == null) {
            return ResultBean.ARG_ERROR;
        }

        int updated = fileInfoDao.insertFileInfo(bean);

        return updated == 1 ? ResultBean.success(bean) : ResultBean.fail("服务器错误");
    }

    @Transactional
    public ResultBean updateFileName(Long fileId, String fileName) {

        if (fileId == null || StringUtils.isBlank(fileName)) {
            return ResultBean.ARG_ERROR;
        }

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("fileId", fileId);
        param.put("name", fileName);
        param.put("lastTime", new Date());

        int updated = fileInfoDao.updateFileInfo(param);

        return updated == 1 ? ResultBean.success("") : ResultBean.fail("文件不存在");
    }

    @Transactional
    public ResultBean deleteFileInfo(Long fileId) {
        if (fileId == null) {
            return ResultBean.ARG_ERROR;
        }

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("fileId", fileId);
        param.put("status", FileInfoConstant.STATUS_DELETE);
        param.put("lastTime", new Date());

        int updated = fileInfoDao.updateFileInfo(param);
        //同时删除该文件的分享（若分享存在）
        shareService.deleteShareInfoByFileId(fileId);

        return updated == 1 ? ResultBean.success("") : ResultBean.fail("文件不存在");
    }

    @Transactional(readOnly = true)
    public List<FileInfoBean> listUserFiles(Long userId, Integer status, int pageNum, int pageSize) {

        List<FileInfoBean> list = new ArrayList<FileInfoBean>();

        if (userId == null) {
            return list;
        }
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", userId);
        if (status != null) {
            param.put("status", status);
        }

        param.put("index", (pageNum - 1) * pageSize);
        param.put("limit", pageSize);

        list = fileInfoDao.selectFileInfoList(param);

        return list;
    }

    @Transactional
    public ResultBean uploadFile(Long userId, MultipartFile uploadedFile) throws Exception {
        if (userId == null || uploadedFile == null) {
            return ResultBean.ARG_ERROR;
        }

        String uploadedFileName = uploadedFile.getOriginalFilename();
        long uploadedFileSize = uploadedFile.getSize();

        String uploadedFileMD5 = "";
        try {
            uploadedFileMD5 = FileUtils.getFileMD5(uploadedFile);
        } catch (Exception e) {
            log.error("uploadFile getFileMD5 error: {},\n userId: {}, multipartFile: {}, size: {}", e.toString(),
                    userId, uploadedFile.getOriginalFilename(), uploadedFile.getSize());
            throw e;
        }

        String path = "";
        try {
            FileInfoBean tempFileInfo = this.getFileInfoByMD5(uploadedFileMD5);
            if (tempFileInfo != null) {//服务器中已存在该文件
                path = tempFileInfo.getTruePath();

            } else { //不存在则保存文件
                String toSaveFileName = generateFileName(uploadedFileName);
                path = appConfig.getUserFilePath() + toSaveFileName;
                log.debug("uploadFile path: {}", path);
                File toSaveFile = new File(path);
                FileUtils.writeByteArrayToFile(toSaveFile, uploadedFile.getBytes());
            }


        } catch (IOException e) {
            log.error("uploadFile writeByteArrayToFile error: {},\n userId: {}, multipartFile: {}, size: {}", e.toString(),
                    userId, uploadedFile.getOriginalFilename(), uploadedFile.getSize());
            throw e;
        }

        FileInfoBean bean = new FileInfoBean();
        bean.setName(uploadedFileName);
        bean.setStatus(FileInfoConstant.STATUS_NORMAL);
        bean.setUserId(userId);
        bean.setTruePath(path);
        bean.setMd5(uploadedFileMD5);
        Date now = new Date();
        bean.setCreateTime(now);
        bean.setLastTime(now);
        bean.setSize(uploadedFileSize);

        ResultBean insertResult = this.insertFileInfo(bean);
        if (insertResult.getCode() != ResultCode.ok) {
            return insertResult;
        }

        return ResultBean.success(bean);
    }

    @Transactional(readOnly = true)
    public FileInfoBean getFileInfoByMD5(String md5) {
        if (md5 == null) {
            return null;
        }

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("md5", md5);
        List<FileInfoBean> list = fileInfoDao.selectFileInfoList(param);
        if (list == null || list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    @Transactional(readOnly = true)
    public FileInfoBean getFileInfoById(Long fileId) {

        if (fileId == null) {
            return null;
        }

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("fileId", fileId);
        List<FileInfoBean> list = fileInfoDao.selectFileInfoList(param);
        if (list == null || list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    public int countUserFiles(Long userId, Integer status) {

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", userId);
        if (status != null) {
            param.put("status", status);
        }

        return fileInfoDao.countFileList(param);
    }

    private String generateFileName(String source) {
        // 使用时间戳命名
        String res = System.currentTimeMillis() + "";
        if (StringUtils.isBlank(source)) {
            return res;
        }

        String[] strings = source.split("\\."); //转义.
        res = res + "." + strings[strings.length - 1];

        return res;

    }


}
