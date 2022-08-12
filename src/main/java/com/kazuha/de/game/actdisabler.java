package com.kazuha.de.game;

import com.kazuha.de.main;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;

public class actdisabler implements Listener {
    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void FUCKINTERACT(PlayerInteractEvent event) {
        if(main.GameState == State.COUNTDOWN || main.GameState == State.WATING){
            event.setCancelled(true);
        }

    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void FUCKDAMAGE(EntityDamageEvent event) {
        if(main.GameState == State.COUNTDOWN || main.GameState == State.WATING){
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void FUCKRUN(FoodLevelChangeEvent event) {
        if(main.GameState == State.COUNTDOWN || main.GameState == State.WATING){
            event.setCancelled(true);
        }
    }
}
