package xingyu.lu.lab.unified.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import xingyu.lu.lab.unified.api.dto.AuthCodeGrantDTO;
import xingyu.lu.lab.unified.api.dto.AuthDataChecker;
import xingyu.lu.lab.unified.domain.UnifiedAccessToken;
import xingyu.lu.lab.unified.domain.UnifiedAppKeys;
import xingyu.lu.lab.unified.service.UnifiedAccessTokenService;
import xingyu.lu.lab.unified.mapper.UnifiedAccessTokenMapper;
import org.springframework.stereotype.Service;
import xingyu.lu.lab.unified.service.UnifiedAppKeysService;
import xingyu.lu.lab.unified.service.UnifiedCodeService;
import xingyu.lu.lab.unified.service.UnifiedUserService;
import xingyu.lu.lab.unified.utils.rest.ResultModel;
import xingyu.lu.lab.unified.utils.secure.SecureUtil;
import xingyu.lu.lab.unified.utils.url.UrlQueryUtil;

import javax.annotation.Resource;
import java.util.Map;

/**
 *
 */
@Service
public class UnifiedAccessTokenServiceImpl extends ServiceImpl<UnifiedAccessTokenMapper, UnifiedAccessToken>
        implements UnifiedAccessTokenService {

    @Resource
    private UnifiedAppKeysService unifiedAppKeysService;
    @Resource
    private UnifiedCodeService unifiedCodeService;

    @Override
    public ResultModel tokenGrantByCode(AuthCodeGrantDTO dto) throws Exception {

        //APP CHECK
        UnifiedAppKeys unifiedAppKeys =
                unifiedAppKeysService.getUnifiedAppKeysById(dto.getAppId());

        ResultModel keyCheckResult = AuthDataChecker.checkAppKeys(unifiedAppKeys);
        if (!keyCheckResult.isSuccess()) {
            return keyCheckResult;
        }

        //SIGN CHECK
        String appSign = dto.getSign();
        Map<String, Object> pojoMap = UrlQueryUtil.pojo2Map(dto);
        pojoMap.remove("sign");
        String queryStr = UrlQueryUtil.jointQueryStr(pojoMap);
        String decrypt = SecureUtil.rsaDecryptByPrivate(unifiedAppKeys.getAppPriKey(), appSign);
        if (!queryStr.equals(decrypt)) {
            return ResultModel.signError();
        }

        //CODE CHECK
        String code = dto.getCode();


        //USER CHECK
        dto.getUnifiedUserId();

        //TODO AUTH GET 该用户该App下权限集合

        //JWT CREATE Access_Token Refresh_Token
        //CODE DELETE


        return null;
    }
}




