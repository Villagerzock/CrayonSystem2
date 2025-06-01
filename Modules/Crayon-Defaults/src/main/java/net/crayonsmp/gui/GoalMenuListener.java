package net.crayonsmp.gui;

import net.crayonsmp.Main;
import net.crayonsmp.managers.GoalManager;
import net.crayonsmp.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

import java.util.Objects;

public class GoalMenuListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player p = event.getPlayer();
        if(!GoalManager.hasPlayerGoalData(p)){
            GoalMenu.openGoalMenu(p);
        }
    }

    @EventHandler
    public void onPlayerCloseInv(InventoryCloseEvent event){
        Player p = (Player) event.getPlayer();

        if(GoalMenu.goalInventories.containsKey(p) && !GoalManager.hasPlayerGoalData(p)){
            GoalInventory goalInventory = GoalMenu.goalInventories.get(p);
            if (goalInventory.selectetPlaceholder != null && goalInventory.selectetPrimaryMagic != null && goalInventory.selectetSecondaryMagic != null){
                if (goalInventory.selectetPlaceholder.equals(GoalType.good)) {
                    GoalManager.addPlayerGoalData(p.getUniqueId().toString(), new PlayerGoal(goalInventory.getGoodPlaceholder().getGoal(), goalInventory.selectetPrimaryMagic, goalInventory.selectetSecondaryMagic));
                    GoalMenu.goalInventories.remove(p);
                    p.sendMessage("You can always look at your goal with /goal");
                }
                else if (goalInventory.selectetPlaceholder.equals(GoalType.neutral)) {
                    GoalManager.addPlayerGoalData(p.getUniqueId().toString(), new PlayerGoal(goalInventory.getNeutralPlaceholder().getGoal(), goalInventory.selectetPrimaryMagic, goalInventory.selectetSecondaryMagic));
                    GoalMenu.goalInventories.remove(p);
                    p.sendMessage("You can always look at your goal with /goal");
                }
                else if (goalInventory.selectetPlaceholder.equals(GoalType.bad)) {
                    GoalManager.addPlayerGoalData(p.getUniqueId().toString(), new PlayerGoal(goalInventory.getBadPlaceholder().getGoal(), goalInventory.selectetPrimaryMagic, goalInventory.selectetSecondaryMagic));
                    GoalMenu.goalInventories.remove(p);
                    p.sendMessage("You can always look at your goal with /goal");
                }
                } else {
                Bukkit.getScheduler().runTaskLater(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("CrayonCore")), () -> {
                    // Zusätzliche Prüfungen, um sicherzustellen, dass der Spieler noch online und gültig ist
                    if (p.isOnline() && GoalMenu.goalInventories.containsKey(p)) {
                        p.openInventory(goalInventory.getInv());
                    }
                }, 1L);
            }
        } else if (GoalMenu.goalInventories.containsKey(p) && GoalManager.hasPlayerGoalData(p)) {
            GoalMenu.goalInventories.remove(p);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        Player p = (Player) event.getWhoClicked();
        if(GoalMenu.goalInventories.containsKey(p)){
            if(GoalMenu.goalInventories.get(p).getInv().equals(event.getInventory())){
                GoalInventory Goalinv = GoalMenu.goalInventories.get(p);
                Inventory inv = Goalinv.getInv();

                if(event.getCurrentItem() == null) return;
                if(event.getCurrentItem().getItemMeta() == null) return;

                int clickedSlot = event.getSlot();

                if (clickedSlot >= 0 && clickedSlot <= 2) {
                    if (Goalinv.getSelectetPlaceholder() != GoalType.good) {
                        Goalinv.setSelectetPlaceholder(GoalType.good);
                        GoalMenu.setDefaultModelData(Goalinv); // Resets all to default (unselected)
                        // Highlight the specific "main" GoodGoal button at slot 0
                        inv.setItem(0, new ItemBuilder().setMeterial(Material.IRON_NUGGET).sethidetooltip(true).setCustomModelData(2001).build());
                        Goalinv.setSelectetPrimaryMagic(null);
                        Goalinv.setSelectetSecondaryMagic(null);
                        GoalMenu.setSelectetGoal(Goalinv); // This will populate the main content
                        p.playSound(p, Sound.UI_BUTTON_CLICK, 1, 1);
                        event.setCancelled(true);
                    }
                }
                else if (clickedSlot >= 3 && clickedSlot <= 5) {
                    if (Goalinv.getSelectetPlaceholder() != GoalType.neutral) {
                        Goalinv.setSelectetPlaceholder(GoalType.neutral);
                        GoalMenu.setDefaultModelData(Goalinv); // Resets all to default
                        // Highlight the specific "main" NeutralGoal button at slot 3
                        inv.setItem(3, new ItemBuilder().setMeterial(Material.IRON_NUGGET).sethidetooltip(true).setCustomModelData(2002).build());
                        Goalinv.setSelectetPrimaryMagic(null);
                        Goalinv.setSelectetSecondaryMagic(null);
                        GoalMenu.setSelectetGoal(Goalinv);
                        p.playSound(p, Sound.UI_BUTTON_CLICK, 1, 1);
                        event.setCancelled(true);
                    }
                }
                else if (clickedSlot >= 6 && clickedSlot <= 8) {
                    if (Goalinv.getSelectetPlaceholder() != GoalType.bad) {
                        Goalinv.setSelectetPlaceholder(GoalType.bad);
                        GoalMenu.setDefaultModelData(Goalinv); // Resets all to default
                        inv.setItem(6, new ItemBuilder().setMeterial(Material.IRON_NUGGET).sethidetooltip(true).setCustomModelData(2003).build());
                        Goalinv.setSelectetPrimaryMagic(null);
                        Goalinv.setSelectetSecondaryMagic(null);
                        GoalMenu.setSelectetGoal(Goalinv);
                        p.playSound(p, Sound.UI_BUTTON_CLICK, 1, 1);
                        event.setCancelled(true);
                    }
                }
                else if (clickedSlot == 22) {
                    Magic magic = null;
                        switch (Goalinv.selectetPlaceholder){
                            case good:
                                magic = Goalinv.getGoodPlaceholder().getMagicPrimeryList().get(0);
                                GoalMenu.resetPrimeryMagics(Goalinv.getInv(), Goalinv.getGoodPlaceholder());
                                break;
                            case neutral:
                                magic = Goalinv.getNeutralPlaceholder().getMagicPrimeryList().get(0);
                                GoalMenu.resetPrimeryMagics(Goalinv.getInv(), Goalinv.getNeutralPlaceholder());
                                break;
                            case bad:
                                magic = Goalinv.getBadPlaceholder().getMagicPrimeryList().get(0);
                                GoalMenu.resetPrimeryMagics(Goalinv.getInv(), Goalinv.getBadPlaceholder());
                                break;
                        }
                        Goalinv.setSelectetPrimaryMagic(magic);
                        inv.setItem(22, GoalMenu.generateMagicItems(magic, 2005));
                        event.setCancelled(true);
                    p.playSound(p, Sound.UI_BUTTON_CLICK, 1, 1);
                }
                else if (clickedSlot == 24) {
                    Magic magic = null;
                    switch (Goalinv.selectetPlaceholder){
                        case good:
                            magic = Goalinv.getGoodPlaceholder().getMagicPrimeryList().get(1);
                            GoalMenu.resetPrimeryMagics(Goalinv.getInv(), Goalinv.getGoodPlaceholder());
                            break;
                        case neutral:
                            magic = Goalinv.getNeutralPlaceholder().getMagicPrimeryList().get(1);
                            GoalMenu.resetPrimeryMagics(Goalinv.getInv(), Goalinv.getNeutralPlaceholder());
                            break;
                        case bad:
                            magic = Goalinv.getBadPlaceholder().getMagicPrimeryList().get(1);
                            GoalMenu.resetPrimeryMagics(Goalinv.getInv(), Goalinv.getBadPlaceholder());
                            break;
                    }
                    Goalinv.setSelectetPrimaryMagic(magic);
                    inv.setItem(24, GoalMenu.generateMagicItems(magic, 2006));
                    event.setCancelled(true);
                    p.playSound(p, Sound.UI_BUTTON_CLICK, 1, 1);
                }
                else if (clickedSlot == 26) {
                    Magic magic = null;
                    switch (Goalinv.selectetPlaceholder){
                        case good:
                            magic = Goalinv.getGoodPlaceholder().getMagicPrimeryList().get(2);
                            GoalMenu.resetPrimeryMagics(Goalinv.getInv(), Goalinv.getGoodPlaceholder());
                            break;
                        case neutral:
                            magic = Goalinv.getNeutralPlaceholder().getMagicPrimeryList().get(2);
                            GoalMenu.resetPrimeryMagics(Goalinv.getInv(), Goalinv.getNeutralPlaceholder());
                            break;
                        case bad:
                            magic = Goalinv.getBadPlaceholder().getMagicPrimeryList().get(2);
                            GoalMenu.resetPrimeryMagics(Goalinv.getInv(), Goalinv.getBadPlaceholder());
                            break;
                    }
                    Goalinv.setSelectetPrimaryMagic(magic);
                    inv.setItem(26, GoalMenu.generateMagicItems(magic, 2007));
                    event.setCancelled(true);
                    p.playSound(p, Sound.UI_BUTTON_CLICK, 1, 1);
                }
                else if (clickedSlot == 40) {
                    Magic magic = null;
                    switch (Goalinv.selectetPlaceholder){
                        case good:
                            magic = Goalinv.getGoodPlaceholder().getMagicSecondaryList().get(0);
                            GoalMenu.resetSecondaryMagics(Goalinv.getInv(), Goalinv.getGoodPlaceholder());
                            break;
                        case neutral:
                            magic = Goalinv.getNeutralPlaceholder().getMagicSecondaryList().get(0);
                            GoalMenu.resetSecondaryMagics(Goalinv.getInv(), Goalinv.getNeutralPlaceholder());
                            break;
                        case bad:
                            magic = Goalinv.getBadPlaceholder().getMagicSecondaryList().get(0);
                            GoalMenu.resetSecondaryMagics(Goalinv.getInv(), Goalinv.getBadPlaceholder());
                            break;
                    }
                    Goalinv.setSelectetSecondaryMagic(magic);
                    inv.setItem(40, GoalMenu.generateMagicItems(magic, 2008));
                    event.setCancelled(true);
                    p.playSound(p, Sound.UI_BUTTON_CLICK, 1, 1);
                }
                else if (clickedSlot == 42) {
                    Magic magic = null;
                    switch (Goalinv.selectetPlaceholder){
                        case good:
                            magic = Goalinv.getGoodPlaceholder().getMagicSecondaryList().get(1);
                            GoalMenu.resetSecondaryMagics(Goalinv.getInv(), Goalinv.getGoodPlaceholder());
                            break;
                        case neutral:
                            magic = Goalinv.getNeutralPlaceholder().getMagicSecondaryList().get(1);
                            GoalMenu.resetSecondaryMagics(Goalinv.getInv(), Goalinv.getNeutralPlaceholder());
                            break;
                        case bad:
                            magic = Goalinv.getBadPlaceholder().getMagicSecondaryList().get(1);
                            GoalMenu.resetSecondaryMagics(Goalinv.getInv(), Goalinv.getBadPlaceholder());
                            break;
                    }
                    Goalinv.setSelectetSecondaryMagic(magic);
                    inv.setItem(42, GoalMenu.generateMagicItems(magic, 2009));
                    event.setCancelled(true);
                    p.playSound(p, Sound.UI_BUTTON_CLICK, 1, 1);
                }

                InventoryView openView = p.getOpenInventory();
                if (Goalinv.selectetPlaceholder != null && Goalinv.selectetPrimaryMagic != null && Goalinv.selectetSecondaryMagic != null) {

                    openView.setTitle("<shift:-37><glyph:menu_goals_esc>");
                    p.updateInventory();
                } else {
                    openView.setTitle("<shift:-37><glyph:menu_goals>");
                    p.updateInventory();
                }

                event.setCancelled(true);
                return;
            }

        }


    }
}
