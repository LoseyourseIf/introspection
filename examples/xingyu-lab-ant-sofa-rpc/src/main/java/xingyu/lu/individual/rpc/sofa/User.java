package xingyu.lu.individual.rpc.sofa;

import lombok.Data;

/**
 * @author xingyu.lu
 * @create 2020-12-22 13:03
 **/
@Data
public class User {
    private String userName;
    private int age;
    private String gender;

    public static User getInstance() {
        User user = new User();
        user.setUserName("gRpc-Test-BizData");
        user.setAge(18);
        user.setGender("UN-KNOW");
        return user;
    }
}