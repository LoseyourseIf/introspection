package xingyu.lu.individual.rpc.grpc.services;

import xingyu.lu.individual.rpc.grpc.module.Education;
import xingyu.lu.individual.rpc.grpc.module.User;
import xingyu.lu.individual.rpc.grpc.module.Work;

import java.util.List;

/**
 * @author xingyu.lu
 * @create 2021-01-21 11:36
 **/
public interface IUserService {

    User getUserInfo(String userName, List<Education> educations, List<Work> works);

}
