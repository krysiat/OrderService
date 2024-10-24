package pl.PJATK.s27440OrderService.orders.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class OrderExceptionHandler {

    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException ex) {
        return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Exception occured, here is the message: " + ex);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Exception occured, here is the message: " + ex);
    }

}
