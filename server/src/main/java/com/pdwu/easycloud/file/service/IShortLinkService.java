package com.pdwu.easycloud.file.service;

/**
 * Created by pdwu on 2017/12/20.
 */
public interface IShortLinkService {

    /**
     * 根据shareId获取短链接
     *
     * @param shareId
     * @return
     */
    String getShortLink(Long shareId);

    /**
     * 根据短链接获取shareId
     *
     * @param shortLink
     * @return
     */
    Long getShareId(String shortLink);

}
