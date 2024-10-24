package pl.PJATK.s27440OrderService.orders.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.PJATK.s27440OrderService.productOrder.ProductOrder;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Orders implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer clientId;
    @OneToMany(mappedBy = "order")
    @JsonManagedReference
    private List<ProductOrder> productsOrders;
    private String address;
    @Enumerated(EnumType.STRING)
    private Status status;

    public Orders(int clientId, String address) {
        this.clientId = clientId;
        this.address = address;
        this.status = Status.NEW;
    }
}
