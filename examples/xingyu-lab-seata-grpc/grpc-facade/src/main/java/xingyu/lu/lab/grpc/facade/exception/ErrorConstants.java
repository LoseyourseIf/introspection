package xingyu.lu.lab.grpc.facade.exception;

/**
 * (╯‵□′)╯︵┻━┻
 *
 * <p>报错常量</p>
 *
 * @author xingyu.lu
 * @date 16/11/11 16:13
 */
public interface ErrorConstants {

    String SERVER_ERROR_CODE = "-5000";
    String SERVER_ERROR_MSG = "服务异常，请联系系统管理员！";

    String NoSuchMethodCode = "-7004";
    String NoSuchMethodMessage = "服务端找不到远调方法，请确认Grpc远程调用方法名及参数是否正确！";

    String IllegalAccessCode = "-7777";
    String IllegalAccessMessage = "反射获取字节码对象权限异常！";

    String InvocationTargetCode = "-7005";
    String InvocationTargetMessage = "被调用的方法的内部异常！";

    String InvalidProtocolBufferCode = "-7006";
    String InvalidProtocolBufferMessage = "ProtoStuff转换过程中，无法得知Any对象的类型！";

}
