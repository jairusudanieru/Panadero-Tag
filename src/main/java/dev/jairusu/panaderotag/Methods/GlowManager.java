package dev.jairusu.panaderotag.Methods;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class GlowManager implements Listener {

   public static void runGlowManager() {
      World world = Bukkit.getWorld(Configuration.getString("config.tagWorld"));
      if (world == null) return;

      Bukkit.getScheduler().runTaskTimer(Configuration.getPlugin, ()-> {
         if (TagManager.getTeam().getEntries().isEmpty()) return;
         for (Player players : Bukkit.getOnlinePlayers()) {
            if (!world.equals(players.getWorld())) continue;
            players.playSound(players.getLocation(), Sound.ENTITY_WITHER_BREAK_BLOCK, 1, 1);
            if (TagManager.getTeam().hasPlayer(players)) {
               players.sendMessage("Revealed all players for 5 seconds!");
               continue;
            }
            players.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 100, 1,true, false));
         }
      },1L, 1800L);
   }

}
