package net.crayonsmp.commands;

import net.crayonsmp.utils.ModelManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class InfoCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String [] args) {
        sender.sendMessage(Component.text(" Blöcke: ")
                .color(TextColor.color(0x349FDA))
                .append(Component.text(ModelManager.getBlocksLoaded())
                        .color(NamedTextColor.WHITE)));

        sender.sendMessage(Component.text(" Möbel: ")
                .color(TextColor.color(0x349FDA))
                .append(Component.text(ModelManager.getFurnitureLoaded())
                        .color(NamedTextColor.WHITE)));

        sender.sendMessage(Component.text(" Items: ")
                .color(TextColor.color(0x349FDA))
                .append(Component.text(ModelManager.getItemsLoaded())
                        .color(NamedTextColor.WHITE)));

        sender.sendMessage(Component.text(" MythicMobs: ")
                .color(TextColor.color(0x349FDA))
                .append(Component.text(ModelManager.getMobsLoaded())
                        .color(NamedTextColor.WHITE)));

        sender.sendMessage(Component.text(" ModelEngine-Modelle: ")
                .color(TextColor.color(0x349FDA))
                .append(Component.text(ModelManager.getModelsLoaded())
                        .color(NamedTextColor.WHITE)));

        sender.sendMessage(Component.text(" Skills: ")
                .color(TextColor.color(0x349FDA))
                .append(Component.text(ModelManager.getSkillsLoaded())
                        .color(NamedTextColor.WHITE)));

        return false;
    }
}
