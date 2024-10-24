package pl.PJATK.s27440OrderService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private CapRepository capRepository;
    @Mock
    private ScarfRepository scarfRepository;
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductService productService;

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
    void shouldThrowExceptionAmountCannotBeLessThanZero() {
        Cap cap = new Cap(CapName.DaisyCap, Size.SMALL, 3);
        Cap cap1 = new Cap(CapName.DaisyCap, Size.SMALL, 4);
        when(capRepository.findCapByCapName(CapName.DaisyCap)).thenReturn(Optional.of(cap));

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
                () -> productService.decreaseAmount(cap1));
    }

    @Test
    void testAddNonExistingProduct() {
        Cap cap = new Cap(CapName.DaisyCap, Size.SMALL, 1);
        when(capRepository.findCapByCapName(CapName.DaisyCap)).thenReturn(Optional.empty());
        when(capRepository.save(cap)).thenReturn(cap);

        Product result = productService.addProductToStorage(cap);

        assertEquals(cap, result);
    }
}
