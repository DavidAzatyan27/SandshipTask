package com.sandship.models.material;

public class Bolt extends Material {
    public Bolt(int maxCapacity) {
        super("Bolt", "Used for various constructions", "bolt_icon.png", maxCapacity);
    }

    @Override
    public String toString() {
        return "Bolt{" +
                "name='" + super.getName() + '\'' +
                ", description='" + super.getDescription() + '\'' +
                ", icon='" + super.getIcon() + '\'' +
                ", capacity=" + super.getMaxCapacity() +
                '}';
    }
}
