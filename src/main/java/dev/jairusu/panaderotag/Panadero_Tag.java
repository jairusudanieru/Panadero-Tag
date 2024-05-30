package dev.jairusu.panaderotag;

import dev.jairusu.panaderotag.Commands.Main;
import dev.jairusu.panaderotag.Commands.StartGame;
import dev.jairusu.panaderotag.Commands.StopGame;
import dev.jairusu.panaderotag.Events.*;
import dev.jairusu.panaderotag.Methods.AbilitiesManager;
import dev.jairusu.panaderotag.Methods.Configuration;
import dev.jairusu.panaderotag.Methods.GlowManager;
import dev.jairusu.panaderotag.Methods.TagManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Panadero_Tag extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
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
        TagManager.getTeam().getEntries().clear();
        AbilitiesManager.stopTaskTimer();
        GlowManager.stopTaskTimer();
        Configuration.getPlugin.getLogger().info("Tag Game Stopped");
    }
}
