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
@RequestMapping("/oauth")
public class TokenController {

    @PostMapping("/token")
    public ResultModel getAccessToken(){
        return null;
    }

    @PostMapping("/check_token")
    public ResultModel refreshToken(){
        return null;
    }

}
