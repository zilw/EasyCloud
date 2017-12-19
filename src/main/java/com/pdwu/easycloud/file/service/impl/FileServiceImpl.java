package com.pdwu.easycloud.file.service.impl;

import com.pdwu.easycloud.common.bean.ResultBean;
import com.pdwu.easycloud.file.bean.FileInfoBean;
import com.pdwu.easycloud.file.constant.FileInfoConstant;
import com.pdwu.easycloud.file.dao.FileInfoDao;
import com.pdwu.easycloud.file.service.IFileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by pdwu on 2017/12/19.
 */
@Service
public class FileServiceImpl implements IFileService {

    @Autowired
    private FileInfoDao fileInfoDao;


    public ResultBean insertFileInfo(FileInfoBean bean) {
        if (bean == null) {
            return ResultBean.ARG_ERROR;
        }

        int updated = fileInfoDao.insertFileInfo(bean);

        return updated == 1 ? ResultBean.success(bean) : ResultBean.fail("服务器错误");
    }

    public ResultBean updateFileName(Long fileId, String fileName) {

        if (fileId == null || StringUtils.isBlank(fileName)) {
            return ResultBean.ARG_ERROR;
        }

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("fileId", fileId);
        param.put("name", fileName);
        param.put("lastTime", new Date());

        int updated = fileInfoDao.updateFileInfo(param);

        return updated == 1 ? ResultBean.success("") : ResultBean.fail("服务器错误");
    }

    public ResultBean deleteFileInfo(Long fileId) {
        if (fileId == null) {
            return ResultBean.ARG_ERROR;
        }

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("fileId", fileId);
        param.put("status", FileInfoConstant.STATUS_DELETE);
        param.put("lastTime", new Date());

        int updated = fileInfoDao.updateFileInfo(param);

        return updated == 1 ? ResultBean.success("") : ResultBean.fail("服务器错误");
    }

    public List<FileInfoBean> listUserFiles(Long userId, Integer status) {

        List<FileInfoBean> list = new ArrayList<FileInfoBean>();

        if (userId == null) {
            return list;
        }
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", userId);
        if (status != null) {
            param.put("status", status);
        }
        list = fileInfoDao.selectFileInfoList(param);

        return list;
    }


}
