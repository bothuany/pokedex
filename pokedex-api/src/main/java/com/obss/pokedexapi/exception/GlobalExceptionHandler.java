package com.obss.pokedexapi.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ProblemDetails> handleException(HttpServletRequest httpServletRequest, BusinessException e) {
        log.error("Exception occurred: {} {}", httpServletRequest.getRequestURI(), httpServletRequest.getMethod(), e);
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        return new ResponseEntity<>(new ProblemDetails(httpStatus.value(),e.getMessage()), httpStatus);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ProblemDetails> handleException(HttpServletRequest httpServletRequest, AlreadyExistsException e) {
        log.error("Exception occurred: {} {}", httpServletRequest.getRequestURI(), httpServletRequest.getMethod(), e);
        HttpStatus httpStatus = HttpStatus.CONFLICT;

        return new ResponseEntity<>(new ProblemDetails(httpStatus.value(),e.getMessage()), httpStatus);
    }

    @ExceptionHandler(NotExistsException.class)
    public ResponseEntity<ProblemDetails> handleException(HttpServletRequest httpServletRequest, NotExistsException e) {
        log.error("Exception occurred: {} {}", httpServletRequest.getRequestURI(), httpServletRequest.getMethod(), e);
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(new ProblemDetails(httpStatus.value(),e.getMessage()), httpStatus);
    }

    @ExceptionHandler(NotEnoughBalanceException.class)
    public ResponseEntity<ProblemDetails> handleException(HttpServletRequest httpServletRequest, NotEnoughBalanceException e) {
        log.error("Exception occurred: {} {}", httpServletRequest.getRequestURI(), httpServletRequest.getMethod(), e);
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(new ProblemDetails(httpStatus.value(),e.getMessage()), httpStatus);
    }

    @ExceptionHandler(AlreadyHaveException.class)
    public ResponseEntity<ProblemDetails> handleException(HttpServletRequest httpServletRequest, AlreadyHaveException e) {
        log.error("Exception occurred: {} {}", httpServletRequest.getRequestURI(), httpServletRequest.getMethod(), e);
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(new ProblemDetails(httpStatus.value(),e.getMessage()), httpStatus);
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetails> handleException(HttpServletRequest httpServletRequest, MethodArgumentNotValidException e) {
        log.error("Validation exception occurred: {} {}", httpServletRequest.getRequestURI(), httpServletRequest.getMethod(), e);
        ValidationProblemDetails validationProblemDetails = new ValidationProblemDetails();
        validationProblemDetails.setMessage("Validation exception occurred!");
        validationProblemDetails.setValidationErrors(new HashMap<>());

        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            validationProblemDetails.getValidationErrors().put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        validationProblemDetails.setCode(httpStatus.value());

        return new ResponseEntity<>(validationProblemDetails, httpStatus);
    }

}
