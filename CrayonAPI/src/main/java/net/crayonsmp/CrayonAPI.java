package net.crayonsmp;

import net.crayonsmp.interfaces.CrayonModule;

import java.util.List;
import java.util.logging.Logger;

public interface CrayonAPI {
    Logger getLogger();
    List<CrayonModule> loadedModules();
    // Weitere Methoden, die Module aufrufen können
}

