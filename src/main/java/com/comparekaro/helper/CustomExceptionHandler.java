package com.comparekaro.aop;

import com.comparekaro.errors.CarNotFoundException;
import com.comparekaro.errors.CarsNotFoundException;
import com.comparekaro.errors.SuggestionNotFoundException;
import com.comparekaro.errors.ValidationException;
import com.comparekaro.models.response.ErrorResponse;
import com.comparekaro.models.response.suggestion.RespError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @InitBinder
    private void activateDirectFieldAccess(DataBinder dataBinder) {
        dataBinder.initDirectFieldAccess();
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            ValidationException ex, WebRequest request) {
        RespError error = new RespError("BAD Request", ex.getMessage());
        return new ResponseEntity<>(createErrorResponse(Collections.singletonList(error)), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(CarsNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCarsNotFoundExeception(CarsNotFoundException ex,
                                                                      WebRequest request){
        RespError error = new RespError("CARS INFO NOT FOUND", ex.getMessage());
        return new ResponseEntity<>(createErrorResponse(Collections.singletonList(error)), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SuggestionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSuggestionNotFoundException(SuggestionNotFoundException ex,
                                                                      WebRequest request){
        RespError error = new RespError("SUGGESTION NOT FOUND", ex.getMessage());
        return new ResponseEntity<>(createErrorResponse(Collections.singletonList(error)), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CarNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCarNotFoundExeception(CarNotFoundException ex,
                                                                      WebRequest request){
        RespError error = new RespError("CAR INFO NOT FOUND", ex.getMessage());
        return new ResponseEntity<>(createErrorResponse(Collections.singletonList(error)), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnknownException(Exception ex,
                                                                     WebRequest request){
        RespError error = new RespError("INTERNAL SERVER ERROR", ex.getMessage());
        return new ResponseEntity<>(createErrorResponse(Collections.singletonList(error)), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorResponse createErrorResponse(List<RespError> errors) {
        return new ErrorResponse(errors);
    }
}
