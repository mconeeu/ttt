package eu.mcone.ttt.roles;

import lombok.Getter;

public enum Role {

    INNOCENT("§aInnocent"),
    DETECTIVE("§1Detective"),
    TRAITOR("§cTraitor");

    @Getter
    private final String name;

    Role(String name) {
        this.name = name;
    }

    public static Role getRoleByName(String name) {
        for (Role role : values()) {
            if (role.getName().equals(name)) {
                return role;
            }
        }

        return null;
    }

}
