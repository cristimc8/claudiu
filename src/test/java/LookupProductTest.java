import inventory.model.Inventory;
import inventory.model.Product;
import inventory.repository.InventoryRepository;
import inventory.service.InventoryService;
import javafx.collections.FXCollections;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LookupProductTest {
    private final Inventory inventory = new Inventory();

    @BeforeEach()
    public void setup() {
        this.inventory.setProducts(FXCollections.observableArrayList(
                new Product(1, "testX", -3, 1, 1, 1, null),
                new Product(2, "test2", 2.0, 2, 2, 2, null),
                new Product(3, "(●'◡'●)", 3.0, 3, 3, 3, null)
        ));
    }

    @DisplayName("Test lookupProduct() with searchItem null")
    @Test
    public void lookupProductNull() {
        Product product1 = inventory.lookupProduct(null);
        Product product2 = inventory.lookupProduct("");

        assertNull(product1);
        assertNull(product2);
    }

    @DisplayName("Test lookupProduct() with searchItem test")
    @Test
    public void lookupProductTest() {
        Product product = inventory.lookupProduct("test2");

        assertNotNull(product);
        assertTrue(product.getPrice() > 0);
    }

    @DisplayName("Test lookupProduct() with searchItem notExisting")
    @Test
    public void lookupProductNotExisting() {
        Product product = inventory.lookupProduct("notExisting");

        assertEquals(product.getProductId(), 0);
    }

    @DisplayName("Test lookupProduct() with searchItem testX")
    @Test
    public void lookupProductTestX() {
        Product product = inventory.lookupProduct("testX");

        assertNull(product);
    }

    @DisplayName("Test lookupProduct() with searchItem (●'◡'●)")
    @Test
    public void lookupProductSmiley() {
        Product product = inventory.lookupProduct("(●'◡'●)");

        assertEquals(product.getProductId(), 3);
    }
}
