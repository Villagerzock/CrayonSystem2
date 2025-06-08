package net.crayonsmp.tjorven;

import net.crayonsmp.CrayonAPI;
import net.crayonsmp.interfaces.CrayonModule;
import net.crayonsmp.tjorven.abilities.BlockTeleport;
import net.crayonsmp.tjorven.abilities.ChainsawAbility;
import net.crayonsmp.tjorven.abilities.DrillAbility;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class TjorvenModule implements CrayonModule {

    @Override
    public String getName() {
        return "Tjorven";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public <API extends Plugin & CrayonAPI> void onEnable(API plugin) {
        PluginManager pluginManager = plugin.getServer().getPluginManager();

        pluginManager.registerEvents(new BlockTeleport(plugin), plugin);
        pluginManager.registerEvents(new ChainsawAbility(), plugin);
        pluginManager.registerEvents(new DrillAbility(plugin), plugin);
    }
}
