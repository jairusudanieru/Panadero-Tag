package dev.jairusu.panaderotag.Methods;

import dev.jairusu.panaderotag.Panadero_Tag;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.java.JavaPlugin;

public class Configuration {

   public static JavaPlugin getPlugin = JavaPlugin.getPlugin(Panadero_Tag.class);

   public static Component formatText(String text) {
      MiniMessage miniMessage = MiniMessage.miniMessage();
      return miniMessage.deserialize(text);
   }

   public static String getString(String key) {
      return getPlugin.getConfig().getString(key);
   }


}
