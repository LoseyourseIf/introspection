package xingyu.lu.individual.svc.sofa.boot.provider.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.sofa.rpc.common.RpcConstants;
import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import io.grpc.stub.StreamObserver;
import lombok.Data;
import org.springframework.stereotype.Service;
import xingyu.lu.individual.svc.sofa.boot.facade.builder.GrpcXReply;
import xingyu.lu.individual.svc.sofa.boot.facade.builder.GrpcXRequest;
import xingyu.lu.individual.svc.sofa.boot.facade.builder.SofaXServiceTriple;
import xingyu.lu.individual.svc.sofa.boot.provider.service.BootSofaService;

/**
 * @author xingyu.lu
 * @create 2020-12-23 10:25
 **/
@Service
@SofaService(uniqueId = "WaveFlux-Grpc-B", interfaceType = SofaXServiceTriple.IXService.class,
        bindings = {@SofaServiceBinding(
                bindingType = RpcConstants.PROTOCOL_TYPE_TRIPLE,
                serializeType = RpcConstants.SERIALIZE_PROTOBUF)})
public class BootSofaServiceImpl extends SofaXServiceTriple.XServiceImplBase implements BootSofaService {
    @Override
    public void grpcXCall(GrpcXRequest request, StreamObserver<GrpcXReply> responseObserver) {
        System.out.println("Executing thread is " + Thread.currentThread().getName());

        String data = someService(request.getBizContent());

        GrpcXReply reply = GrpcXReply.newBuilder()
                .setBizData(data)
                .build();

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

    @Override
    public String someService(String param) {
        return JSON.toJSONString(User.getInstance());
    }

    @Data
    static class User {
        private String userName;
        private int age;
        private String gender;

        public static User getInstance() {
            User user = new User();
            user.setUserName("gRpc-Test-B");
            user.setAge(18);
            user.setGender("man");
            return user;
        }
    }
}
