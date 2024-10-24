package pl.PJATK.s27440OrderService.product.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.PJATK.s27440OrderService.product.model.Product;
import pl.PJATK.s27440OrderService.product.service.ProductService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{name}")
    public ResponseEntity<Product> findProductByName(@PathVariable String name) {
        return ResponseEntity.ok(productService.findProductByName(name)
                .orElseThrow(() -> new NoSuchElementException("There is no product with such name")));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> findAllAvailableProducts() {
        return ResponseEntity.ok(productService.findAllAvailableProducts());
    }

    @PostMapping("/add")
    public ResponseEntity<Product> addProductToStorage (@RequestBody Product product) {
        Product addedProduct = productService.addProductToStorage(product);
        return ResponseEntity.ok(addedProduct);
    }

    @PatchMapping("/decrease")
    public ResponseEntity<Product> decreaseAmountInStorage(@RequestBody Product product) {
        return ResponseEntity.ok(productService.decreaseAmount(product));
    }

    @PatchMapping("/increase")
    public ResponseEntity<Product> increaseAmountInStorage(@RequestBody Product product) {
        return ResponseEntity.ok(productService.increaseAmount(product));
    }


}
