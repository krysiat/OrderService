package pl.PJATK.s27440OrderService.product.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.PJATK.s27440OrderService.product.service.ProductService;

@RestControllerAdvice
public class ProductExceptionHandler {

    private final ProductService productService;

    public ProductExceptionHandler(ProductService productService) {
        this.productService = productService;
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<String> handleIllegalArgumentEception(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("The exception occured: " + ex);
    }
}
