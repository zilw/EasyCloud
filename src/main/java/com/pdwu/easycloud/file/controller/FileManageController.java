package com.pdwu.easycloud.file.controller;

import com.pdwu.easycloud.common.bean.ResultBean;
import com.pdwu.easycloud.file.bean.FileInfoBean;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pdwu on 2017/12/19.
 */
@Controller
@RequestMapping(value = "/api/file")
public class FileManageController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private IFileService fileService;

    @Autowired
    private IShareService shareService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Object uploadFile(HttpServletRequest request, @RequestParam MultipartFile file) {

        //前端需要设置name=file

        if (file == null) {
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

        return resultBean;
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public Object listMyFiles(HttpServletRequest request) {

        Long userId = (Long) request.getSession().getAttribute("userId");

        List<FileInfoBean> list = fileService.listUserFiles(userId, FileInfoConstant.STATUS_NORMAL);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", list);
        map.put("userId", userId);

        return ResultBean.success(map);
    }

    @RequestMapping(value = "/rename", method = RequestMethod.POST)
    @ResponseBody
    public Object rename(HttpServletRequest request, @RequestParam Long fileId, @RequestParam String filename) {
        //TODO 限制文件名长度
        return fileService.updateFileName(fileId, filename);

    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Object delete(HttpServletRequest request, @RequestParam Long fileId) {

        return fileService.deleteFileInfo(fileId);

    }

    @RequestMapping(value = "/share", method = RequestMethod.POST)
    @ResponseBody
    public Object share(HttpServletRequest request, @RequestParam Long fileId) {

        Long userId = (Long) request.getSession().getAttribute("userId");

        return shareService.insertShareInfo(userId, fileId);
    }

    @RequestMapping(value = "/cancelShare", method = RequestMethod.POST)
    @ResponseBody
    public Object cancelShare(HttpServletRequest request, @RequestParam Long shareId) {

        return shareService.deleteShareInfo(shareId);
    }

}
