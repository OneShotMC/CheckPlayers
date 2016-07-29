package com.oneshotmc.checkplayers.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by UltraX3 on 7/29/2016.
 */
public class ChatUtil {

    public enum Types{
        WARNING (ChatColor.YELLOW),
        SUCCESS (ChatColor.GREEN),
        ERROR (ChatColor.RED),
        NEUTRAL (ChatColor.BLUE);

        private ChatColor color;

        Types(ChatColor chatColor){
            this.color = chatColor;
        }

        public void sendMessage(String message, Player player){
            ChatUtil.sendMessage(formatMessage(message),player);
        }

        public String formatMessage(String message){
            return color + message;
        }
    }

    static public void sendMessage(String message, Player player){
        player.sendMessage(message);
    }
}
