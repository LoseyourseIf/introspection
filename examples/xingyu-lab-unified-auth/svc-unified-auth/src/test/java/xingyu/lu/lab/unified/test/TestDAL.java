package xingyu.lu.lab.unified.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xingyu.lu.lab.unified.UnifiedAuthApplication;
import xingyu.lu.lab.unified.domain.UnifiedUser;
import xingyu.lu.lab.unified.mapper.UnifiedUserMapper;
import xingyu.lu.lab.unified.service.UnifiedUserService;
import xingyu.lu.lab.unified.utils.rest.Paging;
import xingyu.lu.lab.unified.utils.rest.ResultModel;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author xingyu.lu
 * @create 2021-04-23 14:02
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UnifiedAuthApplication.class)
public class TestDAL {

    @Resource
    private UnifiedUserMapper unifiedUserMapper;

    @Resource
    private UnifiedUserService unifiedUserService;


    /**
     * (╯‵□′)╯︵┻━┻
     * <p> pageHelper 分页测试 </p>
     *
     * @author xingyu.lu
     * @date 2021/4/23 15:25
     */
    @Test
    public void pageHelperTest() {
        QueryWrapper<UnifiedUser> queryWrapper =
                Wrappers.<UnifiedUser>query().isNotNull("unified_user_id");

        PageInfo<UnifiedUser> page = PageHelper.startPage(1, 5)
                .doSelectPageInfo(() -> unifiedUserMapper.selectList(queryWrapper));

        Paging<UnifiedUser> paging = Paging.obtainPaging(page, page.getList());

        System.out.println(page.getList());

        System.out.println(JSON.toJSONString(ResultModel.success(paging), SerializerFeature.PrettyFormat));
    }

    /**
     * (╯‵□′)╯︵┻━┻
     * <p> 批量插入 雪花算法ID 测试 </p>
     *
     * @author xingyu.lu
     * @date 2021/4/23 15:25
     */
    @Test
    public void batchInsert() {

        List<UnifiedUser> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            UnifiedUser u = new UnifiedUser();
            u.setUserName("user_" + i);
            list.add(u);
        }

        boolean flag = unifiedUserService.saveBatch(list);

        assertThat(flag).isTrue();

        for (UnifiedUser u : list) {
            System.out.println(u);
        }

    }
}
