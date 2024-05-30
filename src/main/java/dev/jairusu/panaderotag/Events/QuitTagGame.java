package dev.jairusu.panaderotag.Events;

import dev.jairusu.panaderotag.Methods.Configuration;
import dev.jairusu.panaderotag.Methods.TagManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitTagGame implements Listener {

   @EventHandler
   public void onChangeWorld(PlayerChangedWorldEvent event) {
      Player player = event.getPlayer();
      World previousWorld = event.getFrom();
      if (!previousWorld.getName().equals(Configuration.getString("config.tagWorld"))) return;
      if (!TagManager.getTeam().hasPlayer(player)) return;

      for (String entry : TagManager.getTeam().getEntries()) {
         TagManager.getTeam().removeEntry(entry);
      }

      for (Player players : Bukkit.getOnlinePlayers()) {
         if (!players.getWorld().equals(previousWorld)) continue;
         players.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 2);
         players.sendTitlePart(TitlePart.TITLE, Component.text("The tagger left the game!"));
         players.sendTitlePart(TitlePart.SUBTITLE, Component.text("Please start a new one to continue"));
      }
      Configuration.getPlugin.getLogger().info("Tag Game Stopped");
   }

   @EventHandler
   public void onPlayerQuit(PlayerQuitEvent event) {
      Player player = event.getPlayer();
      World world = player.getWorld();
      if (!TagManager.getTeam().hasPlayer(player)) return;
      TagManager.getTeam().getEntries().clear();

      for (Player players : Bukkit.getOnlinePlayers()) {
         if (!players.getWorld().equals(world)) continue;
         players.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 2);
         players.sendTitlePart(TitlePart.TITLE, Component.text("The tagger left the game!"));
         players.sendTitlePart(TitlePart.SUBTITLE, Component.text("Please start a new one to continue"));
      }
      Configuration.getPlugin.getLogger().info("Tag Game Stopped");
   }

}