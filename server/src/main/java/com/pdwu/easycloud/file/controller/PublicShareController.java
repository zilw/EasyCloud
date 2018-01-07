package com.pdwu.easycloud.file.controller;

import com.pdwu.easycloud.common.bean.ResultBean;
import com.pdwu.easycloud.common.bean.ResultCode;
import com.pdwu.easycloud.common.config.AppConfig;
import com.pdwu.easycloud.file.bean.ShareInfoBean;
import com.pdwu.easycloud.file.service.IShareService;
import com.pdwu.easycloud.file.service.IShortLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by pdwu on 2017/12/20.
 */
@Controller
@RequestMapping(value = AppConfig.API_PUB_SHARE)
public class PublicShareController {

    @Autowired
    private IShareService shareService;

    @Autowired
    private IShortLinkService shortLinkService;

    @RequestMapping(value = "/{shareLink}")
    @ResponseBody
    public Object getShareFile(HttpServletRequest request, @PathVariable String shareLink) {

        Long shareId = shortLinkService.getShareId(shareLink);
        ShareInfoBean shareInfoBean = shareService.getShareFileInfoById(shareId);
        if (shareInfoBean == null) {
            return ResultBean.fail(ResultCode.not_found, "资源不存在");
        }
        return ResultBean.success(shareInfoBean);
    }

}
