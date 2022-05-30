package yzh.lifediary.common.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import yzh.lifediary.common.lang.Message;


@ControllerAdvice
public class GlobalExceptionHandler {


     // 404处理
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Object notFountHandler(){
        return new Message().setData("").setCode(-1).setMessage("找不到路径");
    }
    
}