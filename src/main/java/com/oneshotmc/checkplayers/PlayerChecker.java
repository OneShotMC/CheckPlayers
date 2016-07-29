package com.oneshotmc.checkplayers;

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
    private List<Player> players;
    private Player player;
    public PlayerChecker(Player player,List<Player> players){
        this.players = players;
        this.size = players.size();
        this.player = player;
    }

    public int doNext(){
        Player player = next();
        this.player.sendMessage(ChatColor.BLUE+""+ChatColor.BOLD+"Stalking #"+atomicInteger.get()+" "+player.getName());
        this.player.teleport(player);
        return atomicInteger.get();
    }

    public int doPrev(){
        Player player = previous();
        this.player.sendMessage(ChatColor.BLUE+""+ChatColor.BOLD+"Stalking #"+atomicInteger.get()+" "+player.getName());
        this.player.teleport(player);
        return atomicInteger.get();
    }

    private Player next(){
        if(size == 0)
            return null;
        int nextValue = atomicInteger.incrementAndGet();
        if(nextValue >= size){
            atomicInteger.set(0);
            nextValue = 0;
        }
        if(!players.get(nextValue).isOnline()) {
            players.remove(nextValue);
            updateSize();
            return next();
        }
        return players.get(nextValue);
    }

    private Player previous(){
        if(size == 0)
            return null;
        int prevValue = atomicInteger.decrementAndGet();
        if(prevValue < 0){
            atomicInteger.set(size - 1);
            prevValue = size - 1;
        }
        if(!players.get(prevValue).isOnline()) {
            players.remove(prevValue);
            updateSize();
            return previous();
        }
        return players.get(prevValue);
    }

    private void updateSize(){
        this.size = players.size();
    }

}
