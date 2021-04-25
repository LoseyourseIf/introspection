package xingyu.lu.lab.unified.config.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * (╯‵□′)╯︵┻━┻
 *
 * <p>AuthRealm Shiro</p>
 *
 * @author xingyu.lu
 * @date 19/6/10 14:02
 */
@Slf4j
@Configuration
public class AuthRealm extends AuthorizingRealm {

//    @Lazy
//    @Resource
//    private UserService userService;
//
//
//    @Autowired
//    public void setUserService(UserService userService) {
//        this.userService = userService;
//    }

    /**
     * 支持自己的 JwtToken
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtAuthToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，
     * 例如@RequiresRoles,@RequiresPermissions
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String token = principals.toString();
//        Integer userId = JwtClaimGetter.getClaimValue(token, JwtHelper.UID, Integer.class);
//        //Token验证
//        if (null == userId) {
//            throw new AuthenticationException("Invalid Token");
//        }
//        MccUserExt userExt = userService.getUserInfo(userId);
//        if (null == userExt) {
//            throw new AuthenticationException("User Didn't Existed!");
//        }
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
//        角色

//        if (null != userExt.getRoleId() && StringUtils.isNotBlank(userExt.getRoleShiroExp())) {
//            Set<String> roles = new HashSet<>(
//                    Arrays.asList(userExt.getRoleShiroExp().split(",")));
//            simpleAuthorizationInfo.addRoles(roles);
//        }
//       权限
//        if (null != userExt.getPermissions() && userExt.getPermissions().size() != 0) {
//            Set<String> permission = new HashSet<>(userExt.getPermissions());
//            simpleAuthorizationInfo.addRoles(permission);
//        }
        return simpleAuthorizationInfo;
    }

    /**
     * 使用此方法进行登录Token验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
//        Integer userId = JwtClaimGetter.getClaimValue(token, JwtHelper.UID, Integer.class);
//        if (null == userId) {
//            throw new AuthenticationException("Invalid Token");
//        }
//        MccUserExt userExt = userService.getUserById(userId);
//        if (null == userExt) {
//            throw new AuthenticationException("User Didn't Existed!");
//        }
        return new SimpleAuthenticationInfo(token, token, "auth_realm");
    }
}
