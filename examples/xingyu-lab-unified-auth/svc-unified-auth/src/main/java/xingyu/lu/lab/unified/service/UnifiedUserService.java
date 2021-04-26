package xingyu.lu.lab.unified.service;

import xingyu.lu.lab.unified.api.dto.BizRedirectDTO;
import xingyu.lu.lab.unified.api.dto.AuthUserDTO;
import xingyu.lu.lab.unified.domain.UnifiedUser;
import com.baomidou.mybatisplus.extension.service.IService;
import xingyu.lu.lab.unified.utils.rest.ResultModel;

/**
 *
 */
public interface UnifiedUserService extends IService<UnifiedUser> {

    ResultModel<BizRedirectDTO> unifiedLogin(AuthUserDTO dto) throws Exception;

}
