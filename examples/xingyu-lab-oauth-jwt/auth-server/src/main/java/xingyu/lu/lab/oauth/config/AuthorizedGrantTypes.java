package xingyu.lu.lab.oauth.config;

/**
 * (╯‵□′)╯︵┻━┻
 * AUTHORIZATION_CODE 授权码模式常用于社会化登录和 SSO
 * <p>
 * PASSWORD 密码模式常用于外部服务( 前端 H5 移动端 APP 等 )的认证、授权和鉴权
 * <p>
 * CLIENT_CREDENTIALS客户端模式常用于内部服务的认证
 * <p>
 * IMPLICIT 简化模式 安全原因不常用
 *
 * @author xingyu.lu
 * @date 2021/4/14 13:46
 */
public interface AuthorizedGrantTypes {
    String AUTHORIZATION_CODE = "authorization_code";
    String PASSWORD = "password";
    String CLIENT_CREDENTIALS = "client_credentials";
    //    String IMPLICIT = "implicit";
    String REFRESH_TOKEN = "refresh_token";
}
