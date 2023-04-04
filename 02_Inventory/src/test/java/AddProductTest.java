import inventory.model.InhousePart;
import inventory.model.Part;
import inventory.model.Product;
import inventory.repository.InventoryRepository;
import inventory.service.InventoryService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AddProductTest {
    private InventoryService service;
    private InventoryRepository repo;
    private final Part mockPart = new InhousePart(1, "test", 0.0, 1, 1, 1, 1);
    ObservableList<Part> addParts = FXCollections.observableArrayList();

    @BeforeAll
    public void setup() {
        repo = Mockito.mock(InventoryRepository.class);
        service = new InventoryService(repo);
    }

    @BeforeEach
    public void setupEach() {
        addParts.clear();
        addParts.add(mockPart);
    }


    @Tag("ECP")
    @ParameterizedTest
    @CsvSource({
            "Clock,7.34,",
            ",7.34,A name has not been entered. ",
            "Clock,0,The price must be greater than $0. "
    })
    @DisplayName("Add products with ECP")
    public void addProductECP(String name, double price, String expectedErrorMessage) {
        // arrange
        int inStock = 1;
        int min = 1;
        int max = 1;
        String productName;
        productName = Objects.requireNonNullElse(name, "");
        Product mockProduct = new Product(1, productName, price, inStock, min, max, null);

        // act
        Mockito.doNothing().when(repo).addProduct(any(Product.class));
        Mockito.doReturn(mockProduct).when(repo).lookupProduct(name);

        if (expectedErrorMessage == null || expectedErrorMessage.equals("")) {
            service.addProduct(name, price, inStock, min, max, addParts);
        }
        Product product = service.lookupProduct(name);

        // assert
        if (expectedErrorMessage != null && !expectedErrorMessage.equals("")) {
            Exception e = Assertions.assertThrows(IllegalArgumentException.class, () -> {
                service.addProduct(productName, price, inStock, min, max, addParts);
            });

            Assertions.assertTrue(e.getMessage().contains(expectedErrorMessage));
            return;
        }
        assertEquals(name, product.getName());
    }

    private static Object[][] provideValidData() {
        String maxString = "a".repeat(255);

        // create a max double
        double maxDouble = Double.MAX_VALUE;

        return new Object[][]{
                {"M", 7.34},
                {maxString, 7.34},
                {"M", maxDouble}
        };
    }

    private static Object[][] provideInvalidData() {
        return new Object[][]{
                {"", 7.34, "A name has not been entered. "},
                {"M", 0, "The price must be greater than $0. "}
        };
    }

    @Tag("BVA")
    @ParameterizedTest
    @MethodSource("provideValidData")
    @DisplayName("Add valid product with BVA")
    public void addValidProductBVA(String name, double price) {
        // arrange
        int inStock = 1;
        int min = 1;
        int max = 1;
        String productName;
        productName = Objects.requireNonNullElse(name, "");
        Product mockProduct = new Product(1, productName, price, inStock, min, max, null);

        // act
        Mockito.doNothing().when(repo).addProduct(any(Product.class));
        Mockito.doReturn(mockProduct).when(repo).lookupProduct(name);

        service.addProduct(name, price, inStock, min, max, addParts);
        Product product = service.lookupProduct(name);

        // assert
        assertEquals(name, product.getName());
    }

    @Tag("BVA")
    @ParameterizedTest
    @MethodSource("provideInvalidData")
    @DisplayName("Add invalid product with BVA")
    public void addInvalidProductBVA(String name, double price, String expectedErrorMessage) {
        // arrange
        int inStock = 1;
        int min = 1;
        int max = 1;

        // act
        Mockito.doNothing().when(repo).addProduct(any(Product.class));

        // assert
        Exception e = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.addProduct(name, price, inStock, min, max, addParts);
        });
        assertEquals(expectedErrorMessage, e.getMessage());
    }
}
