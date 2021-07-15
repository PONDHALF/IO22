package me.pondhalf.plugin.commands;

import me.pondhalf.plugin.DataManager;
import me.pondhalf.plugin.IO22;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

import static me.pondhalf.plugin.Utils.color;

public class CreativeCommand implements CommandExecutor {

    private IO22 plugin;

    private World creativeWorld;

    private DataManager backupLocations;

    public CreativeCommand(IO22 plugin) {
        this.plugin = plugin;
        this.creativeWorld = plugin.getCreativeWorld();
        this.backupLocations = plugin.getBackupLocations();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            plugin.getServer().getConsoleSender().sendMessage("This feature isn't allow console!");
            return true;
        }

        Player player = (Player) sender;
        Location location = player.getLocation();
        UUID uuid = player.getUniqueId();

        if (Bukkit.getWorld("creative") != null) {
            if (!(location.getWorld().equals(plugin.getCreativeWorld()))) {
                backupLocations.getConfig().set("Locations.players." + uuid + ".x", location.getX());
                backupLocations.getConfig().set("Locations.players." + uuid + ".y", location.getY());
                backupLocations.getConfig().set("Locations.players." + uuid + ".z", location.getZ());
                backupLocations.getConfig().set("Locations.players." + uuid + ".pitch", location.getPitch());
                backupLocations.getConfig().set("Locations.players." + uuid + ".yaw", location.getYaw());
                backupLocations.getConfig().set("Locations.players." + uuid + ".world", location.getWorld().getName());
                backupLocations.saveConfig();
                joinCreativeWorld(player);
                return true;
            } else {
                double xLoc = backupLocations.getConfig().getDouble("Locations.players." + uuid + ".x");
                double yLoc = backupLocations.getConfig().getDouble("Locations.players." + uuid + ".y");
                double zLoc = backupLocations.getConfig().getDouble("Locations.players." + uuid + ".z");
                float pitch = (float) backupLocations.getConfig().getDouble("Locations.players." + uuid + ".pitch");
                float yaw = (float) backupLocations.getConfig().getDouble("Locations.players." + uuid + ".yaw");
                World world = plugin.getServer().getWorld(backupLocations.getConfig().getString("Locations.players." + uuid + ".world"));

                Location loc = new Location(world, xLoc, yLoc, zLoc, yaw, pitch);

                quitCreativeWorld(player, loc);
                return true;
            }
        }

        return true;
    }

    private void joinCreativeWorld(Player player) {
        player.teleport(creativeWorld.getSpawnLocation());
        player.setGameMode(GameMode.CREATIVE);
        player.sendMessage("");
        plugin.getServer().broadcastMessage(color("&b&lIO22 &3" + player.getName() + " &fhas &ajoined &3CREATIVE &fworld!"));
        player.sendMessage("");
        player.playSound(player.getLocation(), Sound.ENTITY_ENDER_EYE_DEATH,1,1);
    }

    private void quitCreativeWorld(Player player, Location location) {
        player.setGameMode(plugin.getServer().getDefaultGameMode());
        player.teleport(location);
        player.sendMessage("");
        plugin.getServer().broadcastMessage(color("&b&lIO22 &3" + player.getName() + " &fhas &cleft &3CREATIVE &fworld!"));
        player.sendMessage("");
        player.playSound(player.getLocation(), Sound.ENTITY_ENDER_EYE_DEATH,1,1);
    }

}
