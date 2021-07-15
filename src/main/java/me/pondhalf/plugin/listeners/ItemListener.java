package me.pondhalf.plugin.listeners;

import me.pondhalf.plugin.IO22;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static me.pondhalf.plugin.Utils.color;
import static me.pondhalf.plugin.Utils.decolor;

public class ItemListener implements Listener {
// *******##### So Hard!!! Started 22/6/2564 Time: 20:24 #####*******
    private IO22 plugin;

    public HashMap<UUID, Integer> data;

    public ItemListener(IO22 plugin) {
        this.plugin = plugin;
        this.data = plugin.data;
    }


    @EventHandler
    public void onItemMerge(ItemMergeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onItemDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Item) {
            Item item = (Item) event.getEntity();
            data.remove(item.getUniqueId());
            item.remove();
        }
    }

    @EventHandler
    public void onItemDespawn(ItemDespawnEvent event) {
        Item item = event.getEntity();
        UUID uuid = item.getUniqueId();
        if (data.containsKey(uuid)) {
            data.remove(uuid);
        }
    }


    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onEntityPickup(EntityPickupItemEvent event) {
        Item item = event.getItem(); // get event item (item pickup)
        ItemStack itemStack = item.getItemStack(); // get ItemStack from event item

        // get amount of item from data item
        int amount = data.get(item.getUniqueId());

        // if entity picked up is player
        if (event.getEntity() instanceof Player) {

            // if (amount < (512 / 2)) return;
            event.setCancelled(true); // Cancelled the event
            Player player = (Player) event.getEntity(); // Get player;

            // Update item in inventory
            updateInventory(item, player.getInventory(), player);

        } else { // If entity picked up isn't player

            updateItemMeta(item, itemStack, amount - 1);

        }

        // ###############
//        Item item = event.getItem();
//
//        if (event.getEntity() instanceof Player) {
//
//            event.setCancelled(true);
//            Player player = (Player) event.getEntity();
//
//            if (data.containsKey(item.getUniqueId())) {
//
//                ItemStack itemStack = item.getItemStack();
//
//                int amount = data.get(item.getUniqueId());
//
//                int subtract = Math.min(amount, 512);
//
//                while (amount > 0) {
//
//                    itemStack.setAmount(1);
//
//                    if (amount <= 0) {
//
//                        data.remove(item.getUniqueId());
//                        item.remove();
//
//                    } else {
//
//                        amount -= subtract;
//
//                        HashMap<Integer, ItemStack> result = player.getInventory().addItem(itemStack);
//
//                        if (result.get(0) != null) {
//
//                            amount += result.get(0).getAmount();
//                            break;
//
//                        }
//
//                        updateItemAmount(item, data.get(item.getUniqueId()));
//                    }
//
//                }
//
//                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, .2f, (float) (1 + (Math.random())));
//
//            }
//
//        }
//        ######################################

//        event.setCancelled(true);
//
//        if (event.getEntity() instanceof Player) {
//
//            Player player = (Player) event.getEntity();
//
//            Item item = event.getItem();
//
//            ItemStack getitem = item.getItemStack();
//            getitem.setAmount(1);
//
//            if (data.containsKey(item.getUniqueId())) {
//
//                int size = data.get(item.getUniqueId());
//
//                for (int i = size; i >= 0; i--) {
//
//                    if (data.get(item.getUniqueId()) <= 0 || i <= 0) {
//
//                        data.remove(item.getUniqueId());
//                        item.remove();
//                        break;
//                    } else {
//
//                        updateItemAmount(item, size-1);
//                        player.getInventory().addItem(getitem);
//
//                    }
//
//                }
//
//                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1f, 0.8f);
//
//            } else {
//
//                event.setCancelled(false);
//
//            }
//        }
    }

    // On using Dropper
    @EventHandler
    public void onInventoryPickup(InventoryPickupItemEvent event) {
        event.setCancelled(true); // Cancel when pick items

        // Update amount of item
        updateInventory(event.getItem(), event.getInventory());
    }

    // ##### NOT WORK (for this event) ####
    /*@EventHandler
    public void onItemPickup(PlayerPickupItemEvent event) {
        event.setCancelled(true); // Cancel when pick items
        Item item = event.getItem(); // Get item pick up
        Player player = event.getPlayer(); // Get who pick up
        ItemStack getitem = item.getItemStack();
        getitem.setAmount(1);
        // ############# Old Idea (Bug: Items isn't split) ##############
        /*if (data.containsKey(item.getUniqueId())) { // if item's uuid is in data
            ItemStack getitem = item.getItemStack(); // Get ItemStack class from item pickup
            getitem.setAmount(data.get(item.getUniqueId())); // Set amount item pickup (amount from data)
            player.getInventory().addItem(getitem); // Get addItemStack to inventory of player
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP,1,1); // Add some a bit gimmick (Sound effect on pickup)_
            item.remove(); // Remove item pickup on ground (cuz: this event will cancel when pick up)
        }*/

        // ######## FIXED Items isn't split #########
        // ######## BUG created InventoryFull Bug
        /* if (data.containsKey(item.getUniqueId())) {
            int size = data.get(item.getUniqueId());
            for (int i = size; i >= 0; i--) {
                if (hasAvaliableSlot(player) == false) {
                    event.setCancelled(true);
                    break;
                }

                if (i == 0) {

                    data.remove(item.getUniqueId());
                    item.remove();

                } else {

                    player.getInventory().addItem(getitem);

                    data.put(item.getUniqueId(), i);

                }

            }
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP,1f,0.8f);
        } else {
            event.setCancelled(false);
        } */


    @EventHandler
    public void onItemSpawn(ItemSpawnEvent event) {
        // Get (Entity) Item
        Item item = event.getEntity();
        // Get NearbyEntities from ItemSpawn
        List<Entity> ents = item.getNearbyEntities(5,5,5);

        // If Item isn't have Data
        if (!data.containsKey(item.getUniqueId())) {
            data.put(item.getUniqueId(), item.getItemStack().getAmount());
        }

        // Get Size of item from Data
        int size = data.get(item.getUniqueId());

        // -------- Debug Section --------
        /*Bukkit.broadcastMessage(" ");
        Bukkit.broadcastMessage("UUID: " + item.getUniqueId());
        Bukkit.broadcastMessage("Amount: " + data.get(item.getUniqueId()));
        Bukkit.broadcastMessage("Size: " + size);
        Bukkit.broadcastMessage(" "); */
        // -------- End Debug Section --------

        // Loop get Entity from NearbyEntities
        for (Entity ent : ents) {
            // If loop entity item type equals item spawn type
            if ((ent instanceof Item) && ((Item) ent).getItemStack().getType() == event.getEntity().getItemStack().getType()) {
                // Item entity = (Item) ent // Get item from entity
                Item item_entity = (Item) ent;

                // If Item entity isn't Dead (Item is Valid)
                if (!item_entity.isDead() && item_entity.isValid()) {
                    ItemStack itemStack = item.getItemStack();
                    ItemStack itemStack_ent = item_entity.getItemStack();

                    // Check ItemMetaData
                    if (itemStack.isSimilar(itemStack_ent)
                            || itemStack.getItemMeta().getItemFlags() == itemStack_ent.getItemMeta().getItemFlags()) {

                        // Fixed Return null
                        if (data.get(item_entity.getUniqueId()) == null) return;

                        // Get item_entity size
                        int ItemEntitySize = data.get(item_entity.getUniqueId());
                        // If item_entity <= 512
                        if ((size + ItemEntitySize) <= IO22.getMaxStackSize()) {
                            // Math Calculate Size
                            size += ItemEntitySize;
                            // Remove Data item_entity
                            data.remove(item_entity.getUniqueId());
                            // Remove item_entity
                            item_entity.remove();
                        }

                    }

                }

            }
        }


        // Break vanilla Limit Item stack (vanilla item drop stack max: 127)
        if (size < 32) {
            item.getItemStack().setAmount(size);
        } else {
            item.getItemStack().setAmount(32);
        }
        // #######################################################
        // Update Size item
        updateItemAmount(item, size);

        // Debug :: Bukkit.broadcastMessage("UUID: " + item.getUniqueId());
    }

    // Get Item entity DisplayName
    public String getItemName(Item entity) {
        // Get ItemStack from entity(item)
        ItemStack itemStack = entity.getItemStack();
        // Get ItemMeta from ItemStack(entity(item))
        ItemMeta itemMeta = itemStack.getItemMeta();

        // If Item has DisplayName (mean name of item isn't normal) (ex: "Grass Block" -> anvil -> "Grass Block xD")
        if (itemMeta.hasDisplayName()) {
            String ItemName = itemMeta.getDisplayName();

            return ItemName;

        } else { // If name of item is normal (ex: "Grass Block" -> no change anything)
            String ItemName = decolor(entity.getName());

            return ItemName;
        }

    }

    // Set Item(entity) glow
    public boolean ItemEnchantGlow(Item item) {
        if (item.getItemStack().getType() == Material.ENCHANTED_BOOK
                || item.getItemStack().getItemMeta().hasEnchants()) {
            // if item is enchant && enchant book
            return true;
        } else {
            // if item isn't
            return false;
        }

    }

    // Check Player is full inventory : ?
