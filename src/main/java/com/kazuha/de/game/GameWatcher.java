package com.kazuha.de.game;

import com.kazuha.de.main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.ArrayList;
import java.util.List;

import static com.kazuha.de.main.*;

public class GameWatcher implements Listener {
    public static String winner;
    public static List<Player> isAlive = new ArrayList<>();
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(EntityDamageEvent e){
        if(e.getEntity().getType() != EntityType.PLAYER){
            return;
        }
        if(GameState != State.PLAYING){
            e.setCancelled(true);
            return;
        }
        if(isAlive.contains(Bukkit.getPlayer(e.getEntity().getName()))){
            isAlive.remove(Bukkit.getPlayer(e.getEntity().getName()));
            e.setCancelled(true);
            return;
        }
        if(((Player)e.getEntity()).getGameMode() == GameMode.SPECTATOR){
            e.getEntity().teleport(new Location(Bukkit.getWorld("world"),e.getEntity().getLocation().getX(),100,e.getEntity().getLocation().getY()));
            e.setCancelled(true);
            return;
        }
        if((((Player)e.getEntity()).getHealth() - e.getDamage()) <= 0.0){
            e.setCancelled(true);

            Bukkit.getPlayer(e.getEntity().getName()).setGameMode(GameMode.SPECTATOR);
            Bukkit.getPlayer(e.getEntity().getName()).sendTitle("§4你死了！", "§e干的漂亮！");
            rank.add(Bukkit.getPlayer(e.getEntity().getName()));
            Bukkit.broadcastMessage("§c" + e.getEntity().getName() + "§e寄了。");
        }

    }
}
