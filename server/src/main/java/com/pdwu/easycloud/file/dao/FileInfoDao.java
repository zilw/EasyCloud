package com.pdwu.easycloud.file.dao;

import com.pdwu.easycloud.file.bean.FileInfoBean;

import java.util.List;
import java.util.Map;

/**
 * Created by pdwu on 2017/12/18.
 */
public interface FileInfoDao {

    /**
     * 新增一个文件信息
     *
     * @param param
     * @return
     */
    int insertFileInfo(FileInfoBean param);

    /**
     * 获取文件信息列表
     *
     * @param param O(userId, fileId, md5, status, pageIndex & pageCount)
     * @return
     */
    List<FileInfoBean> selectFileInfoList(Map param);

    /**
     * 根据fileId更新文件名/状态
     *
     * @param param O(name, status), R(fileId, lastTime)
     * @return
     */
    int updateFileInfo(Map param);

    /**
     * 统计文件信息数量
     *
     * @param param O(userId, status)
     * @return
     */
    int countFileList(Map param);

}
