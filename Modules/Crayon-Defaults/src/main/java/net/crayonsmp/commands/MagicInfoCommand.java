package net.crayonsmp.commands;

import net.crayonsmp.utils.ChatUtil;
import net.crayonsmp.services.GoalService;
import net.crayonsmp.utils.goal.Magic;
import org.bukkit.command.*; // Importiere alle nötigen Command-bezogenen Klassen
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class MagicInfoCommand implements TabCompleter, CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String [] args) {
        if (!command.getName().equalsIgnoreCase("magicinfo")) {
            return false;
        }

        if (args.length != 1) {
            ChatUtil.sendMessage(sender, "<#cb9e83>Use: <#b4b4b4>/magicinfo <MAGIEID>"); // Angepasste Farbe
            return true;
        }

        String magicId = args[0];

        Magic magic = GoalService.getMagicById(magicId);

        if (magic == null) {
            ChatUtil.sendMessage(sender, "<#a63851>Magic with ID: '<#b4b4b4>" + magicId + "<#a63851>' can not be found."); // Angepasste Farbe
            return true;
        }

        ChatUtil.sendMessage(sender, "<#b4b4b4>------ Magic-Informations: §r" + magic.getName() + " <#b4b4b4>------"); // Angepasste Farben

        if (magic.getDescription() != null && !magic.getDescription().isEmpty()) {
            ChatUtil.sendMessage(sender, "<#cb9e83>Description:<#b4b4b4>"); // Angepasste Farben
            for (String line : magic.getDescription()) {
                ChatUtil.sendMessage(sender, "<#b4b4b4>" + line); // Angepasste Farbe
            }
        } else {
            ChatUtil.sendMessage(sender, "<#cb9e83>Description: <#c99c81>N/A"); // Angepasste Farben
        }

        if (magic.getTheme() != null && !magic.getTheme().isEmpty()) {
            ChatUtil.sendMessage(sender, "<#cb9e83>Theme:<#b4b4b4>"); // Angepasste Farben
            for (String line : magic.getTheme()) {
                ChatUtil.sendMessage(sender, "<#b4b4b4>" + line); // Angepasste Farbe (Hier vielleicht eine andere Farbe für das Thema?)
            }
        } else {
            ChatUtil.sendMessage(sender, "<#cb9e83>Theme: <#c99c81>N/A"); // Angepasste Farben
        }
        ChatUtil.sendMessage(sender, "<#455f7f>-----------------------------------"); // Angepasste Farben

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!command.getName().equalsIgnoreCase("magicinfo")) {
            return Collections.emptyList(); // Leere Liste zurückgeben, wenn es nicht der richtige Befehl ist
        }

        if (args.length == 1) {
            List<String> completions = new ArrayList<>();
            Set<String> magicIds = GoalService.RegistertMagics.keySet(); // Alle registrierten Magie-IDs abrufen

            StringUtil.copyPartialMatches(args[0], magicIds, completions);

            Collections.sort(completions); // Vorschläge alphabetisch sortieren
            return completions;
        }

        return Collections.emptyList(); // Für alle anderen Argumente oder Fälle eine leere Liste zurückgeben
    }
}