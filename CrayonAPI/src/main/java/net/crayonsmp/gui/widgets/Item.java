package net.crayonsmp.gui.widgets;

import net.crayonsmp.gui.InventoryWidget;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class Item implements InventoryWidget {
    private final ItemStack icon;

    public Item(ItemStack icon) {
        this.icon = icon;
    }

    @Override
    public boolean onClicked(InventoryClickEvent e) {
        return false;
    }

    @Override
    public ItemStack getDefault() {
        return icon;
    }
}
