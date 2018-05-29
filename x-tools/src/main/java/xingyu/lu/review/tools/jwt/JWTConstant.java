package xingyu.lu.review.tools.jwt;


import xingyu.lu.review.tools.resource.ResourceUtil;

/**
 * 时间：2016-08-31
 */
public interface JWTConstant {

    String BASE64 = ResourceUtil.getSystem("JWT.base64");

    String ISSUER = "scan-code-applet";

    String DEV_AUDIENCE = "DEV";

    String ADMIN_AUDIENCE = "ADMIN";

    String USER_AUDIENCE = "USER";

    String BLACKLIST_AUDIENCE = "BLACKLIST";
}
