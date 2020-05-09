package eu.mcone.ttt.gadgets;

import eu.mcone.coresystem.api.bukkit.item.ItemBuilder;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum InnocentGadgets {

    SPION("Spion", 2, new ItemBuilder(Material.EYE_OF_ENDER).displayName("§fSpion")
            .lore("§7§oBeobachte die anderen", "§7§oSpieler von einer anderen Perspektive!", "", "§7Kosten: §f2 Rollen-Punkten").create());


    @Getter
    private final String name;
    @Getter
    private final ItemStack item;
    @Getter
    private final int level;

    InnocentGadgets(String name, int level, ItemStack item) {
        this.name = name;
        this.level = level;
        this.item = item;
    }
}

