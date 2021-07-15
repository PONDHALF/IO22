package me.pondhalf.plugin;

import me.pondhalf.plugin.commands.*;
import me.pondhalf.plugin.components.CustomMobs;
import me.pondhalf.plugin.components.TpaPlayer;
import me.pondhalf.plugin.gui.TeleportGUI;
import me.pondhalf.plugin.items.TeleportPaper;
import me.pondhalf.plugin.listeners.*;
import me.pondhalf.plugin.recipes.TeleportPaperRecipe;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.logging.Logger;

import static me.pondhalf.plugin.Utils.decolor;
import static me.pondhalf.plugin.Utils.log;

public final class IO22 extends JavaPlugin {

    private static Logger logger;

    private TeleportPaper teleportPaper;
    private TeleportPaperRecipe teleportPaperRecipe;

    private TeleportGUI teleportGUI;

    private TpaPlayer tpaPlayer;

    public ArrayList<Entity> indicatorList = new ArrayList<>();
    public HashMap<UUID, Integer> data = new HashMap<>();
    public HashMap<UUID, Location> DeathLocationData = new HashMap<>();

    public HashMap<UUID, CustomMobs> CustomMobsData = new HashMap<>();

    private ItemListener itemListener;

    private static int MAX_STACK_SIZE;

    private DataManager homesData;
    private DataManager backupLocations;

    private World creativeWorld;

    @Override
    public void onEnable() {
        // Plugin startup logic
        logger = getServer().getLogger();
        this.saveDefaultConfig();

        log("Creating creative's world..");
        createCreativeWorld();

        homesData = new DataManager(this, "homes.yml");
        backupLocations = new DataManager(this, "backuplocations.yml");

        MAX_STACK_SIZE = this.getConfig().getInt("Items.max_stack_size");

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
        this.getCommand("dev").setExecutor(new DevCommand(this));

        this.getCommand("home").setExecutor(new HomeCommand(this));
        this.getCommand("sethome").setExecutor(new SetHomeCommand(this));
        this.getCommand("back").setExecutor(new BackCommand(this));
        this.getCommand("creative").setExecutor(new CreativeCommand(this));

        addDataItems();
        addDataCustomMobs();

        log("Enabled!");
    }

    void addDataCustomMobs() {
        List<World> worlds = this.getServer().getWorlds();

        for (World world : worlds) {

            for (LivingEntity entity : world.getLivingEntities()) {

                if (entity instanceof Zombie || entity instanceof Creeper
                        || entity instanceof Skeleton) {

                    if (entity != null) {

                        if (entity.getCustomName() != null) {

                            String customName = decolor(entity.getCustomName());

                            for (CustomMobs customMobs : CustomMobs.values()) {

                                if (customName.equalsIgnoreCase(customMobs.getDeColorName())) {

                                    CustomMobsData.put(entity.getUniqueId(), customMobs);

                                }
                            }
                        }
                    }
                }
            }
        }
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
        pluginManager.registerEvents(new CustomMobsListener(this), this);
        pluginManager.registerEvents(new FarmingRegistry(this), this);
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
        String values_string = args[1].replace("x","");
        int values;
        if (Integer.valueOf(values_string) == null) {
            values_string = args[0].replace("x", "");
            values = Integer.valueOf(values_string);

        } else {
            values = Integer.valueOf(values_string);
        }

        return values;
    }

    public DataManager getHomesData() {
        return homesData;
    }

    public DataManager getBackupLocations() {
        return backupLocations;
    }

    public void createCreativeWorld() {
        WorldCreator worldCreator = new WorldCreator("creative");
        worldCreator.type(WorldType.FLAT);
        worldCreator.generateStructures(true);
        creativeWorld = worldCreator.createWorld();
    }

    public World getCreativeWorld() {
        return creativeWorld;
    }

}
