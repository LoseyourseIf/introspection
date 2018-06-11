package xingyu.lu.review.db.multiple.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xingyu.lu.review.db.multiple.service.UserService;
import xingyu.lu.review.tools.result.ResultModel;

import javax.annotation.Resource;

/**
 * @author xingyu.lu
 * @create 2018-06-11 15:12
 **/
@RestController
@RequestMapping("/user")
public class TestApi {

    @Resource
    private UserService userService;

    @RequestMapping("/test")
    public ResultModel test() {
        return userService.getAllUser();
    }
}
