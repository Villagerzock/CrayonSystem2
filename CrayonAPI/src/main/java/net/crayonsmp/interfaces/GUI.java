package net.crayonsmp.interfaces;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public interface GUI {
    Inventory createInventory(InventoryHolder holder);
}
