package xingyu.lu.review.tools.applet;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import xingyu.lu.review.tools.http.HttpClientUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * (╯‵□′)╯︵┻━┻
 * 微信登录接口工具类
 *
 * @author xingyu.lu
 * @date 18/5/7 13:43
 */
public class WxApiTools {
    private static final String REQ_CODE2SESSION_URL = "https://api.weixin.qq.com/" +
            "sns/jscode2session";

    /**
     * (╯‵□′)╯︵┻━┻
     *
     * @param reqModel getSession 请求对象
     * @return openid session_key unionid
     * @author xingyu.lu
     * @date 18/5/7 13:20
     */
    public static WxApiGetSessionKeyResModel sendGetSessionKeyReq(WxApiGetSessionKeyReqModel reqModel) throws IOException {

        Map<String, Object> params = new HashMap<>();
        params.put("appid", reqModel.getAppid());
        params.put("secret", reqModel.getSecret());
        params.put("js_code", reqModel.getJs_code());
        params.put("grant_type", reqModel.getGrant_type());

        String result = HttpClientUtil.post(REQ_CODE2SESSION_URL, params, "UTF-8");

        WxApiGetSessionKeyResModel resModel = null;
        if (StringUtils.isNotBlank(result)) {
            resModel = JSON.parseObject(result,
                    WxApiGetSessionKeyResModel.class);
        }
        return resModel;
    }
}
