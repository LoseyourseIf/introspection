// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: GrpcProto.proto

package xingyu.lu.lab.grpc.facade.builder;

public interface XGrpcReplyOrBuilder extends
    // @@protoc_insertion_point(interface_extends:xingyu.lu.lab.grpc.facade.XGrpcReply)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>bool isSuccess = 1;</code>
   * @return The isSuccess.
   */
  boolean getIsSuccess();

  /**
   * <code>string errorCode = 2;</code>
   * @return The errorCode.
   */
  String getErrorCode();
  /**
   * <code>string errorCode = 2;</code>
   * @return The bytes for errorCode.
   */
  com.google.protobuf.ByteString
      getErrorCodeBytes();

  /**
   * <code>string errorMessage = 3;</code>
   * @return The errorMessage.
   */
  String getErrorMessage();
  /**
   * <code>string errorMessage = 3;</code>
   * @return The bytes for errorMessage.
   */
  com.google.protobuf.ByteString
      getErrorMessageBytes();

  /**
   * <code>.google.protobuf.Any bizData = 4;</code>
   * @return Whether the bizData field is set.
   */
  boolean hasBizData();
  /**
   * <code>.google.protobuf.Any bizData = 4;</code>
   * @return The bizData.
   */
  com.google.protobuf.Any getBizData();
  /**
   * <code>.google.protobuf.Any bizData = 4;</code>
   */
  com.google.protobuf.AnyOrBuilder getBizDataOrBuilder();
}
