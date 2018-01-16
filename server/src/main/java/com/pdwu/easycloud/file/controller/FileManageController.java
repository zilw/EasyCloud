package com.pdwu.easycloud.file.controller;

import com.pdwu.easycloud.common.bean.ResultBean;
import com.pdwu.easycloud.common.bean.ResultCode;
import com.pdwu.easycloud.common.config.AppConfig;
import com.pdwu.easycloud.common.util.WebUtils;
import com.pdwu.easycloud.file.bean.FileInfoBean;
import com.pdwu.easycloud.file.bean.ShareInfoBean;
import com.pdwu.easycloud.file.constant.FileInfoConstant;
import com.pdwu.easycloud.file.service.IFileService;
import com.pdwu.easycloud.file.service.IShareService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pdwu on 2017/12/19.
 */
@Controller
public class FileManageController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private IFileService fileService;

    @Autowired
    private IShareService shareService;

    @RequestMapping(value = AppConfig.API_FILE_UPLOAD, method = RequestMethod.POST)
    @ResponseBody
    public Object uploadFile(HttpServletRequest request, HttpServletResponse response, @RequestParam MultipartFile file) {

        //前端需要设置name=file

        if (file == null) {
            response.setStatus(ResultCode.fail);
            return ResultBean.ARG_ERROR;
        }

        Long userId = (Long) request.getSession().getAttribute("userId");

        log.debug("uploadFile userId: {}, multipartFile contentType: {}, Name: {}, OriginalFilename: {}, size: {}", userId,
                file.getContentType(), file.getName(), file.getOriginalFilename(), file.getSize());

        ResultBean resultBean = null;
        try {
            resultBean = fileService.uploadFile(userId, file);
        } catch (Exception e) {
            resultBean = ResultBean.SERVER_ERROR;
        }

        if (resultBean.getCode() != ResultCode.ok) {
            response.setStatus(ResultCode.server_error);
        }

        return resultBean;
    }

    @RequestMapping(value = AppConfig.API_FILE_LIST)
    @ResponseBody
    public Object listMyFiles(HttpServletRequest request, Integer pageNum, Integer pageSize) {

        Long userId = (Long) request.getSession().getAttribute("userId");

        int intPageNum = 1;
        int intPageSize = 10;
        if (pageNum != null) {
            intPageNum = pageNum;
        }
        if (pageSize != null) {
            intPageSize = pageSize;
        }

        //获取列表详情
        List<FileInfoBean> list = fileService.listUserFiles(userId, FileInfoConstant.STATUS_NORMAL, intPageNum, intPageSize);
        //获取总数
        int totalNumber = fileService.countUserFiles(userId, FileInfoConstant.STATUS_NORMAL);

        Map<String, Object> map = WebUtils.generateListResultMap(list, totalNumber, intPageSize, intPageNum);
        map.put("userId", userId);

        return ResultBean.success(map);
    }

    @RequestMapping(value = AppConfig.API_FILE_RENAME, method = RequestMethod.POST)
    @ResponseBody
    public Object rename(HttpServletRequest request, @RequestParam Long fileId, @RequestParam String filename) {
        //TODO 限制文件名长度
        return fileService.updateFileName(fileId, filename);

    }

    @RequestMapping(value = AppConfig.API_FILE_DELETE, method = RequestMethod.POST)
    @ResponseBody
    public Object delete(HttpServletRequest request, @RequestParam Long fileId) {

        return fileService.deleteFileInfo(fileId);

    }

    @RequestMapping(value = AppConfig.API_FILE_SHARE, method = RequestMethod.POST)
    @ResponseBody
    public Object share(HttpServletRequest request, @RequestParam Long fileId) {

        Long userId = (Long) request.getSession().getAttribute("userId");

        return shareService.insertShareInfo(userId, fileId);
    }

    @RequestMapping(value = AppConfig.API_FILE_CANCEL_SHARE, method = RequestMethod.POST)
    @ResponseBody
    public Object cancelShare(HttpServletRequest request, @RequestParam Long shareId) {

        return shareService.deleteShareInfo(shareId);
    }

    @RequestMapping(value = AppConfig.API_FILE_SHARE_LIST)
    @ResponseBody
    public Object shareList(HttpServletRequest request, Integer status, Integer pageNum, Integer pageSize) {

        Long userId = (Long) request.getSession().getAttribute("userId");

        int intPageNum = 1;
        if (pageNum != null) {
            intPageNum = pageNum;
        }

        int intPageSize = 10;
        if (pageSize != null) {
            intPageSize = pageSize;
        }

        List<ShareInfoBean> list = shareService.listUserShareInfos(userId, status, intPageNum, intPageSize);
        int totalNumber = shareService.countShareList(userId, status);

        Map<String, Object> map = WebUtils.generateListResultMap(list, totalNumber, intPageSize, intPageNum);

        return ResultBean.success(map);
    }

}
