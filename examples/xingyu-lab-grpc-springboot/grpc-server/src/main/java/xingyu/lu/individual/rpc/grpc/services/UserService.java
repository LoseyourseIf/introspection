package xingyu.lu.individual.rpc.grpc.services;

import com.google.protobuf.Any;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xingyu.lu.individual.rpc.grpc.aop.XGrpcMethod;
import xingyu.lu.individual.rpc.grpc.module.Education;
import xingyu.lu.individual.rpc.grpc.module.User;
import xingyu.lu.individual.rpc.grpc.module.Work;
import xingyu.lu.individual.rpc.grpc.utils.ProtoStuffHelper;

import java.util.List;

/**
 * @author xingyu.lu
 * @create 2021-01-14 16:49
 **/
@Service
@Slf4j
public class UserService implements IUserService {

    @XGrpcMethod(paramType = User.class)
    public User getUserInfo(User bizContent) {

        return getUserInfo(
                bizContent.getUserName(),
                bizContent.getEdu(),
                bizContent.getWorks());
    }

    @Override
    public User getUserInfo(String userName,
                            List<Education> educations,
                            List<Work> works) {
//        log.info("bizContent deserializer start");
//        byte[] bizBytes = bizContent.toByteArray();
//        User user = ProtoStuffHelper.deserializer(bizBytes, User.class);

        // 业务逻辑

//        log.info("bizContent deserializer end user = {}", bizContent.toString());
        return User.getInstance();
    }
}
