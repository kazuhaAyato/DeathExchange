package com.kazuha.de.game;

import com.kazuha.de.main;
import com.kazuha.de.papi.papi;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

import static com.kazuha.de.main.*;

public class StartGame implements Listener {

    @EventHandler
    public void OnJoin(PlayerJoinEvent e){
        if(!CanJoin)e.getPlayer().kickPlayer("服务器正在初始化请等一会再来");
        if(!GameState.equals(State.WATING) && !GameState.equals(State.COUNTDOWN)) {
            e.getPlayer().setGameMode(GameMode.SPECTATOR);
            e.setJoinMessage("");
            e.getPlayer().sendMessage("§a游戏已经开始，你现在是旁观者");
            return;
        }
        playerList.add(e.getPlayer());
        SendTitle(e.getPlayer());
        e.getPlayer().getActivePotionEffects().clear();
        e.getPlayer().setHealth(20.0);
        e.getPlayer().setLevel(0);
        e.getPlayer().setExp(0.0f);
        e.getPlayer().setFoodLevel(20);
        e.getPlayer().setGameMode(GameMode.SURVIVAL);
        if(Bukkit.getMaxPlayers() == playerList.size()){
            e.setJoinMessage("§c" + e.getPlayer().getName() + "§e加入了游戏！(§a" + playerList.size() + "§e/§c" + Bukkit.getMaxPlayers() + "§e)");
        }else{
            e.setJoinMessage("§c" + e.getPlayer().getName() + "§e加入了游戏！(§c" + playerList.size() + "§e/§c" + Bukkit.getMaxPlayers() + "§e)");
        }
        if(playerList.size() >= config.getInt("start-player")){
            if(GameState == State.COUNTDOWN){
                return;
            }
            GameState = State.COUNTDOWN;

            return;
        }
    }

    @EventHandler
    public void OnLeave(PlayerQuitEvent e){
        if(GameState == State.PLAYING){
            if(playerList.size() == 0){
                Bukkit.shutdown();
            }
        }
        playerList.remove(e.getPlayer());
        if(!GameState.equals(State.WATING) && !GameState.equals(State.COUNTDOWN) && !GameState.equals(State.END)){
            e.setQuitMessage("§e玩家§c" + e.getPlayer().getName() + "§e离开了游戏! ");
            rank.add(e.getPlayer());

        }else{
            if(GameState.equals(State.END)){
                e.setQuitMessage("§e玩家§c" + e.getPlayer().getName() + "§e离开了游戏! ");

            }

        }
    }
    @EventHandler
    public void OnPing(ServerListPingEvent e){
        if(GameState.equals(State.PLAYING)){
            e.setMotd("游戏进行中");
        }
    }
    public void SendTitle(Player player){
        if(Bukkit.getMaxPlayers() == playerList.size()){
            player.getPlayer().sendTitle(" ", "§2"+ playerList.size() + "§e/" + Bukkit.getMaxPlayers());
            papi.Event_msg = "§a等待中.."+"§2"+ playerList.size() + "§e/" + Bukkit.getMaxPlayers();
            player.playSound(player.getLocation(), Sound.LEVEL_UP,1.0f,1.0f);
        }else if(config.getInt("start-player") == playerList.size()){
            player.getPlayer().sendTitle(" ", "§a"+ playerList.size() + "§e/" + Bukkit.getMaxPlayers());
            papi.Event_msg = "§a等待中.."+"§a"+ playerList.size() + "§e/" + Bukkit.getMaxPlayers();
            player.playSound(player.getLocation(), Sound.LEVEL_UP,1.0f,1.0f);
        }else if(config.getInt("start-player") > playerList.size()){
            papi.Event_msg = "§a等待中.."+"§c"+ playerList.size() + "§e/" + Bukkit.getMaxPlayers();
            player.getPlayer().sendTitle(" ", "§c"+ playerList.size() + "§e/" + Bukkit.getMaxPlayers());
        }else{
            papi.Event_msg = "§a等待中.."+"§c"+ playerList.size() + "§e/" + Bukkit.getMaxPlayers();
            player.getPlayer().sendTitle(" ", "§c"+ playerList.size() + "§e/" + Bukkit.getMaxPlayers());
        }

    }
}
