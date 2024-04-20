package com.sandship.models.player;

import com.sandship.models.warehouse.Warehouse;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private int id;
    private String name;
    private List<Warehouse> warehouses;

    public Player(int id, String name) {
        this.id = id;
        this.name = name;
        this.warehouses = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    public void setWarehouses(List<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }
}
