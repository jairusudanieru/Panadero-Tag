package dev.jairusu.panaderotag.Events;

import dev.jairusu.panaderotag.Methods.AbilitiesManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ItemPickup implements Listener {

   @EventHandler
   public void onItemPickup(PlayerAttemptPickupItemEvent event) {
      ItemStack pickupItem = event.getItem().getItemStack();
      Player player = event.getPlayer();
      Inventory inventory = player.getInventory();

      if (pickupItem.equals(AbilitiesManager.jumpBoostItem()) && inventory.contains(AbilitiesManager.jumpBoostItem())) {
         event.setCancelled(true);
      }

      if (pickupItem.equals(AbilitiesManager.invisibilityItem()) && inventory.contains(AbilitiesManager.invisibilityItem())) {
         event.setCancelled(true);
      }

      if (pickupItem.equals(AbilitiesManager.speedBoostItem()) && inventory.contains(AbilitiesManager.speedBoostItem())) {
         event.setCancelled(true);
      }
   }

}
