package net.crayonsmp;

import net.crayonsmp.commands.TestCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

public class Main implements CrayonModule {

    @Override
    public String getName() {
        return "Crayon Defaults";
    }

    @Override
    public void onLoad(CrayonAPI core) {
        core.getLogger().info("Crayon-Defaults loaded!");
    }

    @Override
    public void onEnable(Plugin plugin) {
        registerCommand("newCommand",plugin).setExecutor(new TestCommand());
    }
}
