package xingyu.lu.lab.unified.api.dto;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import xingyu.lu.lab.unified.domain.UnifiedAppKeys;
import xingyu.lu.lab.unified.domain.UnifiedCode;
import xingyu.lu.lab.unified.domain.UnifiedUser;
import xingyu.lu.lab.unified.utils.rest.ResultModel;

/**
 * @author xingyu.lu
 * @create 2021-04-26 15:54
 **/
public class AuthDataChecker {


    public static ResultModel<Object> checkUser(UnifiedUser user) {
        if (null == user) {
            return ResultModel.commonError("用户账户名或密码错误！");
        } else if (!user.getNonExpired()) {
            return ResultModel.commonError("该用户账户已过期！");
        } else if (!user.getNonLocked()) {
            return ResultModel.commonError("该用户账户已冻结！");
        } else if (!user.getEnabled()) {
            return ResultModel.commonError("该用户账户不可用！");
        } else {
            return ResultModel.success(user);
        }
    }

    public static ResultModel checkAppKeys(UnifiedAppKeys appKeys) {
        if (null == appKeys ||
                StringUtils.isBlank(appKeys.getAppPubKey()) ||
                StringUtils.isBlank(appKeys.getAppPriKey())) {
            return ResultModel.commonError("未设置应用程序秘钥！");
        }
        return ResultModel.success(appKeys);
    }

    public static ResultModel checkCode(UnifiedCode code) {
        if (null == code || StringUtils.isBlank(code.getUnifiedCode())) {
            return ResultModel.commonError("认证Code错误！");
        } else if (System.currentTimeMillis() > code.getExpireAt().getTime()) {
            return ResultModel.commonError("认证Code已过期，请重新认证！");
        } else if (!code.getNonUsed()) {
            return ResultModel.commonError("认证Code已被使用，请重新认证！");
        } else if (code.getNonConfirm()) {
            return ResultModel.commonError("认证需用户手动授权！");
        }
        return ResultModel.success(code);
    }

}
