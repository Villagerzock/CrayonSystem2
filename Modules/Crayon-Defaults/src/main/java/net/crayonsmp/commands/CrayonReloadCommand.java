package net.crayonsmp.commands;

import lombok.RequiredArgsConstructor;
import net.crayonsmp.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.logging.Level;

@RequiredArgsConstructor
public class CrayonReloadCommand implements CommandExecutor, Runnable {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender.hasPermission("crayon.reloadnexo")) {
        run();
        } else {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
        }
        return true;
    }

    @Override
    public void run() {
            new BukkitRunnable() {
                int counter = 30;

                @Override
                public void run() {
                    if (counter > 0) {
                        if (counter == 30) {
                            Bukkit.broadcastMessage(ChatUtil.format("<#b2b2b2>Server reload sequence in: <#ff0040>" + counter + " seconds!"));
                        } else if (counter == 20){
                            Bukkit.broadcastMessage(ChatUtil.format("<#b2b2b2>Server reload sequence in: <#ff0040>" + counter + " seconds!"));
                        } else if (counter <= 10){
                            Bukkit.broadcastMessage(ChatUtil.format("<#b2b2b2>Server reload sequence in: <#ff0040>" + counter + " seconds!"));
                        }
                        counter--;
                    } else {
                        Bukkit.broadcastMessage(ChatUtil.format("<#b2b2b2>Initiating reload sequence now!"));


                        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "cc reload");
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
                                Bukkit.broadcastMessage(ChatUtil.format("<#b2b2b2>Plugin reload sequence completed!"));
                            }
                        }.runTaskLater(Bukkit.getPluginManager().getPlugin("CrayonCore"), 20L * 5);

                        this.cancel();
                    }
                }
            }.runTaskTimer(Bukkit.getPluginManager().getPlugin("CrayonCore"), 0L, 20L);
    }
}