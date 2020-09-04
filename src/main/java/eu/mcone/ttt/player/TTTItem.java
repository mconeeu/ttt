package eu.mcone.ttt.player;

import eu.mcone.ttt.listener.InventoryTriggerListener;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@Getter
public enum TTTItem {

    WOOD_SWORD(new ItemStack[]{
            InventoryTriggerListener.WOOD_SWORD_ITEM
    }),
    STONE_SWORD(new ItemStack[]{
            InventoryTriggerListener.STONE_SWORD_ITEM
    }),
    IRON_SWORD(new ItemStack[]{
            InventoryTriggerListener.IRON_SWORD_ITEM
    }),
    BOW_AND_ARROWS(new ItemStack[]{
            InventoryTriggerListener.BOW_ITEM,
            InventoryTriggerListener.ARROWS_ITEM
    });

    private final ItemStack[] items;

    TTTItem(ItemStack[] items) {
        this.items = items;
    }

}
