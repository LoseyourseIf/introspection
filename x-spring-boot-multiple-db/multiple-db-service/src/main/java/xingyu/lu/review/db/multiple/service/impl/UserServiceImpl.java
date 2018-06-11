package xingyu.lu.review.db.multiple.service.impl;

import org.springframework.stereotype.Service;
import xingyu.lu.review.db.multiple.aop.SwitchOver;
import xingyu.lu.review.db.multiple.config.DataSourceConfigurer;
import xingyu.lu.review.db.multiple.dao.master.UserMapperExt;
import xingyu.lu.review.db.multiple.domain.master.User;
import xingyu.lu.review.db.multiple.service.UserService;

import javax.annotation.Resource;

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
}
