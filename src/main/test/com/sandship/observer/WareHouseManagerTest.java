package com.sandship.observer;

import com.sandship.models.material.Bolt;
import com.sandship.models.material.Iron;
import com.sandship.models.material.Material;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class WareHouseManagerTest {
    @Test
    public void testUpdateValidUpdate() {
        // Redirect console output to a ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Create a WarehouseManager instance
        WarehouseManager warehouseManager = new WarehouseManager("Manager");

        // Create test data
        Map<Material, Integer> materials = new LinkedHashMap<>();
        Material iron = new Iron( 100);
        Material bolt = new Bolt( 50);
        materials.put(iron, 30);
        materials.put(bolt, 20);

        // Call the update method and capture console output
        warehouseManager.update(materials, "Test Warehouse");

        // Assert that the console output matches the expected output
        String expectedOutput = "Manager there are some changes in Test Warehouse\n" +
                "{Iron:30}\n" +
                "{Bolt:20}\n\n" +
                "===============================================================\n\n";
        assertEquals(expectedOutput, outputStream.toString());

        // Restore standard output stream
        System.setOut(System.out);
    }
}
