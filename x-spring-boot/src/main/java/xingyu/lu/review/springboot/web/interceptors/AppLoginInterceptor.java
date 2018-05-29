package xingyu.lu.review.springboot.web.interceptors;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import xingyu.lu.review.tools.jwt.JWTHelper;
import xingyu.lu.review.tools.resource.ResourceUtil;
import xingyu.lu.review.tools.result.ErrorConstants;
import xingyu.lu.review.tools.result.ResultModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Describe 登录拦截
 * @Auther xingyu.lu
 * @Date 18/4/9 14:20
 */
@Component
public class AppLoginInterceptor implements HandlerInterceptor {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(ResourceUtil.getSystem("JWT.TOKEN.NAME"));
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);


        if (StringUtils.isBlank(token)) {
            response.getWriter().write(JSON.toJSONString(ResultModel.customError
                    (ErrorConstants.INVALID_LOGIN_CODE,
                            ErrorConstants.INVALID_LOGIN_MSG)));
            return false;
        }

        Claims claims = JWTHelper.checkLoginToken(token);
        if (claims == null) {
            response.getWriter().write(JSON.toJSONString(ResultModel.customError
                    (ErrorConstants.INVALID_LOGIN_CODE,
                            ErrorConstants.INVALID_LOGIN_MSG)));
            return false;
        }
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
