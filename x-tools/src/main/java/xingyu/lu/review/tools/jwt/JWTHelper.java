package xingyu.lu.review.tools.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

/**
 * 功能：jwt帮助类
 * 时间：2016-08-29
 */
public class JWTHelper {
    private static final String UID = "uid";
    private static final String TYP = "typ";
    private static final String JWT = "JWT";

    /**
     * 解析token
     *
     * @param jWTString      token
     * @param base64Security key
     * @return
     */
    public static Claims parseJWT(String jWTString, String base64Security) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
                    .parseClaimsJws(jWTString).getBody();
            return claims;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 生成token
     *
     * @return
     */
    public static String createJWT(Integer uid, String audience, Date nowDate, Date expiry, String base64Security) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        //生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Security);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder()
                .setHeaderParam(TYP, JWT)
                .claim(UID, uid)
                .setIssuer(JWTConstant.ISSUER)//该JWT的签发者
                .setIssuedAt(nowDate)//签发时间
                .setSubject(uid.toString())//该JWT所面向的用户
                .setAudience(audience)//接收该JWT的一方
                .setId(UUID.randomUUID().toString())//UUID 避免重发
                .signWith(signatureAlgorithm, signingKey);
        //添加Token过期时间
        if (expiry != null) {
            builder.setExpiration(expiry)//过期时间
                    .setNotBefore(nowDate);//开始时间
        }

        //生成JWT
        return builder.compact();
    }

    /**
     * 检查token
     *
     * @param token token
     * @return
     */
    public static Claims checkLoginToken(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        //token实体
        Claims claims = JWTHelper.parseJWT(token, JWTConstant.BASE64);
        if (claims != null
                && JWTConstant.ISSUER.equals(claims.getIssuer())) {
            return claims;
        }

        return null;
    }
}
