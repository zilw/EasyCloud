package com.pdwu.easycloud.file.dao;

import com.pdwu.easycloud.file.bean.ShareInfoBean;

import java.util.List;
import java.util.Map;

/**
 * Created by pdwu on 2017/12/18.
 */
public interface ShareInfoDao {

    /**
     * 新增一个文件分享信息
     *
     * @param bean
     * @return
     */
    int insertShareInfo(ShareInfoBean bean);

    /**
     * 根据 shareId 更新分享状态
     *
     * @param param R(shareId, status, lastTime)
     * @return
     */
    int updateShareInfoStatus(Map param);

    /**
     * 根据 shareId 更新分享信息
     *
     * @param param R(shareId, lastTime),O(shortlink)
     * @return
     */
    int updateShareInfo(Map param);

    /**
     * 查找指定用户的分享信息/某条分享的详情
     *
     * @param param O(userId, shareId, status, fileId)
     * @return
     */
    List<ShareInfoBean> selectShareInfo(Map param);

    /**
     * 删除分享
     *
     * @param param O(shareId, fileId)
     * @return
     */
    int delete(Map param);

    /**
     * 统计分享数量
     *
     * @param param
     * @return
     */
    int countShareList(Map param);

}
