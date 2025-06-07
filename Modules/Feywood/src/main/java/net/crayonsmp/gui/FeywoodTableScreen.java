package net.crayonsmp.gui;

import net.crayonsmp.interfaces.GUI;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class FeywoodTableScreen extends GUI {
    public FeywoodTableScreen(InventoryHolder holder) {
        super(holder);
    }

    @Override
    public boolean onSlotClicked(int slot, ClickType type, InventoryType.SlotType slotType) {
        if (slot == calculateSlotId(1,7) || slot == calculateSlotId(4, 7)){
            return true;
        }
        return false;
    }

    @Override
    public int getRowAmount() {
        return 6;
    }
}
