package xingyu.lu.review.tools.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import xingyu.lu.review.tools.resource.ResourceUtil;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

/**
 * @author xingyu.lu
 * @create 2018-05-09 11:06
 **/
public class JWTClaimGetter {
    public static <T> T getClaimValue(HttpServletRequest request,
                                      String claimName,
                                      Class<T> requiredType) {
        String token = request.getHeader(ResourceUtil.getSystem("JWT.TOKEN.NAME"));
        Claims claims = JWTClaimGetter.parseExpiredJWT(token, JWTConstant.BASE64);
        T claimValue = null;
        if (claims != null) {
            claimValue = (claims.get(claimName, requiredType));
        }
        return claimValue;
    }

    /**
     * 解析token
     *
     * @param jWTString      token
     * @param base64Security key
     * @return
     */
    public static Claims parseExpiredJWT(String jWTString, String base64Security) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
                    .parseClaimsJws(jWTString).getBody();
            return claims;
        } catch (ExpiredJwtException e) {
            claims = e.getClaims();
            return claims;
        } catch (Exception e) {
            return null;
        }
    }

}
