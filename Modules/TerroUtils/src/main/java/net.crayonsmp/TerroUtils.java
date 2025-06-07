package net.crayonsmp;

import net.crayonsmp.interfaces.CrayonModule;
import org.bukkit.plugin.Plugin;

public class TerroUtils implements CrayonModule {
    @Override
    public String getName() {
        return "TerroUtils";
    }

    @Override
    public String getID() {
        return "terroutils";
    }

    @Override
    public String getVersion() {
        return "0.0.1";
    }

    @Override
    public <API extends Plugin & CrayonAPI> void onEnable(API plugin) {

    }
}
