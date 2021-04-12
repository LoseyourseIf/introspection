package xingyu.lu.lab.oauth.config;

/**
 * @author xingyu.lu
 * @create 2021-04-12 13:31
 **/
public interface AuthorizedGrantTypes {
    String AUTHORIZATION_CODE = "authorization_code";
    String PASSWORD = "password";
    String CLIENT_CREDENTIALS = "client_credentials";
    String IMPLICIT = "implicit";
    String REFRESH_TOKEN = "refresh_token";
}
