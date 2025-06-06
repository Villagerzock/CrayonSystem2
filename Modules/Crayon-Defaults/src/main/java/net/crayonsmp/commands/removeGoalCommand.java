package net.crayonsmp.commands;

import net.crayonsmp.gui.GoalMenu;
import net.crayonsmp.services.GoalService;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class removeGoalCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {

        Player taget = Bukkit.getPlayer(args[0]);
        if (!sender.hasPermission("crayon.goalremove")) {
            sender.sendMessage("You do not have permission to use this command!");
            return true;
        }

        if (args.length == 1){
            if (taget != null) {
                GoalService.removePlayerGoalData(taget.getUniqueId().toString());
                taget.sendMessage("Your goal has been removed.");
                GoalMenu.openGoalMenu(taget);
                sender.sendMessage("Removed goal for player " + taget.getName() + ".");
            } else {
                sender.sendMessage("Player '" + args[0] + "' not found.");
            }
        }
        return false;
    }
}
