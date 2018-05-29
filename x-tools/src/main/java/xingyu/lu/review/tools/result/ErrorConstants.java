/**
 * Copyright (c) 2016, 791650277@qq.com(Mr.kiwi) All Rights Reserved.
 */
package xingyu.lu.review.tools.result;

/**
 * @Describe 错误常量
 * @Auther   xingyu.lu
 * @Date     18/3/17 17:24
 */
public interface ErrorConstants {

    /**
     * 无效sign
     */
    String INVALID_SIGN_CODE = "-100";
    String INVALID_SIGN_MSG = "无效签名";

    /**
     * 无效参数
     */
    String INVALID_PARAM_CODE = "-101";
    String INVALID_PARAM_MSG = "无效参数";

    /**
     * 权限不足
     */
    String PERMISSION_DENIED_CODE = "-102";
    String PERMISSION_DENIED_MSG = "权限不足";

    /**
     * 通用错误
     */
    String COMMON_ERROR_CODE = "-103";
    String COMMON_ERROR_MSG = "服务器繁忙，请稍后再试";

    /**
     * 登录失效
     */
    String INVALID_LOGIN_CODE = "-104";
    String INVALID_LOGIN_MSG = "登录失效";

    /**
     * 数据库操作失败
     */
    String DATA_BASE_CODE = "-105";
    String DATA_BASE_MSG = "数据库操作失败";

    /**
     * token失效
     */
    String INVALID_TOKEN_CODE = "-106";
    String INVALID_TOKEN_MSG = "Invalid Token";

    /**
     * 服务器异常
     */
    String SERVER_ERROR_CODE = "-200";
    String SERVER_ERROR_MSG = "服务器异常";

    String WX_USER_INFO_DECRYPT_ERROR_CODE = "-40001";
    String WX_USER_INFO_DECRYPT_ERROR_MSG = "微信加密校验失败！";
}
