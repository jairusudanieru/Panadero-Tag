package dev.jairusu.panaderotag.Events;

import dev.jairusu.panaderotag.Methods.Configuration;
import dev.jairusu.panaderotag.Methods.TagManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerTag implements Listener {

   @EventHandler
   public void onPlayerTag(EntityDamageByEntityEvent event) {
      if (!(event.getEntity() instanceof Player)) return;
      if (!(event.getDamager() instanceof Player)) return;

      Player tagger = (Player) event.getDamager();
      Player player = (Player) event.getEntity();

      if (!TagManager.getTeam().hasPlayer(tagger)) return;
      clearTagger(tagger);
      setTagger(player);
      TagManager.teleportToRandom(player);
   }

   private void clearTagger(Player player) {
      for (String entry : TagManager.getTeam().getEntries()) {
         TagManager.getTeam().removeEntry(entry);
      }
      player.removePotionEffect(PotionEffectType.GLOWING);
   }

   private void setTagger(Player player) {
      TagManager.getTeam().addEntry(player.getName());
      player.sendMessage(Configuration.formatText(Configuration.getString("messages.tagMessage")));
      player.playSound(player.getLocation(), Sound.ENTITY_WITHER_BREAK_BLOCK, 1, 1);
      player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 1,true, true));
      player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, PotionEffect.INFINITE_DURATION, 1,true, true));
      player.setGameMode(GameMode.SPECTATOR);
      Bukkit.getScheduler().runTaskLater(Configuration.getPlugin, ()-> player.setGameMode(GameMode.ADVENTURE), 1L);
   }

}
