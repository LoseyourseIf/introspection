package xingyu.lu.review.tools.applet;


/**
 * @Description 微信 getsessionkey 返回对象
 * @author xingyu.lu
 * @date 18/5/7 13:35
 */
@SuppressWarnings("SpellCheckingInspection")
public class WxApiGetSessionKeyResModel {

    private String openid;
    private String session_key;
    private String unionId;
    private String errcode;
    private String errmsg;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
