package xingyu.lu.individual.rpc.grpc.interceptor;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xingyu.lu
 * @create 2020-12-04 15:45
 **/
@Slf4j
public class GrpcInterceptor implements ServerInterceptor {
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call,
                                                                 Metadata headers,
                                                                 ServerCallHandler<ReqT, RespT> next) {

        log.info(call.getMethodDescriptor().getFullMethodName());

        log.info(call.getAttributes().toString());

        return next.startCall(call, headers);

    }
}
