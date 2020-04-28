package eu.mcone.ttt.gadgets;

import eu.mcone.coresystem.api.bukkit.item.ItemBuilder;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum TraitorGadgets {

    MONSTER_ARROWS("Monster Pfeile", 2, new ItemBuilder(Material.MONSTER_EGG).displayName("§fMonster Pfeile")
            .lore("§7§oSchieße mit deinem Bogen", "§7§oMonster Pfeile ab!", "", "§7Kosten: §f2 Rollen-Punkten").create());


    @Getter
    private final String name;
    @Getter
    private final ItemStack item;
    @Getter
    private final int level;

    TraitorGadgets(String name, int level, ItemStack item) {
        this.name = name;
        this.level = level;
        this.item = item;
    }
}

