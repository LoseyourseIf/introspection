package xingyu.lu.lab.applet.utils;

import cn.hutool.core.bean.BeanUtil;
import xingyu.lu.lab.applet.entity.WXJSSign;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

public class WXUtil {

    public static String buildPreSignStr(WXJSSign wxjsSign) {

        Map<String, Object> beanMap = BeanUtil.beanToMap(wxjsSign);
        //对所有待签名参数按照字段名的ASCII 码从小到大排序
        Map<String, Object> map = new TreeMap<>(String::compareTo);
        map.putAll(beanMap);

        //拼接字符串
        StringBuilder stringBuilder = new StringBuilder();
        map.forEach((key, value) -> stringBuilder.append(key)
                .append("=")
                .append(value)
                .append("&"));

        StringBuilder builder = stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return builder.toString();

    }

    //将拼接后的字符串进行SHA1加密
    public static String sha1(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(str.getBytes());
            byte[] messageDigest = digest.digest();
            // 创建 16进制字符串
            StringBuilder hexString = new StringBuilder();
            // 字节数组转换为 十六进制 数
            for (byte b : messageDigest) {
                String shaHex = Integer.toHexString(b & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
