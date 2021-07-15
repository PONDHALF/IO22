package me.pondhalf.plugin.commands;

import me.pondhalf.plugin.IO22;
import me.pondhalf.plugin.components.TpaPlayer;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

import static me.pondhalf.plugin.Utils.color;
import static me.pondhalf.plugin.Utils.log;

public class TpaAcceptCommand implements CommandExecutor {

    private IO22 plugin;

    private TpaPlayer tpaPlayer;

    private HashMap tpa;

    public TpaAcceptCommand(IO22 plugin) {
        this.plugin = plugin;
        this.tpaPlayer = plugin.getTpaPlayer();
        this.tpa = tpaPlayer.getTpa();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            log("WARN: This feature isn't support on console!");
        }
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (tpa.containsKey(player)) {
                Player whoTpa = (Player) tpa.get(player);
                if (whoTpa != null && whoTpa.isOnline()) {
                    whoTpa.teleport(player.getLocation());
                    whoTpa.playSound(whoTpa.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT,1,1);
                    whoTpa.sendTitle(color("&a&lTeleport.."),"",1,5,1);
                    tpa.remove(player);
                } else {
                    player.sendMessage(color("&b&IO22 &fไม่พบผู้เล่นดังกล่าว!"));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1,1);
                    tpa.remove(player);
                }
            } else {
                player.sendMessage(color("&b&lIO22 &fยังไม่มีใคร tpa มาหาคุณ!"));
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1,1);
            }
        }

        return true;
    }
}
