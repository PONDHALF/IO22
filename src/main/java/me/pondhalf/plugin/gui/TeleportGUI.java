package me.pondhalf.plugin.gui;

import me.pondhalf.plugin.IO22;
import me.pondhalf.plugin.utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

import static me.pondhalf.plugin.utils.color;

public class TeleportGUI {

    private IO22 plugin;

    public TeleportGUI(IO22 plugin) {
        this.plugin = plugin;
    }

    private Inventory gui;

    public static String GUI_TITLE = color("&bTeleport Paper");

    public void openGUI(Player player) {
        ArrayList<Player> player_list = new ArrayList<Player>(player.getServer().getOnlinePlayers());
        gui = Bukkit.createInventory(player, 45, GUI_TITLE);

        for (int i = 0; i < player_list.size(); i++) {
            ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD, 1);
            ItemMeta meta = playerHead.getItemMeta();
            meta.setDisplayName(color("&f" + player_list.get(i).getName()));
            meta.setLore(Arrays.asList(color(" "), color("&a&lLeft-Click to teleport!")));
            playerHead.setItemMeta(meta);

            gui.addItem(playerHead);
        }
        player.openInventory(gui);
    }

    public Inventory getGUI() {
        return gui;
    }

}
