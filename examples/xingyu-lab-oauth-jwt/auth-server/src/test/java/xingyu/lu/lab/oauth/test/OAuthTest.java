package xingyu.lu.lab.oauth.test;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author xingyu.lu
 * @create 2021-04-12 16:25
 **/
public class OAuthTest {

    @Test
    public void bCryptEncoderPwd() {
        String client_secret = "client1_secret";
        System.out.println(new BCryptPasswordEncoder().encode(client_secret));
    }
}
