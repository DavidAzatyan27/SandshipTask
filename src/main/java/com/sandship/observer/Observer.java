package com.sandship.observer;

import com.sandship.models.material.Material;

import java.util.Map;
import java.util.Set;
/**
 * Defines the contract for classes that observe changes.
 */
public interface Observer {
    void update(Map<Material, Integer> materials , String warehouseName);
}
