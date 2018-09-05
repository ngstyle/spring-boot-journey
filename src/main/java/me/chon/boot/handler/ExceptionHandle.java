package me.chon.boot.handler;


import me.chon.boot.aspect.HttpAspect;
import me.chon.boot.bean.HttpResult;
import me.chon.boot.exception.BootException;
import me.chon.boot.exception.ExceptionEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandle {

    private final static Logger logger = LoggerFactory.getLogger(HttpAspect.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public HttpResult handle(Exception exception) {
        HttpResult httpResult = HttpResult.fail();
        if (exception instanceof BootException) {
            BootException empoyeeException = (BootException) exception;

            httpResult.setCode(empoyeeException.getCode());
            httpResult.setMessage(empoyeeException.getMessage());
        } else {
            httpResult.setCode(ExceptionEnum.UNKNOW_ERROR.getCode());
            httpResult.setMessage(ExceptionEnum.UNKNOW_ERROR.getMessage());

            logger.error("【系统异常】 {}", exception.toString() + "\n" + getStackMessage(exception));
        }

        return httpResult;
    }

    private String getStackMessage(Exception exception) {
        StringBuilder sb = new StringBuilder();
        StackTraceElement[] stackArray = exception.getStackTrace();
        for (StackTraceElement element : stackArray) {
            sb.append(element.toString()).append("\n");
        }
        return sb.toString();
    }

}
