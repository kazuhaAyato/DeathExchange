package com.kazuha.de.chat;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Chat implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        e.setFormat("§7"+e.getPlayer().getName() + " §8> §f" + e.getMessage());
    }
}
