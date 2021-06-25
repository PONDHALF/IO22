package me.pondhalf.plugin.commands;

import org.bukkit.entity.Player;

public abstract class SubCommand {

    public abstract String getCommandName();

    public abstract String getDescription();

    public abstract String getSyntax();

    public abstract void execute(Player player, String[] args);

}
