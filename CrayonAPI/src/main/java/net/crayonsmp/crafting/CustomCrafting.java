package net.crayonsmp.crafting;

import com.google.gson.JsonObject;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CustomCrafting {
    public static <T extends CustomCrafting> T findBestCustomCrafting(CustomCraftingType<T> type, List<ItemStack> itemStacks){
        for (T crafting : type.CRAFTINGS){
            if (crafting.matches(itemStacks)){
                return crafting;
            }
        }
        return null;
    }
    public abstract boolean matches(List<ItemStack> itemStacks);
    public abstract ItemStack craft(List<ItemStack> itemStacks);
    public static abstract class CustomCraftingType<T extends CustomCrafting>{
        private final List<T> CRAFTINGS = new ArrayList<>();
        public abstract T read(JsonObject object);
    }
}
