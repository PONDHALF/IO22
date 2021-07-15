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
import static me.pondhalf.plugin.Utils.log;

public class HomeCommand implements CommandExecutor {

    private IO22 plugin;
    private DataManager homesData;

    public HomeCommand(IO22 plugin) {
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

            if (player.getWorld().equals(plugin.getCreativeWorld())) {
                player.sendMessage("");
                player.sendMessage(color("&b&lIO22 &cError! &fYou can't sethome in this world!"));
                player.sendMessage("");
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                return true;
            }

            if (homesData.getConfig().get("Homes.players." + uuid) == null) {
                player.sendMessage("");
                player.sendMessage(color("&b&lIO22 &cError! &fYou gotta to &6sethome &ffirst!"));
                player.sendMessage("");
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1,1);

                return true;
            }

            double xLoc = homesData.getConfig().getDouble("Homes.players." + uuid + ".location.x");
            double yLoc = homesData.getConfig().getDouble("Homes.players." + uuid + ".location.y");
            double zLoc = homesData.getConfig().getDouble("Homes.players." + uuid + ".location.z");

            float pitch = (float) homesData.getConfig().getDouble("Homes.players." + uuid + ".location.pitch");
            float yaw = (float) homesData.getConfig().getDouble("Homes.players." + uuid + ".location.yaw");
            String worldConfig = homesData.getConfig().getString("Homes.players." + uuid + ".location.world");
            World world = plugin.getServer().getWorld(worldConfig);

            Location homeLoc = new Location(world, xLoc, yLoc, zLoc, yaw, pitch);

            player.teleport(homeLoc);

            player.sendMessage("");
            player.sendMessage(color("&b&lIO22 &aSuccessfully! &fteleported!"));
            player.sendMessage("");
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1,1);
            return true;
        }

        return true;
    }
}
