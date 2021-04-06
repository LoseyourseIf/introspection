package xingyu.lu.individual.rpc.grpc.service;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import xingyu.lu.individual.rpc.grpc.client.GrpcXClient;
import xingyu.lu.individual.rpc.grpc.facade.builder.XServiceGrpc;
import xingyu.lu.individual.rpc.grpc.module.Education;
import xingyu.lu.individual.rpc.grpc.module.User;
import xingyu.lu.individual.rpc.grpc.module.Work;

import javax.annotation.Resource;
import javax.sound.midi.Soundbank;
import java.util.List;

/**
 * @author xingyu.lu
 **/
@Service
public class UserDataService {

    @GrpcClient(value = "grpc-server")
    private XServiceGrpc.XServiceBlockingStub xServiceBlockingStub;

    @Resource
    private GrpcXClient grpcXClient;


    public User getUserInfo() {
        User user = new User();
        user.setUserName("张三");

        /*业务逻辑*/

        User user1 = grpcXClient.callGrpcServer(xServiceBlockingStub, user, User.class,
                "userService.getUserInfo", User.class);

        /*业务逻辑*/
        List<Education> educationList = user1.getEdu();
        List<Work> workList = user1.getWorks();

        return user1;
    }
}
