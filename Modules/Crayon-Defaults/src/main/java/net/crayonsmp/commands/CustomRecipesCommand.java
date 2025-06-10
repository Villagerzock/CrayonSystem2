package net.crayonsmp.commands;

import net.crayonsmp.crafting.CustomCrafting;
import net.crayonsmp.gui.FeywoodInventories;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CustomRecipesCommand implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (strings.length >= 1){
            switch (strings[0]){
                case "reload":
                    CustomCrafting.reloadRecipes();
                    break;
                case "show":
                    if (strings.length == 3){
                        CustomCrafting.CustomCraftingType<?> type = CustomCrafting.TYPES.get(strings[1]);
                        CustomCrafting crafting = type.ofName(strings[2]);
                        commandSender.sendMessage("The Recipe looks the Following:");
                        crafting.sendRecipe(commandSender);
                    }
            }
        }else {
            if (commandSender instanceof Player player){
                player.openInventory(FeywoodInventories.feywoodTable());
            }
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        List<String> list = new ArrayList<>();
        switch (strings.length){
            case 1:
                list.add("reload");
                list.add("show");
                break;
            case 2:
                if (strings[0].equalsIgnoreCase("show")){
                    list.addAll(CustomCrafting.TYPES.keySet());
                }
                break;
            case 3:
                if (strings[0].equalsIgnoreCase("show")){
                    CustomCrafting.CustomCraftingType<?> type = CustomCrafting.TYPES.get(strings[1]);
                    for (CustomCrafting crafting : type.getCraftings()){
                        list.add(crafting.getName());
                    }
                }
        }
        return list;
    }
}
