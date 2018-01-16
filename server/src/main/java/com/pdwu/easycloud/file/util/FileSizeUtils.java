package com.pdwu.easycloud.file.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by pdwu on 2018/1/16.
 */
public class FileSizeUtils {

    public static String getStringSize(long size) {

        BigDecimal fileSize = new BigDecimal(size);
        BigDecimal param = new BigDecimal(1024);
        int count = 0;
        while(fileSize.compareTo(param) > 0 && count < 5)
        {
            fileSize = fileSize.divide(param);
            count++;
        }
        DecimalFormat df = new DecimalFormat("#.##");
        String result = df.format(fileSize) + "";
        switch (count) {
            case 0:
                result += "B";
                break;
            case 1:
                result += "KB";
                break;
            case 2:
                result += "MB";
                break;
            case 3:
                result += "GB";
                break;
            case 4:
                result += "TB";
                break;
            case 5:
                result += "PB";
                break;
        }
        return result;
    }
}
