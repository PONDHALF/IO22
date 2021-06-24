package me.pondhalf.plugin.components;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class TpaPlayer {

    // Selector, Self
    private HashMap<Player, Player> tpa = new HashMap<>();

    public HashMap<Player, Player> getTpa() {
        return tpa;
    }
}
