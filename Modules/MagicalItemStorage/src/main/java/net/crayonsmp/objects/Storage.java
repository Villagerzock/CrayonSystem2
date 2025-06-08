package net.crayonsmp.objects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class Storage {
    private final int mexItemStacks;

    private List<ItemStack> items;

    public void addItem(ItemStack item) {
        items.add(item);
    }
    public void removeItem(ItemStack item) {
        items.remove(item);
    }
}
