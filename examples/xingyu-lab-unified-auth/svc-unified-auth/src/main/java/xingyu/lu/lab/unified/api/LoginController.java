package xingyu.lu.lab.unified.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import xingyu.lu.lab.unified.utils.resp.ResultModel;

/**
 * @author xingyu.lu
 * @create 2021-04-15 11:15
 **/
@RestController
public class LoginController {

    @PostMapping(value = "/login")
    public ResultModel unifiedLogin(){
        return null;
    }

}
