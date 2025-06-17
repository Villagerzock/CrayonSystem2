package net.crayonsmp;

import it.sauronsoftware.cron4j.Scheduler;
import net.crayonsmp.commands.*;
import net.crayonsmp.gui.GoalMenuListener;
import net.crayonsmp.interfaces.CrayonModule;
import net.crayonsmp.listeners.DebugListener;
import net.crayonsmp.listeners.PlayerListener;
import net.crayonsmp.services.ConfigService;
import net.crayonsmp.services.DatapackService;
import net.crayonsmp.services.Restart;
import net.crayonsmp.utils.goal.Goal;
import net.crayonsmp.utils.goal.Magic;
import net.crayonsmp.utils.goal.PlayerGoal;
import net.crayonsmp.utils.config.ConfigUtil;
import net.crayonsmp.utils.config.SConfig;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class CrayonDefault implements CrayonModule {


    public DatapackService datapackManager;
    public static SConfig goalConfig;
    public static SConfig playerGoalData;
    public static Plugin plugin;

    @Override
    public void onLoad(CrayonAPI core) {
        core.getLogger().info("Crayon-Defaults loaded!");
    }

    @Override
    public <API extends Plugin & CrayonAPI> void onEnable(API plugin) {
        CrayonDefault.plugin = plugin;
        ConfigurationSerialization.registerClass(Goal.class);
        ConfigurationSerialization.registerClass(Magic.class);
        ConfigurationSerialization.registerClass(PlayerGoal.class);

        goalConfig = ConfigUtil.getConfig("goalconfig", plugin);
        playerGoalData = ConfigUtil.getConfig("playergoaldata", plugin);


        registerCommand("modules",plugin).setExecutor(new ModulesCommand(plugin));
        registerCommand("debugcrayon", plugin).setExecutor(new DebugCommand(plugin));
        registerCommand("goal", plugin).setExecutor(new GoalCommand());
        registerCommand("goalset", plugin).setExecutor(new SetGoalCommand());
        registerCommand("removegoal", plugin).setExecutor(new RemoveGoalCommand());
        registerCommand("crayonreload", plugin).setExecutor(new CrayonReloadCommand());
        registerCommand("magicinfo", plugin).setExecutor(new MagicInfoCommand());
        registerCommand("cr",plugin).setExecutor(new CustomRecipesCommand());

        plugin.getServer().getPluginManager().registerEvents(new DebugListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new GoalMenuListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerListener(), plugin);

        datapackManager = new DatapackService((JavaPlugin) plugin);
        datapackManager.setup();

        ConfigService.registergoalconfig();
        scheduleDailyTasks();
    }

    private void scheduleDailyTasks() {
        Scheduler reload = new Scheduler();
        reload.schedule("0 7,13,17,20,22 * * *", new CrayonReloadCommand());
        reload.start();

        Scheduler restart = new Scheduler();
        restart.schedule("0 3 * * *", new Restart());
        restart.start();
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
    public String getID() {
        return "crayon_defaults";
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
