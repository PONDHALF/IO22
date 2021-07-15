package me.pondhalf.plugin.items;

import me.pondhalf.plugin.IO22;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

import static me.pondhalf.plugin.Utils.color;

public class TeleportPaper {

    private IO22 plugin;

    private ItemStack TELEPORTPAPER;

    public TeleportPaper(IO22 plugin) {
        this.plugin = plugin;

        TELEPORTPAPER = new ItemStack(Material.PAPER, 1);
        ItemMeta TELEPORTPAPER_META = TELEPORTPAPER.getItemMeta();
        TELEPORTPAPER_META.setDisplayName(color("&f&lTeleport Paper"));
        TELEPORTPAPER_META.setLore(Arrays.asList("", color("&a> &7ถือ item ขณะใช้"), color("&a> &7พิมพ์ /tpa <ชื่อผู้เล่น>")));
        TELEPORTPAPER_META.addEnchant(Enchantment.ARROW_DAMAGE, 4, false);
        TELEPORTPAPER_META.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        TELEPORTPAPER.setItemMeta(TELEPORTPAPER_META);
    }

    public ItemStack getTeleportPaperItem() {
        return TELEPORTPAPER;
    }

}
