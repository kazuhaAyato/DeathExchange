package com.kazuha.de;

import com.kazuha.de.game.GameWatcher;
import com.kazuha.de.game.SettleMent;
import com.kazuha.de.game.State;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class DeathThread implements Runnable{
    @Override
    public void run() {
        while (true){
            List<Player> player = new ArrayList<>();
            for(Player p : Bukkit.getOnlinePlayers()){
                if(p.getGameMode() == GameMode.SURVIVAL)player.add(p);
            }
            main.playerList = player;
            if(player.size() < 2){
                main.GameState = State.END;
                GameWatcher.winner = player.get(0).getName();
                new SettleMent();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for(int i =5; i>=0;i--){
                    Bukkit.broadcastMessage("§e服务器将在§c"+ i +"§e秒后重启！");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Bukkit.shutdown();
                return;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
