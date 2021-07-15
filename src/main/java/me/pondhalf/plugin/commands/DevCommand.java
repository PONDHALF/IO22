package me.pondhalf.plugin.commands;

import me.pondhalf.plugin.IO22;
import me.pondhalf.plugin.components.CustomMobs;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static me.pondhalf.plugin.Utils.decolor;

public class DevCommand implements CommandExecutor {

    private IO22 plugin;

    private HashMap<UUID, CustomMobs> mobsData;

    public DevCommand(IO22 plugin) {
        this.plugin = plugin;
        this.mobsData = plugin.CustomMobsData;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            plugin.getServer().getConsoleSender().sendMessage("This feature isn't allow console to use this!");
            return true;
        }

        for (UUID uuid : mobsData.keySet()) {
            System.out.println(uuid);
        }

        return true;
    }
}
