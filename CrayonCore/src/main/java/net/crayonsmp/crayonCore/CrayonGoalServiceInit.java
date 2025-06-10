package net.crayonsmp.crayonCore;

import net.crayonsmp.gui.GoalMenu;
import net.crayonsmp.interfaces.CrayonGoalService;
import net.crayonsmp.services.GoalService;
import net.crayonsmp.utils.goal.Goal;
import net.crayonsmp.utils.goal.GoalInventory;
import net.crayonsmp.utils.goal.Magic;
import net.crayonsmp.utils.goal.PlayerGoal;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class CrayonGoalServiceInit implements CrayonGoalService {

    @Override
    public HashMap<String, Goal> getGoals() {
        return GoalService.RegistertGoals;
    }

    @Override
    public HashMap<String, Magic> getMagics() {
        return GoalService.RegistertMagics;
    }

    @Override
    public HashMap<String, PlayerGoal> getPlayerGoals() {
        return GoalService.PlayerGoalData;
    }

    @Override
    public HashMap<Player, GoalInventory> getGoalInventorys() {
        return GoalMenu.goalInventories;
    }

    @Override
    public Goal getGoalById(String goalId) {
        return GoalService.getGoalById(goalId);
    }

    @Override
    public Magic getMagicById(String magicId) {
        return GoalService.getMagicById(magicId);
    }

    @Override
    public PlayerGoal getPlayerGoalByUUID(UUID playerUUID) {
        return GoalService.PlayerGoalData.get(playerUUID.toString());
    }
}
