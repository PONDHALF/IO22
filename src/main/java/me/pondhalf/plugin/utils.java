package me.pondhalf.plugin;

import org.bukkit.ChatColor;

import java.awt.*;
import java.util.logging.Logger;

public class utils {

    private static Logger logger = IO22.getPluginLogger();

    public static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String decolor(String text) {
        return ChatColor.stripColor(text);
    }

    public static void log(String...messages) {
        for (String msg : messages) {
            logger.info(msg);
        }
    }



}
