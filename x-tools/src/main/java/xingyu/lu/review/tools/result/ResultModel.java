package xingyu.lu.review.tools.result;


import java.io.Serializable;

/**
 * (╯‵□′)╯︵┻━┻
 * Restful 统一返回对象
 *
 * @author xingyu.lu
 * @date 18/5/12 15:06
 */
public class ResultModel<T> implements Serializable {

    private static final long serialVersionUID = 7403746679410775256L;
    private boolean isSuccess;
    private T returnValue;
    private String errorCode;
    private String errorMessage;

    public ResultModel(T returnValue) {
        this.returnValue = returnValue;
        this.isSuccess = true;
    }

    public ResultModel(String errorCode, String errorMessage) {
        this.isSuccess = false;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public ResultModel() {
        this.isSuccess = true;
    }

    /**
     * (╯‵□′)╯︵┻━┻
     * 成功返回
     *
     * @author xingyu.lu
     * @date 16/5/12 15:05
     */
    public static <T> ResultModel<T> success(T returnValue) {
        return new ResultModel<>(returnValue);
    }

    /**
     * (╯‵□′)╯︵┻━┻
     * 失败返回
     *
     * @author xingyu.lu
     * @date 16/5/12 15:05
     */
    public static ResultModel failure(String errorCode, String errorMessage) {
        return new ResultModel(errorCode, errorMessage);
    }

    /**
     * 无效sign
     */
    public static ResultModel signError() {

        return new ResultModel(ErrorConstants.INVALID_SIGN_CODE, ErrorConstants.INVALID_SIGN_MSG);
    }

    /**
     * 无效参数
     */
    public static ResultModel paramError() {

        return new ResultModel(ErrorConstants.INVALID_PARAM_CODE, ErrorConstants.INVALID_PARAM_MSG);
    }

    /**
     * 权限不足
     */
    public static ResultModel perssionError() {

        return new ResultModel(ErrorConstants.PERMISSION_DENIED_CODE, ErrorConstants.PERMISSION_DENIED_MSG);
    }

    /**
     * 定制错误信息
     */
    public static ResultModel customError(String errorCode, String errorMessage) {
        return new ResultModel(errorCode, errorMessage);
    }

    /**
     * 服务器异常
     */
    public static ResultModel serverError() {

        return new ResultModel(ErrorConstants.SERVER_ERROR_CODE, ErrorConstants.SERVER_ERROR_MSG);
    }

    /**
     * 通用异常,用于业务错误
     */
    public static ResultModel commonError(String msg) {

        return new ResultModel(ErrorConstants.COMMON_ERROR_CODE, msg);
    }

    /**
     * 数据库操作失败
     */
    public static ResultModel dataBaseError() {

        return new ResultModel(ErrorConstants.DATA_BASE_CODE, ErrorConstants.DATA_BASE_MSG);
    }

    /**
     * Token失效
     */
    public static ResultModel tokenError() {

        return new ResultModel(ErrorConstants.INVALID_TOKEN_CODE, ErrorConstants.INVALID_TOKEN_MSG);
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public T getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(T returnValue) {
        this.returnValue = returnValue;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


}