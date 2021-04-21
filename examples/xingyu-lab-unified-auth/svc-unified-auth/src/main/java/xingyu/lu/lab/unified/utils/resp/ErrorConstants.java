package xingyu.lu.lab.unified.utils.resp;

/**
 * (╯‵□′)╯︵┻━┻
 *
 * <p>报错常量</p>
 *
 * @author xingyu.lu
 * @date 16/11/11 16:13
 */
public interface ErrorConstants {

    /**
     * 无效sign
     */
    String INVALID_SIGN_CODE = "-1000";
    String INVALID_SIGN_MSG = "无效签名！";

    /**
     * 无效参数
     */
    String INVALID_PARAM_CODE = "-1001";
    String INVALID_PARAM_MSG = "缺少必填参数或参数无效！";

    /**
     * 权限不足
     */
    String PERMISSION_DENIED_CODE = "-1002";
    String PERMISSION_DENIED_MSG = "权限不足！";

    /**
     * 通用错误
     */
    String COMMON_ERROR_CODE = "-1003";
    String COMMON_ERROR_MSG = "服务器繁忙，请稍后再试！";

    /**
     * 登录失效
     */
    String INVALID_LOGIN_CODE = "-1004";
    String INVALID_LOGIN_MSG = "登录失效！";

    /**
     * 数据库操作失败
     */
    String DATA_BASE_CODE = "-1005";
    String DATA_BASE_MSG = "数据库操作失败，请联系系统管理员！";

    /**
     * Token失效
     */
    String INVALID_TOKEN_CODE = "-1006";
    String INVALID_TOKEN_MSG = "Invalid Token";

    /**
     * 服务器异常
     */
    String SERVER_ERROR_CODE = "-200";
    String SERVER_ERROR_MSG = "服务器异常，请联系系统管理员！";
}
