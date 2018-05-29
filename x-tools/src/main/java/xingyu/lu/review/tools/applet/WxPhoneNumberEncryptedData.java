package xingyu.lu.review.tools.applet;

/**
 * 微信用户手机号信息（加密）
 *
 * @author xingyu.lu
 * @create 2018-05-07 17:57
 **/
public class WxPhoneNumberEncryptedData {
    //	包括敏感数据在内的完整用户信息的加密数据，详细见加密数据解密算法
    private String encryptedData;
    //  加密算法的初始向量，详细见加密数据解密算法
    private String iv;


    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

}
