package me.pondhalf.plugin.components;

import me.pondhalf.plugin.IO22;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class EntityEngine {
    public static boolean check(Entity entity) {
        boolean b = false;
        switch (entity.getType()) {
            case DROPPED_ITEM:
            case ITEM_FRAME:
            case MINECART_COMMAND:
            case MINECART_TNT:
            case MINECART_MOB_SPAWNER:
            case MINECART_HOPPER:
            case MINECART_FURNACE:
            case MINECART_CHEST:
            case MINECART:
            case ARMOR_STAND:
            case PLAYER:
            case FIREWORK:
            case FIREBALL:
            case SMALL_FIREBALL:
            case DRAGON_FIREBALL:
            case SPLASH_POTION:
            case THROWN_EXP_BOTTLE:
            case EXPERIENCE_ORB:
            case EGG:
            case ENDER_PEARL:
            case ENDER_CRYSTAL:
            case ENDER_SIGNAL:
            case BOAT:
            case AREA_EFFECT_CLOUD:
            case SPECTRAL_ARROW:
            case ARROW:
            case FALLING_BLOCK:
            case SHULKER_BULLET:
            case FISHING_HOOK:
            case PRIMED_TNT:
            case LIGHTNING:
            case TRIDENT:
            case UNKNOWN:
                b = true;
                break;
        }
        return b;
    }

    public static boolean b(Entity entity) {
        boolean b = false;
        Damageable d = (Damageable) entity;
        if (d.getHealth() != d.getMaxHealth()) {
            b = true;
        }
        return b;
    }

    public static double RandomMove() {
        double a = 0.0D;
        int move = (new Random()).nextInt(999);
        String s = "0";
        if (move != 0)
            s = "0." + move;
        if ((new Random()).nextBoolean()) {
            a = Double.parseDouble(s);
        } else {
            a = Double.parseDouble("-" + s);
        }
        return a;
    }

    public static void killEntity(IO22 plugin, final ArmorStand stand) {
        int time = (new Random()).nextInt(40);
        (new BukkitRunnable() {
            public void run() {
                stand.remove();
            }
        }).runTaskLater((Plugin)plugin, time);
    }

    public static String healthboost(Entity a, double h) {
        LivingEntity b = (LivingEntity)a;
        int i = (int)h;
        String data = (h < 1.0D && h > 0.0D) ? (String.valueOf(h).split("\\.")[0] + "." + (String.valueOf((int)(h % 1.0D * 100.0D)).endsWith("0") ? (int)(h % 1.0D * 100.0D / 10.0D) : (int)(h % 1.0D * 100.0D))) : String.valueOf(i);
        String c = ChatColor.AQUA + "[" + data + "❤]";
        if (b.hasPotionEffect(PotionEffectType.HEALTH_BOOST)) {
            ChatColor color = ChatColor.GOLD;
            c = color + "[" + data + "❤]";
        } else if (b.hasPotionEffect(PotionEffectType.POISON)) {
            ChatColor color = ChatColor.DARK_GREEN;
            c = color + "[" + data + "❤]";
        } else if (b.hasPotionEffect(PotionEffectType.ABSORPTION)) {
            ChatColor color = ChatColor.GOLD;
            c = color + "[" + data + "❤]";
        } else if (b.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)) {
            ChatColor color = ChatColor.AQUA;
            c = color + "[" + data + "❤]";
        } else if (b.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)) {
            ChatColor color = ChatColor.GOLD;
            c = color + "[" + data + "❤]";
        }
        return c;
    }

    public static ArmorStand getIndicatorEntity(Location a) {
        ArmorStand b = (ArmorStand)a.getWorld().spawn(a, ArmorStand.class, c -> {
            c.setVisible(false);
            c.setBasePlate(false);
            c.setInvulnerable(true);
            c.setSmall(true);
            c.setGravity(false);
            c.setMarker(true);
            c.setSilent(true);
        });
        return b;
    }

}
