package net.crayonsmp.interfaces;


import dev.turingcomplete.textcaseconverter.StandardTextCases;
import dev.turingcomplete.textcaseconverter.StandardWordsSplitters;
import net.crayonsmp.CrayonAPI;
import net.crayonsmp.PluginProvider;
import net.crayonsmp.crafting.CustomCrafting;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public interface CrayonModule {

    String getName();

    default String getID() {
        return StandardTextCases.SNAKE_CASE.convert(getName(), StandardWordsSplitters.SPACES);
    }

    default String getAuthor() {
        return "";
    }

    String getVersion();

    default CustomCrafting.CustomCraftingType<?>[] getCraftingTypes() {
        return new CustomCrafting.CustomCraftingType[]{};
    }

    default void onLoad(CrayonAPI api) {
    } // oder einfach kein Parameter

    <API extends Plugin & CrayonAPI> void onEnable(API plugin);

    default void onDisable() {
    }

    default void addCustomCraftingType(CustomCrafting.CustomCraftingType<?> type) {
        PluginProvider.plugin.getLogger().info("Adding Custom Type: " + getID() + ":" + type.getID());
        CustomCrafting.TYPES.put(getID() + ":" + type.getID(), type);
    }

    default PluginCommand registerCommand(String name, Plugin plugin) {
        try {
            Constructor<PluginCommand> pluginCommandConstructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            pluginCommandConstructor.setAccessible(true);
            PluginCommand command = pluginCommandConstructor.newInstance(name, plugin);

            if (Bukkit.getPluginManager() instanceof SimplePluginManager pluginManager) {
                try {
                    Field commandMapField = plugin.getClass().getDeclaredField("commandMap");
                    commandMapField.setAccessible(true);
                    CommandMap commandMap = (CommandMap) commandMapField.get(pluginManager);
                    commandMap.register(StandardTextCases.SNAKE_CASE.convert(getName(), StandardWordsSplitters.SPACES), command);
                    return command;
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }

            return null;
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
