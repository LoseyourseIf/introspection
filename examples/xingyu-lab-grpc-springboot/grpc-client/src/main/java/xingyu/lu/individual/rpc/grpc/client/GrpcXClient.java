package xingyu.lu.individual.rpc.grpc.client;

import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import xingyu.lu.individual.rpc.grpc.facade.builder.GrpcXReply;
import xingyu.lu.individual.rpc.grpc.facade.builder.GrpcXRequest;
import xingyu.lu.individual.rpc.grpc.facade.builder.XServiceGrpc;
import xingyu.lu.individual.rpc.grpc.utils.ProtoStuffHelper;

/**
 * @author xingyu.lu
 * @create 2020-12-11 15:49
 **/
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class GrpcXClient {


    /**
     * 通用Grpc调用方法
     *
     * <p>通用Grpc调用方法</p>
     *
     * @param xServiceBlockingStub 调用 GrpcServer 对应的 GrpcClient("clientName") 实例
     * @param bizContent           Grpc 请求的 BO 业务对象
     * @param bizContentClazz      Grpc 请求的 BO 业务对象的 Class 如 User.Class
     * @param method               远程调用的方法 如 userService.getUserInfo
     * @param bizDataClazz         远程调用方法返回 BO 业务对象的 Class 如 User.Class
     *
     * @return RespT 远程调用方法返回的 BO 业务对象
     *
     * @author xingyu.lu
     * @date 2021/1/20 14:26
     */
    public <ReqT, RespT> RespT callGrpcServer(XServiceGrpc.XServiceBlockingStub xServiceBlockingStub,
                                              ReqT bizContent, Class<ReqT> bizContentClazz,
                                              String method, Class<RespT> bizDataClazz) {

        log.info("GrpcXRequest method = {},bizContentClazz = {},bizContent = {}",
                method, bizContentClazz.getName(), bizContent.toString());
        GrpcXRequest.Builder grpcXReqBuilder = null;
        try {
            byte[] uBytes = ProtoStuffHelper.serializer(bizContent);
            grpcXReqBuilder = GrpcXRequest.newBuilder()
                    .setAppId("WaveFlux.OS.Grpc-Client")
                    .setMethod(method)
                    .setSign("secret")
                    .setVersion("0.0.1")
                    .setBizContent(Any.parseFrom(uBytes));

            GrpcXReply reply = xServiceBlockingStub
                    .grpcXCall(grpcXReqBuilder.build());

            if (reply.getIsSuccess()) {
                byte[] bizBytes = reply.getBizData().toByteArray();
                return ProtoStuffHelper.deserializer(bizBytes, bizDataClazz);
            } else {
                return null;
            }
        } catch (final StatusRuntimeException e) {
            log.error("FAILED with " + e.getStatus().getCode().name());
            e.printStackTrace();
        } catch (InvalidProtocolBufferException e) {
            log.error("ProtoStuffHelper InvalidProtocolBufferException" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
