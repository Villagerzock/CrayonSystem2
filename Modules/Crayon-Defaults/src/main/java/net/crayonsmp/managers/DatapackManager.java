package net.crayonsmp.managers;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class DatapackManager {

    private final JavaPlugin plugin;
    private final Logger logger;
    private final File sourceDatapacksFolder;

    // Speichert, welche Dateien wohin kopiert wurden, zur späteren Bereinigung
    private final Map<World, List<File>> copiedDatapacks = new HashMap<>();

    public DatapackManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.logger = plugin.getLogger();
        // Den Pfad zum Quellordner festlegen
        this.sourceDatapacksFolder = new File(plugin.getDataFolder(), "/datapacks");
    }

    public void setup() {
        logger.info("Initializing DatapackManager...");

        // Quellordner erstellen, falls nicht vorhanden
        if (!sourceDatapacksFolder.exists()) {
            sourceDatapacksFolder.mkdirs();
            logger.info("Plugin datapack source folder created: " + sourceDatapacksFolder.getAbsolutePath());
            logger.info("Place your .zip datapacks inside this folder.");
            return; // Nichts zu kopieren, wenn der Ordner neu ist
        }

        // Überprüfen, ob es sich um einen Ordner handelt
        if (!sourceDatapacksFolder.isDirectory()) {
            logger.severe("Plugin datapack source path is not a directory: " + sourceDatapacksFolder.getAbsolutePath());
            return;
        }

        // Finde alle ZIP-Dateien im Quellordner
        File[] datapackFiles = sourceDatapacksFolder.listFiles((dir, name) -> name.toLowerCase().endsWith(".zip"));

        if (datapackFiles == null || datapackFiles.length == 0) {
            logger.info("No .zip datapacks found in plugin source folder: " + sourceDatapacksFolder.getAbsolutePath());
            return; // Keine Datapacks zum Kopieren
        }

        logger.info("Found " + datapackFiles.length + " .zip datapacks in plugin source folder.");

        // Kopiere Datapacks in den Ordner jeder geladenen Welt
        int copiedCount = 0;
        for (World world : Bukkit.getWorlds()) {
            File worldDatapacksFolder = new File(world.getWorldFolder(), "datapacks");

            // Welt-Datapacks-Ordner erstellen, falls nicht vorhanden
            if (!worldDatapacksFolder.exists()) {
                worldDatapacksFolder.mkdirs();
            }

            // Überprüfen, ob es sich um einen Ordner handelt
            if (!worldDatapacksFolder.isDirectory()) {
                logger.severe("World datapacks path is not a directory for world '" + world.getName() + "': " + worldDatapacksFolder.getAbsolutePath());
                continue; // Diese Welt überspringen
            }


            List<File> copiedInThisWorld = new ArrayList<>();
            for (File datapackFile : datapackFiles) {
                File destinationFile = new File(worldDatapacksFolder, datapackFile.getName());

                try {
                    // Datei kopieren, existierende überschreiben
                    Files.copy(datapackFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    copiedInThisWorld.add(destinationFile); // Zum Löschen vormerken
                    copiedCount++;
                    logger.info("Copied datapack '" + datapackFile.getName() + "' to world '" + world.getName() + "'.");
                } catch (IOException e) {
                    logger.severe("Failed to copy datapack '" + datapackFile.getName() + "' to world '" + world.getName() + "': " + e.getMessage());
                    e.printStackTrace();
                }
            }
            if (!copiedInThisWorld.isEmpty()) {
                copiedDatapacks.put(world, copiedInThisWorld);
            }
        }

        if (copiedCount > 0) {
            logger.info("Finished copying " + copiedCount + " datapack file(s). Triggering server reload...");
            // Trigger einen Server-Reload, um die Datapacks zu laden
            // Führe den Befehl als Konsole aus. Zeitverzögerung, falls der Server noch mit Kopieren beschäftigt ist.
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                Bukkit.dispatchCommand(console, "minecraft:reload");
                logger.info("Server reload command executed.");
            }, 40L); // Etwas längere Verzögerung zur Sicherheit
        } else {
            logger.info("No datapacks were copied to any world.");
        }
    }

    /**
     * Bereinigt die kopierten Datapacks beim Herunterfahren/Deaktivieren.
     */
    public void cleanup() {
        logger.info("Cleaning up copied datapacks...");

        // Lösche die kopierten Datapacks
        int deletedCount = 0;
        // Erstelle eine Kopie der Entry-Liste, falls die Map während der Iteration modifiziert wird (obwohl unwahrscheinlich hier)
        List<Map.Entry<World, List<File>>> entries = new ArrayList<>(copiedDatapacks.entrySet());

        for (Map.Entry<World, List<File>> entry : entries) {
            World world = entry.getKey();
            List<File> filesToDelete = entry.getValue();

            // Überprüfe, ob die Welt noch geladen ist, bevor versucht wird, auf den Ordner zuzugreifen
            // Dies ist wichtig, falls eine Welt entladen wurde, aber der Plugin Disable Code noch läuft
            if (Bukkit.getWorld(world.getUID()) == null) {
                logger.warning("World '" + world.getName() + "' is no longer loaded, cannot clean up datapacks in its folder.");
                continue; // Überspringe diese Welt
            }


            for (File file : filesToDelete) {
                if (file.exists()) {
                    if (file.delete()) {
                        deletedCount++;
                        // Info-Log kann bei vielen Dateien zu spammy sein, Debug wäre eine Alternative
                         logger.info("Deleted datapack '" + file.getName() + "' from world '" + world.getName() + "'.");
                    } else {
                        // Dies kann passieren, wenn die Datei noch von etwas anderem blockiert wird
                        logger.warning("Failed to delete datapack '" + file.getName() + "' from world '" + world.getName() + "'. It might need manual removal.");
                    }
                } else {
                     logger.info("Datapack file '" + file.getName() + "' in world '" + world.getName() + "' not found during cleanup.");
                }
            }
        }

        copiedDatapacks.clear(); // Referenzen löschen
        logger.info("Cleanup finished. " + deletedCount + " datapack file(s) attempted to delete.");

        // Einen optionalen Reload nach der Bereinigung kannst du hier wieder aktivieren, falls gewünscht.
    }
}