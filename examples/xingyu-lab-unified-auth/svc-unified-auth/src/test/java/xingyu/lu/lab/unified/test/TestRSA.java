package xingyu.lu.lab.unified.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xingyu.lu.lab.unified.UnifiedAuthApplication;
import xingyu.lu.lab.unified.domain.UnifiedAppKeys;
import xingyu.lu.lab.unified.service.UnifiedAppKeysService;
import xingyu.lu.lab.unified.utils.random.RandomCodeUtil;
import xingyu.lu.lab.unified.utils.id.TwitterSnowflakeIdWorker;
import xingyu.lu.lab.unified.utils.secure.SecureUtil;

import javax.annotation.Resource;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author xingyu.lu
 * @create 2021-04-25 13:52
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UnifiedAuthApplication.class)
public class TestRSA {

    @Resource
    private UnifiedAppKeysService unifiedAppKeysService;

    @Test
    public void testRSAEncryptDecrypt() {

        TwitterSnowflakeIdWorker worker = new TwitterSnowflakeIdWorker(0, 0);
        long id = worker.nextId();
        System.out.println("ID|" + id);

        try {
            HashMap<String, String> keys = SecureUtil.rsaGenerateKeyPair(id);
            String pubKey = keys.get("public");
            String priKey = keys.get("private");

            System.out.println("PUB|" + pubKey);
            System.out.println("PRI|" + priKey);

            String code = RandomCodeUtil.getRandomString(RandomCodeUtil.RANDOM_CODE_32);
            System.out.println("CODE|" + code);

            String encryptByPri = SecureUtil.rsaEncryptByPrivate(priKey, code);
            System.out.println("EN_PRI|" + encryptByPri);

            String decryptByPub = SecureUtil.rsaDecryptByPublic(pubKey, encryptByPri);
            System.out.println("DESC_PUB|" + decryptByPub);

            assertThat(decryptByPub).isEqualTo(code);

            String encryptByPub = SecureUtil.rsaEncryptByPublic(pubKey, code);
            System.out.println("EN_PUB|" + encryptByPub);

            String decryptByPri = SecureUtil.rsaDecryptByPrivate(priKey, encryptByPub);
            System.out.println("DESC_PRI|" + decryptByPri);

            assertThat(decryptByPri).isEqualTo(code);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testSignSHA256WithRSA() throws Exception {
        long appId = 8909507396767744L;

        String queryString = "?A=1&B=2&code=shPIX3EJnKJkJ6Am1BQPkO0Odf3Qy1WE&unifiedUserId=8192584837107712";

        UnifiedAppKeys unifiedAppKeys = unifiedAppKeysService.getUnifiedAppKeysById(appId);

        String signStr = SecureUtil.signSHA256withRSA(unifiedAppKeys.getAppPriKey(), queryString);

        boolean flag = SecureUtil.verifySHA256withRSA(unifiedAppKeys.getAppPubKey(), queryString,
                signStr);

        assertThat(flag).isTrue();

    }

}
