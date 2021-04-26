package xingyu.lu.lab.unified.service;

import xingyu.lu.lab.unified.api.dto.AuthCodeGrantDTO;
import xingyu.lu.lab.unified.domain.UnifiedAccessToken;
import com.baomidou.mybatisplus.extension.service.IService;
import xingyu.lu.lab.unified.utils.rest.ResultModel;

/**
 *
 */
public interface UnifiedAccessTokenService extends IService<UnifiedAccessToken> {

    ResultModel tokenGrantByCode(AuthCodeGrantDTO reqParamMap) throws Exception;
}
