package xingyu.lu.review.db.multiple.service;

import xingyu.lu.review.db.multiple.domain.master.User;

/**
 * @author xingyu.lu
 * @create 2018-06-08 13:13
 **/
public interface UserService {

    User getMasterUserById(Integer userId);

    User getClusterUserById(Integer userId);
}