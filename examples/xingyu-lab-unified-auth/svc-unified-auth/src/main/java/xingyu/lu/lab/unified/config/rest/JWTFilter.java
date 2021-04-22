package xingyu.lu.lab.unified.config.rest;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.MediaType;
import xingyu.lu.lab.unified.utils.rest.ErrorConstants;
import xingyu.lu.lab.unified.utils.rest.ResultModel;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * (╯‵□′)╯︵┻━┻
 *
 * <p>不加注解  让 Shiro 去实例化</p>
 *
 * @author xingyu.lu
 * @date 19/6/10 14:03
 */
@Slf4j
public class JWTFilter extends BasicHttpAuthenticationFilter {


    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
//        HttpServletRequest req = (HttpServletRequest) request;
//        String authorization = req.getHeader(JwtConstant.TOKEN_KEY);
//        return StringUtils.isNotBlank(authorization);
        return true;
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//        String authorization = httpServletRequest.getHeader(JwtConstant.TOKEN_KEY);
//        Integer userId = JwtClaimGetter.getClaimValue(authorization, JwtHelper.UID, Integer.class);
//        if (null == userId) {
//            return false;
//        }
//        // 提交给realm进行登入，如果错误会抛出异常并被捕获
//        getSubject(request, response).login(JwtAuthToken.buildToken(authorization));
//        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }

    /**
     * 如果在这里返回了false，请求会被直接拦截，前端收不到任何返回需 responseUnAuth
     * Controller中可以通过 subject.isAuthenticated() 来判断用户是否登入
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginAttempt(request, response)) {
            boolean loginSucceedFlag = executeLogin(request, response);
            if (!loginSucceedFlag) {
                responseUnAuth(response);
                return false;
            }
        } else {
            responseUnAuth(response);
            return false;
        }
        return true;
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        return super.preHandle(request, response);
    }

    /**
     * 非法请求
     */
    private void responseUnAuth(ServletResponse resp) {
        try {
            resp.reset();
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
            ServletOutputStream output = resp.getOutputStream();
            output.write(JSON.toJSONString(
                    ResultModel.customError(ErrorConstants.INVALID_TOKEN_CODE,
                            ErrorConstants.INVALID_LOGIN_MSG))
                    .getBytes());
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
