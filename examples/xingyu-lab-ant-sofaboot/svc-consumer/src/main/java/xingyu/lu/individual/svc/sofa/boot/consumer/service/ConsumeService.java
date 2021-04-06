package xingyu.lu.individual.svc.sofa.boot.consumer.service;

import com.alipay.sofa.rpc.common.RpcConstants;
import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import org.springframework.stereotype.Service;
import xingyu.lu.individual.svc.sofa.boot.facade.builder.GrpcXReply;
import xingyu.lu.individual.svc.sofa.boot.facade.builder.GrpcXRequest;
import xingyu.lu.individual.svc.sofa.boot.facade.builder.SofaXServiceTriple;

/**
 * @author xingyu.lu
 * @create 2020-12-29 10:46
 **/
@Service
public class ConsumeService {

    @SofaReference(
            uniqueId = "Grpc-A",
            interfaceType = SofaXServiceTriple.IXService.class,
            binding = @SofaReferenceBinding(
                    bindingType = RpcConstants.PROTOCOL_TYPE_TRIPLE,
                    serializeType = RpcConstants.SERIALIZE_PROTOBUF))
    private SofaXServiceTriple.IXService aService;

    @SofaReference(
            uniqueId = "Grpc-B",
            interfaceType = SofaXServiceTriple.IXService.class,
            binding = @SofaReferenceBinding(
                    bindingType = RpcConstants.PROTOCOL_TYPE_TRIPLE,
                    serializeType = RpcConstants.SERIALIZE_PROTOBUF))
    private SofaXServiceTriple.IXService bService;

    @SofaReference(
            uniqueId = "Grpc-C",
            interfaceType = SofaXServiceTriple.IXService.class,
            binding = @SofaReferenceBinding(
                    bindingType = RpcConstants.PROTOCOL_TYPE_TRIPLE,
                    serializeType = RpcConstants.SERIALIZE_PROTOBUF))
    private SofaXServiceTriple.IXService cService;

    public String echoA() {
        GrpcXRequest request = GrpcXRequest.newBuilder().setAppId("sofa").build();
        GrpcXReply reply = aService.grpcXCall(request);
        return reply.getBizData();
    }

    public String echoB() {
        GrpcXRequest request = GrpcXRequest.newBuilder().setAppId("sofa").build();
        GrpcXReply reply = bService.grpcXCall(request);
        return reply.getBizData();

    }

    public String echoC() {
        GrpcXRequest request = GrpcXRequest.newBuilder().setAppId("sofa").build();
        GrpcXReply reply = cService.grpcXCall(request);
        return reply.getBizData();
    }
}
