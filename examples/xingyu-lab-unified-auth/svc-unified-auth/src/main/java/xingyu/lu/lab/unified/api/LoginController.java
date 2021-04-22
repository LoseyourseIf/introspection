package xingyu.lu.lab.unified.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import xingyu.lu.lab.unified.api.dto.LoginUserDTO;
import xingyu.lu.lab.unified.service.UnifiedUserService;
import xingyu.lu.lab.unified.utils.rest.ResultModel;

import javax.annotation.Resource;

/**
 * @author xingyu.lu
 * @create 2021-04-15 11:15
 **/
@RestController
public class LoginController {

    @Resource
    private UnifiedUserService unifiedUserService;

    @PostMapping(value = "/login")
    public ResultModel unifiedLogin(@RequestBody LoginUserDTO dto) {
        return unifiedUserService.unifiedLogin(dto);
    }

}
