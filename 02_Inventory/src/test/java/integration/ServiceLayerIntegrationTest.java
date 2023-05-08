package integration;

import inventory.model.Part;
import inventory.repository.InventoryRepository;
import inventory.service.InventoryService;
import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ServiceLayerIntegrationTest {
    private static final String filename = "C:\\UBB\\VVSS\\claudiu\\02_Inventory\\data\\items.txt";
    private String filecontent = "";

    private InventoryRepository inventoryRepository;
    private InventoryService inventoryService;

    @BeforeAll
    public void setUp() {
        readFileContent();

        inventoryRepository = new InventoryRepository();
        inventoryService = new InventoryService(inventoryRepository);
    }

    private void readFileContent() {
        try {
            File file = new File(filename);
            file.createNewFile();
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                filecontent += line + "\n";
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    public void tearDown() {
        writeFile(filecontent);
    }

    @BeforeEach
    public void setUpEach() {
        inventoryRepository = new InventoryRepository();
        inventoryService = new InventoryService(inventoryRepository);

        writeFile(filecontent);
    }

    private static void writeFile(String str) {
        try {
            File file = new File(filename);
            FileWriter writer = new FileWriter(file);
            writer.write(str);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddPart() {
        int partCount = inventoryService.getAllParts().size();
        inventoryService.addOutsourcePart("part1", 1, 1, 1, 1, "company1");
        assert (inventoryService.getAllParts().size() == partCount + 1);
    }

    @Test
    public void testLookupAndDelete() {
        // act arrange assert
        String searchItem = "partJNASEDJKFTNASDRJKFNBSDRJGNJLF";
        inventoryService.addOutsourcePart(searchItem, 1, 1, 1, 1, "company1");
        int partCount = inventoryService.getAllParts().size();
        Part part = inventoryService.lookupPart(searchItem);
        assert (part.getName().equals(searchItem));
        inventoryService.deletePart(part);
        assert (inventoryService.lookupPart(searchItem) == null);
        assert (inventoryService.getAllParts().size() == partCount - 1);
    }
}
