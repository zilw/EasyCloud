package com.pdwu.easycloud.file.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by pdwu on 2017/12/18.
 */
public class ShareInfoBean {

    private Long shareId;
    private Long userId;
    private Long fileId;
    private String password;
    private String shortlink;
    private Integer status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastTime;
    private FileInfoBean fileInfo;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getShortlink() {
        return shortlink;
    }

    public void setShortlink(String shortlink) {
        this.shortlink = shortlink;
    }

    public FileInfoBean getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(FileInfoBean fileInfo) {
        this.fileInfo = fileInfo;
    }

    public Long getShareId() {
        return shareId;
    }

    public void setShareId(Long shareId) {
        this.shareId = shareId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    @Override
    public String toString() {
        return "ShareInfoBean{" +
                "shareId=" + shareId +
                ", userId=" + userId +
                ", fileId=" + fileId +
                ", password='" + password + '\'' +
                ", shortlink='" + shortlink + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", lastTime=" + lastTime +
                ", fileInfo=" + fileInfo +
                '}';
    }
}
