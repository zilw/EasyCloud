package com.pdwu.easycloud.file.bean;

import java.util.Date;

/**
 * Created by pdwu on 2017/12/18.
 */
public class FileInfoBean {

    private Long fileId;
    private Long userId;
    private String md5;
    private String path;
    private String name;
    private Integer status;
    private Date createTime;
    private Date lastTime;

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "FileInfoBean{" +
                "fileId=" + fileId +
                ", userId=" + userId +
                ", md5='" + md5 + '\'' +
                ", path='" + path + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", lastTime=" + lastTime +
                '}';
    }
}
