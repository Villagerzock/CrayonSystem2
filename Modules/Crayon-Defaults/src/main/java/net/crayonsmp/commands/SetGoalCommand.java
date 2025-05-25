package net.crayonsmp.commands;

import net.crayonsmp.managers.GoalManager;
import net.crayonsmp.utils.Goal;
import net.crayonsmp.utils.Magic;
import net.crayonsmp.utils.PlayerGoal;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetGoalCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String [] args) {

        Player taget = Bukkit.getPlayer(args[0]);
        if (!sender.hasPermission("crayon.goalset")) {
            sender.sendMessage("You do not have permission to use this command!");
            return true;
        }
        if(args.length == 4){
                    if (taget != null) {
                        Goal goal = GoalManager.registertgoals.get(args[1]);
                        Magic primaryMagic = GoalManager.getMagicById(args[2]);
                        Magic secondaryMagic = GoalManager.getMagicById(args[3]); // Corrected to args[3]

                        if (goal == null) {
                            sender.sendMessage("Error: Goal '" + args[1] + "' not found.");
                            sender.sendMessage("all registert goals:");
                            GoalManager.registertgoals.forEach((key, value) -> sender.sendMessage(key));
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

                        GoalManager.addPlayerGoalData(taget.getUniqueId().toString(), new PlayerGoal(goal, primaryMagic, secondaryMagic));
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
