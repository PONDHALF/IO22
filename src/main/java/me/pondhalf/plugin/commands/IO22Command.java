package me.pondhalf.plugin.commands;

import me.pondhalf.plugin.IO22;
import me.pondhalf.plugin.commands.subcommand.ReloadCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class IO22Command implements CommandExecutor {

    private ArrayList<SubCommand> subCommands = new ArrayList<>();

    private IO22 plugin;


    public IO22Command(IO22 plugin) {
        this.plugin = plugin;
        subCommands.add(new ReloadCommand(plugin));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length > 0) {
                for (int i = 0; i < getSubCommands().size(); i++) {
                    if (args[0].equalsIgnoreCase(getSubCommands().get(i).getCommandName())) {
                        getSubCommands().get(i).execute(player, args);
                    }
                }
            } else if (args.length == 0) {
                player.sendMessage("--------------------------------");
                for (int i = 0; i < getSubCommands().size(); i++){
                    player.sendMessage(getSubCommands().get(i).getSyntax() + " - " + getSubCommands().get(i).getDescription());
                }
                player.sendMessage("--------------------------------");

            }
        }

        return true;
    }

    public ArrayList<SubCommand> getSubCommands() {
        return subCommands;
    }

}
