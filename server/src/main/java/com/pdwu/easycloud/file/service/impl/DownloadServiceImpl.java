package com.pdwu.easycloud.file.service.impl;

import com.pdwu.easycloud.common.bean.ResultBean;
import com.pdwu.easycloud.common.bean.ResultCode;
import com.pdwu.easycloud.file.bean.FileInfoBean;
import com.pdwu.easycloud.file.bean.ShareInfoBean;
import com.pdwu.easycloud.file.constant.ShareInfoConstant;
import com.pdwu.easycloud.file.service.IDownloadService;
import com.pdwu.easycloud.file.service.IFileService;
import com.pdwu.easycloud.file.service.IShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pdwu on 2017/12/21.
 */
@Service
public class DownloadServiceImpl implements IDownloadService {

    @Autowired
    private IFileService fileService;

    @Autowired
    private IShareService shareService;

    @Transactional(readOnly = true)
    public ResultBean download(Long userId, Long fileId, Long shareId, Map<String, Object> param) {

        FileInfoBean fileInfoBean = fileService.getFileInfoById(fileId);

        if (fileInfoBean == null) {
            return ResultBean.fail(ResultCode.not_found, "文件不存在");
        }

        //用户自己的文件
        if (userId != null && userId.equals(fileInfoBean.getUserId())) {
            //直接下载
            return getDownloadResult(fileInfoBean);
        }

        //不是用户自己的文件，那么理应带有shareId
        ShareInfoBean shareInfoBean = shareService.getShareFileInfoById(shareId);
        if (shareInfoBean == null) {
            return ResultBean.fail(ResultCode.not_found, "文件不存在或已取消分享");
        }

        //确实存在该分享，再次判断是否有效
        if (shareInfoBean.getStatus() != ShareInfoConstant.STATUS_NORMAL) {
            return ResultBean.fail(ResultCode.not_found, "下载失败，文件不存在或已取消分享");
        }

        return getDownloadResult(fileInfoBean);
    }

    private ResultBean getDownloadResult(FileInfoBean fileInfoBean) {
        File file = new File(fileInfoBean.getTruePath());

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("fileInfo", fileInfoBean);
        map.put("file", file);
        return ResultBean.success(map);
    }

}
