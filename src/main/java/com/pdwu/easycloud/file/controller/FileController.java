package com.pdwu.easycloud.file.controller;

import com.pdwu.easycloud.common.bean.ResultBean;
import com.pdwu.easycloud.file.service.IFileService;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by pdwu on 2017/12/19.
 */
@Controller
@RequestMapping(value = "/api/file")
public class FileController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private IFileService fileService;

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


}
