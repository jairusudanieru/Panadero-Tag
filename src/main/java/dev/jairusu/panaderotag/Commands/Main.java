package dev.jairusu.panaderotag.Commands;

import dev.jairusu.panaderotag.Methods.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main implements TabCompleter, CommandExecutor {

   @Override
   public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
      if (!sender.isOp()) return Arrays.asList("ping","");
      if (args.length != 1) return new ArrayList<>();
      return Arrays.asList("reload","ping","spawnTrophy","despawnTrophy");
   }

   @Override
   public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
      if (args.length != 1) {
         sender.sendMessage("Invalid command usage!");
         return true;
      }

      switch (args[0]) {
         case "reload":
            Configuration.getPlugin.getLogger().info("Tag Game Stopped");
            Configuration.getPlugin.reloadConfig();
            sender.sendMessage("Configuration successfully reloaded");
            break;
         case "ping":
            sender.sendMessage("pong");
            break;
         case "spawnTrophy":
            if (!sender.isOp()) return true;
            SpawnTrophy.spawnToRandom();
            break;
         case "despawnTrophy":
            if (!sender.isOp()) return true;
            if (!(sender instanceof Player)) return true;
            Player player = (Player) sender;
            player.getWorld().getEntitiesByClass(ArmorStand.class).forEach(Entity::remove);
            break;
      }
      return true;
   }
}
