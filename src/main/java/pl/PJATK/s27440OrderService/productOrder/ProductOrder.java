package pl.PJATK.s27440OrderService.productOrder;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.PJATK.s27440OrderService.orders.model.Orders;
import pl.PJATK.s27440OrderService.product.model.Product;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ProductOrder {
    @EmbeddedId
    private ProductOrderKey id;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Orders order;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;
    private Integer quantity;

    public ProductOrder(Orders order, Product product, Integer quantity) {
        this.id = new ProductOrderKey(order.getId(), product.getId());
        this.order = order;
        this.product = product;
        this.quantity = quantity;
    }
}
