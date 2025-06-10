package net.crayonsmp.gui.widgets;

import lombok.Getter;
import net.crayonsmp.gui.InventoryWidget;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class Button implements InventoryWidget {

    @Getter
    private final ItemStack icon;
    private final Consumer<Button> onClicked;

    public Button(ItemStack icon, Consumer<Button> onClicked) {
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
