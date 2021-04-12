package xingyu.lu.lab.grpc.server;

import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import xingyu.lu.lab.grpc.facade.builder.XGrpcReply;
import xingyu.lu.lab.grpc.facade.builder.XGrpcRequest;
import xingyu.lu.lab.grpc.facade.builder.XGrpcServiceGrpc;
import xingyu.lu.lab.grpc.facade.exception.ErrorConstants;
import xingyu.lu.lab.grpc.facade.utils.ProtoStuffHelper;
import xingyu.lu.lab.grpc.facade.utils.SpringUtil;
import xingyu.lu.lab.grpc.server.aop.XGrpcMethod;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author xingyu.lu
 * @create 2020-12-11 10:52
 **/
@GrpcService
@Slf4j
public class GrpcReceiver extends XGrpcServiceGrpc.XGrpcServiceImplBase {

    public void echoGrpc4J(XGrpcRequest gReq,
                           io.grpc.stub.StreamObserver<XGrpcReply> responseObserver) {

        log.info("[Grpc Receive Started] AppId = {} ,Method = {} ,Version = {}",
                gReq.getAppId(), gReq.getMethod(), gReq.getVersion());

        Any bizContent = gReq.getBizContent();

        XGrpcReply.Builder reply;
        try {

            Object bizData = methodRoute(gReq.getMethod(), gReq.getAppId(), bizContent);

            reply = XGrpcReply.newBuilder()
                    .setIsSuccess(true);

            if (null != bizData) {
                byte[] uBytes = ProtoStuffHelper.serializer(bizData);
                reply.setBizData(Any.parseFrom(uBytes));
            }

            log.info("[Grpc Receive Call Method Success] AppId = {} ,Method = {} ,Version = {}",
                    gReq.getAppId(), gReq.getMethod(), gReq.getVersion());

            log.info("[Grpc Receive Call Method Success] IsSuccess = {} ,BizData = {}",
                    reply.getIsSuccess(), bizData);

        } catch (NoSuchMethodException e) {

            log.error("[Grpc Receive Call FAILED] Exception = {}", e.getMessage());
            e.printStackTrace();
            reply = execFailureGrpcReply(ErrorConstants.NoSuchMethodCode,
                    ErrorConstants.NoSuchMethodMessage);

        } catch (IllegalAccessException e) {

            log.error("[Grpc Receive Call FAILED] Exception = {}", e.getMessage());
            e.printStackTrace();
            reply = execFailureGrpcReply(ErrorConstants.IllegalAccessCode,
                    ErrorConstants.IllegalAccessMessage);

        } catch (InvocationTargetException e) {

            log.error("[Grpc Receive Call FAILED] Exception = InvocationTargetException");
            e.printStackTrace();
            log.error("[Grpc Receive Call FAILED] InvocationTargetException " +
                            "Cause = {}",
                    e.getCause().getMessage());
            e.getCause().printStackTrace();
            reply = execFailureGrpcReply(ErrorConstants.InvocationTargetCode + " " +
                            ErrorConstants.InvocationTargetMessage,
                    e.getCause().getMessage());

        } catch (InvalidProtocolBufferException e) {

            log.error("[Grpc Receive Call FAILED] Exception = {}", e.getMessage());
            e.printStackTrace();
            reply = execFailureGrpcReply(ErrorConstants.InvalidProtocolBufferCode,
                    ErrorConstants.InvalidProtocolBufferMessage);

        }
        responseObserver.onNext(reply.build());
        responseObserver.onCompleted();
    }


    private Object methodRoute(String methodUrl, String appId, Any bizContent) throws
            NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        log.info("[Grpc Receive Started MethodRoute]" +
                        " AppId = {} ,Method = {}",
                appId, methodUrl);

        String[] nameArray = methodUrl.split("\\.");
        Object theService = SpringUtil.getBean(nameArray[0]);

        log.info("[Grpc Receive Started GetSvcBean]" +
                        " AppId = {} ,Method = {} ,Service = {}",
                appId, methodUrl, theService);

        Optional<Method> methodOptionals;
        methodOptionals = Arrays.stream(theService.getClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(XGrpcMethod.class)
                        && nameArray[1].equals(method.getName())).findFirst();

        if (methodOptionals.isPresent()) {

            Method methodRef = methodOptionals.get();

            log.info("[Grpc Receive MethodRef]" +
                            " AppId = {} ,Service = {} ,MethodRef = {}",
                    appId, theService, methodRef);

            if (null == bizContent) {
                return methodRef.invoke(theService);
            }

            Class<?> paramClazz =
                    methodRef.getAnnotation(XGrpcMethod.class).paramType();

            log.info("[Grpc Receive ParamClazz]" +
                            " AppId = {} ,MethodRef = {} ,ParamClazz = {}",
                    appId, methodRef, paramClazz);

            byte[] bizBytes = bizContent.toByteArray();
            Object bo = ProtoStuffHelper.deserializer(bizBytes, paramClazz);

            log.info("[Grpc Receive BizObj] AppId = {} ,MethodRef = {} ,Param = {}",
                    appId, methodRef, bo);

            return methodRef.invoke(theService, bo);

        } else {
            throw new NoSuchMethodException(methodUrl);
        }
    }

    private XGrpcReply.Builder execFailureGrpcReply(String errorCode, String errorMsg) {
        return XGrpcReply.newBuilder()
                .setIsSuccess(false)
                .setErrorCode(errorCode)
                .setErrorMessage(errorMsg);
    }
}
