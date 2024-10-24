package pl.PJATK.s27440OrderService.product.service;

import org.springframework.stereotype.Service;
import pl.PJATK.s27440OrderService.product.model.Cap;
import pl.PJATK.s27440OrderService.product.model.Product;
import pl.PJATK.s27440OrderService.product.model.Scarf;
import pl.PJATK.s27440OrderService.product.options.CapName;
import pl.PJATK.s27440OrderService.product.options.ScarfName;
import pl.PJATK.s27440OrderService.product.repository.CapRepository;
import pl.PJATK.s27440OrderService.product.repository.ProductRepository;
import pl.PJATK.s27440OrderService.product.repository.ScarfRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final CapRepository capRepository;
    private final ScarfRepository scarfRepository;
    private final ProductRepository productRepository;

    public ProductService(CapRepository capRepository, ScarfRepository scarfRepository, ProductRepository productRepository) {
        this.capRepository = capRepository;
        this.scarfRepository = scarfRepository;
        this.productRepository = productRepository;
    }

    public Optional<Product> findProductByName(String name) {
        try {
            CapName capName = CapName.valueOf(name);
            Optional<Cap> cap = capRepository.findCapByCapName(capName);
            if (cap.isPresent()) return Optional.of(cap.get());
        } catch (IllegalArgumentException e) {
            // Not a valid CapName, continue to check for Scarf
        }

        try {
            ScarfName scarfName = ScarfName.valueOf(name);
            Optional<Scarf> scarf = scarfRepository.findScarfByScarfName(scarfName);
            if (scarf.isPresent()) return Optional.of(scarf.get());
        } catch (IllegalArgumentException e) {
            // Not a valid ScarfName, continue to return empty
        }

        return Optional.empty();
    }

    public List<Product> findAllAvailableProducts() {
        return productRepository.findAll();
    }

    public Product addProductToStorage(Product product) {
        if (!findProductByName(product.returnProductName()).isEmpty()) {
            return increaseAmount(product);
        }
        if (product instanceof Cap) {
            return capRepository.save((Cap) product);
        } else if (product instanceof Scarf) {
            return scarfRepository.save((Scarf) product);
        } else {
            throw new IllegalArgumentException("Unsupported product type");
        }
    }

    public Product decreaseAmount(Product product) {
        Optional<Product> productFound = findProductByName(product.returnProductName());
        if (productFound.isEmpty()) {
            throw new IllegalArgumentException("There is no such product in storage, you cannot decrease its amount");
        }
        Product productInStorage = productFound.get();
        if (product.getAmount() > productInStorage.getAmount()) {
            throw new IllegalArgumentException("Amount of product cannot be less than 0");
        }
        productInStorage.setAmount(productInStorage.getAmount() - product.getAmount());
        productRepository.save(productInStorage);
        return productInStorage;
    }

    public Product increaseAmount(Product product) {
        Optional<Product> productFound = findProductByName(product.returnProductName());
        if (productFound.isEmpty()) {
            throw new IllegalArgumentException("There is no such product in storage, you cannot decrease its amount");
        }
        Product productInStorage = productFound.get();
        productInStorage.setAmount(productInStorage.getAmount() + product.getAmount());
        productRepository.save(productInStorage);
        return productInStorage;
    }
}
