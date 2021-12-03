package com.kolon.biotech.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class ExceptionHandlingController implements ErrorController {

    @Override
    public String getErrorPath() {
        return null;
    }

    @GetMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if(status != null){
            int statusCode = Integer.valueOf(status.toString());

            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error/error_404";
            } else if(statusCode == HttpStatus.BAD_REQUEST.value()){
                return "error/error_400";
            } else if(statusCode == HttpStatus.UNAUTHORIZED.value()){
                return "error/error_401-1";
            } else if(statusCode == HttpStatus.FORBIDDEN.value()){
                return "error/error_403-1";
            } else if(statusCode == HttpStatus.METHOD_NOT_ALLOWED.value()){
                return "error/error_405";
            } else if(statusCode == HttpStatus.NOT_ACCEPTABLE.value()){
                return "error/error_406";
            } else if(statusCode == HttpStatus.PROXY_AUTHENTICATION_REQUIRED.value()){
                return "error/error_407";
            } else if(statusCode == HttpStatus.REQUEST_TIMEOUT.value()){
                return "error/error_408";
            } else if(statusCode == HttpStatus.CONFLICT.value()){
                return "error/error_409";
            } else if(statusCode == HttpStatus.GONE.value()){
                return "error/error_410";
            } else if(statusCode == HttpStatus.PRECONDITION_FAILED.value()){
                return "error/error_412";
            } else if(statusCode == HttpStatus.PAYLOAD_TOO_LARGE.value()){
                return "error/error_413";
            } else if(statusCode == HttpStatus.REQUEST_ENTITY_TOO_LARGE.value()){
                return "error/error_413";
            } else if(statusCode == HttpStatus.URI_TOO_LONG.value()){
                return "error/error_414";
            } else if(statusCode == HttpStatus.REQUEST_URI_TOO_LONG.value()){
                return "error/error_414";
            } else if(statusCode == HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()){
                return "error/error_415";
            } else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()){
                return "error/error_500";
            } else if(statusCode == HttpStatus.NOT_IMPLEMENTED.value()){
                return "error/error_501";
            } else if(statusCode == HttpStatus.BAD_GATEWAY.value()){
                return "error/error_502";
            } else if(statusCode == HttpStatus.SERVICE_UNAVAILABLE.value()){
                return "error/error_503";
            } else if(statusCode == HttpStatus.GATEWAY_TIMEOUT.value()){
                return "error/error_504";
            } else if(statusCode == HttpStatus.HTTP_VERSION_NOT_SUPPORTED.value()){
                return "error/error_505";
            } else {
                return "error/error_etc";
            }
        }

        return "error/error_etc";
    }
}
