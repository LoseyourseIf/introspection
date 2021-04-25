package xingyu.lu.lab.unified.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import xingyu.lu.lab.unified.api.dto.CodeRedirectDTO;
import xingyu.lu.lab.unified.api.dto.LoginUserDTO;
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
    public ResultModel<CodeRedirectDTO> unifiedLogin(LoginUserDTO dto) throws Exception {

        UnifiedUser user = getLoginUser(dto);

        if (null == user) {
            //noinspection unchecked
            return ResultModel.commonError("用户名或密码错误！");
        }

        long appId = dto.getAppId();
//        appId = 8909507396767744L;

        //TODO 根据 AppId 获取 App 信息 获取 重定向地址

        String redirectUrl = dto.getRedirectUrl();

        if (StringUtils.isBlank(dto.getRedirectUrl())) {
            redirectUrl = "https://www.baidu.com";
        }

        /*获取秘钥*/
        UnifiedAppKeys appKeys = unifiedAppKeysService.getUnifiedAppKeysById(appId);
        /*生成保存Code*/
        UnifiedCode code = unifiedCodeService.createAndSaveCode(dto);
        /*Code签名串*/
        String codeSign = SecureUtil.rsaEncryptByPrivate(appKeys.getAppPriKey(),
                code.getUnifiedCode());

        /*构建重定向 URL */
        Map<String, Object> queries = dto.getReqParamMap();

        queries.put("code", code.getUnifiedCode());
        queries.put("codeSign", codeSign);
        String redirectWithQueryStr = UrlQueryUtil.jointQueryStrUrl(redirectUrl, queries);

        CodeRedirectDTO result = CodeRedirectDTO.getInstance()
                .setCode(code.getUnifiedCode())
                .setCodeSign(codeSign)
                .setRedirectUrl(redirectWithQueryStr);

        return ResultModel.success(result);
    }


    private UnifiedUser getLoginUser(LoginUserDTO dto) {
        QueryWrapper<UnifiedUser> userQueryWrapper = Wrappers.<UnifiedUser>query()
                .eq("user_name", dto.getUserName())
                .eq("user_pwd", dto.getPassword());

        return unifiedUserMapper.selectOne(userQueryWrapper);
    }
}




