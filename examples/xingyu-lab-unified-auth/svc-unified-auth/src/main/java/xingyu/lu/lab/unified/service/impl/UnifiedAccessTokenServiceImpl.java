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
import xingyu.lu.lab.unified.utils.jwt.JWTClaimsData;
import xingyu.lu.lab.unified.utils.jwt.JWTConstant;
import xingyu.lu.lab.unified.utils.jwt.JWTHelper;
import xingyu.lu.lab.unified.utils.jwt.JWTStrPerms;
import xingyu.lu.lab.unified.utils.map.MPUtil;
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
    @Resource
    private UnifiedAccessTokenMapper unifiedAccessTokenMapper;


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
        Map<String, Object> pojoMap = MPUtil.pojo2Map(dto);
        pojoMap.remove("sign");
        String queryStr = UrlQueryUtil.jointQueryStr(pojoMap);
        String decrypt = SecureUtil.rsaDecryptByPrivate(unifiedAppKeys.getAppPriKey(), appSign);
        if (!queryStr.equals(decrypt)) {
            return ResultModel.signError();
        }

        //CODE CHECK
        UnifiedCode unifiedCode = unifiedCodeService.getUnifiedAuthCode(dto);
        ResultModel codeCheckResult = AuthDataChecker.checkCode(unifiedCode);

        unifiedCode.setNonUsed(false);
        unifiedCode.setEnabled(false);
        //CODE USED
        boolean codeDelResult = unifiedCodeService.saveOrUpdate(unifiedCode);

        if (!codeDelResult) {
            return ResultModel.serverError();
        }

        if (!codeCheckResult.isSuccess()) {
            return codeCheckResult;
        }

        //TODO AUTH GET 该用户该App下权限集合 配合权限框架使用

        JWTStrPerms strPerms = JWTStrPerms.builder()
                .userRoles(null)
                .sysStrPermissions(null)
                .menuStrPermissions(null)
                .btnStrPermissions(null)
                .dataStrPermissions(null)
                .build();

        //JWT withClaim 签发 接收 app 用户 角色 系统权限 菜单权限 按钮权限 数据权限
        JWTClaimsData accessTokenClaim = JWTClaimsData.builder()
                .keyId(unifiedAppKeys.getUnifiedKeypairId())
                .issuer("").audience("")
                .tokenType(JWTConstant.ACCESS_TOKEN)
                .unifiedAppId(unifiedAppKeys.getUnifiedAppId())
                .unifiedUserId(unifiedUser.getUnifiedUserId())
                .unifiedUserName(unifiedUser.getUserName())
                .strPerms(strPerms)
                .build();

        //JWT CREATE Access_Token
        String accessToken = JWTHelper.buildToken(unifiedAppKeys.getAppPubKey(),
                unifiedAppKeys.getAppPriKey(),
                accessTokenClaim);

        JWTClaimsData refreshTokenClaim = JWTClaimsData.builder()
                .keyId(unifiedAppKeys.getUnifiedKeypairId())
                .issuer("").audience("")
                .tokenType(JWTConstant.REFRESH_TOKEN)
                .unifiedAppId(unifiedAppKeys.getUnifiedAppId())
                .unifiedUserId(unifiedUser.getUnifiedUserId())
                .unifiedUserName(unifiedUser.getUserName())
                .build();

        //JWT CREATE REFRESH_TOKEN
        String refreshToken = JWTHelper.buildToken(unifiedAppKeys.getAppPubKey(),
                unifiedAppKeys.getAppPriKey(),
                refreshTokenClaim);

        UnifiedAccessToken unifiedToken = new UnifiedAccessToken();
        unifiedToken.setAccessToken(accessToken);
        unifiedToken.setRefreshToken(refreshToken);

        int result = unifiedAccessTokenMapper.insert(unifiedToken);
        if (result > 0 && 0 != unifiedToken.getUnifiedAccessTokenId()) {

            return ResultModel.success(unifiedToken);
        } else {
            return ResultModel.serverError();
        }
    }
}




