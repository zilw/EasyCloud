package com.pdwu.easycloud.file.service;

import com.pdwu.easycloud.common.bean.ResultBean;
import com.pdwu.easycloud.file.bean.ShareInfoBean;

import java.util.List;

/**
 * Created by pdwu on 2017/12/19.
 */
public interface IShareService {
    /**
     * 新增一个文件分享信息 （分享文件）
     *
     * @param userId
     * @param fileId
     * @return
     */
    ResultBean insertShareInfo(Long userId, Long fileId);

    /**
     * 删除分享信息（取消分享） （物理删除）
     *
     * @param shareId
     * @return
     */
    ResultBean deleteShareInfo(Long shareId);

    /**
     * 删除指定文件的分享 （物理删除）
     *
     * @param fileId
     * @return
     */
    ResultBean deleteShareInfoByFileId(Long fileId);

    /**
     * 根据shareId查看分享的文件
     *
     * @param shareId
     * @return
     */
    ShareInfoBean getShareFileInfoById(Long shareId);

    /**
     * 查找指定用户的分享列表
     *
     * @param userId
     * @param status 为null代表所有状态
     * @return
     */
    List<ShareInfoBean> listUserShareInfos(Long userId, Integer status, int pageNum, int pageSize);

    /**
     * 统计用户分享列表数量
     *
     * @param userId
     * @param status
     * @return
     */
    int countShareList(Long userId, Integer status);

}
