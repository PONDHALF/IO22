package me.pondhalf.plugin;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Utils {

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

    public static ItemStack createItem(Material type, int amount, boolean glow, boolean unbreakable
            ,boolean hideUnb, String name, String... lines) {

        ItemStack item = new ItemStack(type, amount);
        ItemMeta itemMeta = item.getItemMeta();
        if (glow) {
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            itemMeta.addEnchant(Enchantment.DURABILITY,1,true);
        }

        if (unbreakable) itemMeta.setUnbreakable(true);
        if (hideUnb) itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        if (name != null) itemMeta.setDisplayName(color(name));
        if (lines != null) {
            List<String> lore = new ArrayList<>();
            for (String line : lines) {
                lore.add(color(line));
            }
            itemMeta.setLore(lore);
        }

        item.setItemMeta(itemMeta);

        return item;
    }

    public static ItemStack createItemModelData(Material type, int modelData) {
        ItemStack item = new ItemStack(type, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setCustomModelData(modelData);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack createItemModelData(ItemStack item, int modelData) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setCustomModelData(modelData);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack[] makeArmorSet(ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots) {
        ItemStack[] armor = new ItemStack[4];
        armor[3] = helmet;
        armor[2] = chestplate;
        armor[1] = leggings;
        armor[0] = boots;
        return armor;
    }

}
