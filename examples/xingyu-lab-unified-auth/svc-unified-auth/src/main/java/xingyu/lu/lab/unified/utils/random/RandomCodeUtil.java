package xingyu.lu.lab.unified.utils.random;

import java.util.Random;

/**
 * @author xingyu.lu
 * @create 2021-04-25 13:41
 **/
public class RandomCodeUtil {

    public static final int RANDOM_CODE_32 = 32;

    public static final int RANDOM_CODE_64 = 64;


    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
