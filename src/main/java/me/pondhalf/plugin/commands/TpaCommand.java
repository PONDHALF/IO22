package me.pondhalf.plugin.commands;

import me.pondhalf.plugin.IO22;
import me.pondhalf.plugin.components.TpaPlayer;
import me.pondhalf.plugin.items.TeleportPaper;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

import static me.pondhalf.plugin.utils.*;

public class TpaCommand implements CommandExecutor {

    private IO22 plugin;

    private TeleportPaper teleportPaper;
    private TpaPlayer tpaPlayer;

    private HashMap tpa;

    public TpaCommand(IO22 plugin) {
        this.plugin = plugin;
        this.teleportPaper = plugin.getTeleportPaper();
        this.tpaPlayer = plugin.getTpaPlayer();
        this.tpa = plugin.getTpaPlayer().getTpa();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            log("WARN: This feature isn't support on console!");
        }

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                player.sendMessage(color("&b&lCUPID &f/tpa <ชื่อผู่เล่น> - เพื่อวาร์ปไปหาผู้เล่น"));
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,1,1);
            } else {
                ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
                if (teleportPaper.getTeleportPaperItem().isSimilar(itemInMainHand)) {
                    Player selector = Bukkit.getServer().getPlayer(args[0]);
                    if (selector != null && selector.isOnline()) {
                        if (!(tpaPlayer.getTpa().containsKey(selector))) {
                            tpa.put(selector, player);
                            itemInMainHand.setAmount(itemInMainHand.getAmount() - 1);
                            TextComponent txt = new TextComponent();
                            txt.setText(color("&b&lCUPID &fผู้เล่น &3" + player.getName() + " &fต้องการวาร์ปมาหาคุณ &6&nคลิก"));
                            txt.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(color("&aตกลง")).create()));
                            txt.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept"));
                            selector.spigot().sendMessage(txt);
                            selector.playSound(selector.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                            player.sendMessage(color("&b&lCUPID &fส่งคำขอวาร์ปไปยัง &3" + selector.getName() + " &fแล้ว!"));
                            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);

                        } else {
                            player.sendMessage(color("&b&lCUPID &fส่งคำขอไปแล้ว!"));
                            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                        }
                    } else {
                        player.sendMessage(color("&b&lCUPID &fไม่พบผู้เล่นดังกล่าว!"));
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                    }

                }
            }

         }

        return true;
    }
}
