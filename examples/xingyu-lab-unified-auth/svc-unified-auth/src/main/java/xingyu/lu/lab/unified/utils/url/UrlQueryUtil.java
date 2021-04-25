package xingyu.lu.lab.unified.utils.url;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

/**
 * @author xingyu.lu
 * @create 2021-04-25 16:45
 **/
public class UrlQueryUtil {

    public static String jointQueryStrUrl(String url, Map<String, Object> queries) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder(url);
        if (queries != null && queries.keySet().size() > 0) {
            boolean firstFlag = true;
            for (Map.Entry<String, Object> qEntry : queries.entrySet()) {
                if (firstFlag) {
                    sb.append("?")
                            .append(qEntry.getKey())
                            .append("=")
                            .append(URLEncoder.encode(String.valueOf(qEntry.getValue()), "UTF-8"));
                    firstFlag = false;
                } else {
                    sb.append("&")
                            .append(qEntry.getKey())
                            .append("=")
                            .append(URLEncoder.encode(String.valueOf(qEntry.getValue()), "UTF-8"));
                }
            }
        }
        return sb.toString();
    }

}
