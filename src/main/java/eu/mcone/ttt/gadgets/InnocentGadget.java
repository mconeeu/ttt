package eu.mcone.ttt.gadgets;

import eu.mcone.coresystem.api.bukkit.item.ItemBuilder;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Getter
public enum InnocentGadget implements Gadget {

    SPION(
            "Spion",
            2,
            new ItemBuilder(Material.EYE_OF_ENDER)
                    .displayName("§fSpion")
                    .lore("§7§oBeobachte die anderen", "§7§oSpieler von einer anderen Perspektive!", "", "§7Kosten: §f2 Rollen-Punkten")
                    .create()
    );

    private final String name;
    private final ItemStack item;
    private final int level;

    InnocentGadget(String name, int level, ItemStack item) {
        this.name = name;
        this.level = level;
        this.item = item;
    }
}

