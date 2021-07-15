package me.pondhalf.plugin.listeners;

import me.pondhalf.plugin.IO22;
import me.pondhalf.plugin.events.FarmingEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class FarmingRegistry implements Listener {

    private IO22 plugin;

    public FarmingRegistry(IO22 plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        FarmingEvent.onFarmBreak(event, event.getPlayer(), event.getBlock());
    }

}
