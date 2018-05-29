package xingyu.lu.review.tools.string;

/**
 * @author xingyu.lu
 * @create 2018-03-22 17:39
 **/
public class Lang3StringUtils {
    /**
     * @Describe StringUtils.isBlank
     * @Auther   xingyu.lu
     * @Date     18/3/22 17:40
     */
    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    /**
     * @Describe StringUtils.isEmpty
     * @Auther   xingyu.lu
     * @Date     18/3/22 17:40
     */
    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static void main(String[] args) {
        System.out.println("StringUtils.isBlank-->"+isBlank("      "));
        System.out.println("StringUtils.isEmpty-->"+isEmpty("      "));
    }
}
