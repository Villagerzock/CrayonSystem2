package net.crayonsmp.gui;

import net.crayonsmp.PluginProvider;
import net.crayonsmp.gui.widgets.Button;
import net.crayonsmp.gui.widgets.Slot;
import net.crayonsmp.utils.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class FeywoodInventories {
    public static Inventory feywoodTable(){
        InventoryBuilder builder = new InventoryBuilder();
        builder.setRowAmount(6);
        builder.setTitle(Component.text("Feywood Table"));
        // Adding Buttons
        builder.addWidget(5,0,new Button(new ItemBuilder().setMaterial(Material.STICK).setCustomModelData(2990001).build(), button -> {
            button.getIcon().getItemMeta().setCustomModelData(2990002);
            PluginProvider.plugin.getLogger().info("Clicked Button in Inventory");
        }));

        // Adding Input Slots
        builder.addWidget(7,1,new Slot());
        builder.addWidget(7,4,new Slot());
        return builder.build();
    }
}
