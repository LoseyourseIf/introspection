package xingyu.lu.lab.applet.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WXJSSign {

    private String jsapi_ticket;
    private String noncestr;
    private String timestamp;
    private String url;

    public static WXJSSign buildWXJSSign(String url){
        WXJSSign wxjsSign = new WXJSSign();
        wxjsSign.setNoncestr(
                UUID.randomUUID()
                .toString()
                        .replace("-", "")
                        .substring(0, 16));
        wxjsSign.setTimestamp(String.valueOf(System.currentTimeMillis() / 1000));
        wxjsSign.setUrl(url);
        return wxjsSign;
    }

}
