package xingyu.lu.lab.grpc.facade.exception;

/**
 * @author xingyu.lu
 * @create 2021-03-02 14:40
 **/
public class XGrpcCallException extends Exception {

    public XGrpcCallException() {
        super();
    }

    public XGrpcCallException(String originExceptionMsg, Throwable cause) {
        super(originExceptionMsg, cause);
    }

}
