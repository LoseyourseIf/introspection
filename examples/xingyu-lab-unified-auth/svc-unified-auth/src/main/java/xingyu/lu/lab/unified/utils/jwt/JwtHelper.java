package xingyu.lu.lab.unified.utils.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import xingyu.lu.lab.unified.utils.date.DateUtil;
import xingyu.lu.lab.unified.utils.id.TwitterSnowflakeIdWorker;
import xingyu.lu.lab.unified.utils.secure.SecureUtil;

import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * @author xingyu.lu
 * @create 2021-04-16 11:49
 **/
@Slf4j
public class JwtHelper {

    public static DecodedJWT verifyToken(String token, String publicKey) throws InvalidKeySpecException, NoSuchAlgorithmException {

        RSAPublicKey pubKey = (RSAPublicKey) SecureUtil.getPublicKeyFromString(publicKey);

        return verifyToken(token, pubKey);
    }

    private static DecodedJWT verifyToken(String token,
                                          RSAPublicKey publicKey) {
        DecodedJWT jwt = null;
        Algorithm algorithm = Algorithm.RSA256(publicKey, null);
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .withAudience("A")
                    .withIssuer("Issuer")
                    .withClaim("A", 1)
                    .build(); //Reusable verifier instance
            jwt = verifier.verify(token);
            return jwt;
        } catch (TokenExpiredException e) {
            jwt = JWT.decode(token);
            log.info("TokenExpired JWT = {}", jwt.toString());
        } catch (JWTVerificationException exception) {
            exception.printStackTrace();
        }
        return jwt;
    }


    public static String buildToken(String publicKey,
                                    String privateKey) throws InvalidKeySpecException, NoSuchAlgorithmException {

        RSAPublicKey pubKey = (RSAPublicKey) SecureUtil.getPublicKeyFromString(publicKey);
        RSAPrivateKey priKey = (RSAPrivateKey) SecureUtil.getPrivateKeyFromString(privateKey);
        return buildToken(pubKey, priKey);

    }

    private static String buildToken(RSAPublicKey publicKey,
                                     RSAPrivateKey privateKey) {
        try {
            Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);
            return JWT.create()
                    .withKeyId("client_id")
                    .withAudience("A")
                    .withIssuer("auth0")
                    .withExpiresAt(DateUtil.dateAdd(new Date(),Calendar.HOUR,2))
                    .withClaim("A", 1)
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            //Invalid Signing configuration / Couldn't convert Claims.
        }
        return null;
    }

    /**
     * (╯‵□′)╯︵┻━┻
     * 注册 AppName 生成 AppId
     * <p>TwitterSnowflakeIdWorker 推特雪花算法生成自增ID</p>
     * 根据 AppId 生成 RSA 秘钥
     * <p>TwitterSnowflakeIdWorker 作为 RSA 秘钥 Seed </p>
     * 分配 AppId 公钥 AppKey
     * <p>RSAKeyPair pubKey priKey</p>
     * Client 使用 AppId SignWithAppKey 请求 AuthCode
     * 统一认证 根据  AppId + priKey 验签
     * 使用 AppId 重定向地址 + code + SignWithPriKey + QueryString 重定向
     * 页面收到 code + SignWithPriKey + QueryString 请求后端
     * 后端 AppKey 验签 使用 code + SignWithAppKey 请求 Token 返回到前端
     *
     * <p>JWT buildToken 公钥+私钥</p>
     * <p>JWT verifyToken 公钥</p>
     *
     * @author xingyu.lu
     * @date 2021/4/21 16:00
     */
    public static void main(String[] args) {
        TwitterSnowflakeIdWorker idWorker = new TwitterSnowflakeIdWorker(1, 1);
        long id = idWorker.nextId();
        System.out.println("ID " + id);
        try {

            HashMap<String, String> keys = SecureUtil.rsaGenerateKeyPair(id);

            String pubKey = keys.get("public");

            String priKey = keys.get("private");

            System.out.println("PUBLIC " + pubKey);

            System.out.println("PRIVATE " + priKey);

            String token = JwtHelper.buildToken(pubKey, priKey);

            System.out.println("TOKEN " + token);

            DecodedJWT jwt = JwtHelper.verifyToken(token, pubKey);

            System.out.println("JWT " + jwt.getClaims());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
