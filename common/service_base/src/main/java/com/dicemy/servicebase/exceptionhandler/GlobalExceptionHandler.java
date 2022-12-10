package com.dicemy.servicebase.exceptionhandler;

import com.dicemy.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class )
    @ResponseBody
    public R error(Exception ex) {
        log.error(ex.getMessage());
        ex.printStackTrace();
        return R.error().message("执行了全局异常..." + ex.getMessage());
    }
    @ExceptionHandler(ArithmeticException.class )
    @ResponseBody
    public R error(ArithmeticException ex) {
        log.error(ex.getMessage());
        ex.printStackTrace();
        return R.error().message("执行了ArithmeticException异常..." + ex.getMessage());
    }

    @ExceptionHandler(CustomException.class )
    @ResponseBody
    public R error(CustomException ex) {
        log.error(ex.getMessage());
        ex.printStackTrace();
        return R.error().code(ex.getCode()).message(ex.getMsg());
    }
}
