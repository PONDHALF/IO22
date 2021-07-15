package me.pondhalf.plugin.listeners;

import me.pondhalf.plugin.IO22;
import me.pondhalf.plugin.components.CustomMobs;
import org.bukkit.Chunk;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

import java.util.HashMap;
import java.util.UUID;

public class CustomMobsListener implements Listener {

    private IO22 plugin;

    private HashMap<UUID, CustomMobs> CustomMobsData;

    public CustomMobsListener(IO22 plugin) {
        this.plugin = plugin;
        this.CustomMobsData = plugin.CustomMobsData;
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (event.getLocation().getBlock().isLiquid()) return;

        Entity entity = event.getEntity();

        double chance = Math.random();

        switch (event.getEntity().getType()) {
            case ZOMBIE:
               // if ((int) (Math.random() * 10) == 1) { // 0 - 9 10% chance
                if (chance <= 0.25D) {
                    event.setCancelled(true);
                    CustomMobs[] mobTypes = CustomMobs.values();
                    CustomMobs typeToSpawn = mobTypes[0];
                    CustomMobsData.put(typeToSpawn.spawn(event.getLocation()).getUniqueId(), typeToSpawn);
//                        plugin.getServer().broadcastMessage("x: " + event.getLocation().getBlockX() + " y: " +
//                        event.getLocation().getBlockY() + " z: " + event.getLocation().getBlockZ());
                }
                break;
            case SKELETON:
                if (chance <= 0.20D) {
                    event.setCancelled(true);
                    CustomMobs[] mobTypes = CustomMobs.values();
                    CustomMobs typeToSpawn = mobTypes[1];
                    CustomMobsData.put(typeToSpawn.spawn(event.getLocation()).getUniqueId(), typeToSpawn);
                }
                break;
            case CREEPER:
                if (chance <= 0.05D) {
                    event.setCancelled(true);
                    CustomMobs[] mobTypes = CustomMobs.values();
                    CustomMobs typeToSpawn = mobTypes[2];
                    CustomMobsData.put(typeToSpawn.spawn(event.getLocation()).getUniqueId(), typeToSpawn);
                }
                break;
            case WITHER_SKELETON:
                if (chance <= 0.05D) {
                    entity.remove();
                    CustomMobs[] mobTypes = CustomMobs.values();
                    CustomMobs typeToSpawn = mobTypes[3];
                    CustomMobsData.put(typeToSpawn.spawn(event.getLocation()).getUniqueId(), typeToSpawn);
                }
                break;
            default:
                break;
        }
        
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        if (!CustomMobsData.containsKey(entity.getUniqueId())) return;
        event.setDroppedExp(0);
        event.getDrops().clear();

        CustomMobsData.remove(entity.getUniqueId()).tryDropLoot(event.getEntity().getLocation());

    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent event) {
        Chunk chunk = event.getChunk();

        for (Entity entity : chunk.getEntities()) {

            if (CustomMobsData.containsKey(entity.getUniqueId())) {
                CustomMobsData.remove(entity.getUniqueId());
            }

        }
    }

    @EventHandler
    public void onDamageEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) return;

        Player victim = (Player) event.getEntity();

        if (victim.getHealth() <= event.getFinalDamage()) {
            if (CustomMobsData.containsKey(event.getDamager().getUniqueId())) {
                CustomMobsData.remove(event.getDamager().getUniqueId());
                event.getDamager().remove();
            }
        }

    }

}
