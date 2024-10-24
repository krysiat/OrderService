package pl.PJATK.s27440OrderService.orders.service;

import org.springframework.stereotype.Service;
import pl.PJATK.s27440OrderService.orders.model.Orders;
import pl.PJATK.s27440OrderService.orders.model.Status;
import pl.PJATK.s27440OrderService.orders.repository.OrderRepository;
import pl.PJATK.s27440OrderService.product.model.Product;
import pl.PJATK.s27440OrderService.product.service.ProductService;
import pl.PJATK.s27440OrderService.productOrder.ProductOrder;
import pl.PJATK.s27440OrderService.productOrder.ProductOrderRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductOrderRepository productOrderRepository;
    private final ProductService productService;

    public OrderService(OrderRepository orderRepository,
                        ProductOrderRepository productOrderRepository, ProductService productService) {
        this.orderRepository = orderRepository;
        this.productOrderRepository = productOrderRepository;
        this.productService = productService;
    }

    public Orders placeOrder(int clientId, List<Product> products, String address) {
        try {
            canBeOrdered(products);
            // Success in placing order
            Orders order = new Orders(clientId, address);
            orderRepository.save(order);

            // decrease amounts of products in storage and adding product-order to base
            for (Product product : products) {
                Product productInStorage = productService.findProductByName(product.returnProductName()).get();
                productOrderRepository.save(new ProductOrder(order, productInStorage, product.getAmount()));
                productService.decreaseAmount(product);
            }
            return order;
        } catch (RuntimeException e) {
            throw e;
        }
    }

    private void canBeOrdered(List<Product> products) {

        for (Product product : products) {
            // check if sb wants to order at least 1 product
            if (product.getAmount() < 1) {
                throw new IllegalArgumentException("You need to order at least 1 product");
            }
            // check if the product is in storage
            Product productInStorage = productService.findProductByName(product.returnProductName())
                    .orElseThrow(() -> new NoSuchElementException("There is no such product in storage"));
            // check if there is enough amount of product to order
            if (product.getAmount() > productInStorage.getAmount()) {
                throw new RuntimeException("We only have " + productInStorage.getAmount() + " of " +
                        product.returnProductName());
            }
        }
    }

    public Status checkOrderStatus(Long orderId) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchElementException("There is no order with such id"));
        return order.getStatus();
    }

    public Orders findOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("There is no order with such id"));
    }

    public List<Orders> findOrdersByStatus(Status status) {
        if (status != null) {
            return orderRepository.findAllByStatus(status);
        } else {
            return orderRepository.findAll();
        }
    }

    public Orders changeOrderStatus(Long id, Status status) {
        Orders orderInStorage = orderRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("There is no order with such id"));
        orderInStorage.setStatus(status);
        orderRepository.save(orderInStorage);
        return orderInStorage;
    }
}
