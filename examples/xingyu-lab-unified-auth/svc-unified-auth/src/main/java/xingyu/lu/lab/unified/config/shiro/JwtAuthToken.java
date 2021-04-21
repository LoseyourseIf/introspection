package xingyu.lu.lab.unified.config.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * (╯‵□′)╯︵┻━┻
 *
 * <p>String token implements shiro auth-token</p>
 *
 * @author xingyu.lu
 * @date 19/6/10 14:03
 */
public class JwtAuthToken implements AuthenticationToken {

    private static final long serialVersionUID = 6198902331636481894L;
    private String token;

    public JwtAuthToken() {
    }

    private JwtAuthToken(String token) {
        this.token = token;
    }

    public static JwtAuthToken buildToken(String token) {
        return new JwtAuthToken(token);
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
