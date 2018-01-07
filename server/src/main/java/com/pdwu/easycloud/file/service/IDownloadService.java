package com.pdwu.easycloud.file.service;

import com.pdwu.easycloud.common.bean.ResultBean;

import java.util.Map;

/**
 * Created by pdwu on 2017/12/21.
 */
public interface IDownloadService {
    /**
     * 下载文件
     *
     * @param userId
     * @param fileId
     * @param shareId
     * @param param   预留
     * @return 成功则取data转Map, key: fileInfo, file; value: FileInfoBean, File
     */
    ResultBean download(Long userId, Long fileId, Long shareId, Map<String, Object> param);
}
