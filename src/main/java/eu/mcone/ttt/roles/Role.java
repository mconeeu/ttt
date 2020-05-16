package eu.mcone.ttt.roles;

import lombok.Getter;
import org.bukkit.ChatColor;

@Getter
public enum Role {

    INNOCENT("Innocent", ChatColor.GREEN),
    DETECTIVE("Detective", ChatColor.BLUE),
    TRAITOR("Traitor", ChatColor.RED);

    private final String name;
    private final ChatColor color;

    Role(String name, ChatColor color) {
        this.name = name;
        this.color = color;
    }

    public static Role getRoleByName(String name) {
        for (Role role : values()) {
            if (role.getName().equals(name)) {
                return role;
            }
        }

        return null;
    }

    public String getLabel() {
        return color+name;
    }

}
