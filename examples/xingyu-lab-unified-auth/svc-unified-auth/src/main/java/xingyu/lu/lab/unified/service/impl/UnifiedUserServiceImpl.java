package xingyu.lu.lab.unified.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import xingyu.lu.lab.unified.api.dto.AuthDataChecker;
import xingyu.lu.lab.unified.api.dto.BizRedirectDTO;
import xingyu.lu.lab.unified.api.dto.AuthUserDTO;
import xingyu.lu.lab.unified.domain.UnifiedAppKeys;
import xingyu.lu.lab.unified.domain.UnifiedCode;
import xingyu.lu.lab.unified.domain.UnifiedUser;
import xingyu.lu.lab.unified.service.UnifiedAppKeysService;
import xingyu.lu.lab.unified.service.UnifiedCodeService;
import xingyu.lu.lab.unified.service.UnifiedUserService;
import xingyu.lu.lab.unified.mapper.UnifiedUserMapper;
import org.springframework.stereotype.Service;
import xingyu.lu.lab.unified.utils.rest.ResultModel;
import xingyu.lu.lab.unified.utils.secure.SecureUtil;
import xingyu.lu.lab.unified.utils.url.UrlQueryUtil;

import javax.annotation.Resource;
import java.util.Map;

/**
 *
 */
@Service
public class UnifiedUserServiceImpl extends ServiceImpl<UnifiedUserMapper, UnifiedUser>
        implements UnifiedUserService {

    @Resource
    private UnifiedUserMapper unifiedUserMapper;

    @Resource
    private UnifiedCodeService unifiedCodeService;

    @Resource
    private UnifiedAppKeysService unifiedAppKeysService;


    @DS(value = "auth-ds")
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public ResultModel<BizRedirectDTO> unifiedLogin(AuthUserDTO dto) throws Exception {

        UnifiedUser user = getLoginUser(dto);
        ResultModel checkResult = AuthDataChecker.checkUser(user);
        if (!checkResult.isSuccess()) {
            return checkResult;
        }

        long appId = dto.getAppId();

        if (0 == appId) {
            appId = 8909507396767744L;
//            appId = getUnifedAdminAppId();
        }

        //TODO 根据 AppId 获取 App 信息 获取 重定向地址

        String redirectUrl = dto.getRedirectUrl();
        if (StringUtils.isBlank(dto.getRedirectUrl())) {
            redirectUrl = "https://www.baidu.com";
        }

        /*获取秘钥*/
        UnifiedAppKeys appKeys = unifiedAppKeysService.getUnifiedAppKeysById(appId);
        ResultModel checkKeysResult = AuthDataChecker.checkAppKeys(appKeys);
        if (!checkKeysResult.isSuccess()) {
            return checkKeysResult;
        }

        /*生成并保存Code*/
        UnifiedCode code = unifiedCodeService.createAndSaveCode(dto);
        /*构建重定向 URL */
        Map<String, Object> queries = dto.getReqParamMap();
        queries.put("code", code.getUnifiedCode());
        queries.put("unifiedUserId", user.getUnifiedUserId());

        String queryStr = UrlQueryUtil.jointQueryStr(queries);

        /*参数签名*/
        String sign = SecureUtil.rsaEncryptByPrivate(appKeys.getAppPriKey(),
                queryStr);
        queries.put("sign", sign);

        String redirectWithQueryStr = redirectUrl + queries;

        BizRedirectDTO result = BizRedirectDTO.getInstance()
                .setCode(code.getUnifiedCode())
                .setRequireConfirm(false)
                .setPlatformSign(sign)
                .setRedirectUrl(redirectWithQueryStr);

        return ResultModel.success(result);
    }


    private UnifiedUser getLoginUser(AuthUserDTO dto) {
        QueryWrapper<UnifiedUser> userQueryWrapper = Wrappers.<UnifiedUser>query()
                .eq("user_name", dto.getUserName())
                .eq("user_pwd", dto.getPassword());

        return unifiedUserMapper.selectOne(userQueryWrapper);
    }
}




