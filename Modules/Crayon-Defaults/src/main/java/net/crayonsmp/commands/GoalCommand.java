package net.crayonsmp.commands;

import net.crayonsmp.managers.GoalManager;
import net.crayonsmp.utils.Goal;
import net.crayonsmp.utils.Magic;
import net.crayonsmp.utils.PlayerGoal;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GoalCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String [] args) {
        // Ensure the sender is a player, as this command is player-specific
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command!");
            return true; // Return true as the command was handled, just not by console
        }
        Player player = (Player) sender;

        // Get the player's UUID string once for efficiency and readability
        String playerUUID = player.getUniqueId().toString();

        // Retrieve the PlayerGoal object directly from the in-memory HashMap using the raw UUID
        PlayerGoal playerGoal = GoalManager.PlayerGoalData.get(playerUUID);

        // Crucial null check: If playerGoal is null, the player doesn't have an assigned goal
        if (playerGoal == null) {
            player.sendMessage("§cYou do not have a goal assigned yet. Please contact an administrator or use /goalset to set one.");
            return true; // Command handled
        }

        // Now that we've confirmed playerGoal is not null, it's safe to access its methods
        Goal goal = playerGoal.getGoal();
        Magic primaryMagic = playerGoal.getMagicPrimery(); // Note: Method name 'getMagicPrimery' from your PlayerGoal.java
        Magic secondaryMagic = playerGoal.getMagicSecondary();

        // Display goal information with improved formatting and colors
        player.sendMessage("§b--- Your Current Goal ---");
        if (goal != null) { // Defensive check, though Goal should ideally not be null if playerGoal is valid
            player.sendMessage("§eGoal: §a" + goal.getName());
            player.sendMessage("§eType: §a" + goal.getGoalType());
            player.sendMessage("§eDescription:");
            for (String line : goal.getDescription()) {
                player.sendMessage("§f" + line);
            }
        } else {
            player.sendMessage("§cYour assigned goal data is incomplete. Please contact an administrator.");
        }

        // Display primary magic information
        if (primaryMagic != null) { // Defensive check for magic being null
            player.sendMessage("§ePrimary Magic: §d" + primaryMagic.getName());
        } else {
            player.sendMessage("§ePrimary Magic: §cNot assigned or invalid.");
        }

        // Display secondary magic information
        if (secondaryMagic != null) { // Defensive check for magic being null
            player.sendMessage("§eSecondary Magic: §d" + secondaryMagic.getName());
        } else {
            player.sendMessage("§eSecondary Magic: §cNot assigned or invalid.");
        }
        player.sendMessage("§b-------------------------");

        return true; // Command successfully handled
    }
}