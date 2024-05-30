package dev.jairusu.panaderotag.Events;

import dev.jairusu.panaderotag.Methods.TagManager;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMove implements Listener {

   @EventHandler
   public void onPlayerMove(PlayerMoveEvent event) {
      Player player = event.getPlayer();
      if (player.isOp()) return;

      if (player.getGameMode().equals(GameMode.SPECTATOR) && TagManager.getTeam().hasPlayer(player)) {
         event.setCancelled(true);
      }
   }

}
