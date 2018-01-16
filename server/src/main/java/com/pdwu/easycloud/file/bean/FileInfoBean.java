package com.pdwu.easycloud.file.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pdwu.easycloud.file.util.FileSizeSerialize;

import java.util.Date;

/**
 * Created by pdwu on 2017/12/18.
 */
@JsonIgnoreProperties(value = {"truePath"})
public class FileInfoBean {

    private Long fileId;
    private Long userId;
    private String md5;
    private String truePath;    //服务器保存文件的真实路径
    private String name;
    private Integer status;

    @JsonSerialize(using = FileSizeSerialize.class)
    private Long size;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastTime;

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

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

    public String getTruePath() {
        return truePath;
    }

    public void setTruePath(String truePath) {
        this.truePath = truePath;
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
                ", truePath='" + truePath + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", lastTime=" + lastTime +
                '}';
    }
}