//    public boolean hasAvaliableSlot(Player player){
//        Inventory inv = player.getInventory();
//        for (ItemStack item : inv.getContents()) {
//            if (item == null) {
//                return true;
//            }
//        }
//        return false;
//    }

    // Reference from UltimateStacker v2.1.7
    // This isn't works for me :{
//    public void updateInventory(Item item, Inventory inventory) {
//        // Get amount from data items
//        int amount = data.get(item.getUniqueId());
//        // Get itemStack
//        ItemStack itemStack = item.getItemStack();
//
//        // while amount greater than 0
//        while (amount > 0) {
//
//            // ### Example int amount = 128; :: Mean amount of items on ground = 128  ###
//
//            int subtract = Math.min(amount, 512);
//            // now int subtract = 128;
//            amount -= subtract; // amount = 128(amount) - 128(subtract)
//
//            // now int amount = 0;
//
//            ItemStack newItem = itemStack.clone(); // Get clone item from (clone from pick up item);
//            newItem.setAmount(subtract); // Set amount of item clone to .... (now int subtract = 128;)
//
//            // Store amount of items(from newItem) pick up
//            Map<Integer, ItemStack> result = inventory.addItem(newItem);
//
//            // Example:
//            //
//            // Now amount of newItem is equals 128
//            // If player pick up (newItem) amount 64 items
//            // So variable result equals Map.put(64, newItem) and (give player items) inventory.addItem(newItem) give items to player at the same time;
//            //
//
//            // if player can't pick up (mean amount of items pickup equals 0)
//            if (result.get(0) != null) {
//                // Now int amount = 0; right?
//                amount += result.get(0).getAmount(); // mean get amount of items can't pick up cuz inventory full or something
//                // So when update, Now int amount = amount of items can't pick up;
//                // In example: now int amount = 128(amount of items on ground) - 64(amount of items player pickup)
//                break;
//            }
//        }
//
//        if (amount <= 0) {
//            // Remove items from data;
//            data.remove(item.getUniqueId());
//            // Kill entity (item)
//            item.remove();
//        } else {
//            // Update amount of items onGround
//            updateItemAmount(item, amount);
//
//        }
//
//    }

    // FIXED Over stacking picked up items
    // ( Fixed by ThatOneRR, He is a genius =} )
    // check it out: https://www.youtube.com/channel/UC51zMndc4X06yuXGWQk6GPA
    public void updateInventory(Item item, Inventory inventory) {
        int amount = 0;

        // Fixed NullPointException
        if (data.get(item.getUniqueId()) == null) return;

        amount = data.get(item.getUniqueId());

        ItemStack itemStack = item.getItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();

        ItemStack oneItem = new ItemStack(itemStack.getType(), 1);
        oneItem.setItemMeta(itemMeta);
        // Putting them outside the while loop for re-usability, so I don't create a new variable every loop
        Map<Integer, ItemStack> leftovers;

        while (amount > 0) {
            // Add item (1 each loop). Let Minecraft handle the item stacking =D
            leftovers = inventory.addItem(oneItem);
            // If there are leftovers, it means the inventory is full! Stop adding items :O
            if (!leftovers.isEmpty()) break;
            // If not, it means 1 item is added successfully! Let's count the amount down and let the loop run again
            amount --;
        }

        if (amount <= 0) {
            // Remove items from data;
            data.remove(item.getUniqueId());
            // Kill entity (item)
            item.remove();
        } else {
            // Update amount of items onGround
            updateItemAmount(item, amount);
        }

    }

    public void updateInventory(Item item, Inventory inventory, Player player) {
        int amount = 0;

        // Fixed NullPointException
        if (data.get(item.getUniqueId()) == null) return;

        amount = data.get(item.getUniqueId());

        final int amt = amount;

        ItemStack itemStack = item.getItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();

        ItemStack oneItem = new ItemStack(itemStack.getType(), 1);
        oneItem.setItemMeta(itemMeta);
        // Putting them outside the while loop for re-usability, so I don't create a new variable every loop
        Map<Integer, ItemStack> leftovers;

        while (amount > 0) {
            // Add item (1 each loop). Let Minecraft handle the item stacking =D
            leftovers = inventory.addItem(oneItem);

            // If there are leftovers, it means the inventory is full! Stop adding items :O
            if (!leftovers.isEmpty()) break;
            // If not, it means 1 item is added successfully! Let's count the amount down and let the loop run again
            amount --;

            if (amount == amt) return;
            // Add some gimmick effect when pick up items
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, .2f, (float) (1 + Math.random()));
        }

        if (amount <= 0) {
            // Remove items from data;
            data.remove(item.getUniqueId());
            // Kill entity (item)
            item.remove();
        } else {
            // Update amount of items onGround
            updateItemAmount(item, amount);
        }

    }

    // Update amount of items(ex: CustomName, Item data)
    public void updateItemAmount(Item item, int amount) {
        // Put Item to Data;
        data.put(item.getUniqueId(), amount);
        // Get amount from Item Data;
        int size = data.get(item.getUniqueId());
        // Set CustomName entity
        updateItemMeta(item, item.getItemStack(), size);
    }

    public void updateItemMeta(Item item, ItemStack itemStack, int amount) {

        item.setCustomName(null); // Reset CustomName of entity(item)

        String name;


        if (amount > 64) {
            name = ChatColor.YELLOW + "▶" + " " + ChatColor.of(new Color(245, 66, 65)) + String.valueOf(amount) + "x" + " " + color("&7" + getItemName(item));
        } else {
            name = ChatColor.YELLOW + "▶" + " " + ChatColor.of(new Color(235, 52, 88)) + String.valueOf(amount) + "x" + " " + color("&7" + getItemName(item));
        }


//        ItemMeta itemMeta = itemStack.getItemMeta();
//        itemStack.setItemMeta(itemMeta);
//        item.setItemStack(itemStack); // no clue

        item.setCustomName(name); // Set CustomName

        item.setCustomNameVisible(true); // Show CustomName

        // Set Glow if item is enchant
        item.setGlowing(ItemEnchantGlow(item));
    }

}
