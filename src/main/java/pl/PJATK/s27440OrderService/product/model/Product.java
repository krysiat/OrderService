package pl.PJATK.s27440OrderService.product.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.PJATK.s27440OrderService.productOrder.ProductOrder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Cap.class, name = "cap"),
        @JsonSubTypes.Type(value = Scarf.class, name = "scarf")
})
public abstract class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Double prize;
    int amount;
    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<ProductOrder> productOrder = new ArrayList<>();

    public Product(int amount) {
        this.amount = amount;
    }

    public abstract String returnProductName();

}
