package xingyu.lu.lab.oauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author xingyu.lu
 * @create 2021-04-12 13:59
 **/
@Configuration
public class TokenConfig {

    public static final String SIGNING_KEY = "SIGNING_KEY";

    @Bean("OAuthJwtTokenStore")
    public TokenStore tokenStore() {
        //JWT令牌存储方案
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean("OAuthJwtAccessTokenConverter")
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        //对称秘钥，资源服务器使用该秘钥来验证
        converter.setSigningKey(SIGNING_KEY);
        return converter;
    }
}
