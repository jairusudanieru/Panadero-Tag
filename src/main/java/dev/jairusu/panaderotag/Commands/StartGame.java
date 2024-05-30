package dev.jairusu.panaderotag.Commands;

import dev.jairusu.panaderotag.Methods.*;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class StartGame implements TabCompleter, CommandExecutor {

   @Override
   public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
      return new ArrayList<>();
   }

   @Override
   public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
      if (!(sender instanceof Player)) {
         sender.sendMessage("You must be a player to use this command!");
         return true;
      }

      if (!TagManager.getTeam().getEntries().isEmpty()) {
         sender.sendMessage("The game is already starting!");
         return true;
      }

      Player player = (Player) sender;
      clearArena();
      setTagger(player);
      TagManager.teleportToRandom(player);
      AbilitiesManager.spawnAbilities();
      GlowManager.runGlowManager();
      SpawnTrophy.startSpawning();
      Configuration.getPlugin.getLogger().info("Tag Game Started");
      return true;
   }

   private void clearArena() {
      for (Player players : Bukkit.getOnlinePlayers()) {
         if (!players.getWorld().getName().equals(Configuration.getString("config.tagArena"))) continue;
         players.removePotionEffect(PotionEffectType.GLOWING);
      }
   }

   private void setTagger(Player player) {
      TagManager.getTeam().addEntry(player.getName());
      player.sendMessage(Configuration.formatText(Configuration.getString("messages.tagMessage")));
      player.playSound(player.getLocation(), Sound.ENTITY_WITHER_BREAK_BLOCK, 1, 1);
      player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 1,true, true));
      player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, PotionEffect.INFINITE_DURATION, 1,true, true));
      player.setGameMode(GameMode.SPECTATOR);
      Bukkit.getScheduler().runTaskLater(Configuration.getPlugin, ()-> player.setGameMode(GameMode.ADVENTURE), 100L);
   }

}
