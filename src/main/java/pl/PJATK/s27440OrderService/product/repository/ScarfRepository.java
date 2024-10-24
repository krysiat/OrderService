package pl.PJATK.s27440OrderService.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.PJATK.s27440OrderService.product.model.Scarf;
import pl.PJATK.s27440OrderService.product.options.ScarfName;

import java.util.Optional;

public interface ScarfRepository extends JpaRepository<Scarf, Long> {
    Optional<Scarf> findScarfByScarfName(ScarfName scarfName);

}
