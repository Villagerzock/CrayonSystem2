package net.crayonsmp.utils.ingredient;

import net.crayonsmp.interfaces.Ingredient;
import org.bukkit.block.Block;

public class BlockIngredient implements Ingredient<Block> {

    private final Block block;

    public BlockIngredient(String name) {
        block = null;
    }

    @Override
    public boolean test(Block other) {
        return block.equals(other);
    }
}
