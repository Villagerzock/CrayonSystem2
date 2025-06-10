package net.crayonsmp.tjorven.abilities;

import com.nexomc.nexo.api.NexoItems;
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

public class ChainsawAbility implements Listener {

    private final Plugin plugin;

    public ChainsawAbility(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        ItemStack itemStack = player.getInventory().getItem(EquipmentSlot.HAND);

        String chainsaw = NexoItems.idFromItem(itemStack);
        if (chainsaw == null || !chainsaw.equals("chainsaw")) {
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

    private Set<Location> searchAllBlocks(Set<Location> old, Location start, AtomicInteger min, final int maxBlocks, Predicate<Block> filter) {
        if (min.get() >= maxBlocks) {
            return old;
        }

        Set<Location> newLocations = new HashSet<>();
        Location copy = start.clone().subtract(1, 0, 1);

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 2; y++) {
                for (int z = 0; z < 3; z++) {
                    Location finalBlock = copy.clone().add(x, y, z);

                    if (finalBlock.equals(start) || old.contains(finalBlock)) {
                        continue;
                    }

                    if (!filter.test(finalBlock.getBlock())) {
                        continue;
                    }

                    newLocations.add(finalBlock);
                    old.add(finalBlock);
                    min.incrementAndGet();

                    if (min.get() >= maxBlocks) {
                        return old;
                    }
                }
            }
        }

        for (Location newStart : newLocations) {
            this.searchAllBlocks(old, newStart, min, maxBlocks, filter);
        }

        return old;
    }
}
