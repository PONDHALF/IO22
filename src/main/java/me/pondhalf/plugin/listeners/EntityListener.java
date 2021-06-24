package me.pondhalf.plugin.listeners;

import me.pondhalf.plugin.IO22;
import me.pondhalf.plugin.components.EntityEngine;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityListener implements Listener {

    private final IO22 plugin;

    public EntityListener(IO22 plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();

        if (!(EntityEngine.check(entity))) {
            Location loc = entity.getLocation().add(EntityEngine.RandomMove(), entity.getHeight() + 0.2D, EntityEngine.RandomMove());
            ArmorStand stand = EntityEngine.getIndicatorEntity(loc);
            this.plugin.indicatorList.add(stand);
            try {
                Damageable damageable = (Damageable) entity;
                double kill = event.getFinalDamage();
                double health = damageable.getHealth() - kill;
                if (health < 0.0D) {
                    double damage = health + kill;
                    health = 0.0D;
                    int i = (int) damage;
                    String data = (damage < 1.0D && damage > 0.0D) ? (String.valueOf(damage).split("\\.")[0] + "." + (String.valueOf((int) (damage % 1.0D * 100.0D)).endsWith("0") ? (int) (damage % 1.0D * 100.0D / 10.0D) : (int) (damage % 1.0D * 100.0D))) : String.valueOf(i);
                    stand.setCustomName(ChatColor.RED + "-" + data + "❤ " + EntityEngine.healthboost(entity, health));
                } else {
                    double damage = event.getDamage();
                    int i = (int) damage;
                    String data = (damage < 1.0D && damage > 0.0D) ? (String.valueOf(damage).split("\\.")[0] + "." + (String.valueOf((int) (damage % 1.0D * 100.0D)).endsWith("0") ? (int) (damage % 1.0D * 100.0D / 10.0D) : (int) (damage % 1.0D * 100.0D))) : String.valueOf(i);
                    stand.setCustomName(ChatColor.RED + "-" + data + "❤ " + EntityEngine.healthboost(entity, health));
                }
                stand.setCustomNameVisible(true);
            } catch (Exception e) {
                stand.remove();
                return;
            }
            EntityEngine.killEntity(this.plugin, stand);
        }
    }
}
