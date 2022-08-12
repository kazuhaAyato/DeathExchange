package com.kazuha.de.game;

import com.kazuha.de.main;
import com.kazuha.de.papi.papi;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static com.kazuha.de.main.*;

public class CountDown {

    public static int start = config.getInt("countdown-sec");
    public CountDown(){
        new BukkitRunnable(){
            @Override
            public void run(){
                if(GameState != State.COUNTDOWN)return;
                papi.Event_msg = "§e准备开始游戏 §f- §c" + start;
                if(playerList.size() < config.getInt("start-player")){
                    if(GameState != State.COUNTDOWN)return;
                    GameState = State.WATING;

                    for(Player player : Bukkit.getOnlinePlayers()){
                        player.sendTitle("§c§lFAILED", "§e人数不足");
                        player.sendMessage("§c游戏启动失败！ 人数不足！");
                        player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE,1.0f,1.0f);
                        start = config.getInt("countdown-sec");
                    }
                    return;
                }
                if(playerList.size() == Bukkit.getMaxPlayers()){
                    if(start > 5){
                        Bukkit.broadcastMessage("§a玩家已满！减少倒计时");
                        start = 5;
                    }
                }
                if (start <= 0){
                    if(GameState != State.COUNTDOWN)return;
                    GameState = State.PLAYING;
                    new game();
                    return;
                }else if (start < 10){
                    SendTitle(start);
                }else if (start%10 == 0){
                    SendTitle(start);
                }
                start --;
            }
        }.runTaskTimer(instance,0,20);
    }
    public void SendTitle(int time){
            for(Player player : Bukkit.getOnlinePlayers()){
                player.sendTitle("§c§l" + time, "§e游戏即将开始！");
                player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_FAIL,1.0f,1.0f);
            }
    }
}
