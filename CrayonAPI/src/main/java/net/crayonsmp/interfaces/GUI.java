package net.crayonsmp.interfaces;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public abstract class GUI implements Inventory {
    private int maxStackSize = 64;
    private ItemStack[] itemStacks;
    private List<HumanEntity> openPlayers = new ArrayList<>();
    private final InventoryHolder holder;
    public final int calculateSlotId(int row, int slot){
        return row*9 + slot;
    }
    public GUI(InventoryHolder holder) {
        itemStacks = new ItemStack[getSize()];
        this.holder = holder;
    }
    public abstract boolean onSlotClicked(int slot, ClickType type, InventoryType.SlotType slotType);
    public abstract int getRowAmount();
    @Override
    public final int getSize() {
        return Math.clamp(getRowAmount(),1,6) * 9;
    }

    @Override
    public int getMaxStackSize() {
        return maxStackSize;
    }

    @Override
    public void setMaxStackSize(int i) {
        maxStackSize = i;
    }

    @Override
    public @Nullable ItemStack getItem(int i) {
        return itemStacks[i];
    }

    @Override
    public void setItem(int i, @Nullable ItemStack itemStack) {
        itemStacks[i] = itemStack;
    }

    @Override
    public @NotNull HashMap<Integer, ItemStack> addItem(@NotNull ItemStack... itemStacks) throws IllegalArgumentException {
        return null;
    }

    @Override
    public @NotNull HashMap<Integer, ItemStack> removeItem(@NotNull ItemStack... itemStacks) throws IllegalArgumentException {
        return null;
    }

    @Override
    public @NotNull HashMap<Integer, ItemStack> removeItemAnySlot(@NotNull ItemStack... itemStacks) throws IllegalArgumentException {
        return null;
    }

    @Override
    public @Nullable ItemStack @NotNull [] getContents() {
        return itemStacks;
    }

    @Override
    public void setContents(@Nullable ItemStack @NotNull [] itemStacks) throws IllegalArgumentException {
        this.itemStacks = itemStacks;
    }

    @Override
    public @Nullable ItemStack @NotNull [] getStorageContents() {
        return itemStacks;
    }

    @Override
    public void setStorageContents(@Nullable ItemStack @NotNull [] itemStacks) throws IllegalArgumentException {
        this.itemStacks = itemStacks;
    }

    @Override
    public boolean contains(@NotNull Material material) throws IllegalArgumentException {
        for (ItemStack stack : itemStacks){
            if (stack.getType() == material){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contains(@Nullable ItemStack itemStack) {
        return Arrays.stream(itemStacks).toList().contains(itemStack);
    }

    @Override
    public boolean contains(@NotNull Material material, int i) throws IllegalArgumentException {
        return contains(material);
    }

    @Override
    public boolean contains(@Nullable ItemStack itemStack, int i) {
        return contains(itemStack);
    }

    @Override
    public boolean containsAtLeast(@Nullable ItemStack itemStack, int i) {
        return contains(itemStack);
    }

    @Override
    public @NotNull HashMap<Integer, ? extends ItemStack> all(@NotNull Material material) throws IllegalArgumentException {
        return null;
    }

    @Override
    public @NotNull HashMap<Integer, ? extends ItemStack> all(@Nullable ItemStack itemStack) {
        return null;
    }

    @Override
    public int first(@NotNull Material material) throws IllegalArgumentException {
        return 0;
    }

    @Override
    public int first(@NotNull ItemStack itemStack) {
        return 0;
    }

    @Override
    public int firstEmpty() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return Arrays.stream(itemStacks).toList().isEmpty();
    }

    @Override
    public void remove(@NotNull Material material) throws IllegalArgumentException {
    }

    @Override
    public void remove(@NotNull ItemStack itemStack) {

    }

    @Override
    public void clear(int i) {
        itemStacks[i] = null;
    }

    @Override
    public void clear() {
        itemStacks = new ItemStack[getSize()];
    }

    @Override
    public int close() {
        for (HumanEntity player : getViewers()){
            player.closeInventory();
        }
        return 0;
    }

    @Override
    public @NotNull List<HumanEntity> getViewers() {
        return openPlayers;
    }

    @Override
    public @NotNull InventoryType getType() {
        return InventoryType.CHEST;
    }

    @Override
    public @Nullable InventoryHolder getHolder() {
        return holder;
    }

    @Override
    public @Nullable InventoryHolder getHolder(boolean b) {
        return holder;
    }

    @Override
    public @NotNull ListIterator<ItemStack> iterator() {
        return Arrays.stream(itemStacks).toList().listIterator();
    }

    @Override
    public @NotNull ListIterator<ItemStack> iterator(int i) {
        return iterator();
    }

    @Override
    public @Nullable Location getLocation() {
        return null;
    }
}
