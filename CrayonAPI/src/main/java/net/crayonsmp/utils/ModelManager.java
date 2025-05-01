package net.crayonsmp.utils;

import com.nexomc.nexo.api.NexoBlocks;
import com.nexomc.nexo.api.NexoFurniture;
import com.nexomc.nexo.api.NexoItems;
import com.ticxo.modelengine.api.ModelEngineAPI;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMechanicLoadEvent;
import io.lumine.xikage.mythicmobs.commands.mobs.MobsCommand;
import io.lumine.xikage.mythicmobs.mobs.MobManager;
import io.lumine.xikage.mythicmobs.mobs.MythicMob;

public class ModelManager {
    public static int getItemsLoaded() {
        return NexoItems.items().size();
    }
    public static int getMobsLoaded() {
        MobManager mobManager = MythicMobs.inst().getMobManager();
        return mobManager.getMobTypes().size();
    }
    public static int getModelsLoaded() {
        return ModelEngineAPI.api.getModelRegistry().getAllBlueprintId().size();
    }
    public static int getSkillsLoaded() {
        return MythicMobs.inst().getSkillManager().getSkills().size();
    }
    public static int getBlocksLoaded() {
        return NexoBlocks.blockIDs().length;
    }
    public static int getFurnitureLoaded() {
        return NexoFurniture.furnitureIDs().length;
    }
}
