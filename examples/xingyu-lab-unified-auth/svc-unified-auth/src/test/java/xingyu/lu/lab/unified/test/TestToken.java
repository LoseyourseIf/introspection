package xingyu.lu.lab.unified.test;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.Test;
import xingyu.lu.lab.unified.utils.id.TwitterSnowflakeIdWorker;
import xingyu.lu.lab.unified.utils.jwt.JWTClaimsData;
import xingyu.lu.lab.unified.utils.jwt.JWTConstant;
import xingyu.lu.lab.unified.utils.jwt.JWTHelper;
import xingyu.lu.lab.unified.utils.jwt.JWTStrPerms;
import xingyu.lu.lab.unified.utils.secure.SecureUtil;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author xingyu.lu
 * @create 2021-04-28 16:27
 **/
public class TestToken {

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
    @Test
    public void testToken() {
        TwitterSnowflakeIdWorker idWorker = new TwitterSnowflakeIdWorker(1, 1);
        long id = idWorker.nextId();
        System.out.println("ID " + id);
        try {

            HashMap<String, String> keys = SecureUtil.rsaGenerateKeyPair(id);

            String pubKey = keys.get("public");

            String priKey = keys.get("private");

            System.out.println("PUBLIC " + pubKey);

            System.out.println("PRIVATE " + priKey);


            JWTStrPerms strPerms = JWTStrPerms.builder()
                    .userRoles(null)
                    .sysStrPermissions(new ArrayList<>())
                    .menuStrPermissions(new ArrayList<>())
                    .btnStrPermissions(new ArrayList<>())
                    .dataStrPermissions(new ArrayList<>())
                    .build();

            JWTClaimsData claimsData = JWTClaimsData.builder()
                    .keyId(id)
                    .issuer("xingyu.lu")
                    .audience("test")
                    .tokenType(JWTConstant.ACCESS_TOKEN)
                    .unifiedAppId(id)
                    .unifiedUserId(id)
                    .unifiedUserName("xingu.lu.test")
                    .strPerms(strPerms)
                    .build();

            String token = JWTHelper.buildToken(pubKey, priKey, claimsData);

            System.out.println("TOKEN " + token);

            DecodedJWT jwt = JWTHelper.verifyToken(token, pubKey, claimsData);

            System.out.println("JWT " + jwt.getClaims());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
