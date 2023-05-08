package integration;

import inventory.model.Inventory;
import inventory.model.Part;
import inventory.model.Product;
import inventory.repository.InventoryRepository;
import inventory.service.InventoryService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RepositoryLayerIntegrationTest {
    private static final String filename = "C:\\UBB\\VVSS\\claudiu\\02_Inventory\\data\\items.txt";
    private String filecontent = "";

    private InventoryRepository inventoryRepository;
    private InventoryService inventoryService;

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

        inventoryRepository = new InventoryRepository();
        inventoryService = new InventoryService(inventoryRepository);
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

    @Test
    public void addOutsourcePartTest() {
        // arrange
        Inventory mockInventory = Mockito.mock(Inventory.class);
        inventoryRepository.setInventory(mockInventory);
        ObservableList<Part> emptyPartList = FXCollections.observableArrayList();
        ObservableList<Product> emptyProductList = FXCollections.observableArrayList();
        Mockito.when(mockInventory.getAllParts()).thenReturn(emptyPartList);
        Mockito.when(mockInventory.getProducts()).thenReturn(emptyProductList);

        // act
        inventoryService.addOutsourcePart("test", 1.0, 1, 1, 1, "test");

        // assert
        Mockito.verify(mockInventory, Mockito.times(1)).addPart(Mockito.any());
        Mockito.verify(mockInventory).addPart(Mockito.argThat(part -> part.getPrice() == 1.0));
        Mockito.verify(mockInventory).addPart(Mockito.argThat(part -> part.getInStock() == 1));
        Mockito.verify(mockInventory).addPart(Mockito.argThat(part -> part.getMin() == 1));
        Mockito.verify(mockInventory).addPart(Mockito.argThat(part -> part.getMax() == 1));
        Mockito.verify(mockInventory).addPart(Mockito.argThat(part -> part.getName().equals("test")));
        Mockito.verify(mockInventory).addPart(Mockito.argThat(part -> part instanceof inventory.model.OutsourcedPart));
        Mockito.verify(mockInventory).addPart(Mockito.argThat(part -> ((inventory.model.OutsourcedPart) part).getCompanyName().equals("test")));
    }

    @Test
    public void lookupProduct() {
        // arrange
        Inventory mockInventory = Mockito.mock(Inventory.class);
        inventoryRepository.setInventory(mockInventory);
        String searchItem = "test";

        // act
        inventoryService.lookupProduct(searchItem);

        // assert
        Mockito.verify(mockInventory, Mockito.times(1)).lookupProduct(searchItem);
    }
}
