package net.crayonsmp.mana;

import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Mana {
    private int value = 0;
    private int maxValue = 1000;
    private final Plugin plugin;

    public Mana(Plugin plugin) {
        this.plugin = plugin;
    }

    public boolean useMana(int amount){
        if (value >= amount){
            value -= amount;
             return true;
        }
        return false;
    }
    public BukkitRunnable fillManaFromCrystal(ItemStack crystal){
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                if (crystal.getDurability() > 0){
                    crystal.setDurability((short)(crystal.getDurability() - 1));
                    value++;
                }else {
                    this.cancel();
                }
            }
        };
        runnable.runTaskTimer(plugin,0,1);
        return runnable;
    }
    public BukkitRunnable fillManaToCrystal(ItemStack crystal){
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                if (value > 0){
                    crystal.setDurability((short)(crystal.getDurability() + 1));
                    --value;
                }else {
                    this.cancel();
                }
            }
        };
        runnable.runTaskTimer(plugin,0,1);
        return runnable;
    }
    public void regenerateMana(int amount){
        regenerateMana(amount,10);
    }
    public void regenerateMana(int amount,int delay){
        BukkitRunnable runnable = new BukkitRunnable() {
            private int amountRan = 0;
            @Override
            public void run() {
                if (amountRan < amount){
                    amountRan++;
                    value++;
                }else {
                    this.cancel();
                }
            }
        };
        runnable.runTaskTimer(plugin,0,delay);
    }
}
