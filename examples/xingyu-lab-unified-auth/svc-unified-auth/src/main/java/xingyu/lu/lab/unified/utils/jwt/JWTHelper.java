package xingyu.lu.lab.unified.utils.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.patterns.WithinCodeAnnotationPointcut;
import xingyu.lu.lab.unified.utils.date.DateUtil;
import xingyu.lu.lab.unified.utils.id.TwitterSnowflakeIdWorker;
import xingyu.lu.lab.unified.utils.secure.SecureUtil;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * @author xingyu.lu
 * @create 2021-04-16 11:49
 **/
@Slf4j
public class JWTHelper {


    public static DecodedJWT verifyToken(String token, String publicKey, JWTClaimsData claimsData) throws Exception {

        RSAPublicKey pubKey = (RSAPublicKey) SecureUtil.getPublicKeyFromString(publicKey);

        return verifyToken(token, pubKey, claimsData);
    }

    private static DecodedJWT verifyToken(String token, RSAPublicKey publicKey, JWTClaimsData claimsData) {
        DecodedJWT jwt = null;
        Algorithm algorithm = Algorithm.RSA256(publicKey, null);
        try {
            JWTVerifier verifier;
            if (null == claimsData) {
                verifier = JWT.require(algorithm).build(); //Reusable verifier instance
            } else {
                verifier = JWT.require(algorithm)
                        .withIssuer(claimsData.getIssuer())
                        .withAudience(claimsData.getAudience())
                        .withClaim("unifiedAppId", claimsData.getUnifiedAppId())
                        .build();
            }
            jwt = verifier.verify(token);
            return jwt;
        } catch (JWTVerificationException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String buildToken(String publicKey,
                                    String privateKey, JWTClaimsData claimsData) throws Exception {

        RSAPublicKey pubKey = (RSAPublicKey) SecureUtil.getPublicKeyFromString(publicKey);
        RSAPrivateKey priKey = (RSAPrivateKey) SecureUtil.getPrivateKeyFromString(privateKey);
        return buildToken(pubKey, priKey, claimsData);

    }

    private static String buildToken(RSAPublicKey publicKey,
                                     RSAPrivateKey privateKey, JWTClaimsData claimsData) {
        try {
            Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);

            String token;
            if (null == claimsData) {
                token = JWT.create()
                        .withExpiresAt(DateUtil.dateAdd(new Date(), Calendar.HOUR, 2))
                        .sign(algorithm);
            } else {
                token = JWT.create()
                        .withKeyId(String.valueOf(claimsData.getKeyId()))
                        .withAudience(claimsData.getAudience())
                        .withIssuer(claimsData.getIssuer())
                        .withSubject(claimsData.getTokenType())
                        .withClaim("unifiedAppId", claimsData.getUnifiedAppId())
                        .withClaim("unifiedUserId", claimsData.getUnifiedUserId())
                        .withClaim("unifiedUserName", claimsData.getUnifiedUserName())
                        .withPayload(claimsData.getStrPerms().strPermsMap())
                        .withExpiresAt(DateUtil.dateAdd(new Date(),
                                Calendar.HOUR,
                                claimsData.getExpiredHours()))
                        .sign(algorithm);
            }
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
