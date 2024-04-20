package com.sandship.models;

import com.sandship.models.material.Bolt;
import com.sandship.models.material.Cooper;
import com.sandship.models.material.Iron;
import com.sandship.models.material.Material;
import com.sandship.models.player.Player;
import com.sandship.models.warehouse.Warehouse;
import com.sandship.util.NoSuchWarehouseException;
import com.sandship.util.NotEnoughMaterialException;
import com.sandship.util.WarehouseFullException;
import org.junit.Before;
import org.junit.Test;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class WarehouseTest {

    private Player firstPlayer;
    private Player secondPlayer;
    private Material iron;
    private Material bolt;
    private Material cooper;

    @Before
    public void setUp() {

        firstPlayer = new Player(1, "Test Player 1");
        secondPlayer = new Player(2, "Test Player 2");

        iron = new Iron(1000);
        bolt = new Bolt(700);
        cooper = new Cooper(1200);
    }

    @Test
    public void testAddMaterialSuccessfullyAdded() {
        Warehouse warehouse = new Warehouse(firstPlayer.getId(), "Test Warehouse1");
        warehouse.addMaterial(iron, 800);
        warehouse.addMaterial(bolt, 700);
        int countOfIron = warehouse.getMaterials().get(iron);
        int countOfBolt = warehouse.getMaterials().get(bolt);
        assertEquals(800, countOfIron);
        assertEquals(700, countOfBolt);
    }

    @Test
    public void testAddMaterialUnsuccessfullyAddedWithExceedsMaxCapacity() {
        Warehouse warehouse = new Warehouse(firstPlayer.getId(), "Test Warehouse1");
        assertThrows(WarehouseFullException.class, () -> warehouse.addMaterial(iron, 1200));
    }

    @Test
    public void testAddMaterialUnsuccessfullyAddedWithNegativeQuantity() {
        Warehouse firstWarehouse = new Warehouse(firstPlayer.getId(), "Test Warehouse1");
        assertThrows(IllegalArgumentException.class, () -> firstWarehouse.addMaterial(iron, -1200));
    }

    @Test
    public void testRemoveMaterialSuccessfullyRemoved() {
        Warehouse warehouse = new Warehouse(firstPlayer.getId(), "Test Warehouse1");
        warehouse.addMaterial(iron, 800);
        warehouse.addMaterial(bolt, 500);
        warehouse.addMaterial(cooper, 1000);
        warehouse.removeMaterial(iron, 400);
        warehouse.removeMaterial(bolt, 500);
        warehouse.removeMaterial(cooper, 400);
        int countOfIron = warehouse.getMaterials().getOrDefault(iron, 0);
        int countOfBolt = warehouse.getMaterials().getOrDefault(bolt, 0);
        int countOfCooper = warehouse.getMaterials().getOrDefault(cooper, 0);
        assertEquals(400, countOfIron);
        assertEquals(0, countOfBolt);
        assertEquals(600, countOfCooper);
    }

    @Test
    public void testRemoveMaterialUnSuccessfullyRemovedWithInsufficientQuantity() {
        Warehouse warehouse = new Warehouse(firstPlayer.getId(), "Test Warehouse1");
        assertThrows(NotEnoughMaterialException.class, () -> {
            warehouse.addMaterial(iron, 500);
            warehouse.removeMaterial(iron, 600);
        });
    }

    @Test
    public void testMoveMaterialSuccessfullyMoved() {
        Warehouse firstWarehouse = new Warehouse(firstPlayer.getId(), "Test Warehouse1");
        Warehouse secondWarehouse = new Warehouse(firstPlayer.getId(), "Test Warehouse1");
        firstWarehouse.addMaterial(iron, 800);
        firstWarehouse.addMaterial(cooper, 900);

        secondWarehouse.addMaterial(iron, 200);
        secondWarehouse.addMaterial(bolt, 500);

        firstWarehouse.moveMaterial(secondWarehouse, cooper, 400);
        secondWarehouse.moveMaterial(firstWarehouse, bolt, 200);

        int countOfBoltInFirstWarehouse = firstWarehouse.getMaterials().get(bolt);
        int countOfCooperInSecondWarehouse = secondWarehouse.getMaterials().get(cooper);
        assertEquals(200, countOfBoltInFirstWarehouse);
        assertEquals(400, countOfCooperInSecondWarehouse);
    }

    @Test
    public void testMoveMaterialUnSuccessfullyMovedWithExceedsMaxCapacity() {
        Warehouse firstWarehouse = new Warehouse(firstPlayer.getId(), "Test Warehouse1");
        Warehouse secondWarehouse = new Warehouse(firstPlayer.getId(), "Test Warehouse1");
        assertThrows(WarehouseFullException.class, () -> {
            firstWarehouse.addMaterial(iron, 900);
            secondWarehouse.addMaterial(iron, 300);
            secondWarehouse.moveMaterial(firstWarehouse, iron, 200);
        });
    }

    @Test
    public void testMoveMaterialUnSuccessfullyMovedWithInsufficientQuantity() {
        Warehouse firstWarehouse = new Warehouse(firstPlayer.getId(), "Test Warehouse1");
        Warehouse secondWarehouse = new Warehouse(firstPlayer.getId(), "Test Warehouse1");
        assertThrows(NotEnoughMaterialException.class, () -> {
            firstWarehouse.addMaterial(iron, 100);
            secondWarehouse.addMaterial(iron, 100);
            secondWarehouse.moveMaterial(firstWarehouse, iron, 200);
        });
    }

    @Test
    public void testMoveMaterialUnSuccessfullyMovedToAnotherPlayerWarehouse() {
        Warehouse firstWarehouse = new Warehouse(firstPlayer.getId(), "Test Warehouse1");
        Warehouse thirdWarehouse = new Warehouse(secondPlayer.getId(), "Test Warehouse1");
        assertThrows(NoSuchWarehouseException.class, () -> {
            firstWarehouse.addMaterial(iron, 500);
            thirdWarehouse.addMaterial(iron, 600);
            firstWarehouse.moveMaterial(thirdWarehouse, iron, 200);
        });
    }

    @Test
    public void testPrintWarehouseDataWithMaterials() {
        // Redirect console output to a ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        Warehouse warehouse = new Warehouse(firstPlayer.getId(), "Test warehouse");
        warehouse.addMaterial(iron, 290);
        warehouse.removeMaterial(iron, 40);

        warehouse.printWarehouseData();

        String expectedOutput = "Test warehouse Data:\n" +
                "Iron: 250\n\n";

        assertEquals(expectedOutput, outputStream.toString());
        // Restore standard output stream
        System.setOut(System.out);
    }

    @Test
    public void testNotifyObservers() {
        Warehouse warehouse = new Warehouse(firstPlayer.getId(), "Test Warehouse");
        MockObserver observer = new MockObserver(); // Mock observer to verify notification

        warehouse.addObserver(observer);

        // Add some materials to trigger notification
        warehouse.addMaterial(iron, 20);
        warehouse.addMaterial(cooper, 40);

        // Verify observer is notified with updated materials and warehouse name
        Map<Material, Integer> expectedMaterials = warehouse.getMaterials();
        assertEquals(expectedMaterials, observer.getMaterials());
        assertEquals(warehouse.getName(), observer.getWarehouseName());
    }
}
