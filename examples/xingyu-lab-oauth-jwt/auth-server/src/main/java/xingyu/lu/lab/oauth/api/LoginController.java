package xingyu.lu.lab.oauth.api;

import com.alibaba.nacos.common.http.param.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xingyu.lu.lab.oauth.utils.AuthPrincipalUtil;

/**
 * @author xingyu.lu
 * @create 2021-04-12 15:03
 **/
@RestController
public class LoginController {

    @RequestMapping(value = "/login-succeed", produces = {MediaType.TEXT_PLAIN})
    public String loginSucceed() {
        String username = AuthPrincipalUtil.securityPrincipalGetUserName();
        return username + " 登录成功";
    }

}
