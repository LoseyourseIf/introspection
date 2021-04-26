package xingyu.lu.lab.unified.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xingyu.lu.lab.unified.utils.rest.ResultModel;

/**
 * @author xingyu.lu
 * @create 2021-04-16 11:20
 **/
@RestController
@RequestMapping("/token")
public class TokenController {

    @PostMapping("/get")
    public ResultModel getAccessToken() {
        return null;
    }

    @PostMapping("/check")
    public ResultModel refreshToken() {
        return null;
    }

}
