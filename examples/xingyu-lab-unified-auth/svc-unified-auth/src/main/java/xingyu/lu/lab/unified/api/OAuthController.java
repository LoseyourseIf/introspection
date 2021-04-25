package xingyu.lu.lab.unified.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xingyu.lu
 * @create 2021-04-16 11:14
 **/
@Controller
@RequestMapping("/oauth2/")
public class OAuthController {

    @PostMapping("/authorize")
    public void authorizationCode(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getQueryString();
        resp.sendRedirect("http://www.baidu.com");
    }

    @PostMapping("/confirm")
    public void confirmAccess(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getQueryString();
        resp.sendRedirect("http://www.baidu.com");
    }

}
