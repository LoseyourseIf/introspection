package xingyu.lu.individual.rpc.grpc.services.grpc;

import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import xingyu.lu.individual.rpc.grpc.aop.WaveFluxGrpcMethod;
import xingyu.lu.individual.rpc.grpc.facade.builder.GrpcXReply;
import xingyu.lu.individual.rpc.grpc.facade.builder.GrpcXRequest;
import xingyu.lu.individual.rpc.grpc.facade.builder.XServiceGrpc;
import xingyu.lu.individual.rpc.grpc.utils.ProtoStuffHelper;
import xingyu.lu.individual.rpc.grpc.utils.SpringUtil;


import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author xingyu.lu
 * @create 2020-12-11 10:52
 **/
@GrpcService
@Slf4j
public class GrpcXService extends XServiceGrpc.XServiceImplBase {

    @Override
    public void grpcXCall(GrpcXRequest gReq,
                          io.grpc.stub.StreamObserver<GrpcXReply> responseObserver) {

        log.info("AppId = {} ,Method = {} ,Version = {}",
                gReq.getAppId(), gReq.getMethod(), gReq.getVersion());

        Any bizContent = gReq.getBizContent();

        Object bizData = methodRoute(gReq.getMethod(), gReq.getAppId(), bizContent);

        GrpcXReply reply = null;

        try {
            if (null != bizData) {
                byte[] uBytes = ProtoStuffHelper.serializer(bizData);
                reply = GrpcXReply.newBuilder()
                        .setIsSuccess(true)
                        .setErrorCode("")
                        .setErrorMessage("")
                        .setBizData(Any.parseFrom(uBytes))
                        .build();
            } else {
                reply = GrpcXReply.newBuilder()
                        .setIsSuccess(false)
                        .setErrorCode("12345")
                        .setErrorMessage("Error").build();
            }

        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }


    private Object methodRoute(String methodUrl, String appId, Any bizContent) {
        String[] nameArray = methodUrl.split("\\.");
        Object theService = SpringUtil.getBean(nameArray[0]);
        Optional<Method> methodOptionals;
        Method methodRef = null;
        try {
            methodOptionals = Arrays.stream(theService.getClass().getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(WaveFluxGrpcMethod.class)
                            && nameArray[1].equals(method.getName())).findFirst();

            if (methodOptionals.isPresent()) {
                methodRef = methodOptionals.get();
                Class<?> paramClazz =
                        methodRef.getAnnotation(WaveFluxGrpcMethod.class).paramType();
                byte[] bizBytes = bizContent.toByteArray();
                Object bo = ProtoStuffHelper.deserializer(bizBytes, paramClazz);
                return methodRef.invoke(theService, bo);
            } else {
                throw new NoSuchMethodException(methodUrl);
            }
        } catch (NoSuchMethodException e) {
            // 异常日志
            log.error("methodUrl = {}, appId = {}, content = {}, exceptionInfo = {}",
                    methodUrl, appId, bizContent, e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            // 异常日志
            log.error("method = {}, apiUrl = {}, appId = {}, content = {}, exceptionInfo = {}",
                    "GrpcMethodRoute", methodRef, appId, bizContent, e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
