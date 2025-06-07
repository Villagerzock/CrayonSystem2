package net.crayonsmp.listeners;

import com.nexomc.nexo.api.NexoBlocks;
import com.nexomc.nexo.mechanics.custom_block.chorusblock.ChorusBlockMechanic;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Hopper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.HopperInventorySearchEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BlockListener implements Listener {
    @EventHandler
    public void onHopperSearch(HopperInventorySearchEvent e) {
        Inventory hopperInventory = e.getInventory();

        if (hopperInventory.getHolder() instanceof Hopper) {
            Hopper hopper = (Hopper) hopperInventory.getHolder();
            // Jetzt hast du den Hopper-Block
            Block hopperBlock = hopper.getBlock();
            Location location = hopperBlock.getLocation();
            Location downLocation = new Location(location.getWorld(), location.getBlockX(), location.getBlockY() - 1, location.getBlockZ());
            Block downBlock = downLocation.getBlock();
            if (NexoBlocks.isNexoChorusBlock(downBlock)) {
                ChorusBlockMechanic mechanic = NexoBlocks.chorusBlockMechanic(downBlock);
                if (mechanic.getItemID().equals("filterblockid")){

                    Inventory
                    for (ItemStack item : hopperInventory.getStorageContents()) {

                    }
                }
            }
        }
    }
}
