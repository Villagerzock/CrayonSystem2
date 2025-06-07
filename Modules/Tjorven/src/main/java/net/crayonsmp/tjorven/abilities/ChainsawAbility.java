package net.crayonsmp.tjorven.abilities;

import com.nexomc.nexo.api.NexoItems;
import com.nexomc.nexo.items.ItemBuilder;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

@RequiredArgsConstructor
public class ChainsawAbility implements Listener {

    private final Plugin plugin;

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        ItemBuilder chainsaw = NexoItems.itemFromId("chainsaw");
        if (chainsaw == null) {
            return;
        }

        ItemStack itemStack = player.getInventory().getItem(EquipmentSlot.HAND);

        if (!itemStack.hasItemMeta() || !itemStack.getItemMeta().hasCustomModelData()) {
            return;
        }

        if (chainsaw.getType() != itemStack.getType()
            && chainsaw.build().getItemMeta().getCustomModelData() != itemStack.getItemMeta().getCustomModelData()) {
            return;
        }

        Location location = block.getLocation();
        if (!location.getBlock().getType().name().contains("LOG")) {
            return;
        }

        if (player.getInventory().contains(Material.COAL, 1)) {
            player.getInventory().removeItem(new ItemStack(Material.COAL, 1));
        } else if (player.getInventory().contains(Material.CHARCOAL, 1)) {
            player.getInventory().removeItem(new ItemStack(Material.CHARCOAL, 1));
        } else {
            player.sendActionBar(Component.text("Fuel is empty, get some coal!").color(TextColor.fromHexString("#d93232")));
            return;
        }

        List<Block> blocks = this.searchAllBlocks(new HashSet<>(), location, new AtomicInteger(0), 100, value -> value.getType().name().contains("LOG"))
                .stream()
                .map(Location::getBlock)
                .toList();

        for (Block blockToBreak : blocks) {
            blockToBreak.breakNaturally(itemStack);
        }
    }

    /**
     * Recursively scans for all blocks that are attached to a mushroom
     * Laufzeit: O(maxBlocks)
     * 32 blöcke ca. 0.01ms - 0.2ms
     *
     * @param old       this old set | needed for recursion empty on default
     * @param start     the location of the first block
     * @param min       a counter variable | needed for recursion
     * @param maxBlocks the count of maxBlocks blocks scanned | needed for recursion as stop hook
     * @param filter    a predicate to scan for block types to not add all blocks
     * @return a set with a vein of filtered blocks
     */
    private Set<Location> searchAllBlocks(Set<Location> old, Location start, AtomicInteger min, final int maxBlocks, Predicate<Block> filter) {
        // Terminate recursion if min exceeds or equals maxBlocks
        if (min.get() >= maxBlocks) {
            return old;
        }

        // Use a temporary set to track newly found locations in this recursive step
        Set<Location> newLocations = new HashSet<>();
        Location copy = start.clone().subtract(1, 0, 1);

        // Check 3x3x3 radius
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 2; y++) {
                for (int z = 0; z < 3; z++) {
                    Location finalBlock = copy.clone().add(x, y, z);

                    // Skip if it's the start location or already in the set
                    if (finalBlock.equals(start) || old.contains(finalBlock)) {
                        continue;
                    }

                    // Apply filter to determine if the block should be added
                    if (!filter.test(finalBlock.getBlock())) {
                        continue;
                    }

                    newLocations.add(finalBlock);
                    old.add(finalBlock);
                    min.incrementAndGet();

                    // Stop if we reach the maximum depth
                    if (min.get() >= maxBlocks) {
                        return old;
                    }
                }
            }
        }

        // Recursively expand from newly found blocks
        for (Location newStart : newLocations) {
            this.searchAllBlocks(old, newStart, min, maxBlocks, filter);
        }

        return old;
    }
}
