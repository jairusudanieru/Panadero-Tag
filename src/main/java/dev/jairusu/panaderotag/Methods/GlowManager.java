package dev.jairusu.panaderotag.Methods;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class GlowManager implements Listener {

   public static BukkitRunnable task;

   public static void startTaskTimer(World world) {
      task = new BukkitRunnable() {
         @Override
         public void run() {


            for (Player players : Bukkit.getOnlinePlayers()) {
               if (!world.equals(players.getWorld())) continue;
               players.playSound(players.getLocation(), Sound.ENTITY_WITHER_BREAK_BLOCK, 1, 1);
               if (TagManager.getTeam().hasPlayer(players)) {
                  players.sendMessage("Revealed all players for 5 seconds!");
                  continue;
               }
               players.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 100, 1,true, false));
            }
         }
      };
      task.runTaskTimer(Configuration.getPlugin, 0L, 2400L);
   }

   public static void stopTaskTimer() {
      if (task != null) {
         task.cancel();
         task = null;
      }



   }

}
