package cris.noroc.rest.common;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import cris.noroc.model.exceptions.DuplicateInstanceException;
import cris.noroc.model.exceptions.InstanceNotFoundException;
import cris.noroc.model.exceptions.PermissionException;
import cris.noroc.model.exceptions.MaxQuantityExceededException;

@ControllerAdvice
public class CommonControllerAdvice {
    
    private final static String INSTANCE_NOT_FOUND_EXCEPTION_CODE = "project.exceptions.InstanceNotFoundException";
    private final static String DUPLICATE_INSTANCE_EXCEPTION_CODE = "project.exceptions.DuplicateInstanceException";
    private final static String PERMISSION_EXCEPTION_CODE = "project.exceptions.PermissionException";
    private final static String MAX_QUANTITY_EXCEEDED_EXCEPTION_CODE = "project.exceptions.MaxQuantityExceededException";
    
    @Autowired
    private MessageSource messageSource;
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorsDto handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
                
        List<FieldErrorDto> fieldErrors = exception.getBindingResult().getFieldErrors().stream()
            .map(error -> new FieldErrorDto(error.getField(), error.getDefaultMessage())).collect(Collectors.toList());
        
        return new ErrorsDto(fieldErrors);
    }
    
    @ExceptionHandler(InstanceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorsDto handleInstanceNotFoundException(InstanceNotFoundException exception, Locale locale) {
        
        String nameMessage = messageSource.getMessage(exception.getName(), null, exception.getName(), locale);
        String errorMessage = messageSource.getMessage(INSTANCE_NOT_FOUND_EXCEPTION_CODE, 
                new Object[] {nameMessage, exception.getKey().toString()}, INSTANCE_NOT_FOUND_EXCEPTION_CODE, locale);

        return new ErrorsDto(errorMessage);
    }
    
    @ExceptionHandler(DuplicateInstanceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorsDto handleDuplicateInstanceException(DuplicateInstanceException exception, Locale locale) {
        
        String nameMessage = messageSource.getMessage(exception.getName(), null, exception.getName(), locale);
        String errorMessage = messageSource.getMessage(DUPLICATE_INSTANCE_EXCEPTION_CODE, 
                new Object[] {nameMessage, exception.getKey().toString()}, DUPLICATE_INSTANCE_EXCEPTION_CODE, locale);

        return new ErrorsDto(errorMessage);
    }
    
    @ExceptionHandler(PermissionException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorsDto handlePermissionException(PermissionException exception, Locale locale) {
        
        String errorMessage = messageSource.getMessage(PERMISSION_EXCEPTION_CODE, null, PERMISSION_EXCEPTION_CODE, locale);

        return new ErrorsDto(errorMessage);
    }

    @ExceptionHandler(MaxQuantityExceededException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorsDto handleMaxQuantityExceededException(MaxQuantityExceededException exception, Locale locale) {
        
        String errorMessage = messageSource.getMessage(MAX_QUANTITY_EXCEEDED_EXCEPTION_CODE, 
                new Object[] {exception.getMaxAllowedIncrement()}, MAX_QUANTITY_EXCEEDED_EXCEPTION_CODE, locale);

        return new ErrorsDto(errorMessage);
    }
}
