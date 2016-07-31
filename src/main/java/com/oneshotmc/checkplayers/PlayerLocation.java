package com.oneshotmc.checkplayers;

import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Created by UltraX3 on 7/29/2016.
 */
public class PlayerLocation {
    private Location checkLocation;
    private Player player;

    public PlayerLocation(Player player) {
        this.player = player;
    }

    public boolean isOnline(){
        return player.isOnline();
    }

    public Location getLocation() {
        return checkLocation;
    }

    public Player getPlayer() {
        return player;
    }
}
