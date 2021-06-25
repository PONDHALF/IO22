package me.pondhalf.plugin;

import me.pondhalf.plugin.commands.IO22Command;
import me.pondhalf.plugin.commands.TpaAcceptCommand;
import me.pondhalf.plugin.commands.TpaCommand;
import me.pondhalf.plugin.components.TpaPlayer;
import me.pondhalf.plugin.gui.TeleportGUI;
import me.pondhalf.plugin.items.TeleportPaper;
import me.pondhalf.plugin.listeners.*;
import me.pondhalf.plugin.recipes.TeleportPaperRecipe;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import static me.pondhalf.plugin.utils.decolor;
import static me.pondhalf.plugin.utils.log;

public final class IO22 extends JavaPlugin {

    private static Logger logger;

    private TeleportPaper teleportPaper;
    private TeleportPaperRecipe teleportPaperRecipe;

    private TeleportGUI teleportGUI;

    private TpaPlayer tpaPlayer;

    public ArrayList<Entity> indicatorList = new ArrayList<>();
    public HashMap<UUID, Integer> data = new HashMap<>();

    private ItemListener itemListener;

    private static int MAX_STACK_SIZE;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.saveDefaultConfig();

        MAX_STACK_SIZE = this.getConfig().getInt("Items.max_stack_size");

        logger = getServer().getLogger();
        teleportPaper = new TeleportPaper(this);
        teleportPaperRecipe = new TeleportPaperRecipe(this);

        teleportGUI = new TeleportGUI(this);
        tpaPlayer = new TpaPlayer();

        PluginManager pluginManager = getServer().getPluginManager();

        ListenersRegistry(pluginManager);
        RecipesRegistry();

        itemListener = new ItemListener(this);

        this.getCommand("tpa").setExecutor(new TpaCommand(this));
        this.getCommand("tpaccept").setExecutor(new TpaAcceptCommand(this));
        this.getCommand("io22").setExecutor(new IO22Command(this));

        addDataItems();

        log("Enabled!");
    }

    void addDataItems() {

        List<World> worlds = this.getServer().getWorlds();

        for (World world : worlds) {

            for (Entity entity : world.getEntities()) {

                if (entity instanceof Item) {

                    Item item_ent = (Item) entity;
                    if (entity != null || item_ent != null) {

                        if (item_ent.getCustomName() != null) {

                            UUID uuid = item_ent.getUniqueId();
                            String displayName = entity.getCustomName();

                            int amount = getAmountFromName(displayName);

                            data.put(uuid, amount);

                        }

                    }

                }

            }

        }

    }

    void ListenersRegistry(PluginManager pluginManager) {
//        pluginManager.registerEvents(new ItemClickListener(this), this);
//        pluginManager.registerEvents(new GUIClickListener(this), this);
        pluginManager.registerEvents(new EntityListener(this), this);
        pluginManager.registerEvents(new PlayerListeners(this), this);
        pluginManager.registerEvents(new ItemListener(this), this);
    }

    void RecipesRegistry() {
        Bukkit.addRecipe(teleportPaperRecipe.getRecipe());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        for (Entity entity : this.indicatorList) {
            entity.remove();
        }

        log("Disabled!");
    }

    public static Logger getPluginLogger() {
        return logger;
    }

    public static int getMaxStackSize() {
        return MAX_STACK_SIZE;
    }

    public static void setMaxStackSize(int maxStackSize) {
        MAX_STACK_SIZE = maxStackSize;
    }

    public TeleportPaper getTeleportPaper() {
        return teleportPaper;
    }

    public TeleportGUI getTeleportGUI() {
        return teleportGUI;
    }

    public TpaPlayer getTpaPlayer() {
        return tpaPlayer;
    }

    public int getAmountFromName(String displayName) {
        String decolorDisplayName = decolor(displayName);
        String[] args = decolorDisplayName.split(" ");
        String values_string = args[0].replace("x","");
        int values = Integer.valueOf(values_string);
        return values;
    }

}
