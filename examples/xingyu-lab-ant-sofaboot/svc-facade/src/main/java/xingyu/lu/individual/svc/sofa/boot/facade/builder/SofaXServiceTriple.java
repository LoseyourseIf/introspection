    package xingyu.lu.individual.svc.sofa.boot.facade.builder;

import java.util.concurrent.TimeUnit;

import static com.alipay.sofa.rpc.constant.TripleConstant.UNIQUE_ID;
import static xingyu.lu.individual.svc.sofa.boot.facade.builder.XServiceGrpc.getServiceDescriptor;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

@javax.annotation.Generated(
value = "by SofaTriple generator",
comments = "Source: GrpcXModule.proto")
public final class SofaXServiceTriple {
private SofaXServiceTriple() {}

public static class SofaXServiceStub implements IXService {

protected int timeout;

protected XServiceGrpc.XServiceBlockingStub blockingStub;
protected XServiceGrpc.XServiceFutureStub futureStub;
protected XServiceGrpc.XServiceStub stub;
protected io.grpc.CallOptions callOptions;
protected String uniqueId;

public SofaXServiceStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions, int timeout) {
this.timeout = timeout;
this.callOptions = callOptions;
uniqueId= callOptions.getOption(UNIQUE_ID);
blockingStub = XServiceGrpc.newBlockingStub(channel).build(channel, callOptions);
futureStub = XServiceGrpc.newFutureStub(channel).build(channel, callOptions);
stub = XServiceGrpc.newStub(channel).build(channel, callOptions);
}

    public GrpcXReply grpcXCall(GrpcXRequest request) {
    io.grpc.MethodDescriptor<GrpcXRequest, GrpcXReply> method = XServiceGrpc.getGrpcXCallMethod();
    if(isNotEmpty(uniqueId)){
        method = method.toBuilder().setFullMethodName(getFullNameWithUniqueId(method.getFullMethodName(), callOptions.getOption(UNIQUE_ID))).build();
    }
    return blockingUnaryCall(blockingStub.getChannel(),method,callOptions,request);
    }

    public com.google.common.util.concurrent.ListenableFuture<GrpcXReply> grpcXCallAsync(GrpcXRequest request) {
    return futureStub
    .withDeadlineAfter(timeout, TimeUnit.MILLISECONDS)
    .grpcXCall(request);
    }

    public void grpcXCall(GrpcXRequest request, io.grpc.stub.StreamObserver<GrpcXReply> responseObserver){
    stub
    .withDeadlineAfter(timeout, TimeUnit.MILLISECONDS)
    .grpcXCall(request, responseObserver);
    }

    private boolean isNotEmpty(String uniqueId) {
        return uniqueId != null && uniqueId.length() > 0;
    }

    private String getFullNameWithUniqueId(String fullMethodName, String uniqueId) {
        int i = fullMethodName.indexOf("/");
        if (i > 0 &&  isNotEmpty(uniqueId)) {
            String[] split = fullMethodName.split("/");
            fullMethodName = split[0] + "." + uniqueId + "/" + split[1];
        }
        return fullMethodName;
    }

}

public static SofaXServiceStub getSofaStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions,int timeout) {
return new SofaXServiceStub(channel, callOptions, timeout);
}

public static String getServiceName() {
  return XServiceGrpc.SERVICE_NAME;
}

public interface IXService {
    default public GrpcXReply grpcXCall(GrpcXRequest request) {
    throw new UnsupportedOperationException("No need to override this method, extend XxxImplBase and override all methods it allows.");
    }

    default public com.google.common.util.concurrent.ListenableFuture<GrpcXReply> grpcXCallAsync(GrpcXRequest request) {
    throw new UnsupportedOperationException("No need to override this method, extend XxxImplBase and override all methods it allows.");
    }

    public void grpcXCall(GrpcXRequest request, io.grpc.stub.StreamObserver<GrpcXReply> responseObserver);

}

    /**
     * <pre>
     *  service definition.
     *  定义通用Grpc调用服务
     * </pre>
     */
public static abstract class XServiceImplBase implements io.grpc.BindableService, IXService {

private IXService proxiedImpl;

public final void setProxiedImpl(IXService proxiedImpl) {
this.proxiedImpl = proxiedImpl;
}

    @Override
    public final GrpcXReply grpcXCall(GrpcXRequest request) {
    throw new UnsupportedOperationException("No need to override this method, extend XxxImplBase and override all methods it allows.");
    }

    @Override
    public final com.google.common.util.concurrent.ListenableFuture<GrpcXReply> grpcXCallAsync(GrpcXRequest request) {
    throw new UnsupportedOperationException("No need to override this method, extend XxxImplBase and override all methods it allows.");
    }

        public void grpcXCall(GrpcXRequest request,
                              io.grpc.stub.StreamObserver<GrpcXReply> responseObserver) {
        asyncUnimplementedUnaryCall(XServiceGrpc.getGrpcXCallMethod(), responseObserver);
        }

@Override public final io.grpc.ServerServiceDefinition bindService() {
return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
    .addMethod(
    XServiceGrpc.getGrpcXCallMethod(),
    asyncUnaryCall(
    new MethodHandlers<
    GrpcXRequest,
    GrpcXReply>(
    proxiedImpl, METHODID_GRPC_XCALL)))
.build();
}
}
    private static final int METHODID_GRPC_XCALL = 0;

private static final class MethodHandlers
<Req, Resp> implements
io.grpc.stub.ServerCalls.UnaryMethod
<Req, Resp>,
io.grpc.stub.ServerCalls.ServerStreamingMethod
<Req, Resp>,
io.grpc.stub.ServerCalls.ClientStreamingMethod
<Req, Resp>,
io.grpc.stub.ServerCalls.BidiStreamingMethod
<Req, Resp> {
private final IXService serviceImpl;
private final int methodId;

MethodHandlers(IXService serviceImpl, int methodId) {
this.serviceImpl = serviceImpl;
this.methodId = methodId;
}

@Override
@SuppressWarnings("unchecked")
public void invoke(Req request, io.grpc.stub.StreamObserver
<Resp> responseObserver) {
    switch (methodId) {
            case METHODID_GRPC_XCALL:
            serviceImpl.grpcXCall((GrpcXRequest) request,
            (io.grpc.stub.StreamObserver<GrpcXReply>) responseObserver);
            break;
    default:
    throw new AssertionError();
    }
    }

    @Override
    @SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver
    <Req> invoke(io.grpc.stub.StreamObserver
        <Resp> responseObserver) {
            switch (methodId) {
            default:
            throw new AssertionError();
           }
      }
  }

}
