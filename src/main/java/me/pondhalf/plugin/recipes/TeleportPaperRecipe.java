package me.pondhalf.plugin.recipes;

import me.pondhalf.plugin.IO22;
import me.pondhalf.plugin.items.TeleportPaper;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;

public class TeleportPaperRecipe {

    private IO22 plugin;
    private TeleportPaper teleportPaper;

    public TeleportPaperRecipe(IO22 plugin) {
        this.plugin = plugin;
        this.teleportPaper = plugin.getTeleportPaper();
    }

    public ShapedRecipe getRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, "paper");

        ShapedRecipe recipe = new ShapedRecipe(key, teleportPaper.getTeleportPaperItem());

        recipe.shape("PPP", "PDP", "PEP");

        recipe.setIngredient('P', Material.PAPER);
        recipe.setIngredient('D', Material.DIAMOND);
        recipe.setIngredient('E', Material.ENDER_PEARL);

        return recipe;
    }

}
