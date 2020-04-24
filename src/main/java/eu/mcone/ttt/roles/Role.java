package eu.mcone.ttt.roles;

import lombok.Getter;

public enum Role {

    INNOCENT("innocent"),
    DETECTIVE("detective"),
    TRAITOR("traitor");

    @Getter
    private final String name;

    Role(String name) {
        this.name = name;
    }
}
