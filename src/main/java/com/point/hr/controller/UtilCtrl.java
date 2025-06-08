package com.point.hr.controller;

import com.point.hr.api.ApiErrorResponse;
import com.point.hr.api.ApiNotFoundException;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.text.SimpleDateFormat;
import java.util.Date;

@ControllerAdvice // INFO: Make the class apply globally to all controllers
public class UtilCtrl {

    @InitBinder // Info: Configure data binding to trim whitespace from String inputs and convert empty strings to null for cleaner request handling
    public void initBinder(WebDataBinder webDataBinder) {
        StringTrimmerEditor strTrimEditor = new StringTrimmerEditor(true);

        webDataBinder.registerCustomEditor(String.class, strTrimEditor);
    }

    // INFO: Custom getter for formatted timestamp
    public static String getFormattedTimeStamp(long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(timeStamp));
    }

    // INFO: Handle 404 error for API services
    @ExceptionHandler(ApiNotFoundException.class)
    public Object handleNotFoundException(ApiNotFoundException e, WebRequest request) {

        if (isApiRequest(request)) {
            ApiErrorResponse apiError = new ApiErrorResponse();
            apiError.setStatus(HttpStatus.NOT_FOUND.value());
            apiError.setMessage(e.getMessage());
            apiError.setTimeStamp(System.currentTimeMillis());

            return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
        }

        return "error";
    }

    // INFO: Handle 400 error for API services
    @ExceptionHandler(Exception.class)
    public Object handleGenericException(Exception e, WebRequest request) {

        if (isApiRequest(request)) {
            ApiErrorResponse apiError = new ApiErrorResponse();
            apiError.setStatus(HttpStatus.BAD_REQUEST.value());
            apiError.setMessage(e.getMessage());
            apiError.setTimeStamp(System.currentTimeMillis());

            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        }

        return "error";
    }

    // INFO: Determine if the request is for an API
    private boolean isApiRequest(WebRequest request) {

        String requestUri = ((ServletWebRequest) request).getRequest().getRequestURI();
        AntPathMatcher matcher = new AntPathMatcher();
        boolean isApiPath = requestUri != null && matcher.match("/api/**", requestUri);

        String acceptHeader = request.getHeader("Accept");
        boolean isJsonRequest = acceptHeader != null && acceptHeader.equals("application/json");

        return isApiPath || isJsonRequest;
    }
}
