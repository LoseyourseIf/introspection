package xingyu.lu.lab.applet.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WXJSSDKConfig {
    private String appId;
    private String timestamp;
    private String nonceStr;
    private String signature;
    private String url;

    public static WXJSSDKConfig buildWXJSSDKConfig(String appId, WXJSSign sign) {
        WXJSSDKConfig config = new WXJSSDKConfig();
        config.setAppId(appId);
        config.setTimestamp(sign.getTimestamp());
        config.setNonceStr(sign.getNoncestr());
        config.setUrl(sign.getUrl());
        return config;
    }
}
