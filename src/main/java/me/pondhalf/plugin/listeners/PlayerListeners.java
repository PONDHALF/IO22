package me.pondhalf.plugin.listeners;

import me.pondhalf.plugin.IO22;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import static me.pondhalf.plugin.utils.color;

public class PlayerListeners implements Listener {

    private IO22 plugin;

    public PlayerListeners(IO22 plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(PlayerDeathEvent event) {
        Player player = (Player) event.getEntity();
        Location location = player.getLocation();
        plugin.getServer().broadcastMessage(color("(&7" + player.getLocation()
                .getWorld().getName() + "&f) &f" + player.getName() + " &6Death waypoint at " + "&ex: &f" + location.getBlockX() + " &ey: &f" + location.getBlockY() + " &ez: &f" + location.getBlockZ()));

    }

}
