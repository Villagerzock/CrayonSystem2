package net.crayonsmp.tjorven.abilities;

import lombok.RequiredArgsConstructor;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class BlockTeleport implements Listener {

    private final Plugin plugin;

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getPlayer().isSneaking()) {
            return;
        }

        Block teleportInterface = event.getClickedBlock();
        if (teleportInterface == null) {
            return;
        }
        if (teleportInterface.getType() != Material.NOTE_BLOCK) {
            return;
        }

        Player player = event.getPlayer();
        Location dfs = this.findFurthestSculkInterface(teleportInterface.getLocation(), player);
        if (dfs == null) {
            return;
        }

        event.setCancelled(true);

        Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
            player.getWorld().spawnParticle(Particle.SCULK_SOUL, player.getLocation().clone().add(0, 0.3, 0), 80, 0.2, 0.5, 0.2, 0.01);
            player.getWorld().playSound(player, Sound.BLOCK_SCULK_BREAK, 1f, 1f);
        }, 5);
    }

    private Location findFurthestSculkInterface(Location start, Player player) {
        Set<Location> visited = new HashSet<>();
        DFSResult dfsResult = dfsFurthest(start, visited, 255);
        if (visited.size() < 2) {
            return null;
        }

        Location furthestLocation = dfsResult.furthestLocation;
        for (BlockFace face : BlockFace.values()) {
            if (face == BlockFace.SELF) {
                continue;
            }
            Location finalLocation = furthestLocation.clone().add(face.getDirection());
            if (finalLocation.getBlock().getType() != Material.NOTE_BLOCK) {
                continue;
            }

            NoteBlock noteBlock = (NoteBlock) finalLocation.getBlock().getBlockData();
            if (noteBlock.getInstrument() != Instrument.PIANO) {
                return null;
            }

            BlockFace direction = getDirection(noteBlock);
            if (direction == null) {
                return null;
            }

            Location add = finalLocation.add(direction.getDirection());
            if (direction == BlockFace.DOWN) {
                add.subtract(0, 1, 0);
            } else {
                add.add(0, 0.1, 0);
            }

            add.add(0.5, 0, 0.5);

            if (!add.getBlock().isEmpty()) {
                return null;
            }

            this.setPlayerYaw(add, direction, player);
            return add;
        }
        return null;
    }

    public void setPlayerYaw(Location finalLocation, BlockFace blockFace, Player player) {
        float yaw;

        switch (blockFace) {
            case NORTH -> yaw = 180f;
            case SOUTH -> yaw = 0f;
            case WEST -> yaw = 90f;
            case EAST -> yaw = -90f;
            default -> yaw = player.getLocation().getYaw(); // keep current if UP/DOWN
        }

        finalLocation.setYaw(yaw);
        player.setRotation(yaw, player.getPitch());
        player.teleport(finalLocation.clone()); // apply the rotation
    }

    public BlockFace getDirection(NoteBlock block) {
        Note note = block.getNote();
        byte id = note.getId();
        return switch (id) {
            case 4 -> BlockFace.NORTH;
            case 5 -> BlockFace.SOUTH;
            case 6 -> BlockFace.WEST;
            case 7 -> BlockFace.EAST;
            case 8 -> BlockFace.UP;
            case 9 -> BlockFace.DOWN;
            default -> null;
        };
    }

    private DFSResult dfsFurthest(Location current, Set<Location> visited, int depth) {
        Location blockLoc = current.toBlockLocation(); // normalize to integer block coords

        if (visited.contains(blockLoc)) {
            return new DFSResult(blockLoc, depth - 1); // prevent loops
        }
        visited.add(blockLoc);

        Location furthest = blockLoc;
        int maxDepth = depth;

        for (BlockFace face : BlockFace.values()) {
            if (face == BlockFace.SELF) {
                continue;
            }

            Location neighborLoc = blockLoc.clone().add(face.getDirection());
            Block neighborBlock = neighborLoc.getBlock();

            if (neighborBlock.getType() != Material.SCULK) {
                continue;
            }

            DFSResult result = dfsFurthest(neighborLoc, visited, depth + 1);
            if (result.depth > maxDepth) {
                maxDepth = result.depth;
                furthest = result.furthestLocation;
            }
        }

        return new DFSResult(furthest, maxDepth);
    }

    private record DFSResult(Location furthestLocation, int depth) {
    }

}
