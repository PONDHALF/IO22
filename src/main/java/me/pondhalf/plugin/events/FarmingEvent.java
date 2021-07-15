package me.pondhalf.plugin.events;

import me.pondhalf.plugin.IO22;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import static me.pondhalf.plugin.Utils.color;

public class FarmingEvent {

    public static void onFarmBreak(BlockBreakEvent event, Player player, Block block) {
        ItemStack itemInMainHand = event.getPlayer().getInventory().getItemInMainHand();

        if (itemInMainHand.getType().name().endsWith("_HOE")) {
            Location location;
            if (isCrop(block)) {
                Ageable ageable = (Ageable) block.getBlockData();
                location = block.getLocation();
                if (ageable.getAge() == ageable.getMaximumAge()) {
                    event.setDropItems(false);
                    event.setCancelled(true);
                    afterFarmBreak(player, block, location);
                    ageable.setAge(0);
                    block.setBlockData(ageable);
                } else {
                    if (!player.isSneaking()) {
                        event.setCancelled(true);
                        player.sendMessage(color("&c# &7Shift + Left-Click to break this crop!"));
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, .5f, 1);
                    } else {
                        event.setCancelled(false);
                        return;
                    }
                }
            }
        } else {
            if (!isCrop(block)) return;
            Ageable ageable = (Ageable) block.getBlockData();
            if (!(ageable.getAge() == ageable.getMaximumAge())) {
                if (!player.isSneaking()) {
                    event.setCancelled(true);
                    player.sendMessage(color("&c# &7Shift + Left-Click to break this crop!"));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, .5f, 1);
                } else {
                    event.setCancelled(false);
                    return;
                }
            }
        }
    }

    private static void  afterFarmBreak(Player player, Block block, Location location) {
        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.GREEN, 1);
        for (ItemStack itemDrop : block.getDrops()) {
            location.getWorld().dropItemNaturally(location, itemDrop);
        }
        location.add(0.5,0.5,0.5);
        location.getWorld().spawnParticle(Particle.REDSTONE,location, 100,.5,.5,.5, dustOptions);

        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, .5f,1);

    }

    private static boolean isCrop(Block block) {
        switch (block.getType()) {
            case WHEAT:
            case POTATOES:
            case CARROTS:
            case BEETROOTS:
                return true;
            default:
                break;
        }
        return false;
    }

}
