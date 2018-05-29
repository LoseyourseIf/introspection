package xingyu.lu.review.tools.applet;

/**
 * 微信Phone 解密结构
 *
 * @author xingyu.lu
 * @create 2018-05-09 17:46
 **/
public class WxPhoneNumber {
    private String phoneNumber;
    private String purePhoneNumber;
    private String countryCode;
    private WXWaterMark watermark;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPurePhoneNumber() {
        return purePhoneNumber;
    }

    public void setPurePhoneNumber(String purePhoneNumber) {
        this.purePhoneNumber = purePhoneNumber;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public WXWaterMark getWatermark() {
        return watermark;
    }

    public void setWatermark(WXWaterMark watermark) {
        this.watermark = watermark;
    }
}
