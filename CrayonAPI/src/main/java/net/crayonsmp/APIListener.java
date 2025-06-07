package net.crayonsmp;

import net.crayonsmp.interfaces.GUI;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;

public class APIListener implements Listener {
    @EventHandler
    public void onSlotClickedEvent(InventoryClickEvent e){
        if (e.getInventory() instanceof GUI gui){
            e.setCancelled(gui.onSlotClicked(e.getSlot(),e.getClick(),e.getSlotType()));
        }
    }
}
