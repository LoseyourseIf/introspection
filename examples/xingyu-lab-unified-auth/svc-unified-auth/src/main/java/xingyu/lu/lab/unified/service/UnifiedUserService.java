package xingyu.lu.lab.unified.service;

import xingyu.lu.lab.unified.api.dto.CodeRedirectDTO;
import xingyu.lu.lab.unified.api.dto.LoginUserDTO;
import xingyu.lu.lab.unified.domain.UnifiedUser;
import com.baomidou.mybatisplus.extension.service.IService;
import xingyu.lu.lab.unified.utils.rest.ResultModel;

/**
 *
 */
public interface UnifiedUserService extends IService<UnifiedUser> {

    ResultModel<CodeRedirectDTO> unifiedLogin(LoginUserDTO dto) throws Exception;

}
