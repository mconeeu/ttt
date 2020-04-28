package eu.mcone.ttt.gadgets;

import eu.mcone.coresystem.api.bukkit.item.ItemBuilder;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum DetectiveGadgets {

    HEAL_STATION("Heilstation", 2, new ItemBuilder(Material.BEACON).displayName("§fHeilstation")
            .lore("§7§oHeile andere Spieler", "§7§omit deiner Heilstation!", "", "§7Kosten: §f2 Rollen-Punkten").create()),

    COBWEB_GUN("Spinnennetz Granate", 2, new ItemBuilder(Material.WEB).displayName("§fSpinnennetz Granate")
            .lore("§7§oWerfe die Granate", "§7§ound lasse sie explodieren!", "", "§7Kosten: §f3 Rollen-Punkten").create());


    @Getter
    private final String name;
    @Getter
    private final ItemStack item;
    @Getter
    private final int level;

    DetectiveGadgets(String name, int level, ItemStack item) {
        this.name = name;
        this.level = level;
        this.item = item;
    }
}
