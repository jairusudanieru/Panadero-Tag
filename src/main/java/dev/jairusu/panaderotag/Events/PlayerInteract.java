package dev.jairusu.panaderotag.Events;

import dev.jairusu.panaderotag.Methods.AbilitiesManager;
import dev.jairusu.panaderotag.Methods.Configuration;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerInteract implements Listener {

   @EventHandler
   public void onAbilityUse(PlayerInteractEvent event) {
      if (!event.getAction().equals(Action.RIGHT_CLICK_AIR) && !event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;

      ItemStack itemStack = event.getItem();
      if (itemStack == null) return;

      Player player = event.getPlayer();
      if (itemStack.equals(AbilitiesManager.invisibilityItem())) {
         player.sendMessage("You now have \"Invisibility\"");
         player.getInventory().getItemInMainHand().setAmount(0);
         player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100, 1, true, false));
      }

      if (itemStack.equals(AbilitiesManager.speedBoostItem())) {
         player.sendMessage("You now have \"Speed Boost\"");
         player.getInventory().getItemInMainHand().setAmount(0);
         player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 2, true, false));
      }

      if (itemStack.equals(AbilitiesManager.jumpBoostItem())) {
         player.sendMessage("You now have \"Jump Boost\"");
         player.getInventory().getItemInMainHand().setAmount(0);
         player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100, 5, true, false));
      }
   }

   @EventHandler
   public void onPlayerInteract(PlayerInteractEvent event) {
      Player player = event.getPlayer();
      World world = player.getWorld();

      if (!world.getName().equals(Configuration.getString("config.tagWorld"))) return;
      if (player.getGameMode().equals(GameMode.CREATIVE)) return;
      event.setCancelled(true);
   }

}
