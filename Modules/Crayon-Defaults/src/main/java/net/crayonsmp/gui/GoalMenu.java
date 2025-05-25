package net.crayonsmp.gui;

import net.crayonsmp.managers.ChatManager;
import net.crayonsmp.managers.GoalManager;
import net.crayonsmp.utils.*; // Stelle sicher, dass alle benötigten Utilities hier sind
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GoalMenu {
    public static HashMap<Player, GoalInventory> goalInventories = new HashMap<>();

    public static void openGoalMenu(Player player) {
        if (player == null) return;
        if (GoalManager.hasPlayerGoalData(player)) return;
        Inventory inv = Bukkit.createInventory(player, 54, "<shift:-37><glyph:menu_goals>");

        PlayerGoalPlaceholder GoodplayerGoalPlaceholder = GoalManager.getPlayerGoalPlaceholder(GoalManager.getRandomGoalByType(GoalType.good));
        PlayerGoalPlaceholder NeutralplayerGoalPlaceholder = GoalManager.getPlayerGoalPlaceholder(GoalManager.getRandomGoalByType(GoalType.neutral));
        PlayerGoalPlaceholder BadplayerGoalPlaceholder = GoalManager.getPlayerGoalPlaceholder(GoalManager.getRandomGoalByType(GoalType.bad));

        //setGoalTypeButtons
        GoalInventory goalinv = new GoalInventory(inv, GoodplayerGoalPlaceholder, NeutralplayerGoalPlaceholder, BadplayerGoalPlaceholder);
        //----------------------

        // Zuerst Spieler in die Map einfügen, falls die setSelectetGoal oder setDefaultModelData
        // intern auf goalInventories zugreifen sollten, bevor das Inventar geöffnet wird.
        // Das ist hier nicht direkt der Fall, aber eine gute Praxis für den Fall.
        goalInventories.put(player, goalinv); // Hinzugefügt: Spieler und GoalInventory in die Map

        goalinv.setSelectetPlaceholder(GoalType.good);
        setDefaultModelData(inv); // Resets all to default (unselected)
        // Das Item an Slot 0 wird hier gesetzt, um den "GoodGoal" als aktiv zu markieren.
        // Die CustomModelData 2001 sollte anzeigen, dass dieser Typ ausgewählt ist.
        inv.setItem(0, new ItemBuilder().setMeterial(Material.IRON_NUGGET).setTitle("§rGoodGoal").setCustomModelData(2001).build());
        setSelectetGoal(goalinv); // This will populate the main content based on the selected goal type

        player.openInventory(inv); // Inventar jetzt öffnen
    }

    private static ItemStack generateGoalItem(PlayerGoalPlaceholder playerGoalPlaceholder) {
        ItemBuilder itemBuilder = new ItemBuilder();
        itemBuilder.setMeterial(Material.IRON_NUGGET);
        itemBuilder.setTitle("§r" + playerGoalPlaceholder.getGoal().getName());

        // FIX: Erstelle eine NEUE, modifizierbare Liste aus der unmodifizierbaren Liste
        List<String> lore = new ArrayList<>(playerGoalPlaceholder.getGoal().getDescription());

        // Jetzt kannst du replaceAll auf der neuen Liste aufrufen.
        // ChatManager.format() kümmert sich jetzt um die Standardfarbe.
        lore.replaceAll(ChatManager::format);

        itemBuilder.setLore(lore);
        itemBuilder.setCustomModelData(1000);
        return itemBuilder.build();
    }

    public static ItemStack generateMagicItems(Magic magic, int customModelData){
        ItemBuilder itemBuilder = new ItemBuilder();
        itemBuilder.setMeterial(Material.IRON_NUGGET);
        itemBuilder.setTitle("§r" + magic.getName());

        List<String> lore = new ArrayList<>();
        // Verwende den Hex-Code für die Überschriften, um Konsistenz mit dem ChatManager zu gewährleisten.
        // ChatManager.format() wird dies dann korrekt verarbeiten.
        lore.add("Description:"); // Beispiel: Fett und die Standardfarbe
        magic.getDescription().forEach(lore::add); // Füge Beschreibungszeilen hinzu
        lore.add("Theme:"); // Beispiel: Fett und die Standardfarbe
        magic.getTheme().forEach(lore::add); // Füge Themenzeilen hinzu

        // Alle gesammelten Zeilen gemeinsam formatieren.
        // ChatManager.format() fügt automatisch die Standardfarbe hinzu,
        // wenn eine Zeile noch keine Farbe hat.
        lore.replaceAll(ChatManager::format);

        itemBuilder.setLore(lore);
        itemBuilder.setCustomModelData(customModelData);
        return itemBuilder.build();
    }

    public static void setSelectetGoal(GoalInventory goalInventory){
        // Verwende if-else if für exklusive Bedingungen, um Redundanz zu vermeiden
        if(goalInventory.selectetPlaceholder.equals(GoalType.good)){
            setGoalItems(goalInventory.getInv(), goalInventory.getGoodPlaceholder());
        } else if(goalInventory.selectetPlaceholder.equals(GoalType.neutral)){
            setGoalItems(goalInventory.getInv(), goalInventory.getNeutralPlaceholder());
        } else if(goalInventory.selectetPlaceholder.equals(GoalType.bad)){
            setGoalItems(goalInventory.getInv(), goalInventory.getBadPlaceholder());
        }
    }


    private static void setGoalItems(Inventory inv, PlayerGoalPlaceholder playerGoalPlaceholders){
        // Generiere und setze die Haupt-Goal-Items
        for (int i = 18; i < 21; i++) {
            inv.setItem(i, generateGoalItem(playerGoalPlaceholders));
        }
        for (int i = 27; i < 30; i++) {
            inv.setItem(i, generateGoalItem(playerGoalPlaceholders));
        }
        for (int i = 36; i < 39; i++) {
            inv.setItem(i, generateGoalItem(playerGoalPlaceholders));
        }
        for (int i = 45; i < 48; i++) {
            inv.setItem(i, generateGoalItem(playerGoalPlaceholders));
        }

        // Generiere und setze die primären Magie-Items
        // Stelle sicher, dass die Listen nicht leer sind, bevor du get(index) aufrufst
        if (playerGoalPlaceholders.getMagicPrimeryList() != null && playerGoalPlaceholders.getMagicPrimeryList().size() >= 3) {
            inv.setItem(22, generateMagicItems(playerGoalPlaceholders.getMagicPrimeryList().get(0), 1005));
            inv.setItem(24, generateMagicItems(playerGoalPlaceholders.getMagicPrimeryList().get(1), 1006));
            inv.setItem(26, generateMagicItems(playerGoalPlaceholders.getMagicPrimeryList().get(2), 1007));
        } else {
            Bukkit.getLogger().warning("Primary magic list is insufficient for GoalTemplate: " + playerGoalPlaceholders.getGoal().getName());
            // Optional: Setze hier leere Items oder Platzhalter, wenn die Liste zu kurz ist
        }


        // Generiere und setze die sekundären Magie-Items
        if (playerGoalPlaceholders.getMagicSecondaryList() != null && playerGoalPlaceholders.getMagicSecondaryList().size() >= 2) {
            inv.setItem(40, generateMagicItems(playerGoalPlaceholders.getMagicSecondaryList().get(0), 1008));
            inv.setItem(42, generateMagicItems(playerGoalPlaceholders.getMagicSecondaryList().get(1), 1009));
        } else {
            Bukkit.getLogger().warning("Secondary magic list is insufficient for GoalTemplate: " + playerGoalPlaceholders.getGoal().getName());
            // Optional: Setze hier leere Items oder Platzhalter, wenn die Liste zu kurz ist
        }

    }

    public static void resetPrimeryMagics(Inventory inv, PlayerGoalPlaceholder playerGoalPlaceholders){
        if (playerGoalPlaceholders.getMagicPrimeryList() != null && playerGoalPlaceholders.getMagicPrimeryList().size() >= 3) {
            inv.setItem(22, generateMagicItems(playerGoalPlaceholders.getMagicPrimeryList().get(0), 1005));
            inv.setItem(24, generateMagicItems(playerGoalPlaceholders.getMagicPrimeryList().get(1), 1006));
            inv.setItem(26, generateMagicItems(playerGoalPlaceholders.getMagicPrimeryList().get(2), 1007));
        } else {
            Bukkit.getLogger().warning("Primary magic list is insufficient for GoalTemplate: " + playerGoalPlaceholders.getGoal().getName());
            // Optional: Setze hier leere Items oder Platzhalter, wenn die Liste zu kurz ist
        }
    }
    public static void resetSecondaryMagics(Inventory inv, PlayerGoalPlaceholder playerGoalPlaceholders){
        if (playerGoalPlaceholders.getMagicSecondaryList() != null && playerGoalPlaceholders.getMagicSecondaryList().size() >= 2) {
            inv.setItem(40, generateMagicItems(playerGoalPlaceholders.getMagicSecondaryList().get(0), 1008));
            inv.setItem(42, generateMagicItems(playerGoalPlaceholders.getMagicSecondaryList().get(1), 1009));
        } else {
            Bukkit.getLogger().warning("Secondary magic list is insufficient for GoalTemplate: " + playerGoalPlaceholders.getGoal().getName());
            // Optional: Setze hier leere Items oder Platzhalter, wenn die Liste zu kurz ist
        }
    }

    public static void setDefaultModelData(Inventory inv){
        // Setzt die Standard-CustomModelData für alle Goal-Typ-Buttons
        // (Slots 0-8 sind die Typ-Buttons)
        for (int i = 0; i < 3; i++) {
            inv.setItem(i, new ItemBuilder().setMeterial(Material.IRON_NUGGET).sethidetooltip(true).setCustomModelData(1000).build());
        }

        for (int i = 3; i < 6; i++) {
            inv.setItem(i, new ItemBuilder().setMeterial(Material.IRON_NUGGET).sethidetooltip(true).setCustomModelData(1000).build());
        }

        for (int i = 6; i < 9; i++) {
            inv.setItem(i, new ItemBuilder().setMeterial(Material.IRON_NUGGET).sethidetooltip(true).setCustomModelData(1000).build());
        }

        // Setzt die spezifischen CustomModelData für die "ausgewählten" Buttons
        // (Diese werden dann später durch setSelectetGoal überschrieben, wenn ein Typ aktiv ausgewählt wird)
        inv.setItem(0, new ItemBuilder().setMeterial(Material.IRON_NUGGET).sethidetooltip(true).setCustomModelData(1001).build());
        inv.setItem(3, new ItemBuilder().setMeterial(Material.IRON_NUGGET).sethidetooltip(true).setCustomModelData(1002).build());
        inv.setItem(6, new ItemBuilder().setMeterial(Material.IRON_NUGGET).sethidetooltip(true).setCustomModelData(1003).build());
    }
}