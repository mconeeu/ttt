package eu.mcone.ttt.scoreboard;

import eu.mcone.coresystem.api.bukkit.player.CorePlayer;
import eu.mcone.coresystem.api.bukkit.scoreboard.CoreScoreboard;
import eu.mcone.coresystem.api.bukkit.scoreboard.CoreScoreboardEntry;
import eu.mcone.coresystem.api.core.util.Random;
import eu.mcone.gameapi.api.player.GamePlayer;
import eu.mcone.ttt.TTT;
import eu.mcone.ttt.roles.Role;
import org.bukkit.scoreboard.Team;

public class Tablist extends CoreScoreboard {

    @Override
    public void modifyTeam(CorePlayer o, CorePlayer p, CoreScoreboardEntry team) {
        GamePlayer gpO = TTT.getInstance().getGamePlayer(o.bukkit());
        GamePlayer gp = TTT.getInstance().getGamePlayer(p.bukkit());

        if (
                (gpO.getTeam().getName().equals(Role.INNOCENT.getName())
                        || gpO.getTeam().getName().equals(Role.DETECTIVE.getName())
                ) && gp.getTeam().getName().equals(Role.TRAITOR.getName())
        ) {
            team.priority((Role.INNOCENT.ordinal() * 10)).name(Role.INNOCENT.name()).prefix(
                    "§" + TTT.getInstance().getTeamManager().getTeam(Role.INNOCENT.getName()).getChatColor().getChar() + ""
            );
        } else {
            team.priority(Role.getRoleByName(gp.getTeam().getName()).ordinal() * 10).prefix(
                    "§" + gp.getTeam().getChatColor().getChar() + ""
            );
        }
    }

}
