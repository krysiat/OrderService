package pl.PJATK.s27440OrderService.orders.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.PJATK.s27440OrderService.orders.model.*;
import pl.PJATK.s27440OrderService.orders.service.OrderService;
import pl.PJATK.s27440OrderService.product.model.Product;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orders> findOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.findOrderById(id));
    }

    @GetMapping("status")
    public ResponseEntity<List<Orders>> findOrdersByStatus(@RequestParam(required = false) Status status) {
        return ResponseEntity.ok(orderService.findOrdersByStatus(status));
    }

    @PostMapping("/placeOrder")
    public ResponseEntity<Orders> placeOrder(@RequestBody List<Product> products) {
        return ResponseEntity.ok(orderService.placeOrder(1, products, "abc123"));
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<String> getOrderStatus (@PathVariable Long id) {
        return ResponseEntity.ok(orderService.checkOrderStatus(id).toString());
    }


    @PatchMapping("/changeStatus/{id}")
    public ResponseEntity<Orders> changeOrderStatus(@PathVariable Long id, @RequestParam Status status) {
        return ResponseEntity.ok(orderService.changeOrderStatus(id, status));
    }

}
