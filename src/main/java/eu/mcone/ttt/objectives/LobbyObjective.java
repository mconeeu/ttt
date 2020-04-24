package eu.mcone.ttt.objectives;

import eu.mcone.coresystem.api.bukkit.player.CorePlayer;
import org.bukkit.Bukkit;

public class LobbyObjective extends eu.mcone.gameapi.api.scoreboard.LobbyObjective {

//    private static int i = 0;

    public LobbyObjective() {
        super("TTT");
    }

    @Override
    protected void onRegister(CorePlayer corePlayer) {
        super.onRegister(corePlayer);
        setDisplayName("§7§l⚔ §c§l§nTTT");

        setScore(3, "");
        setScore(2, "§8» §7Wartende Spieler:");
        onReload(corePlayer);
    }

    @Override
    protected void onReload(CorePlayer corePlayer) {
        super.onReload(corePlayer);
        setScore(1, "§f  " + Bukkit.getOnlinePlayers().size());
    }

//    public static void updateLines() {
//        if (Minewar.getInstance().getGameStateManager().getRunning() instanceof LobbyState) {
//            if (i >= 4) i = 0;
//            i++;
//            for (final CorePlayer p : CoreSystem.getInstance().getOnlineCorePlayers()) {
//                final CoreSidebarObjective o = (CoreSidebarObjective) p.getScoreboard().getObjective(DisplaySlot.SIDEBAR);
//                if (o != null) {
//                    if (o instanceof LobbyObjective) {
//                        o.setDisplayName("§7§l⚔ §d§l§nMinewar");
//
//                    }
//
//                    if (i == 1) {
//                        o.setScore(1, "§8»§7 Teamspeak:");
//                        o.setScore(0, "  §f§ots.mcone.eu");
//                    } else if (i == 2) {
//                        o.setScore(1, "§8»§7 Website:");
//                        o.setScore(0, "  §f§omcone.eu");
//                    } else if (i == 3) {
//                        o.setScore(1, "§8»§7 Twitter:");
//                        o.setScore(0, "  §b§o@mconeeu");
//                    } else if (i == 4) {
//                        o.setScore(1, "§8»§7 YouTube:");
//                        o.setScore(0, "  §c§oyt.mcone.eu");
//                    } else {
//                        o.setScore(1, "§8»§7 Teamspeak:");
//                        o.setScore(0, "  §f§ots.mcone.eu");
//                    }
//                }
//            }
//        }
//
//    }
}
