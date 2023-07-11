package xingyu.lu.lab.applet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Charsets;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import xingyu.lu.lab.applet.entity.WXAccessToken;
import xingyu.lu.lab.applet.entity.WXJSAPITicket;
import xingyu.lu.lab.applet.entity.WXJSSDKConfig;
import xingyu.lu.lab.applet.entity.WXJSSign;
import xingyu.lu.lab.applet.utils.WXUtil;

import javax.annotation.Resource;
import java.net.URLDecoder;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Slf4j
public class TestGetWxAccessToken {


    public static String ACCESS_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={1}&secret={2}";

    public static String JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&access_token={1}";

    public static final String APP_ID = "wx564f728c160d1da0";
    public static final String APP_SECRET = "5220f2cf99c242acf0b619de3652215d";

    @Resource
    private RestTemplate restTemplate;


    @Test
    public void testBuildWXJSConfig() throws Exception {

        WXJSSign wxjsSign = WXJSSign.buildWXJSSign("https://cbsp.ynstt.org.cn/");
        /*Get WXAccessToken*/
        WXAccessToken accessToken = getAccessToken();
        System.out.println("========================WXAccessToken=========================");
        System.out.println(JSON.toJSONString(accessToken, SerializerFeature.PrettyFormat));

        if (null==accessToken|| StringUtils.isBlank(accessToken.getAccessToken())){
            throw new Exception("WX AccessToken 获取失败！");
        }
        /*Get WXJSAPITicket*/
        WXJSAPITicket jsapiTicket = getJSAPITicket(accessToken);
        System.out.println("========================WXJSAPITicket=========================");
        System.out.println(JSON.toJSONString(jsapiTicket, SerializerFeature.PrettyFormat));

        if (null==jsapiTicket|| StringUtils.isBlank(jsapiTicket.getTicket())){
            throw new Exception("WX JSAPITicket 获取失败！");
        }
        wxjsSign.setJsapi_ticket(jsapiTicket.getTicket());
        /*Build PreSignString*/
        String preSignString = WXUtil.buildPreSignStr(wxjsSign);

        System.out.println("========================PreSignString=========================");
        System.out.println(preSignString);

        /*Sign SHA1*/
        String sign = WXUtil.sha1(preSignString);

        WXJSSDKConfig config = WXJSSDKConfig.buildWXJSSDKConfig(APP_ID,wxjsSign);
        config.setSignature(sign);
        System.out.println("========================WXJSSDKConfig=========================");
        System.out.println(JSON.toJSONString(
                config,
                SerializerFeature.PrettyFormat));

    }

    public WXAccessToken getAccessToken(){
        ResponseEntity<WXAccessToken> responseEntity;
        try {

            responseEntity = restTemplate.getForEntity(ACCESS_TOKEN_URL,
                    WXAccessToken.class,
                    APP_ID,APP_SECRET);

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                WXAccessToken accessToken = responseEntity.getBody();
                if (null != accessToken ) {

                    return accessToken;
                }
            }

        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return null;
    }

    public WXJSAPITicket getJSAPITicket(WXAccessToken accessToken){
        ResponseEntity<WXJSAPITicket> responseEntity;
        try {

            responseEntity = restTemplate.getForEntity(JSAPI_TICKET_URL,
                    WXJSAPITicket.class,
                    accessToken.getAccessToken());

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                WXJSAPITicket ticket = responseEntity.getBody();
                if (null != ticket ) {
                 return ticket;
                }
            }

        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return null;
    }

}
