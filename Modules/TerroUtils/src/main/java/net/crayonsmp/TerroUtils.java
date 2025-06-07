package net.crayonsmp;

import com.nexomc.nexo.api.events.NexoMechanicsRegisteredEvent;
import com.nexomc.nexo.mechanics.MechanicFactory;
import net.crayonsmp.interfaces.CrayonModule;
import org.bukkit.plugin.Plugin;

public class TerroUtils implements CrayonModule {
    @Override
    public String getName() {
        return "net.crayonsmp.TerroUtils";
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
