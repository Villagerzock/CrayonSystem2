package net.crayonsmp.managers;

import org.bukkit.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatManager {

    private static final Pattern START_WITH_COLOR_PATTERN = Pattern.compile(
            "^(?:[&§][0-9a-fk-or]|#[a-fA-F0-9]{6}).*", // Non-capturing group for the alternatives
            Pattern.CASE_INSENSITIVE // Hex-Codes können Groß- oder Kleinbuchstaben enthalten
    );

    public static String format(String message) {
        // Erstelle einen Matcher für die aktuelle Nachricht
        Matcher matcher = START_WITH_COLOR_PATTERN.matcher(message);

        // Wenn die Nachricht NICHT mit einem bekannten Farb- oder Formatierungscode beginnt,
        // füge &7 (grau) am Anfang hinzu.
        if (!matcher.matches()) {
            message = "&7" + message;
        }

        String translatedMessage = ChatColor.translateAlternateColorCodes('&', message);

        return hex(translatedMessage);
    }

    public static String hex(String message) {
        // Updated pattern to look for hex codes enclosed in < >
        Pattern pattern = Pattern.compile("<#[a-fA-F0-9]{6}>");
        Matcher matcher = pattern.matcher(message);
        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            String fullHexCode = matcher.group(); // e.g., <#RRGGBB>
            // Extract just the hex part without the brackets
            String hexCode = fullHexCode.substring(1, fullHexCode.length() - 1); // e.g., #RRGGBB

            String replaceSharp = hexCode.replace('#', 'x');

            char[] ch = replaceSharp.toCharArray();
            StringBuilder builder = new StringBuilder();
            for (char c : ch) {
                builder.append("&").append(c);
            }

            // Replace the entire <#RRGGBB> with the Bukkit format
            matcher.appendReplacement(result, builder.toString());
        }

        matcher.appendTail(result);
        return ChatColor.translateAlternateColorCodes('&', result.toString());
    }
}