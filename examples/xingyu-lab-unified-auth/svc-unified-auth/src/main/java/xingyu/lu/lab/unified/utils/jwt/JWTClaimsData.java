package xingyu.lu.lab.unified.utils.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xingyu.lu
 * @create 2021-04-27 16:59
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JWTClaimsData {

    /*APP KEY ID*/
    private long keyId;
    /*JWT ISSUER*/
    private String issuer;
    /*JWT AUDIENCE*/
    private String audience;
    /*Token Type*/
    private String tokenType;
    /*APP ID*/
    private long unifiedAppId;
    /*APP USER ID*/
    private long unifiedUserId;
    /*USER*/
    private String unifiedUserName;
    /*过期时间 单位 h*/
    private int expiredHours;

    private JWTStrPerms strPerms;

}
