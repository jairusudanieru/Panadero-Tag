package dev.jairusu.panaderotag.Methods;

import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpawnTrophy {

   private static void spawn(Location location) {
      World world = Bukkit.getWorld(Configuration.getString("config.tagWorld"));
      if (world == null) return;

      world.getEntitiesByClass(ArmorStand.class).forEach(Entity::remove);
      Entity entity = world.spawnEntity(location, EntityType.ARMOR_STAND);
      entity.setCustomNameVisible(false);
      entity.setGlowing(true);

      ArmorStand armorStand = (ArmorStand) entity;
      armorStand.setItem(EquipmentSlot.HEAD, new ItemStack(Material.GOLDEN_HELMET));
      armorStand.setItem(EquipmentSlot.FEET, new ItemStack(Material.GOLDEN_BOOTS));
      armorStand.setSmall(true);
      armorStand.setInvisible(true);

      for (Player player : Bukkit.getOnlinePlayers()) {
         if (!player.getWorld().equals(location.getWorld())) continue;
         player.sendMessage(Configuration.formatText("<gold>A wild Trophy has been spawned!"));
         player.playSound(player.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1, 1);
         if (TagManager.getTeam().hasPlayer(player)) {
            player.sendMessage("Revealed all players for 5 seconds!");
            continue;
         }
         player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 100, 1,true, false));
      }
   }

   public static void spawnToRandom() {
      List<Location> locations = new ArrayList<>();
      World world = Bukkit.getWorld(Configuration.getString("config.tagWorld"));

      locations.add(new Location(world, -39.5, 65, -19.5, 90, 0));
      locations.add(new Location(world, -26.5, 61, 5.5, 180, 0));
      locations.add(new Location(world, -2.5, 70, 19.5, 270, 0));
      locations.add(new Location(world, 25.5, 65, -15.5, 270, 0));
      locations.add(new Location(world, 23.5, 61, 13.5, 90, 0));
      locations.add(new Location(world, -28.5, 68, -9.5, 0, 0));

      Random random = new Random();
      int randomIndex = random.nextInt(locations.size());
      Location randomLocation = locations.get(randomIndex);
      spawn(randomLocation);
   }

   public static final BukkitTask[] task = new BukkitTask[1];

   public static void startSpawning() {
      task[0] = Bukkit.getScheduler().runTaskTimer(Configuration.getPlugin, () -> {
         if (TagManager.getTeam().getEntries().isEmpty()) {
            task[0].cancel();
            return;
         }
         spawnToRandom();
      }, TagManager.trophySpawnTime(), TagManager.trophySpawnTime());
   }

}
