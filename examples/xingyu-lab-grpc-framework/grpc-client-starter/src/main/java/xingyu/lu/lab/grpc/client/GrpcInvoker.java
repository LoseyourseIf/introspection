package xingyu.lu.lab.grpc.client;

import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import xingyu.lu.lab.grpc.facade.builder.XGrpcReply;
import xingyu.lu.lab.grpc.facade.builder.XGrpcRequest;
import xingyu.lu.lab.grpc.facade.builder.XGrpcServiceGrpc;
import xingyu.lu.lab.grpc.facade.exception.XGrpcCallException;
import xingyu.lu.lab.grpc.facade.utils.ProtoStuffHelper;

/**
 * @author xingyu.lu
 * @create 2020-12-11 15:49
 **/
@Component
@Slf4j
public class GrpcInvoker {


    @Value("${grpc.appId}")
    private String appId;
    @Value("${grpc.framework-version}")
    private String version;

    /**
     * 通用Grpc调用方法
     *
     * <p>通用Grpc调用方法</p>
     *
     * @param blockingStub 调用 GrpcServer 对应的 GrpcClient("clientName") blockingStub 实例
     * @param method       远程调用的方法 如 userService.getUserInfo
     * @param bizContent   Grpc 请求的 BO 业务对象
     * @param bizDataClazz 远程调用方法返回 BO 业务对象的 Class 如 User.Class
     * @return RespT 远程调用方法返回的 BO 业务对象
     * @author xingyu.lu
     * @date 2021/1/20 14:26
     */
    public <ReqT, RespT> RespT invokeGrpcMethod(XGrpcServiceGrpc.XGrpcServiceBlockingStub blockingStub,
                                                String method, ReqT bizContent, Class<RespT> bizDataClazz)
            throws XGrpcCallException {

        log.info("[Grpc Invoke Started] GrpcRequest Method = {},BizContent = {}",
                method, bizContent);

        XGrpcRequest.Builder grpcReqBuilder;
        XGrpcReply reply;

        try {
            byte[] uBytes;

            grpcReqBuilder = XGrpcRequest.newBuilder()
                    .setAppId(appId)
                    .setMethod(method)
                    .setSign("Credentials")
                    .setVersion(version);

            if (null != bizContent) {
                uBytes = ProtoStuffHelper.serializer(bizContent);
                grpcReqBuilder.setBizContent(Any.parseFrom(uBytes));
            }

            reply = blockingStub.echoGrpc4J(grpcReqBuilder.build());

            log.info("[Grpc Invoke Reply] GrpcRequest Method = {} , " +
                            "BizContent = {} , Success = {}",
                    method, bizContent, reply.getIsSuccess());

            if (reply.getIsSuccess()) {

                byte[] bizBytes;

                if (null != bizDataClazz) {

                    bizBytes = reply.getBizData().toByteArray();

                    RespT bizData = ProtoStuffHelper.deserializer(bizBytes, bizDataClazz);

                    log.info("[Grpc Invoke Success] GrpcXRequest Method = {} , " +
                                    "BizContent = {} , Success = {} , BizData = {} ",
                            method, bizContent, reply.getIsSuccess(), bizData);

                    return bizData;
                }
                return null;

            } else {

                log.error("[Grpc Invoke FAILED] Method = {},BizContent = {}",
                        method, bizContent);
                log.error("[Grpc Invoke FAILED] ErrorCode = {} , ErrorMessage = {}",
                        reply.getErrorCode(), reply.getErrorMessage());

                throw new XGrpcCallException(reply.getErrorMessage(),
                        new RuntimeException(reply.getErrorCode() + " " + reply.getErrorMessage()));

            }
        } catch (StatusRuntimeException | InvalidProtocolBufferException e) {

            log.error("[Grpc Invoke FAILED] Exception = {}", e.getMessage());
            e.printStackTrace();
            throw new XGrpcCallException(ExceptionUtils.getMessage(e), e);

        }
    }
}
