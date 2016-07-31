package com.oneshotmc.checkplayers.commands;

import com.oneshotmc.checkplayers.CheckPlayers;
import com.oneshotmc.checkplayers.PlayerChecker;
import com.oneshotmc.checkplayers.PlayerLocation;
import com.oneshotmc.checkplayers.util.ChatUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * Created by UltraX3 on 6/21/2016.
 */
public class CheckPlayersCommand implements CommandExecutor {

    private CheckPlayers checkPlayers;
    public CheckPlayersCommand(CheckPlayers checkPlayers){
        this.checkPlayers = checkPlayers;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player))
            return true;
        if(args.length != 1)
            return false;
        Player player = (Player) sender;
        UUID pUUID = player.getUniqueId();
        switch (args[0]){
            case "start":
                //We need to do this because Server#getOnlinePlayers() is unmodifiable
                List<PlayerLocation> otherPlayers = new ArrayList<>();
                for(Player onlinePlayer : checkPlayers.getServer().getOnlinePlayers())
                    otherPlayers.add(new PlayerLocation(onlinePlayer));
                //We don't want to check ourselves!!!
                Iterator<PlayerLocation> playerLocationsIterator = otherPlayers.iterator();
                while(playerLocationsIterator.hasNext()){
                    PlayerLocation playerLocation = playerLocationsIterator.next();
                    UUID otherUUID = playerLocation.getPlayer().getUniqueId();
                    if(player.getUniqueId().equals(otherUUID))
                        playerLocationsIterator.remove();
                }
                if(otherPlayers.size() == 0){
                    ChatUtil.Types.WARNING.sendMessage("There are no other players to check!", player);
                }
                else {
                    checkPlayers.getPlayersChecking().put(pUUID, new PlayerChecker(player, otherPlayers));
                    ChatUtil.Types.SUCCESS.sendMessage("Starting to check all players", player);
                }
                break;
            case "stop":
                checkPlayers.getPlayersChecking().remove(pUUID);
                ChatUtil.Types.SUCCESS.sendMessage("Stopping checking players",player);
                break;
            default:
                return false;
        }
        return true;
    }
}
