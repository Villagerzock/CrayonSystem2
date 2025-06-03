package net.crayonsmp.mana;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class Mana {
    private int value = 0;
    private int maxValue;
    private final Plugin plugin;
    private final Consumer<Integer> onValueChanged;
    private final Consumer<Integer> onMaxValueChanged;

    public static Mana getManaFromPlayer(Player player, Plugin plugin){
        int value = player.getPersistentDataContainer().get(NamespacedKey.fromString("mana", plugin), PersistentDataType.INTEGER);
        int maxValue = player.getPersistentDataContainer().get(NamespacedKey.fromString("max_mana", plugin), PersistentDataType.INTEGER);
        return new Mana(plugin,(mana)->{
            player.getPersistentDataContainer().set(NamespacedKey.fromString("mana", plugin), PersistentDataType.INTEGER,mana);
        },(maxMana)->{
            player.getPersistentDataContainer().set(NamespacedKey.fromString("max_mana", plugin), PersistentDataType.INTEGER,maxMana);
        });
    }

    public Mana(Plugin plugin, Consumer<Integer> onValueChanged, Consumer<Integer> onMaxValueChanged) {
        this.plugin = plugin;
        this.onValueChanged = onValueChanged;
        this.onMaxValueChanged = onMaxValueChanged;
    }
    private void setValue(int value){
        this.value = value;
        onValueChanged.accept(value);
    }
    public void setMaxValue(int value){
        this.maxValue = value;
        onMaxValueChanged.accept(value);
    }
    public boolean useMana(int amount){
        if (value >= amount){
            setValue(value - amount);
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
                    setValue(value+1);
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
                    setValue(value-1);
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
                    setValue(value+1);
                }else {
                    this.cancel();
                }
            }
        };
        runnable.runTaskTimer(plugin,0,delay);
    }
}
