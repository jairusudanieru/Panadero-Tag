package dev.jairusu.panaderotag.Methods;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TagManager {

   public static Team getTeam() {
      Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
      Team team = scoreboard.getTeam("tag:tagger");
      if (team == null) {
         team = scoreboard.registerNewTeam("tag:tagger");
         team.color(NamedTextColor.RED);
      }
      return team;
   }

   public static void teleportToRandom(Player player) {
      List<Location> locations = new ArrayList<>();
      World world = Bukkit.getWorld(Configuration.getString("config.tagWorld"));

      locations.add(new Location(world, -34.5, 61, -55.5, 0, 0));
      locations.add(new Location(world, 24.5, 57, -62.5, 0, 0));
      locations.add(new Location(world, 8.5, 62, 27.5, 180, 0));
      locations.add(new Location(world, -41.5 ,63 ,7.5,215 ,0));

      Random random = new Random();
      int randomIndex = random.nextInt(locations.size());
      Location randomLocation = locations.get(randomIndex);
      player.teleport(randomLocation);
   }

}
