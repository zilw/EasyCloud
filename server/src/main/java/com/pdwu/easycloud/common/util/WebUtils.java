package com.pdwu.easycloud.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pdwu on 2017/12/14.
 */
public class WebUtils {

    private static String[] pubUris = new String[]{
            "/easycloud/api/pub.*",
            "/api/pub.*",
            "/error.*",
            "/static.*"
    };

    public static boolean checkUriPublic(String uri) {

        if (StringUtils.isBlank(uri)) {
            return false;
        }

        for (String s : pubUris) {
            if (uri.matches(s)) {
                return true;
            }
        }

        return false;
    }

    public static Map<String,Object> generateListResultMap(List list, int totalNumber, int pageSize, int pageNum){

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", list);
        map.put("count", list.size());
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);

        map.put("totalNumber", totalNumber);

        int totalPage = totalNumber / pageSize;
        if (totalNumber % pageSize > 0) {
            totalPage++;
        }
        map.put("totalPage", totalPage);

        return map;
    }

}
