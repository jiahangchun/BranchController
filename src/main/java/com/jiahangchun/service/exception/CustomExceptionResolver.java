//package com.jiahangchun.service.exception;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//
///**
// * @author chunchun
// * @descritpion
// * @date Created at 2018/9/25 上午9:21
// **/
//@Slf4j
//@ControllerAdvice
//public class CustomExceptionResolver {
//    /**
//     *  @ExceptionHandler({MissingServletRequestParameterException.class,TypeMismatchException.class})
//      * @param e
//     * @return
//     */
//
//    @ExceptionHandler
//    @ResponseBody
//    public String handle(Exception e) {
//        log.error("异常：{}", e.getMessage(),e);
//        return "{\"code\":20003,\"msg\":\"the param is invalid\"}";
//    }
//
//}