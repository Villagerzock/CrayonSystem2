package net.crayonsmp.utils.goal;

import lombok.Setter;
import net.crayonsmp.enums.GoalType;
import org.bukkit.inventory.Inventory;

public class GoalInventory {

    private final Inventory inv;
    private final PlayerGoalPlaceholder goodPlaceholder;
    private final PlayerGoalPlaceholder neutralPlaceholder;
    private final PlayerGoalPlaceholder badPlaceholder;
    @Setter
    public GoalType selectetPlaceholder;
    @Setter
    public Magic selectetPrimaryMagic;
    @Setter
    public Magic selectetSecondaryMagic;

    public GoalInventory(Inventory inv, PlayerGoalPlaceholder goodPlaceholder, PlayerGoalPlaceholder neutralPlaceholder, PlayerGoalPlaceholder badPlaceholder) {
        this.inv = inv;
        this.goodPlaceholder = goodPlaceholder;
        this.neutralPlaceholder = neutralPlaceholder;
        this.badPlaceholder = badPlaceholder;
    }

    public Inventory getInv() {
        return inv;
    }

    public PlayerGoalPlaceholder getGoodPlaceholder() {
        return goodPlaceholder;
    }

    public PlayerGoalPlaceholder getNeutralPlaceholder() {
        return neutralPlaceholder;
    }

    public PlayerGoalPlaceholder getBadPlaceholder() {
        return badPlaceholder;
    }

    public GoalType getSelectetPlaceholder() {
        return selectetPlaceholder;
    }

    public Magic getSelectetPrimaryMagic() {
        return selectetPrimaryMagic;
    }

    public Magic getSelectetSecondaryMagic() {
        return selectetSecondaryMagic;
    }

}
