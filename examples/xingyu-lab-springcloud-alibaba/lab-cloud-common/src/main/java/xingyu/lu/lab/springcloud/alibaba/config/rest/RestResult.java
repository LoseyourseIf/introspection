package xingyu.lu.lab.springcloud.alibaba.config.rest;

public class RestResult<T> {

    private Integer code;

    private String message;

    private T data;

    public static <T> RestResult<T> success(T data) {
        return new RestResult<>(RestResultEnum.SUCCESS.getCode(), RestResultEnum.SUCCESS.getMessage(),
                data);
    }

    public static <T> RestResult<T> success(String message, T data) {
        return new RestResult<>(RestResultEnum.SUCCESS.getCode(), message, data);
    }

    public static RestResult<?> failed() {
        return new RestResult<>(RestResultEnum.COMMON_FAILED.getCode(),
                RestResultEnum.COMMON_FAILED.getMessage(), null);
    }

    public static RestResult<?> failed(String message) {
        return new RestResult<>(RestResultEnum.COMMON_FAILED.getCode(), message, null);
    }

    public static RestResult<?> failed(IRestResult errorResult) {
        return new RestResult<>(errorResult.getCode(), errorResult.getMessage(), null);
    }

    public RestResult() {
    }

    public RestResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> RestResult<T> instance(Integer code, String message, T data) {
        RestResult<T> result = new RestResult<>();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

}
