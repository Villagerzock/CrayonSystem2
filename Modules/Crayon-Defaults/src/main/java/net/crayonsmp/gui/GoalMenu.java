package net.crayonsmp.gui;

import net.crayonsmp.managers.GoalManager;
import net.crayonsmp.utils.*;
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
        Inventory inv = Bukkit.createInventory(player, 54, "Goal Menu");

        PlayerGoalPlaceholder GoodplayerGoalPlaceholder = GoalManager.getPlayerGoalPlaceholder(GoalManager.getRandomGoalByType(GoalType.good));
        PlayerGoalPlaceholder NeutralplayerGoalPlaceholder = GoalManager.getPlayerGoalPlaceholder(GoalManager.getRandomGoalByType(GoalType.neutral));
        PlayerGoalPlaceholder BadplayerGoalPlaceholder = GoalManager.getPlayerGoalPlaceholder(GoalManager.getRandomGoalByType(GoalType.bad));

        //setGoalTypeButtons
        GoalInventory goalinv = new GoalInventory(inv, GoodplayerGoalPlaceholder, NeutralplayerGoalPlaceholder, BadplayerGoalPlaceholder);
        //----------------------

        // <-- VERSCHOBEN: Zuerst Spieler in die Map einfügen!

        goalinv.setSelectetPlaceholder(GoalType.good);
        GoalMenu.setDefaultModelData(inv); // Resets all to default (unselected)
        inv.setItem(0, new ItemBuilder().setMeterial(Material.IRON_NUGGET).setTitle("GoodGoal").setCustomModelData(2001).build());
        GoalMenu.setSelectetGoal(goalinv); // This will populate the main content
        goalInventories.put(player, goalinv);
        player.openInventory(inv); // Inventar jetzt öffnen
    }

    private static ItemStack generateGoalItem(PlayerGoalPlaceholder playerGoalPlaceholder) {
        ItemBuilder itemBuilder = new ItemBuilder();
        itemBuilder.setMeterial(Material.IRON_NUGGET);
        itemBuilder.setTitle(playerGoalPlaceholder.getGoal().getName());
        itemBuilder.setLore(playerGoalPlaceholder.getGoal().getDescription());
        itemBuilder.setCustomModelData(1000);
        return itemBuilder.build();
    }

    public static ItemStack generateMagicItems(Magic magic, int customModelData){
        ItemBuilder itemBuilder = new ItemBuilder();
        itemBuilder.setMeterial(Material.IRON_NUGGET);
        itemBuilder.setTitle(magic.getName());

        List<String> lore = new ArrayList<>();
        lore.add("§1Descriptsion:");
        magic.getDescription().forEach(lore::add);
        lore.add("§1Theme:");
        magic.getTheme().forEach(lore::add);

        itemBuilder.setLore(lore);
        itemBuilder.setCustomModelData(customModelData);
        return itemBuilder.build();
    }

    public static void setSelectetGoal(GoalInventory goalInventory){
        if(goalInventory.selectetPlaceholder.equals(GoalType.good)){
            setGoalItems(goalInventory.getInv(), goalInventory.getGoodPlaceholder());
        }
        if(goalInventory.selectetPlaceholder.equals(GoalType.neutral)){
            setGoalItems(goalInventory.getInv(), goalInventory.getNeutralPlaceholder());
        }
        if(goalInventory.selectetPlaceholder.equals(GoalType.bad)){
            setGoalItems(goalInventory.getInv(), goalInventory.getBadPlaceholder());
        }
    }


    private static void setGoalItems(Inventory inv, PlayerGoalPlaceholder playerGoalPlaceholders){
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

        inv.setItem(22, generateMagicItems(playerGoalPlaceholders.getMagicPrimeryList().get(0), 1005));
        inv.setItem(24, generateMagicItems(playerGoalPlaceholders.getMagicPrimeryList().get(1), 1006));
        inv.setItem(26, generateMagicItems(playerGoalPlaceholders.getMagicPrimeryList().get(2), 1007));

        inv.setItem(40, generateMagicItems(playerGoalPlaceholders.getMagicSecondaryList().get(0), 1008));
        inv.setItem(42, generateMagicItems(playerGoalPlaceholders.getMagicSecondaryList().get(1), 1009));
    }

    public static void setDefaultModelData(Inventory inv){
        for (int i = 0; i < 3; i++) {
            inv.setItem(i, new ItemBuilder().setMeterial(Material.IRON_NUGGET).setTitle("GoodGoal").setCustomModelData(1000).build());
        }

        for (int i = 3; i < 6; i++) {
            inv.setItem(i, new ItemBuilder().setMeterial(Material.IRON_NUGGET).setTitle("NeutralGoal").setCustomModelData(1000).build());
        }

        for (int i = 6; i < 9; i++) {
            inv.setItem(i, new ItemBuilder().setMeterial(Material.IRON_NUGGET).setTitle("BadGoal").setCustomModelData(1000).build());
        }

        inv.setItem(0, new ItemBuilder().setMeterial(Material.IRON_NUGGET).setTitle("GoodGoal").setCustomModelData(1001).build());

        inv.setItem(3, new ItemBuilder().setMeterial(Material.IRON_NUGGET).setTitle("NeutralGoal").setCustomModelData(1002).build());

        inv.setItem(6, new ItemBuilder().setMeterial(Material.IRON_NUGGET).setTitle("BadGoal").setCustomModelData(1003).build());
    }
}
