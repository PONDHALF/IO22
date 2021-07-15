package me.pondhalf.plugin.commands;

import me.pondhalf.plugin.IO22;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

import static me.pondhalf.plugin.Utils.color;

public class BackCommand implements CommandExecutor {

    private IO22 plugin;

    private HashMap<UUID, Location> DeathLocationData;

    public BackCommand(IO22 plugin) {
        this.plugin = plugin;
        this.DeathLocationData = plugin.DeathLocationData;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            plugin.getServer().getConsoleSender().sendMessage("This feature isn't allow console!");
            return true;
        }

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (DeathLocationData.get(player.getUniqueId()) == null) {
                player.sendMessage("");
                player.sendMessage(color("&b&lIO22 &fYou don't have data death waypoint!"));
                player.sendMessage("");
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1,1);
                return true;
            }

            if (DeathLocationData.get(player.getUniqueId()) != null) {
                player.teleport(DeathLocationData.get(player.getUniqueId()));
                player.sendMessage("");
                player.sendMessage(color("&b&lIO22 &aSuccessfully! &fteleported to death waypoint!"));
                player.sendMessage("");
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1,1);
            }
        }

        return true;
    }


}
