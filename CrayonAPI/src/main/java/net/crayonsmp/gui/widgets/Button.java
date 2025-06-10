package net.crayonsmp.gui.widgets;

import net.crayonsmp.gui.BuiltInventoryHolder;
import net.crayonsmp.gui.InventoryWidget;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class Button implements InventoryWidget {
    private final ItemStack icon;
    private final Consumer<Button> onClicked;

    public ItemStack getIcon() {
        return icon;
    }

    public Button(ItemStack icon, Consumer<Button> onClicked){
        this.icon = icon;
        this.onClicked = onClicked;
    }
    @Override
    public boolean onClicked(InventoryClickEvent e) {
        onClicked.accept(this);
        return false;
    }

    @Override
    public ItemStack getDefault() {
        return icon;
    }
}
