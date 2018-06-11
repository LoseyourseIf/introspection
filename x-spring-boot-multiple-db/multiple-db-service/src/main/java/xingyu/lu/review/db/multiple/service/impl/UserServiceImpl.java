package xingyu.lu.review.db.multiple.service.impl;

import org.springframework.stereotype.Service;
import xingyu.lu.review.db.multiple.aop.SwitchOver;
import xingyu.lu.review.db.multiple.config.DataSourceConfigurer;
import xingyu.lu.review.db.multiple.dao.master.UserMapperExt;
import xingyu.lu.review.db.multiple.domain.master.User;
import xingyu.lu.review.db.multiple.service.UserService;
import xingyu.lu.review.tools.result.ResultModel;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xingyu.lu
 * @create 2018-06-08 13:13
 **/
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapperExt userMapperExt;

    @SwitchOver
    @Override
    public User getMasterUserById(Integer userId) {
        return userMapperExt.selectByPrimaryKey(userId);
    }

    @SwitchOver(db = DataSourceConfigurer.CLUSTER_DB_NAME)
    @Override
    public User getClusterUserById(Integer userId) {
        return userMapperExt.selectByPrimaryKey(userId);
    }

    @Override
    public ResultModel getAllUser() {
        List<User> users = new ArrayList<>();
        users.add(getMasterUserById(1));
        users.add(getClusterUserById(1));
        return ResultModel.success(users);
    }
}
