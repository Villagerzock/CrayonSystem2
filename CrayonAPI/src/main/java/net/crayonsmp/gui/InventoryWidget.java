package net.crayonsmp.gui;

import org.bukkit.event.inventory.InventoryClickEvent;

public interface InventoryWidget {
    boolean onClicked(InventoryClickEvent e);
}
