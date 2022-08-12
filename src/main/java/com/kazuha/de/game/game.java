package com.kazuha.de.game;

import com.google.common.collect.Lists;
import com.kazuha.de.main;
import com.kazuha.de.papi.papi;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class game {
    Long start;
    public static int SwitchTimes = 0;
    public game(){
        start = System.currentTimeMillis();
        Bukkit.broadcastMessage("§a请稍等，系统正在配置游戏！");
        Objects.requireNonNull(Bukkit.getWorld("world")).setDifficulty(Difficulty.HARD);

        Bukkit.getWorld("world").getWorldBorder().setCenter(new Location(Bukkit.getWorld("world"),0.0,0.0,0.0));
        Bukkit.getWorld("world").getWorldBorder().setSize(5000);
        Random random = new Random();
        for(Player p : Bukkit.getOnlinePlayers()){
            Location loc = new Location(Bukkit.getWorld("world"),random.nextInt(5000) - 2500,256.0,random.nextInt(5000) - 2500);
            while(true){
                if(loc.getBlock().getType() == Material.AIR){
                    loc.setY(loc.getY()-1.0);
                }else{
                    loc.setY(loc.getY()+1.0);
                    break;
                }
            }
            p.teleport(loc);
        }
        List<String> list = main.config.getStringList("start-info");
        for(String e : list){
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',e));
        }
        Bukkit.broadcastMessage("§4§l互换将在 §c1分30秒 §4§l后开始 祝你好运 glhf");
        SimpleDateFormat format = new SimpleDateFormat("mm:ss");
        papi.Event_msg = "§a准备阶段";
        final int[] e = {0};
        new BukkitRunnable(){
            @Override
            public void run(){
                if(main.GameState!=State.PLAYING){
                    return;
                }
                if(e[0] <= -160){
                    Bukkit.broadcastMessage("§4§l互换 §c将在 §f30秒 §c后开始");
                    e[0] = 30;
                }
                if(e[0] == 0){
                    List<Player> playerList = Lists.newArrayList(Bukkit.getOnlinePlayers());
                    Random random1 = new Random();
                    Bukkit.broadcastMessage("§c互换已开始！");
                    if(playerList.size()%2 != 0){
                        playerList.remove(random1.nextInt(Bukkit.getOnlinePlayers().size()));
                    }
                    for(Player p : Bukkit.getOnlinePlayers()){
                        //互换核心代码
                        if(playerList.isEmpty())break;
                        Player player = playerList.get(random1.nextInt(playerList.size()));
                        Player player1 = playerList.get(random1.nextInt(playerList.size()));
                        Location player1loc = player.getLocation();
                        Location player2loc = player1.getLocation();
                        player1.teleport(player1loc);
                        player.teleport(player2loc);
                        playerList.remove(player1);
                        playerList.remove(player);
                    }
                    SwitchTimes ++;
                    papi.Event_msg = "§c位置已互换！";

                    papi.Event_msg = "§7等待下次互换";
                    e[0]--;
                }else{
                    e[0]--;
                    if(e[0] > 0){
                       if(String.valueOf(e[0]).length() < 2){
                           papi.Event_msg = "§a即将互换 §f- §c00:0"+ e[0];
                       }else{
                           papi.Event_msg = "§a即将互换 §f- §c00:"+ e[0];
                       }
                    }

                    return;
                }


            }
            //TODO 语法逻辑检查
        }.runTaskTimer(main.instance,1800, 20);
    }
}
