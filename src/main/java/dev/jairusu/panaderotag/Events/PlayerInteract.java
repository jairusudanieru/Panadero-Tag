package dev.jairusu.panaderotag.Events;

import dev.jairusu.panaderotag.Methods.AbilitiesManager;
import dev.jairusu.panaderotag.Methods.Configuration;
import dev.jairusu.panaderotag.Methods.TagManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class PlayerInteract implements Listener {

   @EventHandler
   public void onAbilityUse(PlayerInteractEvent event) {
      Action action = event.getAction();
      if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {

         ItemStack itemStack = event.getItem();
         if (itemStack == null) return;

         Player player = event.getPlayer();
         if (itemStack.equals(AbilitiesManager.invisibilityItem())) {
            player.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, player.getLocation(), 1);
            player.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, player.getLocation(), 30, 0.5, 0.5, 0.5, 0.1);
            player.getWorld().spawnParticle(Particle.SMOKE_LARGE, player.getLocation(), 10, 0.5, 0.5, 0.5, 0.1);
            player.sendMessage("You now have \"Invisibility\"");
            player.getInventory().getItemInMainHand().setAmount(0);
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100, 1, true, false));
            if (TagManager.getTeam().hasPlayer(player)) {
               player.removePotionEffect(PotionEffectType.GLOWING);
               Bukkit.getScheduler().runTaskLater(Configuration.getPlugin, ()-> {
                  player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, PotionEffect.INFINITE_DURATION, 1,true, false));
               }, 1L);
            }
         }

         if (itemStack.equals(AbilitiesManager.speedBoostItem())) {
            player.sendMessage("You now have \"Speed Boost\"");
            player.getInventory().getItemInMainHand().setAmount(0);
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60, 2, true, false));
         }

         if (itemStack.equals(AbilitiesManager.jumpBoostItem())) {
            player.sendMessage("You now have \"Jump Boost\"");
            player.getInventory().getItemInMainHand().setAmount(0);
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 60, 5, true, false));
         }
      }
   }

   @EventHandler
   public void onPlayerInteract(PlayerInteractEvent event) {
      Player player = event.getPlayer();
      World world = player.getWorld();
      Block block = event.getClickedBlock();
      if (block == null) return;

      List<Material> blockList = new ArrayList<>();
      blockList.add(Material.DARK_OAK_DOOR);
      blockList.add(Material.SPRUCE_DOOR);
      blockList.add(Material.BIRCH_DOOR);
      blockList.add(Material.OAK_DOOR);
      blockList.add(Material.JUNGLE_DOOR);
      blockList.add(Material.ACACIA_DOOR);

      if (!world.getName().equals(Configuration.getString("config.tagWorld"))) return;
      if (event.getAction() == Action.RIGHT_CLICK_BLOCK && blockList.contains(block.getType())) return;
      if (player.getGameMode().equals(GameMode.CREATIVE)) return;
      event.setCancelled(true);
   }

}
