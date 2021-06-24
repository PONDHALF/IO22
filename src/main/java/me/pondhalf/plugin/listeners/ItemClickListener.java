package me.pondhalf.plugin.listeners;

import me.pondhalf.plugin.IO22;
import me.pondhalf.plugin.gui.TeleportGUI;
import me.pondhalf.plugin.items.TeleportPaper;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ItemClickListener implements Listener {

    private IO22 plugin;
    private TeleportPaper teleportPaper;
    private TeleportGUI teleportGUI;

    public ItemClickListener(IO22 plugin) {
        this.plugin = plugin;
        this.teleportPaper = plugin.getTeleportPaper();
        this.teleportGUI = plugin.getTeleportGUI();
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        ItemStack teleportPaperItem = teleportPaper.getTeleportPaperItem();
        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            if (teleportPaperItem.isSimilar(itemInMainHand)) {
                int AmountLeft = itemInMainHand.getAmount();
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1, 1);
                teleportGUI.openGUI(player);
            }
        }
    }

}
