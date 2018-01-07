package com.pdwu.easycloud.file.service.impl;

import com.pdwu.easycloud.common.bean.ResultBean;
import com.pdwu.easycloud.file.bean.ShareInfoBean;
import com.pdwu.easycloud.file.constant.ShareInfoConstant;
import com.pdwu.easycloud.file.dao.ShareInfoDao;
import com.pdwu.easycloud.file.service.IShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by pdwu on 2017/12/19.
 */
@Service
public class ShareServiceImpl implements IShareService {


    @Autowired
    private ShareInfoDao shareInfoDao;

    public ResultBean insertShareInfo(Long userId, Long fileId) {
        if (userId == null || fileId == null) {
            return ResultBean.ARG_ERROR;
        }

        ShareInfoBean bean = new ShareInfoBean();
        bean.setUserId(userId);
        bean.setFileId(fileId);
        bean.setStatus(ShareInfoConstant.STATUS_NORMAL);

        Date now = new Date();
        bean.setCreateTime(now);
        bean.setLastTime(now);

        int updated = shareInfoDao.insertShareInfo(bean);

        return updated == 1 ? ResultBean.success(bean) : ResultBean.fail("服务器错误");
    }

    public ResultBean deleteShareInfo(Long shareId) {
        if (shareId == null) {
            return ResultBean.ARG_ERROR;
        }
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("shareId", shareId);
        param.put("status", ShareInfoConstant.STATUS_CANCLE);
        param.put("lastTime", new Date());

        int updated = shareInfoDao.updateShareInfoStatus(param);

        return updated == 1 ? ResultBean.success("") : ResultBean.fail("不存在该分享");
    }

    public ShareInfoBean getShareFileInfoById(Long shareId) {
        if (shareId == null) {
            return null;
        }
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("shareId", shareId);
        List<ShareInfoBean> list = shareInfoDao.selectShareInfo(param);
        if (list == null || list.size() == 0) {
            return null;
        }

        return list.get(0);
    }

    public List<ShareInfoBean> listUserShareInfos(Long userId, Integer status, int pageNum, int pageSize) {

        if (userId == null) {
            return new ArrayList<ShareInfoBean>();
        }

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", userId);
        if (status != null) {
            param.put("status", status);
        }
        param.put("index", (pageNum - 1) * pageSize);
        param.put("limit", pageSize);

        List<ShareInfoBean> list = shareInfoDao.selectShareInfo(param);

        return list;
    }
}
