package net.crayonsmp.gui;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryBuilder {
    private int rowAmount = 3;
    private InventoryType type = null;
    private Component title = Component.empty();
    private final Map<Integer,InventoryWidget> widgets = new HashMap<>();
    public InventoryBuilder setTitle(Component title){
        this.title = title;
        return this;
    }
    public InventoryBuilder setType(InventoryType type){
        this.type = type;
        return this;
    }
    public InventoryBuilder setRowAmount(int rowAmount){
        this.rowAmount = rowAmount;
        return this;
    }
    public InventoryBuilder addWidget(int slot,InventoryWidget widget){
        widgets.put(slot,widget);
        return this;
    }
    public InventoryBuilder addWidget(int x,int y,InventoryWidget widget){
        widgets.put(y * 9 + x,widget);
        return this;
    }
    public Inventory build(){
        if (type == null){
            return Bukkit.createInventory(new BuiltInventoryHolder(this),9 * rowAmount,title);
        }else {
            return Bukkit.createInventory(new BuiltInventoryHolder(this),type,title);
        }
    }
    public static class InventoryListener implements Listener {
        @EventHandler
        public void onSlotClickedEvent(InventoryClickEvent e){
            if (e.getClickedInventory().getHolder() instanceof BuiltInventoryHolder holder){
                InventoryBuilder builder = holder.getBuilder();
                if (builder.widgets.containsKey(e.getSlot())){
                    e.setCancelled(builder.widgets.get(e.getSlot()).onClicked(e));
                }else {
                    e.setCancelled(true);
                }
            }
        }
    }
}
