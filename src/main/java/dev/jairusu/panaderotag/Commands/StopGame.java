package dev.jairusu.panaderotag.Commands;

import dev.jairusu.panaderotag.Methods.AbilitiesManager;
import dev.jairusu.panaderotag.Methods.Configuration;
import dev.jairusu.panaderotag.Methods.GlowManager;
import dev.jairusu.panaderotag.Methods.TagManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class StopGame implements TabCompleter, CommandExecutor {

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

      if (TagManager.getTeam().getEntries().isEmpty()) {
         sender.sendMessage("There's no game to stop!");
         return true;
      }

      AbilitiesManager.stopTaskTimer();
      GlowManager.stopTaskTimer();
      Configuration.getPlugin.getLogger().info("Tag Game Stopped");

      for (String playerName : TagManager.getTeam().getEntries()) {
         TagManager.getTeam().removeEntry(playerName);
      }

      for (Player player : Bukkit.getOnlinePlayers()) {
         if (!player.getWorld().getName().equals(Configuration.getString("config.tagWorld"))) continue;
         player.removePotionEffect(PotionEffectType.GLOWING);
         player.getInventory().clear();
      }
      return true;
   }

}
