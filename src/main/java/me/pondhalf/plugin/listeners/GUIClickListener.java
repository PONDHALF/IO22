package me.pondhalf.plugin.listeners;

import me.pondhalf.plugin.IO22;
import me.pondhalf.plugin.gui.TeleportGUI;
import me.pondhalf.plugin.items.TeleportPaper;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import static me.pondhalf.plugin.Utils.color;
import static me.pondhalf.plugin.Utils.decolor;

public class GUIClickListener implements Listener {

    private IO22 plugin;
    private TeleportGUI teleportGUI;
    private TeleportPaper teleportPaper;

    public GUIClickListener(IO22 plugin) {
        this.plugin = plugin;
        this.teleportGUI = plugin.getTeleportGUI();
        this.teleportPaper = plugin.getTeleportPaper();
    }

    @EventHandler
    public void onGuiClick(InventoryClickEvent event) {
        // Get WhoClicked(Player)
        Player player = (Player) event.getWhoClicked();

        // If player's inventory isn't gui (EX: player's inventory)
        if (!(event.getView().getTitle().equalsIgnoreCase(TeleportGUI.GUI_TITLE))) return;
        // If currentItem click is air or none
        if (event.getCurrentItem() == null) return;
        // If itemMeta of currentItem is none
        if (event.getCurrentItem().getItemMeta() == null) return;
        // If ItemMeta(DisplayName) of currentItem is none
        if (event.getCurrentItem().getItemMeta().getDisplayName() == null) return;

        // Get ClickType (EX: right, left)
        ClickType clickType = event.getClick();
        // Get ItemInMainHand of player(clicker)
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

        // If player's inventory is GUI
        if (event.getView().getTitle().equalsIgnoreCase(TeleportGUI.GUI_TITLE)) {
            // When Left-Click
            if (clickType == ClickType.LEFT) {
                // Check ItemInMain is Teleport Paper
                if (teleportPaper.getTeleportPaperItem().isSimilar(itemInMainHand)) {
                    // If item clicked is Player Head
                    if (event.getCurrentItem().getType() == Material.PLAYER_HEAD) {
                        // Get Amount of items in mainHand
                        int AmountLeft = itemInMainHand.getAmount();
                        // Check If selector isn't none
                        if (player.getServer().getPlayer(decolor(event.getCurrentItem().getItemMeta().getDisplayName())) != null) {
                            // Get Player from DisplayName's Player Head
                            Player whoToTeleport = player.getServer().getPlayer(decolor(event.getCurrentItem().getItemMeta().getDisplayName()));
                            // Update Amount of items
                            itemInMainHand.setAmount(AmountLeft - 1);
                            // Teleport player(clicker) to selector(player head)
                            player.teleport(whoToTeleport.getLocation());
                            // play sound to player (gimmick effect)
                            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                            // output message
                            player.sendMessage(color("&b&lIO22 &fTeleport.."));
                        }
                    }
                }
            }
        }
        // make it so they cant move items
        event.setCancelled(true);
    }

}
