package pl.PJATK.s27440OrderService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.PJATK.s27440OrderService.orders.model.Orders;
import pl.PJATK.s27440OrderService.orders.repository.OrderRepository;
import pl.PJATK.s27440OrderService.orders.service.OrderService;
import pl.PJATK.s27440OrderService.product.model.Cap;
import pl.PJATK.s27440OrderService.product.model.Product;
import pl.PJATK.s27440OrderService.product.model.Scarf;
import pl.PJATK.s27440OrderService.product.options.CapName;
import pl.PJATK.s27440OrderService.product.options.ScarfName;
import pl.PJATK.s27440OrderService.product.options.Size;
import pl.PJATK.s27440OrderService.product.repository.CapRepository;
import pl.PJATK.s27440OrderService.product.repository.ProductRepository;
import pl.PJATK.s27440OrderService.product.repository.ScarfRepository;
import pl.PJATK.s27440OrderService.product.service.ProductService;
import pl.PJATK.s27440OrderService.productOrder.ProductOrder;
import pl.PJATK.s27440OrderService.productOrder.ProductOrderRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OrderServiceIntegrationTest {
    @MockBean
    ProductRepository productRepository;
    @MockBean
    CapRepository capRepository;
    @MockBean
    ScarfRepository scarfRepository;
    @MockBean
    ProductOrderRepository productOrderRepository;
    @MockBean
    OrderRepository orderRepository;
    @Autowired
    ProductService productService;
    @Autowired
    private OrderService orderService;

    @Test
    void testFindProductByName_Cap() {
        Cap cap = new Cap(CapName.RoseCap, Size.SMALL, 1);
        Mockito.when(capRepository.findCapByCapName(CapName.RoseCap)).thenReturn(Optional.of(cap));

        Optional<Product> result = productService.findProductByName("RoseCap");

        assertTrue(result.isPresent());
        assertEquals(cap, result.get());
        verify(capRepository).findCapByCapName(CapName.RoseCap);
        verify(scarfRepository, Mockito.never()).findScarfByScarfName(Mockito.any());
    }

    @Test
    void testFindProductByName_Scarf() {
        Scarf scarf = new Scarf(ScarfName.RoseScarf, 1);
        when(scarfRepository.findScarfByScarfName(ScarfName.RoseScarf)).thenReturn(Optional.of(scarf));

        Optional<Product> result = productService.findProductByName("RoseScarf");

        assertTrue(result.isPresent());
        assertEquals(scarf, result.get());
        verify(scarfRepository).findScarfByScarfName(ScarfName.RoseScarf);
        verify(capRepository, Mockito.never()).findCapByCapName(Mockito.any());
    }

    @Test
    void testIncreaseAmount() {
        Cap cap = new Cap(CapName.DaisyCap, Size.SMALL, 1);
        when(capRepository.findCapByCapName(CapName.DaisyCap)).thenReturn(Optional.of(cap));

        Product result = productService.increaseAmount(cap);
        assertEquals(2, result.getAmount());
    }

    @Test
    void testDecreasingAmount() {
        Cap cap = new Cap(CapName.DaisyCap, Size.SMALL, 3);
        Cap cap1 = new Cap(CapName.DaisyCap, Size.SMALL, 1);
        when(capRepository.findCapByCapName(CapName.DaisyCap)).thenReturn(Optional.of(cap));

        Product result = productService.decreaseAmount(cap1);
        assertEquals(2, result.getAmount());
    }

    @Test
    public void testPlaceOrder() {
        //GIVEN
        Cap cap = new Cap(CapName.DaisyCap, Size.SMALL, 1);
        Scarf scarf = new Scarf(ScarfName.DaisyScarf, 1);
        Orders order = new Orders(1, "Slonimskiego1");
        ProductOrder productOrder1 = new ProductOrder(order, cap, 1);
        ProductOrder productOrder2 = new ProductOrder(order, scarf, 1);
        order.setProductsOrders(List.of(productOrder1, productOrder2));

        Mockito.when(capRepository.findCapByCapName(CapName.DaisyCap)).thenReturn(Optional.of(cap));
        Mockito.when(scarfRepository.findScarfByScarfName(ScarfName.DaisyScarf)).thenReturn(Optional.of(scarf));

        //WHEN
        Orders result = orderService.placeOrder(1, List.of(cap, scarf), "Slonimskiego1");

        // THEN
        // Verify that the order is saved
        Mockito.verify(orderRepository, Mockito.times(1)).save(Mockito.any(Orders.class));

        // Verify that productOrderRepository is saving product orders
        Mockito.verify(productOrderRepository, Mockito.times(2)).save(Mockito.any(ProductOrder.class));

        Assertions.assertNotNull(result);
    }
}
