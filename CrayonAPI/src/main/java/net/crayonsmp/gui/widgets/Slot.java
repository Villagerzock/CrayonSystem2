package net.crayonsmp.gui.widgets;

import net.crayonsmp.gui.InventoryWidget;
import org.bukkit.event.inventory.InventoryClickEvent;

public class Slot implements InventoryWidget {
    @Override
    public boolean onClicked(InventoryClickEvent e) {
        return true;
    }
}
