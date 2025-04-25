package net.crayonsmp.crayonCore;

import net.crayonsmp.CrayonAPI;
import net.crayonsmp.interfaces.CrayonModule;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;


public class CrayonCore extends JavaPlugin implements CrayonAPI {

    private final List<CrayonModule> loadedModules = new ArrayList<>();

    @Override
    public void onLoad() {
        getLogger().info("Loading CrayonCore...");
        getLogger().info("Scanning for modules...");

        String modulePackage = "net.crayonsmp";

        Reflections reflections;
        try {
            reflections = new Reflections(modulePackage);
        } catch (Throwable t) {
            if (isDebugMode()) getLogger().log(Level.SEVERE, "Failed to initialize Reflections for package: " + modulePackage, t);
            return;
        }

        Set<Class<? extends CrayonModule>> moduleClasses = reflections.getSubTypesOf(CrayonModule.class);
        if (isDebugMode()) getLogger().info("Found " + moduleClasses.size() + " potential module classes in package: " + modulePackage);

        for (Class<? extends CrayonModule> moduleClass : moduleClasses) {
            String moduleClassName = moduleClass.getName();
            getLogger().info("Attempting to load module class: " + moduleClassName);
            try {
                if (isDebugMode()) getLogger().info("  Instantiating " + moduleClassName + "...");
                CrayonModule module = moduleClass.getDeclaredConstructor().newInstance();
                if (isDebugMode()) getLogger().info("  Successfully instantiated " + moduleClassName);

                if (isDebugMode()) getLogger().info("  Calling onLoad() for " + module.getName() + " (" + moduleClassName + ")...");
                module.onLoad(this);
                if (isDebugMode()) getLogger().info("  Finished onLoad() for " + module.getName());

                if (isDebugMode()) getLogger().info("  Adding " + module.getName() + " to loaded modules list...");
                loadedModules.add(module);
                if (isDebugMode()) getLogger().info("  Successfully loaded and processed module: " + module.getName());

            } catch (Throwable e) {
                getLogger().log(Level.SEVERE, "!!! CRITICAL FAILURE loading module: " + moduleClassName + " !!!", e);
            }
        }
        getLogger().info("Finished module loading sequence. Total modules loaded: " + loadedModules.size());
    }

    @Override
    public void onEnable() {
        getLogger().info("Enabling CrayonCore...");
        getLogger().info("Number of loaded modules to enable: " + loadedModules.size());

        if (loadedModules.isEmpty()) {
            getLogger().warning("No modules were loaded successfully. Check previous logs for errors during onLoad.");
        }

        for (CrayonModule module : loadedModules) {
            String moduleName = module.getName();
            if (isDebugMode()) getLogger().info("Attempting to enable module: " + moduleName + " (" + module.getClass().getName() + ")");
            try {
                module.onEnable(this);
                getLogger().info("Successfully enabled module: " + moduleName);

            } catch (Throwable e) {
                getLogger().log(Level.SEVERE, "!!! CRITICAL FAILURE enabling module: " + moduleName + " !!!", e);
            }
        }
        getLogger().info("Finished enabling modules.");
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling CrayonCore...");
        getLogger().info("Number of loaded modules to disable: " + loadedModules.size());
        for (CrayonModule module : loadedModules) {
            try {
                if (isDebugMode()) getLogger().info("Disabling module: " + module.getName());
                module.onDisable();
                getLogger().info("Successfully disabled module: " + module.getName());
            } catch (Throwable e) {
                getLogger().log(Level.SEVERE,"Error disabling module " + module.getName(), e);
            }
        }
        loadedModules.clear();
        getLogger().info("Finished disabling modules.");
    }

    @Override
    public List<CrayonModule> loadedModules() {
        return loadedModules;
    }

    @Override
    public Boolean isDebugMode() {
        return true;
    }
}