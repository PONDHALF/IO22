package me.pondhalf.plugin;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DataManager {

    private IO22 plugin;
    private String fileName;
    private FileConfiguration config = null;
    private File file = null;

    public DataManager(IO22 plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;
        // save/init the config
        saveDefaultConfig();
    }

    public void reloadConfig() {
        if (this.file == null) {
            this.file = new File(plugin.getDataFolder(), fileName);
        }

        this.config = YamlConfiguration.loadConfiguration(file);

        InputStream defaultStream = plugin.getResource(fileName);
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            config.setDefaults(defaultConfig);
        }
    }

    public FileConfiguration getConfig() {
        if (config == null) {
            reloadConfig();
        }
        return config;
    }

    public void saveConfig() {
        if (config == null || file == null) {
            return;
        }

        try {
            getConfig().save(file);

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void saveDefaultConfig() {
        if (file == null) {
            file = new File(plugin.getDataFolder(), fileName);
        }

        if (!file.exists()) {
            plugin.saveResource(fileName, false);
        }

    }
}
