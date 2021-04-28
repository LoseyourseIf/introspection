package xingyu.lu.lab.unified.utils.jwt;

/**
 * @author xingyu.lu
 * @create 2021-04-28 15:22
 **/
public interface JWTConstant {

    String ACCESS_TOKEN = "ACCESS_TOKEN";
    String REFRESH_TOKEN = "REFRESH_TOKEN";

    int ACCESS_TOKEN_EXPIRE_HOURS = 4;
    int REFRESH_TOKEN_EXPIRE_HOURS = 72;

}
