package net.crayonsmp.commands;

import net.crayonsmp.services.GoalService;
import net.crayonsmp.utils.goal.Goal;
import net.crayonsmp.utils.goal.Magic;
import net.crayonsmp.utils.goal.PlayerGoal;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetGoalCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        Player taget = Bukkit.getPlayer(args[0]);
        if (!sender.hasPermission("crayon.goalset")) {
            sender.sendMessage("You do not have permission to use this command!");
            return true;
        }
        if (args.length == 4) {
            if (taget != null) {
                Goal goal = GoalService.RegistertGoals.get(args[1]);
                Magic primaryMagic = GoalService.getMagicById(args[2]);
                Magic secondaryMagic = GoalService.getMagicById(args[3]); // Corrected to args[3]

                if (goal == null) {
                    sender.sendMessage("Error: Goal '" + args[1] + "' not found.");
                    sender.sendMessage("all registert goals:");
                    GoalService.RegistertGoals.forEach((key, value) -> sender.sendMessage(key));
                    return true;
                }
                if (primaryMagic == null) {
                    sender.sendMessage("Error: Primary Magic '" + args[2] + "' not found.");
                    return true;
                }
                if (secondaryMagic == null) {
                    sender.sendMessage("Error: Secondary Magic '" + args[3] + "' not found.");
                    return true;
                }

                GoalService.addPlayerGoalData(taget.getUniqueId().toString(), new PlayerGoal(goal, primaryMagic, secondaryMagic));
                taget.sendMessage("Your goal has been set to " + args[1]);
                sender.sendMessage("Set goal for player " + taget.getName() + " to '" + goal.getName() + "' with primary magic '" + primaryMagic.getName() + "' and secondary magic '" + secondaryMagic.getName() + "'.");
                return true;
            } else {
                sender.sendMessage("Player '" + args[0] + "' not found.");
                return true;
            }
        } else {
            sender.sendMessage("Usage: /goalset <player> <goalId> <primaryMagicId> <secondaryMagicId>");
            return true;
        }
    }
}
