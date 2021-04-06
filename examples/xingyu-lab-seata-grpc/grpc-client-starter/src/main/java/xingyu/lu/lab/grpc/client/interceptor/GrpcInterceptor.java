package xingyu.lu.lab.grpc.client.interceptor;

import io.grpc.*;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lxy
 */
@Slf4j
public class GrpcInterceptor implements ClientInterceptor {

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method,
                                                               CallOptions callOptions, Channel next) {

        log.info(method.getServiceName());
        log.info(method.getFullMethodName());
        log.info(method.getRequestMarshaller().toString());

        log.info(callOptions.toString());
        log.info(next.toString());

        return next.newCall(method, callOptions);
    }

}
