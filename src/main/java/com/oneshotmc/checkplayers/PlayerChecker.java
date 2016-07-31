package com.oneshotmc.checkplayers;

import com.oneshotmc.checkplayers.util.ChatUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by UltraX3 on 6/21/2016.
 */
public class PlayerChecker {
    private AtomicInteger atomicInteger = new AtomicInteger(0);
    private int size;
    private List<PlayerLocation> players;
    private Player player;
    private boolean switchedRecently = false;
    public PlayerChecker(Player player,List<PlayerLocation> players){
        this.players = players;
        this.size = players.size();
        this.player = player;
    }

    public int doNext(){
        PlayerLocation player = next();
        if(player.isOnline())
            ChatUtil.Types.NEUTRAL.sendMessage(ChatColor.BOLD + "Stalking #"+atomicInteger.get() + " "+player.getPlayer().getName(),this.player);
        else
            ChatUtil.Types.WARNING.sendMessage(ChatColor.BOLD + "Stalking #"+atomicInteger.get()+ " OfflinePlayer: "+player.getPlayer().getName(),this.player);
        if(player.isOnline())
            this.player.teleport(player.getPlayer().getLocation());
        else
            this.player.teleport(player.getLocation());
        switchedRecently = true;
        CheckPlayers.instace.getServer().getScheduler().scheduleSyncDelayedTask(CheckPlayers.instace, new Runnable() {
            @Override
            public void run() {
                switchedRecently = false;
            }
        }, 6);
        return atomicInteger.get();
    }

    public int doPrev(){
        PlayerLocation player = previous();
        if(player.isOnline())
            ChatUtil.Types.NEUTRAL.sendMessage(ChatColor.BOLD + "Stalking #"+atomicInteger.get() + " "+player.getPlayer().getName(), this.player);
        else
            ChatUtil.Types.WARNING.sendMessage(ChatColor.BOLD + "Stalking #"+atomicInteger.get()+ " OfflinePlayer: "+player.getPlayer().getName(), this.player);
        if(player.isOnline())
            this.player.teleport(player.getPlayer().getLocation());
        else
            this.player.teleport(player.getLocation());
        switchedRecently = true;
        switchedRecently = true;
        CheckPlayers.instace.getServer().getScheduler().scheduleSyncDelayedTask(CheckPlayers.instace, new Runnable() {
            @Override
            public void run() {
                switchedRecently = false;
            }
        }, 6);
        return atomicInteger.get();
    }

    private PlayerLocation next(){
        if(size == 0)
            return null;
        int nextValue = atomicInteger.incrementAndGet();
        if(nextValue >= size){
            atomicInteger.set(0);
            nextValue = 0;
        }
        return players.get(nextValue);
    }

    private PlayerLocation previous(){
        if(size == 0)
            return null;
        int prevValue = atomicInteger.decrementAndGet();
        if(prevValue < 0){
            atomicInteger.set(size - 1);
            prevValue = size - 1;
        }
        return players.get(prevValue);
    }

    public boolean switchedRecently() {
        return switchedRecently;
    }

    private void updateSize(){
        this.size = players.size();
    }

}
