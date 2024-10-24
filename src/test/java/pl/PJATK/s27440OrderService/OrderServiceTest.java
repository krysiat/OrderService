package pl.PJATK.s27440OrderService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.PJATK.s27440OrderService.orders.model.Orders;
import pl.PJATK.s27440OrderService.orders.model.Status;
import pl.PJATK.s27440OrderService.orders.repository.OrderRepository;
import pl.PJATK.s27440OrderService.orders.service.OrderService;
import pl.PJATK.s27440OrderService.product.model.Cap;
import pl.PJATK.s27440OrderService.product.model.Scarf;
import pl.PJATK.s27440OrderService.product.options.CapName;
import pl.PJATK.s27440OrderService.product.options.ScarfName;
import pl.PJATK.s27440OrderService.product.options.Size;
import pl.PJATK.s27440OrderService.product.service.ProductService;
import pl.PJATK.s27440OrderService.productOrder.ProductOrder;
import pl.PJATK.s27440OrderService.productOrder.ProductOrderRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    ProductOrderRepository productOrderRepository;

    @Mock
    private ProductService productService;

    @InjectMocks
    private OrderService orderService;

    @Test
    public void ShouldThrowExceptionNoOrder () {
        //GIVEN
        Mockito.when(orderRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(
                () -> orderService.findOrderById(1L));
    }

    @Test
    public void testChangeOrderStatus () {
        //GIVEN
        Orders order = new Orders(1, "Slonimskiego 1");
        Mockito.when(orderRepository.findById(Mockito.any())).thenReturn(Optional.of(order));

        //WHEN
        Orders result = orderService.changeOrderStatus(1L, Status.IN_REALIZATION);

        //THEN
        assertEquals(Status.IN_REALIZATION, result.getStatus());
    }

    @Test
    public void testPlaceOrder () {
        //GIVEN
        Cap cap = new Cap(CapName.DaisyCap, Size.SMALL, 1);
        Scarf scarf = new Scarf(ScarfName.DaisyScarf, 1);
        Orders order = new Orders(1, "Slonimskiego1");
        ProductOrder productOrder1 = new ProductOrder(order, cap, 1);
        ProductOrder productOrder2 = new ProductOrder(order, scarf, 1);
        order.setProductsOrders(List.of(productOrder1, productOrder2));

        Mockito.when(productService.findProductByName("DaisyCap")).thenReturn(Optional.of(cap));
        Mockito.when(productService.findProductByName("DaisyScarf")).thenReturn(Optional.of(scarf));

        //WHEN
        Orders result = orderService.placeOrder(1, List.of(cap, scarf), "Slonimskiego1");

        // THEN
        // Verify that the order is saved
        Mockito.verify(orderRepository, Mockito.times(1)).save(Mockito.any(Orders.class));

        // Verify that productOrderRepository is saving product orders
        Mockito.verify(productOrderRepository, Mockito.times(2)).save(Mockito.any(ProductOrder.class));

        // Verify that the amount of each product in storage was decreased
        Mockito.verify(productService, Mockito.times(1)).decreaseAmount(cap);
        Mockito.verify(productService, Mockito.times(1)).decreaseAmount(scarf);

        Assertions.assertNotNull(result);
    }

    @Test
    public void shouldThrowRunTimeException () {
        //GIVEN
        Cap cap = new Cap(CapName.DaisyCap, Size.SMALL, 1);
        Cap cap1 = new Cap(CapName.DaisyCap, Size.SMALL, 2);
        Mockito.when(productService.findProductByName("DaisyCap")).thenReturn(Optional.of(cap));

        //WHEN
        //THEN
        assertThatExceptionOfType(RuntimeException.class).isThrownBy(
                () -> orderService.placeOrder(1, List.of(cap1), "abs123"));
    }

}
