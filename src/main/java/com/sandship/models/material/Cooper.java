package com.sandship.models.material;

public class Cooper extends Material {
    public Cooper(int maxCapacity) {
        super("Copper", "Used for electrical components", "copper_icon.png", maxCapacity);
    }

    @Override
    public String toString() {
        return "Cooper{" +
                "name='" + super.getName() + '\'' +
                ", description='" + super.getDescription() + '\'' +
                ", icon='" + super.getIcon() + '\'' +
                ", capacity=" + super.getMaxCapacity() +
                '}';
    }
}
