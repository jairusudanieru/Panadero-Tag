package dev.jairusu.panaderotag.Events;

import dev.jairusu.panaderotag.Methods.Configuration;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;

import java.util.Collection;

public class CommandSend implements Listener {

   @EventHandler
   public void onArenaCommand(PlayerCommandSendEvent event) {
      Player player = event.getPlayer();
      World playerWorld = player.getWorld();

      Collection<String> commands = event.getCommands();
      if (playerWorld.getName().equals(Configuration.getString("config.tagWorld"))) return;
      commands.remove("startgame");
      commands.remove("stopgame");
   }
}