package xingyu.lu.lab.unified.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;
import xingyu.lu.lab.unified.api.dto.AuthCodeGrantDTO;
import xingyu.lu.lab.unified.service.UnifiedAccessTokenService;
import xingyu.lu.lab.unified.service.UnifiedCodeService;
import xingyu.lu.lab.unified.utils.rest.ResultModel;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author xingyu.lu
 * @create 2021-04-16 11:14
 **/
@Controller
@RequestMapping("/oauth2/")
public class OAuthController {

    @Resource
    private UnifiedAccessTokenService unifiedAccessTokenService;

    @PostMapping("/authorize")
    public ResultModel authorizationCode(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        Map<String, Object> reqParamMap = WebUtils.getParametersStartingWith(req, "");

        AuthCodeGrantDTO dto = AuthCodeGrantDTO.loadParam(reqParamMap);
        if (!dto.paramCheck()) {
            throw new IllegalArgumentException();
        }

        return unifiedAccessTokenService.tokenGrantByCode(dto);
    }

    @PostMapping("/confirm")
    public void confirmAccess(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getQueryString();
        resp.sendRedirect("http://www.baidu.com");
    }

}
