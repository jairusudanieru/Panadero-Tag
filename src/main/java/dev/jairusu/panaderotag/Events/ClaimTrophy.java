package dev.jairusu.panaderotag.Events;

import dev.jairusu.panaderotag.Methods.Configuration;
import dev.jairusu.panaderotag.Methods.TagManager;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ClaimTrophy implements Listener {

   @EventHandler
   public void onPlayerTag(EntityDamageByEntityEvent event) {
      if (!(event.getEntity() instanceof ArmorStand)) return;
      if (!(event.getDamager() instanceof Player)) return;

      Player player = (Player) event.getDamager();
      ArmorStand armorStand = (ArmorStand) event.getEntity();

      World world = Bukkit.getWorld(Configuration.getString("config.tagWorld"));
      if (world == null) return;

      armorStand.remove();
      if (TagManager.getTeam().hasPlayer(player)) {
         taggerBonus(world);
         return;
      }

      playerBonus(world);
   }

   private void taggerBonus(World world) {
      for (Player players : Bukkit.getOnlinePlayers()) {
         if (!world.equals(players.getWorld())) continue;
         players.playSound(players.getLocation(), Sound.ENTITY_WITHER_BREAK_BLOCK, 1, 1);
         if (TagManager.getTeam().hasPlayer(players)) {
            players.sendMessage("<green>Slowed all players for 15 seconds!");
            continue;
         }

         players.getInventory().clear();
         players.sendMessage("<red>You have been slowed for 15 seconds!");
         players.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 300, 20,true, false));
         players.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 300, 20,true, false));
         players.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 300, 1, true, false));
      }
   }

   private void playerBonus(World world) {
      for (Player players : Bukkit.getOnlinePlayers()) {
         if (!world.equals(players.getWorld())) continue;
         players.playSound(players.getLocation(), Sound.ENTITY_WITHER_BREAK_BLOCK, 1, 1);
         if (!TagManager.getTeam().hasPlayer(players)) {
            players.sendMessage("<green>The tagger has been detained for 10 seconds!");
            continue;
         }
         players.sendMessage("<red>You have been detained for 10 seconds!");
         players.teleport(new Location(world, -7.5, 90, -10.5, 0, 100));
         Bukkit.getScheduler().runTaskLater(Configuration.getPlugin, ()-> {
            TagManager.teleportToRandom(players);
         }, 200L);
      }
   }

   @EventHandler
   public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
      Entity entity = event.getRightClicked();
      if (entity instanceof ArmorStand) {
         event.setCancelled(true);
      }
   }
}
