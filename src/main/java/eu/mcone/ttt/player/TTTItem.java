package eu.mcone.ttt.player;

import eu.mcone.coresystem.api.bukkit.item.ItemBuilder;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Getter
public enum TTTItem {

    WOOD_SWORD(new ItemStack[]{
            new ItemBuilder(Material.WOOD_SWORD, 1, 0)
                    .displayName("§fHolzschwert")
                    .create()
    }),
    STONE_SWORD(new ItemStack[]{
            new ItemBuilder(Material.STONE_SWORD, 1, 0)
                    .displayName("§fSteinschwert")
                    .create()
    }),
    IRON_SWORD(new ItemStack[]{
            new ItemBuilder(Material.IRON_SWORD, 1, 0)
                    .displayName("§fEisenschwert")
                    .create()
    }),
    BOW_AND_ARROWS(new ItemStack[]{
            new ItemBuilder(Material.BOW, 1, 0)
                    .displayName("§fBogen")
                    .create(),
            new ItemBuilder(Material.ARROW, 32, 0)
                    .create()
    });

    private final ItemStack[] items;

    TTTItem(ItemStack[] items) {
        this.items = items;
    }

}
