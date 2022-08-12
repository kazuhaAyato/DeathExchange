package com.kazuha.de.game;

import com.kazuha.de.main;
import com.kazuha.de.papi.papi;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import static com.kazuha.de.main.*;

public class StartGame implements Listener {

    @EventHandler
    public void OnJoin(PlayerJoinEvent e){
        if(!GameState.equals(State.WATING) && !GameState.equals(State.COUNTDOWN)) {
            e.getPlayer().kickPlayer("§c游戏已经开始 你无法加入游戏！");
            return;
        }
        SendTitle(e.getPlayer());
        e.getPlayer().setHealth(20.0);
        e.getPlayer().setLevel(0);
        e.getPlayer().setExp(0.0f);
        e.getPlayer().setFoodLevel(20);
        e.getPlayer().setGameMode(GameMode.SURVIVAL);
        if(Bukkit.getMaxPlayers() == Bukkit.getOnlinePlayers().size()){
            e.setJoinMessage("§c" + e.getPlayer().getName() + "§e加入了游戏！(§a" + Bukkit.getOnlinePlayers().size() + "§e/§c" + Bukkit.getMaxPlayers() + "§e)");
        }else{
            e.setJoinMessage("§c" + e.getPlayer().getName() + "§e加入了游戏！(§c" + Bukkit.getOnlinePlayers().size() + "§e/§c" + Bukkit.getMaxPlayers() + "§e)");
        }
        playerList.add(e.getPlayer());
        if(playerList.size() >= config.getInt("start-player")){
            if(GameState == State.COUNTDOWN){
                return;
            }
            GameState = State.COUNTDOWN;

            new CountDown();
            return;
        }
    }
    @EventHandler
    public void OnLeave(PlayerQuitEvent e){
        if(GameState == State.PLAYING){
            if(Bukkit.getOnlinePlayers().size() == 0){
                Bukkit.shutdown();
            }
        }
        if(!GameState.equals(State.WATING) && !GameState.equals(State.COUNTDOWN)){
            if(!playerList.contains(e.getPlayer()))return;
            Bukkit.broadcastMessage("§e玩家§c" + e.getPlayer().getName() + "§e离开了游戏! 视为放弃!");
            Bukkit.broadcastMessage("§c" + e.getPlayer().getName() + "§e寄了。");
            rank.add(e.getPlayer());
            main.playerList.remove(Bukkit.getPlayer(e.getPlayer().getName()));
            if(main.playerList.size() == 1){
                main.GameState = State.END;
                GameWatcher.winner = main.playerList.get(0).getName();
                new SettleMent();
            }
        }else{
            SendTitle(e.getPlayer());
            playerList.remove(e.getPlayer());
            e.setQuitMessage("§c" + e.getPlayer().getName() + "§e离开了游戏！(§c" + playerList.size() + "§e/§c" + Bukkit.getMaxPlayers() + "§e)");
        }
    }
    public void SendTitle(Player player){
        if(Bukkit.getMaxPlayers() == Bukkit.getOnlinePlayers().size()){
            player.getPlayer().sendTitle(" ", "§2"+ Bukkit.getOnlinePlayers().size() + "§e/" + Bukkit.getMaxPlayers());
            papi.Event_msg = "§a等待中.."+"§2"+ Bukkit.getOnlinePlayers().size() + "§e/" + Bukkit.getMaxPlayers();
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,1.0f,1.0f);
        }else if(config.getInt("start-player") == Bukkit.getOnlinePlayers().size()){
            player.getPlayer().sendTitle(" ", "§a"+ Bukkit.getOnlinePlayers().size() + "§e/" + Bukkit.getMaxPlayers());
            papi.Event_msg = "§a等待中.."+"§a"+ Bukkit.getOnlinePlayers().size() + "§e/" + Bukkit.getMaxPlayers();
            player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_SHOOT,1.0f,1.0f);
        }else if(config.getInt("start-player") > Bukkit.getOnlinePlayers().size()){
            papi.Event_msg = "§a等待中.."+"§c"+ Bukkit.getOnlinePlayers().size() + "§e/" + Bukkit.getMaxPlayers();
            player.getPlayer().sendTitle(" ", "§c"+ Bukkit.getOnlinePlayers().size() + "§e/" + Bukkit.getMaxPlayers());
        }else{
            papi.Event_msg = "§a等待中.."+"§c"+ Bukkit.getOnlinePlayers().size() + "§e/" + Bukkit.getMaxPlayers();
            player.getPlayer().sendTitle(" ", "§c"+ Bukkit.getOnlinePlayers().size() + "§e/" + Bukkit.getMaxPlayers());
        }

    }
}
