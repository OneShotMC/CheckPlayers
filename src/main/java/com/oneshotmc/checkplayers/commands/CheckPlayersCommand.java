package com.oneshotmc.checkplayers.commands;

import com.oneshotmc.checkplayers.CheckPlayers;
import com.oneshotmc.checkplayers.PlayerChecker;
import com.oneshotmc.checkplayers.util.ChatUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
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
                List<Player> otherPlayers = new ArrayList<>();
                otherPlayers.addAll((List<Player>) checkPlayers.getServer().getOnlinePlayers());
                //We don't want to check ourselves!!!
                otherPlayers.remove(player);
                checkPlayers.getPlayersChecking().put(pUUID, new PlayerChecker(player,otherPlayers));
                ChatUtil.Types.SUCCESS.sendMessage("Starting to check all players",player);
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
