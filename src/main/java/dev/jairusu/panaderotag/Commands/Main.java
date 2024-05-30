package dev.jairusu.panaderotag.Commands;

import dev.jairusu.panaderotag.Methods.AbilitiesManager;
import dev.jairusu.panaderotag.Methods.Configuration;
import dev.jairusu.panaderotag.Methods.GlowManager;
import dev.jairusu.panaderotag.Methods.TagManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main implements TabCompleter, CommandExecutor {

   @Override
   public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
      if (!sender.isOp()) return Arrays.asList("ping","");
      if (args.length != 1) return new ArrayList<>();
      return Arrays.asList("reload","ping");
   }

   @Override
   public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
      if (args.length != 1) {
         sender.sendMessage("Invalid command usage!");
         return true;
      }

      if (args[0].equals("reload")) {
         TagManager.getTeam().getEntries().clear();
         AbilitiesManager.stopTaskTimer();
         GlowManager.stopTaskTimer();
         Configuration.getPlugin.getLogger().info("Tag Game Stopped");
         Configuration.getPlugin.reloadConfig();
         sender.sendMessage("Configuration successfully reloaded");
      } else if (args[0].equals("ping")) {
         sender.sendMessage("pong");
      }
      return true;
   }
}
