package net.crayonsmp.commands;

import net.crayonsmp.managers.ChatManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

public class CrayonReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender.hasPermission("crayon.reloadnexo")) {

            new BukkitRunnable() {
                int counter = 30;

                @Override
                public void run() {
                    if (counter > 0) {
                        if (counter == 30) {
                            Bukkit.broadcastMessage(ChatManager.format("<#b2b2b2>Server reload sequence in: <#ff0040>" + counter + " seconds!"));
                        } else if (counter == 20){
                            Bukkit.broadcastMessage(ChatManager.format("<#b2b2b2>Server reload sequence in: <#ff0040>" + counter + " seconds!"));
                        } else if (counter <= 10){
                            Bukkit.broadcastMessage(ChatManager.format("<#b2b2b2>Server reload sequence in: <#ff0040>" + counter + " seconds!"));
                        }
                        counter--;
                    } else {
                        Bukkit.broadcastMessage(ChatManager.format("<#b2b2b2>Initiating reload sequence now!"));

                        // Execute the first command immediately

                        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "cc reload");
                        // Schedule subsequent commands with delays
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "meg reload");
                            }
                        }.runTaskLater(Bukkit.getPluginManager().getPlugin("CrayonCore"), 20L * 2);

                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "mm reload");
                            }
                        }.runTaskLater(Bukkit.getPluginManager().getPlugin("CrayonCore"), 20L * 3);

                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "n rl all");
                                Bukkit.broadcastMessage(ChatManager.format("<#b2b2b2>Plugin reload sequence completed!"));
                            }
                        }.runTaskLater(Bukkit.getPluginManager().getPlugin("CrayonCore"), 20L * 5);

                        // Cancel this runnable so it doesn't keep scheduling
                        this.cancel();
                    }
                }
            }.runTaskTimer(Bukkit.getPluginManager().getPlugin("CrayonCore"), 0L, 20L);
        } else {
            sender.sendMessage(ChatManager.format("<red>You do not have permission to use this command."));
        }

        return true;
    }
}