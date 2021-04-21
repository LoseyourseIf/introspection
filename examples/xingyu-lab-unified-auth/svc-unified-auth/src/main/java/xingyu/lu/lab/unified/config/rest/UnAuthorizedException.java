package xingyu.lu.lab.unified.config.rest;

public class UnAuthorizedException extends RuntimeException {

    public UnAuthorizedException(String msg) {
        super(msg);
    }

    public UnAuthorizedException() {
        super();
    }
    
}
