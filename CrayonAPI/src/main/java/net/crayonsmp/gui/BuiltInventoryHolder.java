package net.crayonsmp.gui;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class BuiltInventoryHolder implements InventoryHolder {

    private final InventoryBuilder builder;

    public BuiltInventoryHolder(InventoryBuilder builder) {
        this.builder = builder;
    }

    public InventoryBuilder getBuilder() {
        return builder;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return builder.build();
    }
}
