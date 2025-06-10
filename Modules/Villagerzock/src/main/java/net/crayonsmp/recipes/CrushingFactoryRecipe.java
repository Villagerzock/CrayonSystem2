package net.crayonsmp.recipes;

import com.google.gson.JsonObject;
import net.crayonsmp.crafting.CustomCrafting;
import net.crayonsmp.utils.ItemBuilder;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CrushingFactoryRecipe extends CustomCrafting {
    private final ItemStack input;
    private final ItemStack output;
    protected CrushingFactoryRecipe(String name, ItemStack input, ItemStack output) {
        super(name);
        this.input = input;
        this.output = output;
    }

    @Override
    public boolean matches(List<ItemStack> itemStacks) {
        return itemStacks.get(0) == input;
    }

    @Override
    public ItemStack craft(List<ItemStack> itemStacks) {
        itemStacks.clear();
        return output;
    }

    @Override
    public void sendRecipe(CommandSender commandSender) {

    }
    public static class Type extends CustomCraftingType<CrushingFactoryRecipe>{

        @Override
        public CrushingFactoryRecipe read(JsonObject object, String name) {
            ItemStack input = ItemBuilder.makeFromID(object.get("input").getAsString());
            ItemStack output = ItemBuilder.makeFromID(object.get("output").getAsString());
            return new CrushingFactoryRecipe(name,input,output);
        }

        @Override
        public String getID() {
            return "crushing_factory";
        }
    }
}
