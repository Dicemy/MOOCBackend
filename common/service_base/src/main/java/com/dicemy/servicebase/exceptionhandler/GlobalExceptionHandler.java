package com.dicemy.servicebase.exceptionhandler;

import com.dicemy.commonutils.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class )
    @ResponseBody
    public R error(Exception ex) {
        ex.printStackTrace();
        return R.error().message("执行了全局异常..." + ex.getMessage());
    }
}
