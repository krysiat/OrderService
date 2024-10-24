package pl.PJATK.s27440OrderService.orders;

import pl.PJATK.s27440OrderService.orders.model.Status;
import pl.PJATK.s27440OrderService.product.model.Product;

import java.util.List;

public record OrderStatus(Status status, List<Product> products) {
}
