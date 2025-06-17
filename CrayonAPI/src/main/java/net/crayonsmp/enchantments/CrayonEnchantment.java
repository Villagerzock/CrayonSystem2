package net.crayonsmp.enchantments;

import io.papermc.paper.enchantments.EnchantmentRarity;
import io.papermc.paper.registry.set.RegistryKeySet;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityCategory;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Set;

public class CrayonEnchantment extends Enchantment {
    private final Component name;
    private final int maxLevel;
    private final int startLevel;
    private final EnchantmentTarget enchantmentTarget;
    private final Enchantment[] conflictingEnchantments;
    private final boolean isCursed;
    private final boolean isTreasure;
    private final String[] itemIDs;
    private final boolean isTradeable;
    private final boolean isDiscoverable;

    public CrayonEnchantment(Component name, int maxLevel, int startLevel, EnchantmentTarget enchantmentTarget, Enchantment[] conflictingEnchantments, boolean isCursed, boolean isTreasure, String[] itemIDs, boolean isTradeable, boolean isDiscoverable) {
        this.name = name;
        this.maxLevel = maxLevel;
        this.startLevel = startLevel;
        this.enchantmentTarget = enchantmentTarget;
        this.conflictingEnchantments = conflictingEnchantments;
        this.isCursed = isCursed;
        this.isTreasure = isTreasure;
        this.itemIDs = itemIDs;
        this.isTradeable = isTradeable;
        this.isDiscoverable = isDiscoverable;
    }

    @Override
    public @NotNull String getName() {
        return name.toString();
    }

    @Override
    public int getMaxLevel() {
        return maxLevel;
    }

    @Override
    public int getStartLevel() {
        return startLevel;
    }

    @Override
    public @NotNull EnchantmentTarget getItemTarget() {
        return enchantmentTarget;
    }

    @Override
    public boolean isTreasure() {
        return isTreasure;
    }

    @Override
    public boolean isCursed() {
        return isCursed;
    }

    @Override
    public boolean conflictsWith(@NotNull Enchantment enchantment) {
        return Arrays.stream(conflictingEnchantments).toList().contains(enchantment);
    }

    @Override
    public boolean canEnchantItem(@NotNull ItemStack itemStack) {
        return true;
    }

    @Override
    public @NotNull Component displayName(int i) {
        return name;
    }

    @Override
    public boolean isTradeable() {
        return isTradeable;
    }

    @Override
    public boolean isDiscoverable() {
        return isDiscoverable;
    }

    @Override
    public int getMinModifiedCost(int i) {
        return 0;
    }

    @Override
    public int getMaxModifiedCost(int i) {
        return 0;
    }

    @Override
    public int getAnvilCost() {
        return 0;
    }

    @Override
    public @NotNull EnchantmentRarity getRarity() {
        return EnchantmentRarity.RARE;
    }

    @Override
    public float getDamageIncrease(int i, @NotNull EntityCategory entityCategory) {
        return 0;
    }

    @Override
    public float getDamageIncrease(int i, @NotNull EntityType entityType) {
        return 0;
    }

    @Override
    public @NotNull Set<EquipmentSlotGroup> getActiveSlotGroups() {
        return Set.of();
    }

    @Override
    public @NotNull Component description() {
        return null;
    }

    @Override
    public @NotNull RegistryKeySet<ItemType> getSupportedItems() {
        return null;
    }

    @Override
    public @Nullable RegistryKeySet<ItemType> getPrimaryItems() {
        return null;
    }

    @Override
    public int getWeight() {
        return 0;
    }

    @Override
    public @NotNull RegistryKeySet<Enchantment> getExclusiveWith() {
        return null;
    }

    @Override
    public @NotNull String translationKey() {
        return "";
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return null;
    }

    @Override
    public @NotNull String getTranslationKey() {
        return "";
    }
}
