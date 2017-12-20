package com.pdwu.easycloud.file.service.impl;

import com.pdwu.easycloud.file.service.IShortLinkService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 短链接实现类
 * 使用数字转62进制的方式
 * <p>
 * Created by pdwu on 2017/12/20.
 */
@Service
public class ShortLinkServiceImpl implements IShortLinkService {

    private static final String standardArray = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String myArray = "lmnopFGhijkNOPQRSTHwx45qyzABCDE67IJKLM0129abcdefgUVWXYZ3rstuv8";

    private static String array = myArray;

    public static void setArrayToStandard() {
        ShortLinkServiceImpl.array = standardArray;
    }

    public static void setArrayToMine() {
        ShortLinkServiceImpl.array = myArray;
    }

    public String getShortLink(Long shareId) {
        StringBuilder str = new StringBuilder();

        long num = shareId.longValue();
        if (num == 0) {
            return array.charAt(0) + "";
        }

        while (num != 0) {
            long rest = num - (num / 62) * 62;
            str.append(array.charAt((int) rest));
            num = num / 62;
        }

        return str.reverse().toString();
    }

    public Long getShareId(String shortLink) {
        if (StringUtils.isBlank(shortLink)) {
            return 0L;
        }

        long num = 0;
        int length = shortLink.length();
        for (int i = length - 1; i >= 0; i--) {
            char c = shortLink.charAt(i);
            num = num + array.indexOf(c) * (long) Math.pow(62, length - i - 1);
        }

        return num;
    }

}
