package xingyu.lu.individual.rpc.sofa;

import com.alibaba.fastjson.JSON;
import io.grpc.stub.StreamObserver;
import xingyu.lu.individual.rpc.sofa.builder.GrpcXReply;
import xingyu.lu.individual.rpc.sofa.builder.GrpcXRequest;
import xingyu.lu.individual.rpc.sofa.builder.SofaXServiceTriple;

/**
 * @author xingyu.lu
 * @create 2020-12-22 17:36
 **/
public class XServiceImpl extends SofaXServiceTriple.XServiceImplBase {
    @Override
    public void grpcXCall(GrpcXRequest request, StreamObserver<GrpcXReply> responseObserver) {
        System.out.println("Executing thread is " + Thread.currentThread().getName());
        GrpcXReply reply = GrpcXReply.newBuilder()
                .setBizData(JSON.toJSONString(User.getInstance()))
                .build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
