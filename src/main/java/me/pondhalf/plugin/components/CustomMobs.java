package me.pondhalf.plugin.components;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

import static me.pondhalf.plugin.Utils.*;

public enum CustomMobs {

    COAL_ZOMBIE("&7Coal Zombie", false,5, 26, 15, EntityType.ZOMBIE
            , null
            , makeArmorSet(new ItemStack(Material.COAL_ORE), null, null, null),
            new LootItem(new ItemStack(Material.ROTTEN_FLESH), 1,2,100),
            new LootItem(new ItemStack(Material.COAL),1,3,65))
    ,
    IRON_SKELETON("&7Iron Skeleton", false, 5, 30, 15, EntityType.SKELETON
            , new ItemStack(Material.BOW, 1)
            , makeArmorSet(new ItemStack(Material.IRON_ORE), new ItemStack(Material.IRON_CHESTPLATE), null, null)
            , new LootItem(new ItemStack(Material.BONE),1,2,100)
            , new LootItem(new ItemStack(Material.RAW_IRON),1,5,67))
    ,
    DIAMOND_CREEPER("&bDiamond Creeper", false, 5, 32,15,EntityType.CREEPER
            , null
            , null
            , new LootItem(new ItemStack(Material.GUNPOWDER),1,2,100)
            , new LootItem(new ItemStack(Material.DIAMOND),10)),
    NINJA_WITHER("&8Ninja Wither", false, 12.5, 60, 1, EntityType.WITHER_SKELETON
            , createItemModelData(new ItemStack(Material.NETHERITE_SWORD, 1),1)
            ,makeArmorSet(new ItemStack(Material.NETHERITE_HELMET), new ItemStack(Material.NETHERITE_CHESTPLATE), null, null)
            , new LootItem(new ItemStack(Material.BONE),1,2,100)
            , new LootItem(new ItemStack(Material.COAL),1,2,100)
            , new LootItem(new ItemStack(Material.ANCIENT_DEBRIS),1,2,75))
    ;


    private String name;
    private boolean isBaby;
    private double damage, maxHealth, spawnChance;
    private EntityType type;
    private ItemStack mainItem;
    private ItemStack[] armor;
    private List<LootItem> lootTable;

    CustomMobs(String name, boolean isBaby, double damage, double maxHealth, double spawnChance, EntityType type, ItemStack mainItem, ItemStack[] armor, LootItem... lootItems) {
        this.name =color(name);
        this.damage = damage;
        this.isBaby =isBaby;
        this.maxHealth =maxHealth;
        this.spawnChance =spawnChance;
        this.type =type;
        this.mainItem =mainItem;
        this.armor =armor;
        lootTable = Arrays.asList(lootItems);
    }

    public LivingEntity spawn(Location location) {

        LivingEntity entity = (LivingEntity) location.getWorld().spawnEntity(location, type);
        entity.setCustomNameVisible(true);
        entity.setCustomName(name);
        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
        entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(damage);
        entity.setHealth(maxHealth);
        entity.setCanPickupItems(false);

        if (entity instanceof Ageable) {
            if (isBaby) {
                ((Ageable) entity).setBaby();
            } else {
                ((Ageable) entity).setAdult();
            }
        }

        EntityEquipment inv = entity.getEquipment();

        if (armor != null) inv.setArmorContents(armor);

        inv.setHelmetDropChance(0f);
        inv.setChestplateDropChance(0f);
        inv.setLeggingsDropChance(0f);
        inv.setBootsDropChance(0f);
        inv.setItemInMainHand(mainItem);
        inv.setItemInMainHandDropChance(0f);
        return entity;
    }

    public void tryDropLoot(Location location) {
        for (LootItem item : this.lootTable) {
            item.tryDropItem(location);
        }
    }

    public String getName() {
        return name;
    }

    public String getDeColorName() {
        return decolor(name);
    }

    public static CustomMobs getMobFromName(String mob_name) {
        for (CustomMobs customMobs : values()) {
            if (decolor(customMobs.getName()).equalsIgnoreCase(decolor(mob_name))) {
                return customMobs;
            }
        }
        return null;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public double getSpawnChance() {
        return spawnChance;
    }

}
