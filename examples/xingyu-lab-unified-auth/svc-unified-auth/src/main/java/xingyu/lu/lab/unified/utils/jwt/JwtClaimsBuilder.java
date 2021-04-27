package xingyu.lu.lab.unified.utils.jwt;

import java.util.Date;
import java.util.List;

/**
 * @author xingyu.lu
 * @create 2021-04-27 16:59
 **/
public class JwtClaimsBuilder {

    /*APP KEY ID*/
    private long keyId;
    /*JWT ISSUER*/
    private String issuer;
    /*JWT AUDIENCE*/
    private String audience;

    /*APP ID*/
    private long unifiedAppId;
    /*APP USER ID*/
    private long unifiedUserId;
    /*USER*/
    private String unifiedUserName;

    /*PRIVILEGES*/
    private List<String> userRoles;
    private List<String> sysPrivileges;
    private List<String> menuPrivileges;
    private List<String> btnPrivileges;
    private List<String> dataPrivileges;


}
