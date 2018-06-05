package xingyu.lu.review.db.multiple.web.advice;


import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import xingyu.lu.review.tools.result.ErrorConstants;
import xingyu.lu.review.tools.result.ResultModel;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

    //声明要捕获的异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultModel defaultExceptionHandler(HttpServletRequest request, Exception ex) {
        if (ex instanceof MissingServletRequestParameterException) {

            MissingServletRequestParameterException missingServletRequestParameterException =
                    (MissingServletRequestParameterException) ex;

            String parameterName = missingServletRequestParameterException.getParameterName();
            //            ex
            return ResultModel.customError(ErrorConstants.INVALID_PARAM_CODE, ErrorConstants
                    .INVALID_PARAM_MSG + " >> " + parameterName);
        } else {
            ex.printStackTrace();
            logger.error("服务器报错 >> {}", ExceptionUtils.getStackTrace(ex));
            return ResultModel.serverError();
        }
    }

}