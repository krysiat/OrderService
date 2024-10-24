package pl.PJATK.s27440OrderService.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.PJATK.s27440OrderService.product.model.Cap;
import pl.PJATK.s27440OrderService.product.options.CapName;

import java.util.Optional;

public interface CapRepository extends JpaRepository<Cap, Long> {
    Optional<Cap> findCapByCapName(CapName capName);
}
