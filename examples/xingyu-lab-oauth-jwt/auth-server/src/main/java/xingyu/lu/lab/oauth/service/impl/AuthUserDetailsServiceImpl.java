package xingyu.lu.lab.oauth.service.impl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import xingyu.lu.lab.oauth.module.AuthUserDetails;
import xingyu.lu.lab.oauth.module.User;

import java.util.List;

/**
 * @author xingyu.lu
 * @create 2021-04-12 14:36
 **/
@Service
public class AuthUserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        if (null == userName) {
            throw new UsernameNotFoundException("用户不存在!");
        }

        User user = new User();
        user.setUserName(userName);
        user.setPassword(new BCryptPasswordEncoder().encode("password"));
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
        user.setAccountNonLocked(true);

        //简单的权限集合
        List<GrantedAuthority> authorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("admin");

        //从数据库查出的用户信息赋值给security框架中user对象

        return new AuthUserDetails(
                user.getUserName(),
                user.getPassword(),
                user.isEnabled(),
                user.isAccountNonExpired(),
                user.isCredentialsNonExpired(),
                user.isAccountNonLocked(), authorities);
    }
}
