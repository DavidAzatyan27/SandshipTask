package com.sandship.models.material;

public class Iron extends Material {
    public Iron(int maxCapacity) {
        super("Iron", "Basic building material", "iron_icon.png", maxCapacity);
    }

    @Override
    public String toString() {
        return "Iron{" +
                "name='" + super.getName() + '\'' +
                ", description='" + super.getDescription() + '\'' +
                ", icon='" + super.getIcon() + '\'' +
                ", capacity=" + super.getMaxCapacity() +
                '}';
    }
}
