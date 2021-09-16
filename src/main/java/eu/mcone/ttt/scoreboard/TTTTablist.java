package eu.mcone.ttt.scoreboard;

import eu.mcone.coresystem.api.bukkit.player.CorePlayer;
import eu.mcone.coresystem.api.bukkit.scoreboard.CoreScoreboard;
import eu.mcone.coresystem.api.bukkit.scoreboard.CoreScoreboardEntry;
import eu.mcone.gameapi.api.player.GamePlayer;
import eu.mcone.ttt.TTT;
import eu.mcone.ttt.roles.Role;

import java.util.Objects;

public class TTTTablist extends CoreScoreboard {

    @Override
    public void modifyTeam(CorePlayer o, CorePlayer p, CoreScoreboardEntry team) {
        GamePlayer gpO = TTT.getInstance().getGamePlayer(o.bukkit());
        GamePlayer gp = TTT.getInstance().getGamePlayer(p.bukkit());

        if (
                (gpO.getTeam().equals(TTT.getInstance().getInnocentTeam())
                        || gpO.getTeam().equals(TTT.getInstance().getDetectiveTeam())
                ) && gp.getTeam().equals(TTT.getInstance().getTraitorTeam())
        ) {
            team.priority((Role.INNOCENT.ordinal() * 10)).name(Role.INNOCENT.name()).prefix(
                    TTT.getInstance().getInnocentTeam().getColor().toString()
            );
        } else {
            team.priority(Objects.requireNonNull(Role.getRoleByName(gp.getTeam().getName())).ordinal() * 10).prefix(
                    gp.getTeam().getColor().toString()
            );
        }
    }

}
