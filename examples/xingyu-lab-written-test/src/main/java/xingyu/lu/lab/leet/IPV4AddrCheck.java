package xingyu.lu.lab.leet;

import java.util.regex.Pattern;

/**
 * @Description: IPV4 CHECK
 * @Version: 1.0
 * @Author: XingYu.Lu
 * @CreateTime: 2024-07-17  11:17
 */
public class IPV4AddrCheck {

    public static final String IPV4_REGEX = "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
            "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
            "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
            "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

    public static boolean isValidIPv4(String ip) {
        Pattern pattern = Pattern.compile(IPV4_REGEX);
        return pattern.matcher(ip).matches();
    }

    public static void main(String[] args) {
        String ip = "192.168.1.1";
        System.out.println("Is the IP " + ip + " valid? " + isValidIPv4(ip));

        ip = "256.256.256.256";
        System.out.println("Is the IP " + ip + " valid? " + isValidIPv4(ip));
    }
}
