package com.sandship.models.warehouse;

import com.sandship.models.material.Material;
import com.sandship.observer.Observer;
import com.sandship.observer.Subject;
import com.sandship.util.NoSuchWarehouseException;
import com.sandship.util.NotEnoughMaterialException;
import com.sandship.util.WarehouseFullException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a warehouse entity with materials and their quantities.
 * Implements the Subject interface to support observer pattern.
 */
public class Warehouse implements Subject {
    private int playerId;
    private final String name;
    private final Map<Material, Integer> materials;
    private final List<Observer> observers;

    public Warehouse(int playerId, final String name) {
        this.playerId = playerId;
        this.name = name;
        materials = new HashMap<>();
        observers = new ArrayList<>();
    }

    public void addMaterial(final Material material, final int quantity) {

        validate(quantity, material);

        int currentQuantity = materials.getOrDefault(material, 0);
        if (currentQuantity + quantity > material.getMaxCapacity()) {
            throw new WarehouseFullException("Exceeds max capacity for material: " + material.getName());
        }
        materials.put(material, currentQuantity + quantity);
        notifyObservers();
    }

    public void removeMaterial(final Material material, final int quantity) {

        validate(quantity, material);

        int currentQuantity = materials.getOrDefault(material, 0);
        if (currentQuantity < quantity) {
            throw new NotEnoughMaterialException("Insufficient quantity of material: " + material.getName());
        }
        if (currentQuantity - quantity == 0) {
            materials.remove(material);
            return;
        }
        materials.put(material, currentQuantity - quantity);
        notifyObservers();
    }

    public void moveMaterial(final Warehouse destinationWarehouse, final Material material, final int quantity) {
        validate(quantity, material);

        if (destinationWarehouse == null) {
            throw new IllegalArgumentException("Invalid input: warehouse is null.");
        }

        checkWarehouseBelongToPlayer(destinationWarehouse);

        removeMaterial(material, quantity);
        destinationWarehouse.addMaterial(material, quantity);

    }

    private void validate(final int quantity, final Material material) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive.");
        }
        if (material == null) {
            throw new IllegalArgumentException("Material is null.");
        }
    }

    private void checkWarehouseBelongToPlayer(Warehouse warehouse) {
        if (this.playerId != warehouse.getPlayerId()) {
            throw new NoSuchWarehouseException("The warehouse " + warehouse.getName() + " doesn't belong to the player.");
        }
    }

    public void printWarehouseData() {
        System.out.println(name + " Data:");
        for (Map.Entry<Material, Integer> entry : materials.entrySet()) {
            System.out.println(entry.getKey().getName() + ": " + entry.getValue());
        }
        System.out.println();
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getName() {
        return name;
    }

    public Map<Material, Integer> getMaterials(){
        return materials;
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(materials, name);
        }
    }
}
