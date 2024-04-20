package com.sandship.observer;

import com.sandship.models.material.Material;

import java.util.Map;
import java.util.Set;

/**
 * Represents an observer that receives notifications about warehouse changes.
 */
public class WarehouseManager implements Observer {

    private final String name;

    public WarehouseManager(final String name) {
        this.name = name;
    }

    @Override
    public void update(Map<Material, Integer> materials, String warehouseName) {
        StringBuilder result = new StringBuilder();
        for (Material material : materials.keySet()) {
            result.append("{").append(material.getName()).append(":").append(materials.get(material)).append("}").append("\n");
        }
        System.out.println(name + " there are some changes in " + warehouseName + "\n" + result
                + "\n===============================================================\n");
    }
}
