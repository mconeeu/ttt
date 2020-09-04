package eu.mcone.ttt.player;

import eu.mcone.coresystem.api.bukkit.player.CorePlayer;
import eu.mcone.coresystem.api.bukkit.player.plugin.GamePlayer;
import eu.mcone.ttt.TTT;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

public class TTTPlayer extends GamePlayer<TTTPlayerProfile> {

    @Getter
    private int traitorPasses, detectivePasses;
    @Getter
    private final Set<TTTItem> currentItems = new HashSet<>();
    @Getter
    private TTTPass redeemedPass = null;

    public TTTPlayer(CorePlayer player) {
        super(player);
        TTT.getInstance().registerTTTPlayer(this);
    }

    @Override
    public TTTPlayerProfile reload() {
        TTTPlayerProfile profile = super.reload();

        this.traitorPasses = profile.getTraitorPasses();
        this.detectivePasses = profile.getDetectivePasses();

        return profile;
    }

    @Override
    protected TTTPlayerProfile loadData() {
        return TTT.getInstance().loadGameProfile(corePlayer.bukkit(), TTTPlayerProfile.class);
    }

    @Override
    protected void saveData() {
        TTT.getInstance().saveGameProfile(new TTTPlayerProfile(corePlayer.bukkit(), traitorPasses, detectivePasses));
    }

    public void addTraitorPass(int amount) {
        traitorPasses += amount;
        saveData();
    }

    public void addDetectivePass(int amount) {
        detectivePasses += amount;
        saveData();
    }

    public void removeTraitorPass(int amount) {
        traitorPasses -= amount;
        saveData();
    }

    public void removeDetectivePass(int amount) {
        detectivePasses -= amount;
        saveData();
    }

    public void redeemPass(TTTPass pass) {
        if (pass.equals(TTTPass.TRAITOR) && traitorPasses > 0) {
            redeemedPass = pass;
        } else if (pass.equals(TTTPass.DETECTIVE) && detectivePasses > 0) {
            redeemedPass = pass;
        } else {
            throw new IllegalStateException("Could not redeem pass "+pass.name()+". The user has no passes of this type!");
        }
    }

    public void setPassRedeemed() {
        if (this.redeemedPass != null) {
            if (redeemedPass.equals(TTTPass.DETECTIVE)) {
                removeDetectivePass(1);
            } else if (redeemedPass.equals(TTTPass.TRAITOR)) {
                removeTraitorPass(1);
            }
        } else {
            throw new IllegalStateException("Could not remove redeemed pass from ttt player. This player has not pass redeemed!");
        }
    }

    public boolean hasRedeemedPass() {
        return redeemedPass != null;
    }

    public TTTItem getAndSaveNewTTTItem() {
        if (currentItems.isEmpty()) {
            if (Math.random() < 0.5) {
                currentItems.add(TTTItem.WOOD_SWORD);
                return TTTItem.WOOD_SWORD;
            } else {
                currentItems.add(TTTItem.BOW_AND_ARROWS);
                return TTTItem.BOW_AND_ARROWS;
            }
        } else if (currentItems.contains(TTTItem.BOW_AND_ARROWS) && !currentItems.contains(TTTItem.WOOD_SWORD)) {
            currentItems.add(TTTItem.WOOD_SWORD);
            return TTTItem.WOOD_SWORD;
        } else if (!currentItems.contains(TTTItem.BOW_AND_ARROWS) && currentItems.contains(TTTItem.WOOD_SWORD)) {
            currentItems.add(TTTItem.BOW_AND_ARROWS);
            return TTTItem.BOW_AND_ARROWS;
        } else if (currentItems.contains(TTTItem.BOW_AND_ARROWS) && currentItems.contains(TTTItem.WOOD_SWORD) && !currentItems.contains(TTTItem.STONE_SWORD)) {
            currentItems.add(TTTItem.STONE_SWORD);
            return TTTItem.STONE_SWORD;
        } else {
            return null;
        }
    }

}
