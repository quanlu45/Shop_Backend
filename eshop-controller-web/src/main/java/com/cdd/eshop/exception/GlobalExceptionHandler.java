package com.cdd.eshop.exception;

import com.cdd.eshop.bean.dto.ResponseDTO;
import com.cdd.eshop.common.StatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理程序
 *
 * @author quan
 * @date 2021/01/08
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 异常处理程序
     * 处理空指针的异常
     *
     * @param request   请求
     * @param exception 异常
     * @return {@link ResponseDTO}
     */
    @ExceptionHandler(value =NullPointerException.class)
    @ResponseBody
    public ResponseDTO exceptionHandler(HttpServletRequest request, NullPointerException exception){
        log.error("空指针!{}",exception.getCause().getMessage());
        return ResponseDTO.error(StatusEnum.SERVER_ERROR, exception.getCause().getMessage());
    }


    /**
     * 异常处理程序
     * 处理其他异常
     *
     * @param request   请求
     * @param exception 异常
     * @return {@link ResponseDTO}
     */
    @ExceptionHandler(value =Exception.class)
    @ResponseBody
    public ResponseDTO exceptionHandler(HttpServletRequest request, Exception exception){
        log.error("未知原因{}",exception.getCause().getMessage());
        return ResponseDTO.error(StatusEnum.SERVER_ERROR, exception.getCause().getMessage());
    }
}