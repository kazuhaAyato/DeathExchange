package com.kazuha.de.game;

import com.kazuha.de.main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import static com.kazuha.de.main.*;

public class GameWatcher implements Listener {
    public static String winner;
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(EntityDamageEvent e){
        if(e.getEntity().getType() != EntityType.PLAYER){
            return;
        }
        if(GameState != State.PLAYING){
            e.setCancelled(true);
            return;
        }
        if((((Player)e.getEntity()).getHealth() - e.getDamage()) <= 0.0){
            e.setCancelled(true);
            Bukkit.getPlayer(e.getEntity().getName()).setGameMode(GameMode.SPECTATOR);
            Bukkit.getPlayer(e.getEntity().getName()).sendTitle("§4你死了！", "§e干的漂亮！");
            rank.add(((Player) e.getEntity()).getPlayer());
            playerList.remove(((Player) e.getEntity()).getPlayer());
            Bukkit.broadcastMessage("§c" + e.getEntity().getName() + "§e寄了。");
            if(main.playerList.size() == 1){
                winner = main.playerList.get(0).getName();
                GameState = State.END;
                new SettleMent();
            }
        }

    }
}
