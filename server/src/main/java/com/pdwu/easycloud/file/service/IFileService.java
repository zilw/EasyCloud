package com.pdwu.easycloud.file.service;

import com.pdwu.easycloud.common.bean.ResultBean;
import com.pdwu.easycloud.file.bean.FileInfoBean;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by pdwu on 2017/12/19.
 */
public interface IFileService {

    /**
     * 新增一份文件信息
     *
     * @param bean
     * @return
     */
    ResultBean insertFileInfo(FileInfoBean bean);

    /**
     * 修改文件名
     *
     * @param fileId
     * @param fileName
     * @return
     */
    ResultBean updateFileName(Long fileId, String fileName);

    /**
     * 删除文件信息（逻辑删除）
     *
     * @param fileId
     * @return
     */
    ResultBean deleteFileInfo(Long fileId);


    /**
     * 查找用户的文件列表
     *
     * @param userId
     * @param status 为null代表所有状态 （FileInfoConstant.STATUS_xxx)
     * @return
     */
    List<FileInfoBean> listUserFiles(Long userId, Integer status, int pageNum, int pageSize);

    /**
     * 上传文件
     *
     * @param userId
     * @param file
     * @return
     * @throws Exception
     */
    ResultBean uploadFile(Long userId, MultipartFile file) throws Exception;

    /**
     * 根据MD5查找是否已存在文件信息
     *
     * @param md5
     * @return
     */
    FileInfoBean getFileInfoByMD5(String md5);

    /**
     * 根据fileId查找文件信息
     *
     * @param fileId
     * @return
     */
    FileInfoBean getFileInfoById(Long fileId);

    /**
     * 获取用户文件列表的总数
     *
     * @param userId
     * @param status 为null代表所有状态 （FileInfoConstant.STATUS_xxx)
     * @return
     */
    int countUserFiles(Long userId, Integer status);

}
