package xingyu.lu.lab.unified.test;

import org.junit.Test;
import xingyu.lu.lab.unified.utils.random.RandomCodeUtil;
import xingyu.lu.lab.unified.utils.id.TwitterSnowflakeIdWorker;
import xingyu.lu.lab.unified.utils.secure.SecureUtil;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author xingyu.lu
 * @create 2021-04-25 13:52
 **/
public class TestSignCodeWithRSA {

    @Test
    public void testCodeSign() {

        TwitterSnowflakeIdWorker worker = new TwitterSnowflakeIdWorker(1, 1);
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
}
