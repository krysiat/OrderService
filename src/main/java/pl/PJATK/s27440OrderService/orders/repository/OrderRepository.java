package pl.PJATK.s27440OrderService.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.PJATK.s27440OrderService.orders.model.*;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {

    public List<Orders> findAllByStatus(Status status);
}
