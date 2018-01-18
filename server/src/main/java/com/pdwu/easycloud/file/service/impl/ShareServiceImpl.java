package com.pdwu.easycloud.file.service.impl;

import com.pdwu.easycloud.common.bean.ResultBean;
import com.pdwu.easycloud.common.config.AppConfig;
import com.pdwu.easycloud.file.bean.ShareInfoBean;
import com.pdwu.easycloud.file.constant.ShareInfoConstant;
import com.pdwu.easycloud.file.dao.ShareInfoDao;
import com.pdwu.easycloud.file.service.IShareService;
import com.pdwu.easycloud.file.service.IShortLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by pdwu on 2017/12/19.
 */
@Service
public class ShareServiceImpl implements IShareService {


    @Autowired
    private ShareInfoDao shareInfoDao;

    @Autowired
    private IShortLinkService shortLinkService;

    @Autowired
    private AppConfig appConfig;

    @Transactional
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

        shareInfoDao.insertShareInfo(bean);
        //短链接： （地址+分享目录前缀+短链接码）
        String shortlink = appConfig.getAppSite() +
                AppConfig.URL_SHORT_LINK_PRE +
                shortLinkService.getShortLink(bean.getShareId());

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("shareId", bean.getShareId());
        param.put("shortlink", shortlink);
        param.put("lastTime", new Date());
        shareInfoDao.updateShareInfo(param);

        return ResultBean.success(bean);
    }

    @Transactional
    public ResultBean deleteShareInfo(Long shareId) {
        if (shareId == null) {
            return ResultBean.ARG_ERROR;
        }
        Map<String, Object> param = new HashMap<String, Object>();

        param.put("shareId", shareId);
        int updated = shareInfoDao.delete(param);

        return updated >= 1 ? ResultBean.success("") : ResultBean.fail("不存在该分享");
    }

    @Transactional
    public ResultBean deleteShareInfoByFileId(Long fileId) {
        if (fileId == null) {
            return ResultBean.ARG_ERROR;
        }
        Map<String, Object> param = new HashMap<String, Object>();

        param.put("fileId", fileId);
        int updated = shareInfoDao.delete(param);

        return updated >= 1 ? ResultBean.success("") : ResultBean.fail("不存在该文件的分享");
    }

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
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

    public int countShareList(Long userId, Integer status) {

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", userId);
        if (status != null) {
            param.put("status", status);
        }

        return shareInfoDao.countShareList(param);
    }
}
