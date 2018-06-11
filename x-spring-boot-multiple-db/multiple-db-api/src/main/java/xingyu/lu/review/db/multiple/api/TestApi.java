package xingyu.lu.review.db.multiple.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xingyu.lu.review.db.multiple.domain.master.User;
import xingyu.lu.review.db.multiple.service.UserService;
import xingyu.lu.review.tools.result.ResultModel;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
        List<User> users = new ArrayList<>();
        users.add(userService.getMasterUserById(1));
        users.add(userService.getClusterUserById(1));
        return ResultModel.success(users);
    }
}
