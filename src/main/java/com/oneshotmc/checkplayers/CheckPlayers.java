package com.oneshotmc.checkplayers;

import com.oneshotmc.checkplayers.commands.CheckPlayersCommand;
import com.oneshotmc.checkplayers.util.ChatUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by UltraX3 on 6/21/2016.
 */
public class CheckPlayers extends JavaPlugin implements Listener {
    private HashMap<UUID, PlayerChecker> playersChecking;
    private ChatUtil chatUtil;

    @Override
    public void onEnable() {
        playersChecking = new HashMap<>();
        getServer().getPluginCommand("checkplayers").setExecutor(new CheckPlayersCommand(this));
        getServer().getPluginManager().registerEvents(this,this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public HashMap<UUID, PlayerChecker> getPlayersChecking() {
        return playersChecking;
    }

    public void setPlayersChecking(HashMap<UUID, PlayerChecker> playersChecking) {
        this.playersChecking = playersChecking;
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        PlayerChecker playerChecker = playersChecking.get(player.getUniqueId());
        if(playerChecker == null)
            return;
        Action action = event.getAction();
        switch (action){
            case LEFT_CLICK_AIR:
            case LEFT_CLICK_BLOCK:
                playerChecker.doPrev();
                break;
            case RIGHT_CLICK_AIR:
            case RIGHT_CLICK_BLOCK:
                playerChecker.doNext();
                break;
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        unregister(event.getPlayer());
    }

    @EventHandler
    public void onKick(PlayerKickEvent event){
        unregister(event.getPlayer());
    }

    void unregister(Player player){
        UUID pUUID = player.getUniqueId();
        playersChecking.remove(pUUID);
    }
}
