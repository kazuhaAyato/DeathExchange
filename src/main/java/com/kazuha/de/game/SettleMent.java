package com.kazuha.de.game;

import com.kazuha.de.main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collections;

public class SettleMent {
    public SettleMent(){
        Bukkit.getPlayer(GameWatcher.winner).sendTitle("§6§l胜利!","§e你赢得了这场比赛");
        for(Player p : main.rank){
            p.sendTitle("§c§l游戏结束！", "§7你失败了 但问题不大");
        }
        for(Player p : Bukkit.getOnlinePlayers()){
            p.setGameMode(GameMode.SPECTATOR);
            p.sendMessage("§f===============================================");
            p.sendMessage("§f");
            p.sendMessage("§4死亡互换 §f@ §bMhxj§f.§aClub §7对局#"+ main.getUid());
            p.sendMessage("§f");
            p.sendMessage("§c#1 - §f"+ GameWatcher.winner);
            Collections.reverse(main.rank);
            if(main.rank.size() > 0){
                p.sendMessage("§6#2 - §f"+ main.rank.get(0).getName());
            }
            if(main.rank.size() > 1){
                p.sendMessage("§e#2 - §f"+ main.rank.get(1).getName());
            }
            p.sendMessage("§f");
            p.sendMessage("§f===============================================");
        }
        final int[] e = {5};
        new BukkitRunnable(){
            @Override
            public void run(){
                Bukkit.broadcastMessage("§e服务器将在§c"+ e[0] +"§e秒后重启！");
                e[0]--;
                if(e[0] < 0){
                    Bukkit.getServer().shutdown();
                }
            }
        }.runTaskTimer(main.instance,200L,20L);
    }
}
