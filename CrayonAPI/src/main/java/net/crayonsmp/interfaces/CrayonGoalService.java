package net.crayonsmp.interfaces;

import net.crayonsmp.utils.goal.Goal;
import net.crayonsmp.utils.goal.GoalInventory;
import net.crayonsmp.utils.goal.Magic;
import net.crayonsmp.utils.goal.PlayerGoal;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public interface CrayonGoalService {

    HashMap<String, Goal> getGoals();

    HashMap<String, Magic> getMagics();

    HashMap<String, PlayerGoal> getPlayerGoals();

    HashMap<Player, GoalInventory> getGoalInventorys();

    Goal getGoalById(String goalId);

    Magic getMagicById(String magicId);

    PlayerGoal getPlayerGoalByUUID(UUID playerUUID);
}
