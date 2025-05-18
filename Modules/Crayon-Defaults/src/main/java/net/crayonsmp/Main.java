package net.crayonsmp;

import net.crayonsmp.commands.DebugCommand;
import net.crayonsmp.commands.ModulesCommand;
import net.crayonsmp.interfaces.CrayonModule;
import net.crayonsmp.listeners.DebugListener;
import net.crayonsmp.managers.DatapackManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main implements CrayonModule {


    public DatapackManager datapackManager;

    @Override
    public void onLoad(CrayonAPI core) {
        core.getLogger().info("Crayon-Defaults loaded!");
    }

    @Override
    public <API extends Plugin & CrayonAPI> void onEnable(API plugin) {


        registerCommand("modules",plugin).setExecutor(new ModulesCommand(plugin));
        registerCommand("debugcrayon", plugin).setExecutor(new DebugCommand(plugin));

        plugin.getServer().getPluginManager().registerEvents(new DebugListener(), plugin);

        datapackManager = new DatapackManager((JavaPlugin) plugin);
        datapackManager.setup();
    }

    @Override
    public void onDisable() {
        datapackManager.cleanup();
    }

    @Override
    public String getName() {
        return "Crayon-Defaults";
    }

    @Override
    public String getVersion() { return "0.0.1"; }

    @Override
    public String getAuthor() {
        return "Terrocraft, Villagerzock";
    }
}
