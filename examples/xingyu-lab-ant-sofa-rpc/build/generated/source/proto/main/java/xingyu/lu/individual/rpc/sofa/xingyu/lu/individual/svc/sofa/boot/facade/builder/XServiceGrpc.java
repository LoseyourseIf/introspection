package xingyu.lu.individual.rpc.sofa.xingyu.lu.individual.svc.sofa.boot.facade.builder;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 * <pre>
 * service definition.
 * 定义通用Grpc调用服务
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.28.0)",
    comments = "Source: GrpcXModule.proto")
public final class XServiceGrpc {

  private XServiceGrpc() {}

  public static final String SERVICE_NAME = "xingyu.lu.individual.rpc.sofa.XService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<xingyu.lu.individual.rpc.sofa.xingyu.lu.individual.svc.sofa.boot.facade.builder.GrpcXRequest,
      xingyu.lu.individual.rpc.sofa.xingyu.lu.individual.svc.sofa.boot.facade.builder.GrpcXReply> getGrpcXCallMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GrpcXCall",
      requestType = xingyu.lu.individual.rpc.sofa.xingyu.lu.individual.svc.sofa.boot.facade.builder.GrpcXRequest.class,
      responseType = xingyu.lu.individual.rpc.sofa.xingyu.lu.individual.svc.sofa.boot.facade.builder.GrpcXReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<xingyu.lu.individual.rpc.sofa.xingyu.lu.individual.svc.sofa.boot.facade.builder.GrpcXRequest,
      xingyu.lu.individual.rpc.sofa.xingyu.lu.individual.svc.sofa.boot.facade.builder.GrpcXReply> getGrpcXCallMethod() {
    io.grpc.MethodDescriptor<xingyu.lu.individual.rpc.sofa.xingyu.lu.individual.svc.sofa.boot.facade.builder.GrpcXRequest, xingyu.lu.individual.rpc.sofa.xingyu.lu.individual.svc.sofa.boot.facade.builder.GrpcXReply> getGrpcXCallMethod;
    if ((getGrpcXCallMethod = XServiceGrpc.getGrpcXCallMethod) == null) {
      synchronized (XServiceGrpc.class) {
        if ((getGrpcXCallMethod = XServiceGrpc.getGrpcXCallMethod) == null) {
          XServiceGrpc.getGrpcXCallMethod = getGrpcXCallMethod =
              io.grpc.MethodDescriptor.<xingyu.lu.individual.rpc.sofa.xingyu.lu.individual.svc.sofa.boot.facade.builder.GrpcXRequest, xingyu.lu.individual.rpc.sofa.xingyu.lu.individual.svc.sofa.boot.facade.builder.GrpcXReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GrpcXCall"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  xingyu.lu.individual.rpc.sofa.xingyu.lu.individual.svc.sofa.boot.facade.builder.GrpcXRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  xingyu.lu.individual.rpc.sofa.xingyu.lu.individual.svc.sofa.boot.facade.builder.GrpcXReply.getDefaultInstance()))
              .setSchemaDescriptor(new XServiceMethodDescriptorSupplier("GrpcXCall"))
              .build();
        }
      }
    }
    return getGrpcXCallMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static XServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<XServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<XServiceStub>() {
        @java.lang.Override
        public XServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new XServiceStub(channel, callOptions);
        }
      };
    return XServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static XServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<XServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<XServiceBlockingStub>() {
        @java.lang.Override
        public XServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new XServiceBlockingStub(channel, callOptions);
        }
      };
    return XServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static XServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<XServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<XServiceFutureStub>() {
        @java.lang.Override
        public XServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new XServiceFutureStub(channel, callOptions);
        }
      };
    return XServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * service definition.
   * 定义通用Grpc调用服务
   * </pre>
   */
  public static abstract class XServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void grpcXCall(xingyu.lu.individual.rpc.sofa.xingyu.lu.individual.svc.sofa.boot.facade.builder.GrpcXRequest request,
        io.grpc.stub.StreamObserver<xingyu.lu.individual.rpc.sofa.xingyu.lu.individual.svc.sofa.boot.facade.builder.GrpcXReply> responseObserver) {
      asyncUnimplementedUnaryCall(getGrpcXCallMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGrpcXCallMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                xingyu.lu.individual.rpc.sofa.xingyu.lu.individual.svc.sofa.boot.facade.builder.GrpcXRequest,
                xingyu.lu.individual.rpc.sofa.xingyu.lu.individual.svc.sofa.boot.facade.builder.GrpcXReply>(
                  this, METHODID_GRPC_XCALL)))
          .build();
    }
  }

  /**
   * <pre>
   * service definition.
   * 定义通用Grpc调用服务
   * </pre>
   */
  public static final class XServiceStub extends io.grpc.stub.AbstractAsyncStub<XServiceStub> {
    private XServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected XServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new XServiceStub(channel, callOptions);
    }

    /**
     */
    public void grpcXCall(xingyu.lu.individual.rpc.sofa.xingyu.lu.individual.svc.sofa.boot.facade.builder.GrpcXRequest request,
        io.grpc.stub.StreamObserver<xingyu.lu.individual.rpc.sofa.xingyu.lu.individual.svc.sofa.boot.facade.builder.GrpcXReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGrpcXCallMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * service definition.
   * 定义通用Grpc调用服务
   * </pre>
   */
  public static final class XServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<XServiceBlockingStub> {
    private XServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected XServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new XServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public xingyu.lu.individual.rpc.sofa.xingyu.lu.individual.svc.sofa.boot.facade.builder.GrpcXReply grpcXCall(xingyu.lu.individual.rpc.sofa.xingyu.lu.individual.svc.sofa.boot.facade.builder.GrpcXRequest request) {
      return blockingUnaryCall(
          getChannel(), getGrpcXCallMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * service definition.
   * 定义通用Grpc调用服务
   * </pre>
   */
  public static final class XServiceFutureStub extends io.grpc.stub.AbstractFutureStub<XServiceFutureStub> {
    private XServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected XServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new XServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<xingyu.lu.individual.rpc.sofa.xingyu.lu.individual.svc.sofa.boot.facade.builder.GrpcXReply> grpcXCall(
        xingyu.lu.individual.rpc.sofa.xingyu.lu.individual.svc.sofa.boot.facade.builder.GrpcXRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGrpcXCallMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GRPC_XCALL = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final XServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(XServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GRPC_XCALL:
          serviceImpl.grpcXCall((xingyu.lu.individual.rpc.sofa.xingyu.lu.individual.svc.sofa.boot.facade.builder.GrpcXRequest) request,
              (io.grpc.stub.StreamObserver<xingyu.lu.individual.rpc.sofa.xingyu.lu.individual.svc.sofa.boot.facade.builder.GrpcXReply>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class XServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    XServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return xingyu.lu.individual.rpc.sofa.xingyu.lu.individual.svc.sofa.boot.facade.builder.GrpcXModule.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("XService");
    }
  }

  private static final class XServiceFileDescriptorSupplier
      extends XServiceBaseDescriptorSupplier {
    XServiceFileDescriptorSupplier() {}
  }

  private static final class XServiceMethodDescriptorSupplier
      extends XServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    XServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (XServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new XServiceFileDescriptorSupplier())
              .addMethod(getGrpcXCallMethod())
              .build();
        }
      }
    }
    return result;
  }
}
