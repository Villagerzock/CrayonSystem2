package net.crayonsmp.commands;

import net.crayonsmp.managers.ChatManager;
import net.crayonsmp.managers.GoalManager;
import net.crayonsmp.utils.Goal;
import net.crayonsmp.utils.GoalType;
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
            player.sendMessage("§cYou do not have a goal assigned yet. Please rejoin.");
            return true; // Command handled
        }

        // Now that we've confirmed playerGoal is not null, it's safe to access its methods
        Goal goal = playerGoal.getGoal();
        Magic primaryMagic = playerGoal.getMagicPrimery(); // Note: Method name 'getMagicPrimery' from your PlayerGoal.java
        Magic secondaryMagic = playerGoal.getMagicSecondary();
        String color = null;
        switch (goal.getGoalType()) {
            case bad:
                color = "<#a63851>";
                break;
            case good:
                color = "<#92b460>";
                break;
            case neutral:
                color = "<#455f7f>";
                break;
        }
        // Display goal information with improved formatting and colors
        player.sendMessage(ChatManager.format(color + "--- Your Current Goal ---"));
        if (goal != null) { // Defensive check, though Goal should ideally not be null if playerGoal is valid
            player.sendMessage(ChatManager.format("<#f3d6ac>Goal: <#b4b4b4>" + goal.getName()));
            switch (goal.getGoalType()){
                case bad:
                    player.sendMessage(ChatManager.format("<#f3d6ac>Type: <glyph:bad_glyph_crayon>"));
                    break;
                case good:
                    player.sendMessage(ChatManager.format("<#f3d6ac>Type: <glyph:good_glyph_crayon>"));
                    break;
                case neutral:
                    player.sendMessage(ChatManager.format("<#f3d6ac>Type: <glyph:neutral_glyph_crayon>"));
                    break;
            }
            player.sendMessage(ChatManager.format("<#cb9e83>Description: <#b4b4b4>"));
            for (String line : goal.getDescription()) {
                player.sendMessage("§f" + line);
            }
            player.sendMessage("");
        } else {
            player.sendMessage("§cYour assigned goal data is incomplete. Please contact an administrator.");
        }

        // Display primary magic information
        if (primaryMagic != null) { // Defensive check for magic being null
            player.sendMessage(ChatManager.format("<#cb9e83>Primary Magic: <#b4b4b4>" + primaryMagic.getName() + " <glyph:book_glyph_crayon>"));
        } else {
            player.sendMessage(ChatManager.format("<#cb9e83>Primary Magic: §cNot assigned or invalid."));
        }

        // Display secondary magic information
        if (secondaryMagic != null) { // Defensive check for magic being null
            player.sendMessage(ChatManager.format("<#cb9e83>Secondary Magic: <#b4b4b4>" + secondaryMagic.getName() + " <glyph:ampule_glyph_crayon>"));
        } else {
            player.sendMessage(ChatManager.format("<#cb9e83>Secondary Magic: §cNot assigned or invalid."));
        }
        player.sendMessage(ChatManager.format(color + "-------------------------"));

        return true; // Command successfully handled
    }
}