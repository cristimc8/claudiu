package unit;

import inventory.model.Part;
import inventory.repository.InventoryRepository;
import inventory.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InventoryServiceUnitTest {
    private InventoryService inventoryService;

    @Test
    public void addPartTest() {
        InventoryRepository inventoryRepository = Mockito.mock(InventoryRepository.class);
        doNothing().when(inventoryRepository).addPart(any(Part.class));

        inventoryService = new InventoryService(inventoryRepository);
        inventoryService.addInhousePart("test", 1, 1, 1, 1, 1);
        inventoryService.addOutsourcePart("test", 1, 1, 1, 1, "test");

        Mockito.verify(inventoryRepository, Mockito.times(2)).addPart(any(Part.class));
    }

    @Test
    public void lookupProductTest() {
        InventoryRepository inventoryRepository = Mockito.mock(InventoryRepository.class);
        inventoryService = new InventoryService(inventoryRepository);
        inventoryService.lookupProduct("test");
        Mockito.verify(inventoryRepository, Mockito.times(1)).lookupProduct("test");
    }
}
