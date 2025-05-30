package net.crayonsmp;

import net.crayonsmp.commands.*;
import net.crayonsmp.gui.GoalMenuListener;
import net.crayonsmp.interfaces.CrayonModule;
import net.crayonsmp.listeners.DebugListener;
import net.crayonsmp.listeners.PlayerListener;
import net.crayonsmp.managers.ConfigManager;
import net.crayonsmp.managers.DatapackManager;
import net.crayonsmp.utils.Goal;
import net.crayonsmp.utils.Magic;
import net.crayonsmp.utils.PlayerGoal;
import net.crayonsmp.utils.config.ConfigUtil;
import net.crayonsmp.utils.config.SConfig;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main implements CrayonModule {


    public DatapackManager datapackManager;
    public static SConfig GoalConfig;
    public static SConfig PlayerGoalData;
    public static Plugin plugin;

    @Override
    public void onLoad(CrayonAPI core) {
        core.getLogger().info("Crayon-Defaults loaded!");
    }

    @Override
    public <API extends Plugin & CrayonAPI> void onEnable(API plugin) {
        plugin = plugin;
        ConfigurationSerialization.registerClass(Goal.class);
        ConfigurationSerialization.registerClass(Magic.class);
        ConfigurationSerialization.registerClass(PlayerGoal.class);

        GoalConfig = ConfigUtil.getConfig("goalconfig", plugin);
        PlayerGoalData = ConfigUtil.getConfig("playergoaldata", plugin);


        registerCommand("modules",plugin).setExecutor(new ModulesCommand(plugin));
        registerCommand("debugcrayon", plugin).setExecutor(new DebugCommand(plugin));
        registerCommand("goal", plugin).setExecutor(new GoalCommand());
        registerCommand("goalset", plugin).setExecutor(new SetGoalCommand());
        registerCommand("removegoal", plugin).setExecutor(new removeGoalCommand());

        plugin.getServer().getPluginManager().registerEvents(new DebugListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new GoalMenuListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerListener(), plugin);

        datapackManager = new DatapackManager((JavaPlugin) plugin);
        datapackManager.setup();

        ConfigManager.registergoalconfig();
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

    public static Plugin getPlugin() {
        return plugin;
    }
}
