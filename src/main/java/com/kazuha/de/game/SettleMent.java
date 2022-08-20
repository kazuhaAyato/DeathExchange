package com.kazuha.de.game;

import com.kazuha.de.main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;
import java.util.Collections;

public class SettleMent {
    public SettleMent(){
        Bukkit.getPlayer(GameWatcher.winner).sendTitle("§6§l胜利!","§e你赢得了这场比赛");
        for(Player p : main.rank){
            p.sendTitle("§c§l游戏结束！", "§7你失败了 但问题不大");
        }
        SimpleDateFormat format = new SimpleDateFormat("hh时mm分ss秒");
        for(Player p : Bukkit.getOnlinePlayers()){
            Bukkit.getScheduler().runTask(main.instance,()-> p.setGameMode(GameMode.SPECTATOR));
            p.sendMessage("§f===============================================");
            p.sendMessage("§f");
            p.sendMessage("§4死亡互换 §f@ §bMhxj§f.§aClub §7对局#"+ main.getUid());
            p.sendMessage("§f");
            p.sendMessage("§c获胜者 - §f"+ GameWatcher.winner);
            p.sendMessage("§6游戏持续时间：§f" + format.format(System.currentTimeMillis() - game.StartTime));
            p.sendMessage("§f");
            p.sendMessage("§a感谢你的游玩, 祝你玩的开心！");
            p.sendMessage("§f===============================================");
        }
    }
}
