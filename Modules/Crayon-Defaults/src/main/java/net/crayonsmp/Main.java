package net.crayonsmp;

import net.crayonsmp.commands.DebugCommand;
import net.crayonsmp.commands.ModulesCommand;
import net.crayonsmp.interfaces.CrayonModule;
import net.crayonsmp.listeners.DebugListener;
import org.bukkit.plugin.Plugin;

public class Main implements CrayonModule {

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

    @Override
    public void onLoad(CrayonAPI core) {
        core.getLogger().info("Crayon-Defaults loaded!");
    }

    @Override
    public <API extends Plugin & CrayonAPI> void onEnable(API plugin) {
        registerCommand("modules",plugin).setExecutor(new ModulesCommand(plugin));
        registerCommand("debugcrayon", plugin).setExecutor(new DebugCommand());

        plugin.getServer().getPluginManager().registerEvents(new DebugListener(), plugin);
    }

    @Override
    public void onDisable() {
        // Aufräumen
    }
}
