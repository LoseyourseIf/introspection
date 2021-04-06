package xingyu.lu.individual.rpc.grpc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xingyu.lu.individual.rpc.grpc.service.UserDataService;

import javax.annotation.Resource;

/**
 * @author xingyu.lu
 * @create 2020-12-11 15:58
 **/
@RestController
public class CheckController {

    @Resource
    private UserDataService userDataService;

    @RequestMapping("/check")
    public Object check() {
        return userDataService.getUserInfo();
    }

}
