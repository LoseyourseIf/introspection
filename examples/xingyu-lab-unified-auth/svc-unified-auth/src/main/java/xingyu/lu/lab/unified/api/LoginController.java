package xingyu.lu.lab.unified.api;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;
import xingyu.lu.lab.unified.api.dto.CodeRedirectDTO;
import xingyu.lu.lab.unified.api.dto.LoginUserDTO;
import xingyu.lu.lab.unified.service.UnifiedUserService;
import xingyu.lu.lab.unified.utils.rest.ResultModel;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author xingyu.lu
 * @create 2021-04-15 11:15
 **/
@Slf4j
@RestController
public class LoginController {

    @Resource
    private UnifiedUserService unifiedUserService;

    @PostMapping(value = "/login")
    public ResultModel<CodeRedirectDTO> unifiedLogin(@RequestBody LoginUserDTO dto, HttpServletRequest request,
                                                     HttpServletResponse response) throws Exception {
        Map<String, Object> reqParamMap = WebUtils.getParametersStartingWith(request, "");
        dto.setReqParamMap(reqParamMap);

        ResultModel<CodeRedirectDTO> result = unifiedUserService.unifiedLogin(dto);

        log.info("SEND_REDIRECT URL={}", result.getReturnValue().getRedirectUrl());

        // TODO 打开注释
//        if (result.isSuccess()) {
//            response.sendRedirect(result.getReturnValue().getRedirectUrl());
//        }

        return result;
    }

}
