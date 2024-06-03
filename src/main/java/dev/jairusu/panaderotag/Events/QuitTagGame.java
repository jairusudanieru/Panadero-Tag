package dev.jairusu.panaderotag.Events;

import dev.jairusu.panaderotag.Methods.Configuration;
import dev.jairusu.panaderotag.Methods.SpawnTrophy;
import dev.jairusu.panaderotag.Methods.TagManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffectType;

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
         players.getInventory().clear();
         players.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 2);
         players.sendTitlePart(TitlePart.TITLE, Component.text("The tagger left the game!"));
         players.sendTitlePart(TitlePart.SUBTITLE, Component.text("Please start a new one to continue"));
      }

      SpawnTrophy.task[0].cancel();
      previousWorld.getEntitiesByClass(Item.class).forEach(Entity::remove);
      previousWorld.getEntitiesByClass(ArmorStand.class).forEach(Entity::remove);
      Configuration.getPlugin.getLogger().info("Tag Game Stopped");
   }

   @EventHandler
   public void onPlayerQuit(PlayerQuitEvent event) {
      Player player = event.getPlayer();
      World world = player.getWorld();
      if (!TagManager.getTeam().hasPlayer(player)) return;

      for (String entry : TagManager.getTeam().getEntries()) {
         TagManager.getTeam().removeEntry(entry);
      }

      for (Player players : Bukkit.getOnlinePlayers()) {
         if (!players.getWorld().equals(world)) continue;
         players.getInventory().clear();
         players.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 2);
         players.sendTitlePart(TitlePart.TITLE, Component.text("The tagger left the game!"));
         players.sendTitlePart(TitlePart.SUBTITLE, Component.text("Please start a new one to continue"));
      }

      SpawnTrophy.task[0].cancel();
      world.getEntitiesByClass(Item.class).forEach(Entity::remove);
      world.getEntitiesByClass(ArmorStand.class).forEach(Entity::remove);
      Configuration.getPlugin.getLogger().info("Tag Game Stopped");
   }

   @EventHandler
   public void onChangeWorld1(PlayerChangedWorldEvent event) {
      Player player = event.getPlayer();
      World world = player.getWorld();
      if (!world.getName().equals(Configuration.getString("config.tagWorld"))) return;
      player.getInventory().clear();
      player.removePotionEffect(PotionEffectType.GLOWING);
   }

}