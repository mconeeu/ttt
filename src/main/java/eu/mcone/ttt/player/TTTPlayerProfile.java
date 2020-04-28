package eu.mcone.ttt.player;

import eu.mcone.coresystem.api.bukkit.player.profile.GameProfile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class TTTPlayerProfile extends GameProfile {

    private int traitorPasses, detectivePasses;

    TTTPlayerProfile(Player p, int traitorPasses, int detectivePasses) {
        super(p);

        this.traitorPasses = traitorPasses;
        this.detectivePasses = detectivePasses;
    }

    @Override
    public void doSetData(Player player) {}

}
