package net.crayonsmp;

import net.crayonsmp.enums.MISBlockType;
import net.crayonsmp.interfaces.CrayonModule;
import net.crayonsmp.objects.blocks.MagicalDrive;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;

public class MagicalItemStorage implements CrayonModule {
    public static Plugin plugin;

    @Override
    public <API extends Plugin & CrayonAPI> void onEnable(API plugin) {
        MagicalItemStorage.plugin = plugin;

    }


    @Override
    public String getName() {
        return "MagicalItemStorage";
    }

    @Override
    public String getID() {
        return "mis";
    }

    @Override
    public String getAuthor() {
        return "TamashiiMon";
    }

    @Override
    public String getVersion() {
        return "0.0.1-Dev";
    }
}