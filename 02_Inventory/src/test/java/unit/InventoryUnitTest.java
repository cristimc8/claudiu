package unit;

import inventory.model.InhousePart;
import inventory.model.Inventory;
import inventory.model.Part;
import inventory.model.Product;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertTrue;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class InventoryUnitTest {
    private Inventory inventory;

    @BeforeEach
    void setUp() {
        inventory = new Inventory();
    }

    @Test
    void addProductTest() {
        Product product = new Product(1, "Test Product", 10.0, 5, 1, 10, null);
        inventory.addProduct(product);
        ObservableList<Product> products = inventory.getProducts();
        assertTrue(products.contains(product));
    }

    @Test
    public void testAddPart() {
        // Create a new InHousePart and add it to the inventory
        Part part = new InhousePart(inventory.getAutoPartId(), "Bolt", 1.99, 100, 1, 200, 123);
        inventory.addPart(part);

        // Check that the inventory contains the new part
        ObservableList<Part> allParts = inventory.getAllParts();
        assertTrue(allParts.contains(part));
    }
}
