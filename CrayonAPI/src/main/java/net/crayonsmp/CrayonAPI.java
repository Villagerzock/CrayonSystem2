package net.crayonsmp;

import org.checkerframework.checker.units.qual.C;

import java.util.List;
import java.util.logging.Logger;

public interface CrayonAPI {
    Logger getLogger();
    List<CrayonModule> loadedModules();
    // Weitere Methoden, die Module aufrufen können
}

