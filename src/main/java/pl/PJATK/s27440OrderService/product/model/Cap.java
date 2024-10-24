package pl.PJATK.s27440OrderService.product.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.PJATK.s27440OrderService.product.options.CapName;
import pl.PJATK.s27440OrderService.product.options.Size;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Cap extends Product implements Serializable {
    @Enumerated(EnumType.STRING)
    private Size size;
    @Enumerated(EnumType.STRING)
    private CapName capName;

    public Cap(CapName capName, Size size, Integer amount) {
        super(amount);
        this.size = size;
        this.capName = capName;
        this.prize = capName.getPrize();
    }

    @Override
    public String returnProductName() {
        return this.capName.toString();
    }
}
