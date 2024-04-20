package com.sandship.models;

import com.sandship.models.material.Material;
import com.sandship.observer.Observer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Helper class to mock Observer behavior
 */
public class MockObserver implements Observer {
    private Map<Material, Integer> materials;
    private String warehouseName;

    @Override
    public void update(Map<Material, Integer> materials, String warehouseName) {
        this.materials = new HashMap<>(materials); // Copy to avoid mutability issues
        this.warehouseName = warehouseName;
    }

    public Map<Material, Integer> getMaterials() {
        return materials;
    }

    public String getWarehouseName() {
        return warehouseName;
    }
}
