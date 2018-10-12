import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xingyu.lu.review.db.multiple.Application;
import xingyu.lu.review.db.multiple.dao.cluster.UserOrderMapperExt;
import xingyu.lu.review.db.multiple.dao.master.UserMapperExt;
import xingyu.lu.review.db.multiple.domain.master.User;
import xingyu.lu.review.db.multiple.service.UserService;

import javax.annotation.Resource;

/**
 * @author xingyu.lu
 * @create 2018-06-08 11:26
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TestMultipleDataSource {
    @Resource
    private UserMapperExt userMapperExt;
    @Resource
    private UserOrderMapperExt userOrderMapperExt;
    @Resource
    private UserService userService;
    @Test
    public void testSelect() {
        System.out.println(
                JSON.toJSONString(
                        userMapperExt.selectByPrimaryKey(1),
                        SerializerFeature.PrettyFormat));

        System.out.println(
                JSON.toJSONString(
                        userOrderMapperExt.selectByPrimaryKey(1),
                        SerializerFeature.PrettyFormat));
    }
    @Test
    public void testSwitchOver() {
        System.out.println(
                JSON.toJSONString(
                        userService.getMasterUserById(1),
                        SerializerFeature.PrettyFormat));

        System.out.println(
                JSON.toJSONString(
                        userService.getClusterUserById(1),
                        SerializerFeature.PrettyFormat));
    }
    @Test
    public void testGetAllUser() {
        System.out.println(
                JSON.toJSONString(
                        userService.getAllUser(),
                        SerializerFeature.PrettyFormat));
    }
}
