package net.crayonsmp;


import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public interface CrayonModule {
    String getName();
    void onLoad(CrayonAPI api); // oder einfach kein Parameter
    void onEnable(Plugin plugin);
    void onDisable();
    default PluginCommand registerCommand(String name, Plugin plugin) {
        plugin.getLogger().info("Type: " + Bukkit.getPluginManager().getClass().getCanonicalName());
        if (Bukkit.getPluginManager() instanceof SimplePluginManager pluginManager) {
            try {
                int i = 0;
                Field commandMapField = pluginManager.getClass().getDeclaredField("commandMap");
                i++;
                commandMapField.setAccessible(true);
                i++;
                CommandMap commandMap = (CommandMap) commandMapField.get(pluginManager);
                i++;
                Constructor<PluginCommand> pluginCommandConstructor = PluginCommand.class.getDeclaredConstructor(String.class,Plugin.class);
                i++;
                pluginCommandConstructor.setAccessible(true);
                i++;
                PluginCommand command = pluginCommandConstructor.newInstance(name,plugin);
                i++;
                commandMap.register(name,command);
                i++;
                plugin.getLogger().info("Registered command " + name + " with index " + i);
                return command;
            } catch (NoSuchFieldException | InvocationTargetException | IllegalAccessException | NoSuchMethodException |
                     InstantiationException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
