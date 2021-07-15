package me.pondhalf.plugin.listeners;

import com.destroystokyo.paper.event.player.PlayerAdvancementCriterionGrantEvent;
import me.pondhalf.plugin.IO22;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.world.PortalCreateEvent;

import java.util.HashMap;
import java.util.UUID;

import static me.pondhalf.plugin.Utils.color;

public class PlayerListeners implements Listener {

    private IO22 plugin;

    private HashMap<UUID, Location> deathLocData;

    public PlayerListeners(IO22 plugin) {
        this.plugin = plugin;
        this.deathLocData = plugin.DeathLocationData;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(PlayerDeathEvent event) {
        Player player = (Player) event.getEntity();
        Location location = player.getLocation();
        plugin.getServer().broadcastMessage(color("(&7" + player.getLocation()
                .getWorld().getName() + "&f) &f" + player.getName() + " &6Death waypoint at " + "&ex: &f" + location.getBlockX() + " &ey: &f" + location.getBlockY() + " &ez: &f" + location.getBlockZ()));
        deathLocData.put(player.getUniqueId(), player.getLocation());
        player.sendMessage(color("&6Type &e/back &6to return to death waypoint!"));

    }

    @EventHandler
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {

        Bukkit.getServer().getScheduler().runTaskLater(plugin, new Runnable() {

            @Override
            public void run() {
                if (event.getPlayer().isSleeping()) {
                    event.getPlayer().getWorld().setTime(0L);
                    event.getPlayer().getWorld().setStorm(false);
                    event.getPlayer().getWorld().setThundering(false);
                    plugin.getServer().broadcastMessage(color("&b&lIO22 &f" + event.getPlayer().getName() + " &ewent to bed."));

                } else {
                    return;
                }
            }

        }, 100L);

    }
    
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        if (!player.getWorld().equals(plugin.getCreativeWorld())) return;

        if (player.getWorld().equals(plugin.getCreativeWorld())) {
            if (block.getType() == Material.ENDER_CHEST) {
                event.setCancelled(true);
                player.sendMessage(color(""));
                player.sendMessage(color("&b&lIO22 &fYou can't use this block!"));
                player.sendMessage(color(""));
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
            }
        }
    }

    @EventHandler
    public void onPlaceEnderChest(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if (player.getWorld().equals(plugin.getCreativeWorld())) {
            if (block.getType() == Material.ENDER_CHEST) {
                event.setCancelled(true);
                player.sendMessage(color(""));
                player.sendMessage(color("&b&lIO22 &fYou can't place this block!"));
                player.sendMessage(color(""));
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO,1,1);
            }
        }
    }

    @EventHandler
    public void onEntityPortal(EntityPortalEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        if (event.getEntity().getWorld().getName().equalsIgnoreCase("creative")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPortalEvent(PlayerPortalEvent event) {
        if (event.getPlayer().getWorld().getName().equalsIgnoreCase("creative")) {
            event.setCanCreatePortal(false);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPortalCreate(PortalCreateEvent event) {
        if (event.getWorld().getName().equalsIgnoreCase("creative")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onAdvacement(PlayerAdvancementCriterionGrantEvent event) {
        Player player = event.getPlayer();
        World world = player.getLocation().getWorld();

        if (world.equals(plugin.getCreativeWorld())) {
            event.setCancelled(true);
        }
    }

}
