package xingyu.lu.lab.unified.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import xingyu.lu.lab.unified.api.dto.AuthCodeGrantDTO;
import xingyu.lu.lab.unified.api.dto.AuthDataChecker;
import xingyu.lu.lab.unified.domain.UnifiedAccessToken;
import xingyu.lu.lab.unified.domain.UnifiedAppKeys;
import xingyu.lu.lab.unified.domain.UnifiedCode;
import xingyu.lu.lab.unified.domain.UnifiedUser;
import xingyu.lu.lab.unified.service.UnifiedAccessTokenService;
import xingyu.lu.lab.unified.mapper.UnifiedAccessTokenMapper;
import org.springframework.stereotype.Service;
import xingyu.lu.lab.unified.service.UnifiedAppKeysService;
import xingyu.lu.lab.unified.service.UnifiedCodeService;
import xingyu.lu.lab.unified.service.UnifiedUserService;
import xingyu.lu.lab.unified.utils.jwt.JwtClaimsData;
import xingyu.lu.lab.unified.utils.jwt.JwtHelper;
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
    @Resource
    private UnifiedUserService unifiedUserService;

    @Override
    public ResultModel tokenGrantByCode(AuthCodeGrantDTO dto) throws Exception {

        //APP CHECK
        UnifiedAppKeys unifiedAppKeys =
                unifiedAppKeysService.getUnifiedAppKeysById(dto.getAppId());

        ResultModel keyCheckResult = AuthDataChecker.checkAppKeys(unifiedAppKeys);
        if (!keyCheckResult.isSuccess()) {
            return keyCheckResult;
        }

        //USER CHECK
        dto.getUnifiedUserId();
        UnifiedUser unifiedUser = unifiedUserService.getById(dto.getUnifiedUserId());

        ResultModel userCheckResult = AuthDataChecker.checkUser(unifiedUser);
        if (!userCheckResult.isSuccess()) {
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
        UnifiedCode unifiedCode = unifiedCodeService.getUnifiedAuthCode(dto);
        ResultModel codeCheckResult = AuthDataChecker.checkCode(unifiedCode);
        if (!codeCheckResult.isSuccess()) {
            return codeCheckResult;
        }


        //TODO AUTH GET 该用户该App下权限集合

        JwtClaimsData claimsData = JwtClaimsData.builder()
                .keyId(0)
                .issuer("")
                .audience("")
                .unifiedAppId(0)
                .unifiedUserId(0)
                .unifiedUserName("")
                .userRoles(null)
                .sysPrivileges(null)
                .menuPrivileges(null)
                .btnPrivileges(null)
                .dataPrivileges(null)
                .build();


        //JWT CREATE Access_Token Refresh_Token
        //JWT withClaim 签发 接收 app 用户 角色 系统权限 菜单权限 按钮权限 数据权限 过期时间
        //CODE USED


        return null;
    }
}




