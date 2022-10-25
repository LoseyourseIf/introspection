package xingyu.lu.lab.grpc.facade.builder;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.35.0)",
    comments = "Source: GrpcProto.proto")
public final class XGrpcServiceGrpc {

  private XGrpcServiceGrpc() {}

  public static final String SERVICE_NAME = "xingyu.lu.lab.grpc.facade.XGrpcService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<XGrpcRequest,
      XGrpcReply> getEchoGrpc4JMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "echoGrpc4J",
      requestType = XGrpcRequest.class,
      responseType = XGrpcReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<XGrpcRequest,
      XGrpcReply> getEchoGrpc4JMethod() {
    io.grpc.MethodDescriptor<XGrpcRequest, XGrpcReply> getEchoGrpc4JMethod;
    if ((getEchoGrpc4JMethod = XGrpcServiceGrpc.getEchoGrpc4JMethod) == null) {
      synchronized (XGrpcServiceGrpc.class) {
        if ((getEchoGrpc4JMethod = XGrpcServiceGrpc.getEchoGrpc4JMethod) == null) {
          XGrpcServiceGrpc.getEchoGrpc4JMethod = getEchoGrpc4JMethod =
              io.grpc.MethodDescriptor.<XGrpcRequest, XGrpcReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "echoGrpc4J"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  XGrpcRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  XGrpcReply.getDefaultInstance()))
              .setSchemaDescriptor(new XGrpcServiceMethodDescriptorSupplier("echoGrpc4J"))
              .build();
        }
      }
    }
    return getEchoGrpc4JMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static XGrpcServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<XGrpcServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<XGrpcServiceStub>() {
        @Override
        public XGrpcServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new XGrpcServiceStub(channel, callOptions);
        }
      };
    return XGrpcServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static XGrpcServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<XGrpcServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<XGrpcServiceBlockingStub>() {
        @Override
        public XGrpcServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new XGrpcServiceBlockingStub(channel, callOptions);
        }
      };
    return XGrpcServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static XGrpcServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<XGrpcServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<XGrpcServiceFutureStub>() {
        @Override
        public XGrpcServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new XGrpcServiceFutureStub(channel, callOptions);
        }
      };
    return XGrpcServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class XGrpcServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void echoGrpc4J(XGrpcRequest request,
                           io.grpc.stub.StreamObserver<XGrpcReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getEchoGrpc4JMethod(), responseObserver);
    }

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getEchoGrpc4JMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                XGrpcRequest,
                XGrpcReply>(
                  this, METHODID_ECHO_GRPC4J)))
          .build();
    }
  }

  /**
   */
  public static final class XGrpcServiceStub extends io.grpc.stub.AbstractAsyncStub<XGrpcServiceStub> {
    private XGrpcServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected XGrpcServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new XGrpcServiceStub(channel, callOptions);
    }

    /**
     */
    public void echoGrpc4J(XGrpcRequest request,
                           io.grpc.stub.StreamObserver<XGrpcReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getEchoGrpc4JMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class XGrpcServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<XGrpcServiceBlockingStub> {
    private XGrpcServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected XGrpcServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new XGrpcServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public XGrpcReply echoGrpc4J(XGrpcRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getEchoGrpc4JMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class XGrpcServiceFutureStub extends io.grpc.stub.AbstractFutureStub<XGrpcServiceFutureStub> {
    private XGrpcServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected XGrpcServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new XGrpcServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<XGrpcReply> echoGrpc4J(
        XGrpcRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getEchoGrpc4JMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_ECHO_GRPC4J = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final XGrpcServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(XGrpcServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_ECHO_GRPC4J:
          serviceImpl.echoGrpc4J((XGrpcRequest) request,
              (io.grpc.stub.StreamObserver<XGrpcReply>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @Override
    @SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class XGrpcServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    XGrpcServiceBaseDescriptorSupplier() {}

    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return XGrpcProto.getDescriptor();
    }

    @Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("XGrpcService");
    }
  }

  private static final class XGrpcServiceFileDescriptorSupplier
      extends XGrpcServiceBaseDescriptorSupplier {
    XGrpcServiceFileDescriptorSupplier() {}
  }

  private static final class XGrpcServiceMethodDescriptorSupplier
      extends XGrpcServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    XGrpcServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (XGrpcServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new XGrpcServiceFileDescriptorSupplier())
              .addMethod(getEchoGrpc4JMethod())
              .build();
        }
      }
    }
    return result;
  }
}
