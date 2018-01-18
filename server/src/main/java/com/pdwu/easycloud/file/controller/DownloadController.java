package com.pdwu.easycloud.file.controller;

import com.pdwu.easycloud.common.bean.ResultBean;
import com.pdwu.easycloud.common.bean.ResultCode;
import com.pdwu.easycloud.common.config.AppConfig;
import com.pdwu.easycloud.common.util.JsonUtils;
import com.pdwu.easycloud.file.bean.FileInfoBean;
import com.pdwu.easycloud.file.service.IDownloadService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.util.Map;

/**
 * Created by pdwu on 2017/12/21.
 */
@Controller
public class DownloadController {

    @Autowired
    private IDownloadService downloadService;

    @RequestMapping(value = AppConfig.API_PUB_DOWNLOAD)
    public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Long userId = (Long) request.getSession().getAttribute("userId");

        String fileIdStr = request.getParameter("fileId");
        String shareIdStr = request.getParameter("shareId");

        Long fileId = null;
        Long shareId = null;

        if (!StringUtils.isBlank(fileIdStr)) {
            fileId = Long.valueOf(fileIdStr);
        }

        if (!StringUtils.isBlank(shareIdStr)) {
            shareId = Long.valueOf(shareIdStr);
        }

        ResultBean bean = downloadService.download(userId, fileId, shareId, null);
        if (bean.getCode() != ResultCode.ok) {
            writeError(response, bean);
        } else {
            Map<String, Object> map = (Map<String, Object>) bean.getData();
            writeFile(response, (File) map.get("file"), (FileInfoBean) map.get("fileInfo"), true);
        }

    }

    private void writeError(HttpServletResponse response, ResultBean resultBean) throws IOException {

        response.setStatus(resultBean.getCode());

        String json = JsonUtils.objectToJson(resultBean);
        response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);

    }

    private void writeFile(HttpServletResponse response, File file, FileInfoBean fileInfoBean, boolean needDisposition) throws IOException {
        if (file == null || fileInfoBean == null || !file.exists()) {
            writeError(response, ResultBean.SERVER_ERROR);
            return;
        }

        String mimeType = URLConnection.guessContentTypeFromName(fileInfoBean.getName());
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }

        //防止中文乱码
        String fileName = new String(fileInfoBean.getName().getBytes("gbk"), "ISO8859-1");

        response.setContentType(mimeType);
        //response.setContentLengthLong(file.length());
        if (needDisposition) {
            response.setHeader("Content-Disposition", "attachment;fileName=\"" + fileName + "\"");
        }

        //文件下载
        InputStream in = new BufferedInputStream(new FileInputStream(file));
        FileCopyUtils.copy(in, response.getOutputStream());
    }

    @RequestMapping(value = {AppConfig.API_PUB_PREVIEW})
    public void preview(HttpServletRequest request, HttpServletResponse response, Long fileId, Long shareId) throws IOException {

        Long userId = (Long) request.getSession().getAttribute("userId");

        ResultBean bean = downloadService.download(userId, fileId, shareId, null);
        if (bean.getCode() != ResultCode.ok) {
            writeError(response, bean);
        } else {
            Map<String, Object> map = (Map<String, Object>) bean.getData();
            writeFile(response, (File) map.get("file"), (FileInfoBean) map.get("fileInfo"), false);
        }

    }

}
