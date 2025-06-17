package net.crayonsmp.utils.goal;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@SerializableAs("CrayonMagic")
public class Magic implements ConfigurationSerializable {

    private static final long serialVersionUID = 1L; // Für Serialisierung, wichtig für Persistenz

    private final String id;

    private final String name;

    private final List<String> description;

    private final List<String> theme;

    public Magic(String id, String name, java.util.List<String> description, java.util.List<String> theme) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Magic id cannot be null or empty.");
        }
        this.name = (name != null) ? name.trim() : "";

        this.id = id.trim();

        this.description = (description != null) ? Collections.unmodifiableList(new ArrayList<>(description)) : Collections.emptyList();
        this.theme = (theme != null) ? Collections.unmodifiableList(new ArrayList<>(theme)) : Collections.emptyList();
    }

    public Magic(Map<String, Object> map) {
        this.id = (String) map.get("id");
        this.name = (String) map.get("name");
        this.description = (List<String>) map.get("description");
        this.theme = (List<String>) map.get("theme");

        if (this.id == null || this.id.trim().isEmpty()) {
            throw new IllegalArgumentException("Deserialized Magic 'id' cannot be null or empty.");
        }
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new java.util.LinkedHashMap<>(); // Use LinkedHashMap to preserve order
        map.put("id", id);
        map.put("name", name);
        map.put("description", new ArrayList<>(description)); // Convert unmodifiable to modifiable for serialization
        map.put("theme", new ArrayList<>(theme));
        return map;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getDescription() {
        return description;
    }

    public List<String> getTheme() {
        return theme;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Magic magic = (Magic) o;
        return id.equals(magic.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Magic{" +
               "id='" + id + '\'' +
               ", name='" + name + '\'' +
               '}';
    }
}