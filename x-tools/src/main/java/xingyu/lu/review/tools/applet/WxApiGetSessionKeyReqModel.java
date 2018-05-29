package xingyu.lu.review.tools.applet;


/**
 * 微信小程序getSessionKey 请求对象
 *
 * @author xingyu.lu
 * @date 18/5/7 13:36
 */
public class WxApiGetSessionKeyReqModel {

    private String appid;
    private String secret;
    private String js_code;
    private String grant_type;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getJs_code() {
        return js_code;
    }

    public void setJs_code(String js_code) {
        this.js_code = js_code;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }
}
