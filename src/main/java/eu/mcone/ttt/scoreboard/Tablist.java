package eu.mcone.ttt.scoreboard;

import eu.mcone.coresystem.api.bukkit.player.CorePlayer;
import eu.mcone.coresystem.api.bukkit.scoreboard.CoreScoreboard;
import eu.mcone.gameapi.api.player.GamePlayer;
import eu.mcone.ttt.TTT;
import eu.mcone.ttt.roles.Role;
import org.bukkit.scoreboard.Team;

public class Tablist extends CoreScoreboard {

    @Override
    public Team modifyTeam(CorePlayer owner, CorePlayer player, Team team) {
        GamePlayer gpOwner = TTT.getInstance().getGamePlayer(owner.bukkit());
        GamePlayer gp = TTT.getInstance().getGamePlayer(player.bukkit());

        team.setPrefix(
                !(gpOwner.getTeam().getName().equals(Role.TRAITOR.getName())) && gp.getTeam().getName().equals(Role.TRAITOR.getName())
                        ? Role.INNOCENT.getName()
                        : gp.getTeam().getPrefix()
        );

        return null;
    }

}
