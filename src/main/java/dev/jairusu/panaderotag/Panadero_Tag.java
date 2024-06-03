package dev.jairusu.panaderotag;

import dev.jairusu.panaderotag.Commands.Main;
import dev.jairusu.panaderotag.Commands.StartGame;
import dev.jairusu.panaderotag.Commands.StopGame;
import dev.jairusu.panaderotag.Events.*;
import dev.jairusu.panaderotag.Methods.Configuration;
import dev.jairusu.panaderotag.Methods.SpawnTrophy;
import dev.jairusu.panaderotag.Methods.TagManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public final class Panadero_Tag extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new ClaimTrophy(), this);
        Bukkit.getPluginManager().registerEvents(new CommandSend(), this);
        Bukkit.getPluginManager().registerEvents(new ItemPickup(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteract(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerMove(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerTag(), this);
        Bukkit.getPluginManager().registerEvents(new QuitTagGame(), this);
        Objects.requireNonNull(Bukkit.getPluginCommand("panaderotag")).setExecutor(new Main());
        Objects.requireNonNull(Bukkit.getPluginCommand("startgame")).setExecutor(new StartGame());
        Objects.requireNonNull(Bukkit.getPluginCommand("stopgame")).setExecutor(new StopGame());
    }

    @Override
    public void onDisable() {
        World world = Bukkit.getWorld(Configuration.getString("config.tagWorld"));
        if (world == null) return;

        for (String playerName : TagManager.getTeam().getEntries()) {
            TagManager.getTeam().removeEntry(playerName);
        }

        for (Player players : Bukkit.getOnlinePlayers()) {
            if (!players.getWorld().getName().equals(world.getName())) continue;
            players.removePotionEffect(PotionEffectType.GLOWING);
            players.getInventory().clear();
        }

        SpawnTrophy.task[0].cancel();
        world.getEntitiesByClass(Item.class).forEach(Entity::remove);
        world.getEntitiesByClass(ArmorStand.class).forEach(Entity::remove);
        Configuration.getPlugin.getLogger().info("Tag Game Stopped");
    }
}
