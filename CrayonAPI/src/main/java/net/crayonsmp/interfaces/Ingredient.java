package net.crayonsmp.interfaces;

import net.crayonsmp.utils.ingredient.BlockIngredient;
import net.crayonsmp.utils.ingredient.ItemIngredient;
import net.crayonsmp.utils.ingredient.NotRequiredIngredientTypeException;

import java.util.Map;

public interface Ingredient<T> {

    Map<Class<?>, String> typeMap = Map.ofEntries(
            Map.entry(ItemIngredient.class, "item"),
            Map.entry(BlockIngredient.class, "block")
    );

    static String getRequiredFromClass(Class<?> type) {
        return typeMap.get(type);
    }

    static Class<?> getClassFromRequired(String type) {
        return typeMap.keySet().stream().toList().get(typeMap.values().stream().toList().indexOf(type));
    }

    static <T> Ingredient<T> getFromID(String key, String value, Class<Ingredient<T>> required) {
        if (required == getClassFromRequired(key)) {
            switch (key) {
                case "item":
                    return (Ingredient<T>) new ItemIngredient(value);
                case "block":
                    return (Ingredient<T>) new BlockIngredient(value);
                default:
                    return null;
            }
        } else {
            throw new NotRequiredIngredientTypeException(key, getRequiredFromClass(required));
        }
    }

    boolean test(T other);
}
