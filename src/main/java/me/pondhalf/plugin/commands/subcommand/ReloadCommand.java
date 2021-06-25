package me.pondhalf.plugin.commands.subcommand;

import me.pondhalf.plugin.IO22;
import me.pondhalf.plugin.commands.SubCommand;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import static me.pondhalf.plugin.utils.color;

public class ReloadCommand extends SubCommand {

    private IO22 plugin;

    public ReloadCommand(IO22 plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getCommandName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "reload config";
    }

    @Override
    public String getSyntax() {
        return "/io22 reload";
    }

    @Override
    public void execute(Player player, String[] args) {

        if (player.hasPermission("io22.command.reload")) {

            plugin.reloadConfig();

            IO22.setMaxStackSize(plugin.getConfig().getInt("Items.max_stack_size"));

            player.sendMessage(color(""));
            player.sendMessage(color("&b&lIO22 &aSuccessfully reloaded!"));
            player.sendMessage(color(""));
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1,1);

        }

    }


}
