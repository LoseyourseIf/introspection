package xingyu.lu.lab.unified.utils.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import xingyu.lu.lab.unified.utils.TwitterSnowflakeIdWorker;
import xingyu.lu.lab.unified.utils.secure.SecureUtil;

import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;

/**
 * @author xingyu.lu
 * @create 2021-04-16 11:49
 **/
@Slf4j
public class JwtHelper {

    public static DecodedJWT verifyToken(String token, String publicKey, String privateKey) throws InvalidKeySpecException, NoSuchAlgorithmException {

        RSAPublicKey pubKey = (RSAPublicKey) SecureUtil.getPublicKeyFromString(publicKey);
        RSAPrivateKey priKey = (RSAPrivateKey) SecureUtil.getPrivateKeyFromString(privateKey);

        return verifyToken(token, pubKey, priKey);
    }

    private static DecodedJWT verifyToken(String token,
                                          RSAPublicKey publicKey,
                                          RSAPrivateKey privateKey) {
        DecodedJWT jwt = null;
        Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .withAudience("A")
                    .withClaim("A", 1)
                    .withIssuer("auth0")
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
                    .withAudience("A")
                    .withClaim("A", 1)
                    .withIssuer("auth0")
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            //Invalid Signing configuration / Couldn't convert Claims.
        }
        return null;
    }

    public static void main(String[] args) {
        TwitterSnowflakeIdWorker idWorker = new TwitterSnowflakeIdWorker(1, 1);
        long id = idWorker.nextId();
        System.out.println("ID " + id);
        try {

            HashMap<String, String> keys = SecureUtil.rsaGenerateKeyPair(id);

            String pubKey = keys.get("public");

            String priKey = keys.get("private");

            System.out.println("PUBLIC " + pubKey);

            System.out.println("PRIKEY " + priKey);

            String token = JwtHelper.buildToken(pubKey, priKey);

            System.out.println("TOKEN " + token);

            DecodedJWT jwt = JwtHelper.verifyToken(token, pubKey, priKey);

            System.out.println("JWT " + jwt.getClaims());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
