package net.crayonsmp;

import net.crayonsmp.crafting.CustomCrafting;
import net.crayonsmp.interfaces.CrayonModule;
import net.crayonsmp.recipes.CrushingFactoryRecipe;
import org.bukkit.plugin.Plugin;

public class Villagerzock implements CrayonModule {

    @Override
    public String getName() {
        return "Villagerzock's Module";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public String getAuthor() {
        return "Villagerzock";
    }

    @Override
    public CustomCrafting.CustomCraftingType<?>[] getCraftingTypes() {
        return new CustomCrafting.CustomCraftingType[]{
                new CrushingFactoryRecipe.Type()
        };
    }

    @Override
    public <API extends Plugin & CrayonAPI> void onEnable(API plugin) {

    }
    @Override
    public String getID() {
        return "villagerzock";
    }
}