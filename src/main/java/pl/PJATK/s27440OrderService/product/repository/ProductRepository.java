package pl.PJATK.s27440OrderService.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.PJATK.s27440OrderService.product.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
