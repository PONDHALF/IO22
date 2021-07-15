package me.pondhalf.plugin.commands;

import me.pondhalf.plugin.DataManager;
import me.pondhalf.plugin.IO22;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

import static me.pondhalf.plugin.Utils.color;

public class SetHomeCommand implements CommandExecutor {

    private IO22 plugin;

    private DataManager homesData;

    public SetHomeCommand(IO22 plugin) {
        this.plugin = plugin;
        this.homesData = plugin.getHomesData();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            plugin.getServer().getConsoleSender().sendMessage("This feature isn't allow console!");
            return true;
        }

        if (sender instanceof Player) {
            Player player = (Player) sender;
            UUID uuid = player.getUniqueId();

            Location location = player.getLocation();

            if (location.getWorld().equals(plugin.getCreativeWorld())) {
                player.sendMessage("");
                player.sendMessage(color("&b&lIO22 &cError! &fYou can't sethome in this world!"));
                player.sendMessage("");
                player.playSound(location, Sound.ENTITY_VILLAGER_NO, 1, 1);
                return true;
            }

            homesData.getConfig().set("Homes.players." + uuid + ".location.x", location.getX());
            homesData.getConfig().set("Homes.players." + uuid + ".location.y", location.getY());
            homesData.getConfig().set("Homes.players." + uuid + ".location.z", location.getZ());
            homesData.getConfig().set("Homes.players." + uuid + ".location.pitch", location.getPitch());
            homesData.getConfig().set("Homes.players." + uuid + ".location.yaw", location.getYaw());
            homesData.getConfig().set("Homes.players." + uuid + ".location.world", location.getWorld().getName());

            homesData.saveConfig();

            player.sendMessage("");
            player.sendMessage(color("&b&lIO22 &aSuccessfully! &fSet home!"));
            player.sendMessage("");
            player.playSound(location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);

        }

        return true;
    }
}
