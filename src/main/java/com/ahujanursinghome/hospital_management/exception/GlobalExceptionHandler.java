package com.ahujanursinghome.hospital_management.exception;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice 
public class GlobalExceptionHandler {

private ResponseEntity<Map<String,Object>>buildError(
    HttpStatus status, String message,Object details){
        
Map<String,Object>body=new LinkedHashMap<>();

body.put("timestamp", System.currentTimeMillis());
body.put("status",status.value());
body.put("error",status.getReasonPhrase());
body.put("message", message);
if(details!=null){
    body.put("details",details);
}
return  ResponseEntity.status(status).body(body);
}

@ExceptionHandler(MethodArgumentNotValidException.class)


public ResponseEntity<Map<String,Object>>handleValidationException(MethodArgumentNotValidException ex){
    Map<String,String>errors=ex.getBindingResult().getFieldErrors().stream()
    .collect(Collectors.toMap(
        error->error.getField(),
        error->error.getDefaultMessage()!=null?
        error.getDefaultMessage():"Invalid value",
        (first,second)->first,LinkedHashMap::new ));

    return buildError(HttpStatus.BAD_REQUEST, "Validation failed", errors);
}

@ExceptionHandler (jakarta.validation.ConstraintViolationException.class)
public ResponseEntity<Map<String,Object>>handleContrainstViolationException(jakarta.validation.ConstraintViolationException ex){

    return buildError(HttpStatus.BAD_REQUEST, "Validation failed", ex.getMessage());
}
@ExceptionHandler (MissingServletRequestParameterException.class)
public ResponseEntity<Map<String,Object>>handleMissingParameter(MissingServletRequestParameterException ex){
    return buildError(HttpStatus.BAD_REQUEST,"Missing reuired parameter",ex.getParameterName());
}


@ExceptionHandler (MethodArgumentTypeMismatchException.class)
public ResponseEntity<Map<String,Object>>handleMethodArgumentNotMatchException(MethodArgumentTypeMismatchException ex){

    return buildError(HttpStatus.BAD_REQUEST,"Invalid parameter type",ex.getParameter());
    
}

@ExceptionHandler (Exception.class)
public ResponseEntity<Map<String,Object>>handleGenericException(Exception e){
    return buildError(HttpStatus.INTERNAL_SERVER_ERROR,"An Internal Server Error Occured",e.getMessage()); // you can also give null instead of e.getMessage() if you don't want to expose the exception message to the client
}
}
