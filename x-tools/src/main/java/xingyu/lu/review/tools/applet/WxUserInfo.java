package xingyu.lu.review.tools.applet;

/**
 * 微信UserInfo 解密结构
 * @author xingyu.lu
 * @create 2018-05-07 16:57
 **/
public class WxUserInfo {
    //    String	用户昵称
    private String nickName;
    //    String	用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640)
    private String avatarUrl;
    //    String	用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
    private String gender;
    //    String	用户所在城市
    private String city;
    //    String	用户所在省份
    private String province;
    //    String	用户所在国家
    private String country;
    //    String	用户的语言，简体中文为zh_CN
    private String language;

    private WXWaterMark watermark;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public WXWaterMark getWatermark() {
        return watermark;
    }

    public void setWatermark(WXWaterMark watermark) {
        this.watermark = watermark;
    }
}
