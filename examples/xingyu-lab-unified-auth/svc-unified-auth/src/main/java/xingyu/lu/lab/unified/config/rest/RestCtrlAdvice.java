package xingyu.lu.lab.unified.config.rest;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xingyu.lu.lab.unified.utils.rest.ResultModel;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class RestCtrlAdvice {

    // 捕捉shiro的异常
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public ResultModel handleShiroException(ShiroException e) {
        e.printStackTrace();
        log.error(ExceptionUtils.getMessage(e));
        return ResultModel.commonError("Un Authorized!");
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnAuthorizedException.class)
    public ResultModel handleUnauthorizedException(UnAuthorizedException e) {
        e.printStackTrace();
        log.error(ExceptionUtils.getMessage(e));
        return ResultModel.commonError("Un Authorized!");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResultModel handleIllegalArgumentException(IllegalArgumentException e) {
        e.printStackTrace();
        return ResultModel.paramError();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidFormatException.class)
    public ResultModel handleInvalidFormatException(InvalidFormatException e) {
        e.printStackTrace();
        return ResultModel.paramError();
    }

    // 捕捉其他所有异常
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ResultModel handleExceptions(HttpServletRequest request, Throwable ex) {
        ex.printStackTrace();
        log.error(ExceptionUtils.getMessage(ex));
        return ResultModel.customError(String.valueOf(getStatus(request).value()), ex.getMessage());
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}

