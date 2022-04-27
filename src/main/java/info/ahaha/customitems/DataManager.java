package info.ahaha.customitems;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DataManager {
    public static CustomItems plugin;
    public static FileConfiguration config = null;
    public static File configfile = null;

    public DataManager(CustomItems plugins) {
        plugin = plugins;
        saveDefaultConfig();
    }

    public static void reloadConfig() {
        if (configfile == null)
            configfile = new File(plugin.getDataFolder(), "config.yml");

        config = YamlConfiguration.loadConfiguration(configfile);

        InputStream configStream = plugin.getResource("config.yml");

        if (configStream != null) {
            YamlConfiguration configs = YamlConfiguration.loadConfiguration(new InputStreamReader(configStream));
            config.setDefaults(configs);
        }

    }


    public FileConfiguration getConfig() {
        if (config == null)
            reloadConfig();
        return config;
    }


    public void saveDefaultConfig() {

        if (configfile == null)
            configfile = new File(plugin.getDataFolder(), "config.yml");

        if (!configfile.exists())
            plugin.saveResource("config.yml", false);

    }
}
