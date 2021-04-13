package xingyu.lu.lab.oauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import javax.annotation.Resource;

/**
 * @author xingyu.lu
 * @create 2021-04-12 13:21
 *
 * <p>
 * 框架默认Endpoint
 * </p>
 * <p>
 * /oauth/authorize：授权端点。
 * /oauth/token：令牌端点。
 * /oauth/confirm_access：用户确认授权提交端点。
 * /oauth/error：授权服务错误信息端点。
 * /oauth/check_token：用于资源服务访问的令牌解析端点。
 * /oauth/token_key：提供公有密匙的端点，如果你使用JWT令牌的话。
 */
@Configuration
@EnableAuthorizationServer
public class AuthServerConfigAdapter extends AuthorizationServerConfigurerAdapter {

    /**
     * Token储存
     */
    @Resource(name = "OAuthTokenStore", type = TokenStore.class)
    private TokenStore tokenStore;

    @Bean("OAuthTokenStore")
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

    @Resource
    private ClientDetailsService clientDetailsService;

    /**
     * 认证管理器
     */
    @Resource(name = "OAuthenticationManager", type = AuthenticationManager.class)
    private AuthenticationManager authenticationManager;

    /**
     * 授权码模式服务
     */
    @Resource(name = "OAuthorizationCodeServices", type = AuthorizationCodeServices.class)
    private AuthorizationCodeServices authorizationCodeServices;

    @Bean("OAuthorizationCodeServices")
    public AuthorizationCodeServices authorizationCodeServices() {
        return new InMemoryAuthorizationCodeServices();
    }

    /**
     * 令牌管理服务
     */
    @Bean
    public AuthorizationServerTokenServices tokenService() {
        DefaultTokenServices service = new DefaultTokenServices();
        //客户端详情服务
        service.setClientDetailsService(clientDetailsService);
        //支持刷新令牌
        service.setSupportRefreshToken(true);
        //令牌存储策略
        service.setTokenStore(tokenStore);
        // 令牌默认有效期2小时
        service.setAccessTokenValiditySeconds(7200);
        // 刷新令牌默认有效期3天
        service.setRefreshTokenValiditySeconds(259200);
        return service;
    }

    /**
     * 令牌访问端点配置
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                //认证管理器
                .authenticationManager(authenticationManager)
                //授权码服务
                .authorizationCodeServices(authorizationCodeServices)
                //令牌管理服务
                .tokenServices(tokenService())
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);
    }

    /**
     * 令牌端点的安全约束
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security
                //oauth/token_key是公开（提供公有密匙的端点，使用JWT非对称令牌加密）
                .tokenKeyAccess("permitAll()")
                //oauth/check_token公开(用于资源服务访问的令牌解析端点)
                .checkTokenAccess("permitAll()")
                //表单认证（申请令牌）
                .allowFormAuthenticationForClients();
    }

    /**
     * ClientDetails 配置
     * AUTHORIZATION_CODE 授权码模式常用于社会化登录和 SSO
     * PASSWORD 密码模式常用于外部服务的认证、授权和鉴权
     * CLIENT_CREDENTIALS 客户端模式常用于内部服务的认证
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                // 使用in‐memory存储
                .inMemory()
                // client_id
                .withClient("client1")
                // 客户端密钥
                .secret(new BCryptPasswordEncoder().encode("client1_secret"))
                // 客户端访问的资源列表
                .resourceIds("resource1")
                // 该client允许的授权类型
                .authorizedGrantTypes(
                        AuthorizedGrantTypes.AUTHORIZATION_CODE,
                        AuthorizedGrantTypes.PASSWORD,
                        AuthorizedGrantTypes.CLIENT_CREDENTIALS,
                        AuthorizedGrantTypes.REFRESH_TOKEN)
                // 允许的授权范围
                .scopes("all")
                // true  允许跳转到授权页面
                // false 静默授权 需用户确认
                .autoApprove(true)
                // 回调地址
                .redirectUris("https://www.baidu.com");
    }

}
