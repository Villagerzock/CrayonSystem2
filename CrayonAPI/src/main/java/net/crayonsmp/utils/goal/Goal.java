package net.crayonsmp.utils.goal;

import net.crayonsmp.enums.GoalType;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.List;
import java.util.Collections; // Für immutable Listen
import java.util.ArrayList;   // Für defensive Kopie
import java.util.Map;

@SerializableAs("CrayonGoal")
public class Goal implements ConfigurationSerializable {

    private final String configKey;

    private final GoalType goalType;

    private final String name;

    private final String ID;

    private List<String> description;

    private List<String> primaryMagicConfigs;

    private List<String> secondaryMagicConfigs;


    public Goal(String configKey, GoalType goalType, String name, String ID, List<String> description, List<String> primaryMagicConfigs, List<String> secondaryMagicConfigs) {
        if (configKey == null || configKey.trim().isEmpty()) {
            throw new IllegalArgumentException("GoalTemplate configKey cannot be null or empty.");
        }
        if (goalType == null) {
            throw new IllegalArgumentException("GoalTemplate goalType cannot be null.");
        }
        this.name = (name != null) ? name.trim() : "";
        this.ID = ID.trim();


        this.configKey = configKey.trim();
        this.goalType = goalType;

        this.description = (description != null) ? Collections.unmodifiableList(new ArrayList<>(description)) : Collections.emptyList();
        this.primaryMagicConfigs = (primaryMagicConfigs != null) ? Collections.unmodifiableList(new ArrayList<>(primaryMagicConfigs)) : Collections.emptyList();
        this.secondaryMagicConfigs = (secondaryMagicConfigs != null) ? Collections.unmodifiableList(new ArrayList<>(secondaryMagicConfigs)) : Collections.emptyList();
    }


    public String getConfigKey() {
        return configKey;
    }

    public GoalType getGoalType() {
        return goalType;
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return ID;
    }

    public List<String> getDescription() {
        return description;
    }

    public List<String> getPrimaryMagicConfigs() {
        return primaryMagicConfigs;
    }

    public List<String> getSecondaryMagicConfigs() {
        return secondaryMagicConfigs;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goal that = (Goal) o;
        return configKey.equals(that.configKey) && goalType == that.goalType;
    }

    @Override
    public int hashCode() {
        int result = configKey.hashCode();
        result = 31 * result + goalType.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "GoalTemplate{" +
                "configKey='" + configKey + '\'' +
                ", goalType=" + goalType +
                ", name='" + name + '\'' +
                '}';
    }

    public Goal(Map<String, Object> map) {
        this.configKey = (String) map.get("configKey");
        this.goalType = GoalType.valueOf((String) map.get("goalType")); // Assuming GoalType is an Enum
        this.name = (String) map.get("name");
        this.ID = (String) map.get("ID");
        this.description = (List<String>) map.get("description");
        this.primaryMagicConfigs = (List<String>) map.get("primaryMagicConfigs");
        this.secondaryMagicConfigs = (List<String>) map.get("secondaryMagicConfigs");

        this.description = (this.description != null) ? Collections.unmodifiableList(new ArrayList<>(this.description)) : Collections.emptyList();
        this.primaryMagicConfigs = (this.primaryMagicConfigs != null) ? Collections.unmodifiableList(new ArrayList<>(this.primaryMagicConfigs)) : Collections.emptyList();
        this.secondaryMagicConfigs = (this.secondaryMagicConfigs != null) ? Collections.unmodifiableList(new ArrayList<>(this.secondaryMagicConfigs)) : Collections.emptyList();

        if (this.configKey == null || this.configKey.trim().isEmpty()) {
            throw new IllegalArgumentException("Deserialized Goal 'configKey' cannot be null or empty.");
        }
        if (this.goalType == null) {
            throw new IllegalArgumentException("Deserialized Goal 'goalType' cannot be null.");
        }
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new java.util.LinkedHashMap<>();
        map.put("configKey", configKey);
        map.put("goalType", goalType.name()); // Store enum as its name string
        map.put("name", name);
        map.put("ID", ID);
        map.put("description", new ArrayList<>(description));
        map.put("primaryMagicConfigs", new ArrayList<>(primaryMagicConfigs));
        map.put("secondaryMagicConfigs", new ArrayList<>(secondaryMagicConfigs));
        return map;
    }
}