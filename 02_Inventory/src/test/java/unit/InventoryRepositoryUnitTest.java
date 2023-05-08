package unit;

import inventory.model.Inventory;
import inventory.model.Part;
import inventory.model.Product;
import inventory.repository.InventoryRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InventoryRepositoryUnitTest {
    private static final String filename = "C:\\UBB\\VVSS\\claudiu\\02_Inventory\\data\\items.txt";
    private String filecontent = "";

    @BeforeAll
    public void setUp() {
        try {
            File file = new File(filename);
            file.createNewFile();
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while((line=br.readLine())!=null){
                filecontent += line + "\n";
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddPart() {
        // arrange
        InventoryRepository inventoryRepository = new InventoryRepository();
        Inventory inventory = Mockito.mock(Inventory.class);
        ObservableList<Part> emptyPartList = FXCollections.observableArrayList();
        ObservableList<Product> emptyProductList = FXCollections.observableArrayList();
        Mockito.when(inventory.getAllParts()).thenReturn(emptyPartList);
        Mockito.when(inventory.getProducts()).thenReturn(emptyProductList);
        inventoryRepository.setInventory(inventory);
        Part part = Mockito.mock(Part.class);

        // act
        inventoryRepository.addPart(part);

        // assert
        Mockito.verify(inventory, Mockito.times(1)).addPart(part);
    }

    @Test
    public void testLookupPart() {
        // arrange
        InventoryRepository inventoryRepository = new InventoryRepository();
        Inventory inventory = Mockito.mock(Inventory.class);
        inventoryRepository.setInventory(inventory);
        Part part = Mockito.mock(Part.class);
        Mockito.when(inventory.lookupPart(part.getName())).thenReturn(part);

        // act
        Part result = inventoryRepository.lookupPart(part.getName());

        // assert
        Assertions.assertEquals(part, result);
    }

    @AfterAll
    public void tearDown() {
        try {
            // write the original content back to the file
            File file = new File(filename);
            FileWriter writer = new FileWriter(file);
            writer.write(filecontent);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
