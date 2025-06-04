package net.crayonsmp.utils.goal;


import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.Map;

@SerializableAs("CrayonPlayerGoal")
public class PlayerGoal implements ConfigurationSerializable {

    private final Goal goal;

    private final Magic magicPrimery;

    private final Magic magicSecondary;

    public PlayerGoal(Goal goal, Magic MagicPrimery, Magic MagicSecondary) {
        if (goal == null) {
            throw new IllegalArgumentException("PlayerGoal goal cannot be null.");
        }
        if (MagicPrimery == null) {
            throw new IllegalArgumentException("PlayerGoal MagicPrimeryList cannot be null.");
        }
        if (MagicSecondary == null) {
            throw new IllegalArgumentException("PlayerGoal MagicSecondaryList cannot be null.");
        }
        this.goal = goal;
        this.magicPrimery = MagicPrimery;
        this.magicSecondary = MagicSecondary;
    }

    public PlayerGoal(Map<String, Object> map) {
        this.goal = (Goal) map.get("goal"); // Cast to Goal
        this.magicPrimery = (Magic) map.get("magicPrimery"); // Cast to Magic
        this.magicSecondary = (Magic) map.get("magicSecondary"); // Cast to Magic

        if (this.goal == null) {
            throw new IllegalArgumentException("Deserialized PlayerGoal 'goal' cannot be null.");
        }
        if (this.magicPrimery == null) {
            throw new IllegalArgumentException("Deserialized PlayerGoal 'magicPrimery' cannot be null.");
        }
        if (this.magicSecondary == null) {
            throw new IllegalArgumentException("Deserialized PlayerGoal 'magicSecondary' cannot be null.");
        }
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new java.util.LinkedHashMap<>();
        map.put("goal", goal); // Bukkit will automatically serialize Goal because it's ConfigurationSerializable
        map.put("magicPrimery", magicPrimery); // Bukkit will automatically serialize Magic
        map.put("magicSecondary", magicSecondary); // Bukkit will automatically serialize Magic
        return map;
    }

    public Goal getGoal() {
        return goal;
    }
    public Magic getMagicPrimery() {
        return magicPrimery;
    }
    public Magic getMagicSecondary() {
        return magicSecondary;
    }
}