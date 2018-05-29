package xingyu.lu.review.tools.applet;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xingyu.lu.review.tools.encrypt.AES;

/**
 * 微信小程序加密算法
 *
 * @author xingyu.lu
 * @create 2018-05-07 15:47
 **/
public class WxEncryptedDataTool {
    private static final Logger LOGGER = LoggerFactory.getLogger(WxEncryptedDataTool.class);

    private static final String WATERMARK = "watermark";
    private static final String APPID = "appid";

    /**
     * 解密数据
     *
     * @return
     * @throws Exception
     */
    public static String decrypt(String appId, String encryptedData, String sessionKey, String iv) {
        String result = "";
        try {
            AES aes = new AES();
            byte[] resultByte = aes.decrypt(Base64.decodeBase64(encryptedData), Base64.decodeBase64(sessionKey), Base64.decodeBase64(iv));
            if (null != resultByte && resultByte.length > 0) {
                result = new String(WxPKCS7Encoder.decode(resultByte));
                JSONObject jsonObject = JSONObject.parseObject(result);
                String decryptAppid = jsonObject.getJSONObject(WATERMARK).getString(APPID);
                if (!appId.equals(decryptAppid)) {
                    result = "";
                }
            }
        } catch (Exception e) {
            LOGGER.error("DECRYPT EncryptedData = {} , SessionKey = {} , IV = {}", encryptedData,
                    sessionKey, iv);
            LOGGER.error("DECRYPT ERROR = {}", ExceptionUtils.getMessage(e));
            result = "";
            e.printStackTrace();
        }
        return result;
    }
}