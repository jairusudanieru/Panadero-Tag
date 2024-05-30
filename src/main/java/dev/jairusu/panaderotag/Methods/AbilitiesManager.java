package dev.jairusu.panaderotag.Methods;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;
import net.kyori.adventure.text.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AbilitiesManager {

   public static final List<Item> spawnedItems = new ArrayList<>();

   private static final ItemStack[] spawnableMaterials = {
           speedBoostItem(),
           invisibilityItem(),
           jumpBoostItem()
   };

   public static void spawnAbilities() {
      World world = Bukkit.getWorld(Configuration.getString("config.tagWorld"));
      if (world == null) return;

      Bukkit.getScheduler().runTaskTimer(Configuration.getPlugin, () -> {
         world.getEntitiesByClass(Item.class).forEach(Entity::remove);
         spawnedItems.clear();
         if (TagManager.getTeam().getEntries().isEmpty()) return;

         int minHeight = world.getMinHeight();
         int maxHeight = world.getMaxHeight();
         int worldSize = 75;

         for (int x = -worldSize; x <= worldSize; x++) {
            for (int z = -worldSize; z <= worldSize; z++) {
               for (int y = minHeight; y <= maxHeight; y++) {
                  Location location = new Location(world, x, y, z);
                  if (location.getBlock().getType() == Material.IRON_BLOCK) {
                     Location spawnLocation = location.add(0.5, 1, 0.5);
                     ItemStack randomItem = getRandomItem();
                     Item spawnedItem = world.dropItem(spawnLocation, randomItem);
                     spawnedItem.setVelocity(new Vector(0, 0, 0));
                     spawnedItems.add(spawnedItem);
                  }
               }
            }
         }
      }, 1L, 2400L);
   }

   public static ItemStack speedBoostItem() {
      ItemStack itemStack = new ItemStack(Material.FEATHER);
      ItemMeta itemMeta = itemStack.getItemMeta();
      itemMeta.displayName(Configuration.formatText("<reset>Speed Boost Item"));
      List<Component> lore = new ArrayList<>();
      lore.add(Configuration.formatText("<gray>Click to enable speed boost"));
      itemMeta.lore(lore);
      itemStack.setItemMeta(itemMeta);
      return itemStack;
   }

   public static ItemStack invisibilityItem() {
      ItemStack itemStack = new ItemStack(Material.GRAY_DYE);
      ItemMeta itemMeta = itemStack.getItemMeta();
      itemMeta.displayName(Configuration.formatText("<reset>Invisibility Item"));
      List<Component> lore = new ArrayList<>();
      lore.add(Configuration.formatText("<gray>Click to enable invisibility"));
      itemMeta.lore(lore);
      itemStack.setItemMeta(itemMeta);
      return itemStack;
   }

   public static ItemStack jumpBoostItem() {
      ItemStack itemStack = new ItemStack(Material.SLIME_BALL);
      ItemMeta itemMeta = itemStack.getItemMeta();
      itemMeta.displayName(Configuration.formatText("<reset>Jump Boost Item"));
      List<Component> lore = new ArrayList<>();
      lore.add(Configuration.formatText("<gray>Click to enable jump boost"));
      itemMeta.lore(lore);
      itemStack.setItemMeta(itemMeta);
      return itemStack;
   }

   private static ItemStack getRandomItem() {
      Random random = new Random();
      return spawnableMaterials[random.nextInt(spawnableMaterials.length)];
   }

}
