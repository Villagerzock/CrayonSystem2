package net.crayonsmp.utils.ingredient;

import net.crayonsmp.interfaces.Ingredient;
import net.crayonsmp.utils.ItemBuilder;
import org.bukkit.inventory.ItemStack;

public class ItemIngredient implements Ingredient<ItemStack> {

    private final ItemStack stack;

    public ItemIngredient(String name) {
        stack = ItemBuilder.makeFromID(name);
    }

    @Override
    public boolean test(ItemStack other) {
        return other.equals(stack);
    }
}
