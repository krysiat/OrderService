package pl.PJATK.s27440OrderService.product.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.PJATK.s27440OrderService.product.options.ScarfName;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Scarf extends Product implements Serializable {
    @Enumerated(EnumType.STRING)
    private ScarfName scarfName;

    public Scarf(ScarfName scarfName, Integer amountInStorage) {
        super(amountInStorage);
        this.scarfName = scarfName;
        this.prize = scarfName.getPrize();
    }
    @Override
    public String returnProductName() {
        return this.scarfName.toString();
    }
}
