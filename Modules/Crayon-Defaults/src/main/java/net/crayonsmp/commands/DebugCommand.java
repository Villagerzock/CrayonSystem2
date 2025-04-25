package net.crayonsmp.commands;

import net.crayonsmp.events.DebugEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class DebugCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String @NotNull [] args) {
        if (sender.hasPermission("crayon.debug")){
            DebugEvent event = new DebugEvent();
            event.callEvent();

            Bukkit.broadcast(Component.text("All Crayon Modules Debugged!").color(TextColor.color(0x13f832)));
        }
        return false;
    }
}
