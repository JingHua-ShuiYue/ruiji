package com.example.ruiji.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice(annotations = {RestController.class})
@RestController
@Slf4j
public class MyExceptionHandler {
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    private R<String> mySQLIntegrityConstraintViolationExceptionHandler(SQLIntegrityConstraintViolationException e){
        log.error(e.getMessage());
        if (e.getMessage().contains("Duplicate entry")){
            return R.error("用户已存在");
        }
        return R.error("未知错误");
    }
}
