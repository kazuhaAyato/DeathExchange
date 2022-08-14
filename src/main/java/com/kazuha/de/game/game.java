package com.kazuha.de.game;

import com.google.common.collect.Lists;
import com.kazuha.de.DeathThread;
import com.kazuha.de.main;
import com.kazuha.de.papi.papi;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;
import java.util.*;

public class game {
    Long start;
    public static int SwitchTimes = 0;
    public game(){
        start = System.currentTimeMillis();
        Objects.requireNonNull(Bukkit.getWorld("world")).setDifficulty(Difficulty.HARD);
        PotionEffect potionEffect = new PotionEffect(PotionEffectType.BLINDNESS,99999,1);
        PotionEffect potion2Effect = new PotionEffect(PotionEffectType.SLOW,99999,1);
        Bukkit.getOnlinePlayers().forEach(Player -> {
            Player.addPotionEffect(potionEffect);
            Player.addPotionEffect(potion2Effect);
            Player.sendTitle("§a正在配置游戏", "§7请等一会拉");
        });
        final int[] swtir = {0};
        Bukkit.getOnlinePlayers().forEach(Player -> {
            Player.teleport(main.tplocs.get(swtir[0]));
            swtir[0]++;
            Player.getActivePotionEffects().clear();
            Player.sendTitle(" ", " ");
        });
        main.playerList = Lists.newArrayList(Bukkit.getOnlinePlayers());
        Bukkit.getWorld("world").getWorldBorder().setCenter(new Location(Bukkit.getWorld("world"),0.0,0.0,0.0));
        Bukkit.getWorld("world").getWorldBorder().setSize(5000);
        //GameDaemon
        Thread thread = new Thread(new DeathThread());
        thread.start();
        List<String> list = main.config.getStringList("start-info");
        for(String e : list){
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',e));
        }
        Bukkit.broadcastMessage("§4§l互换将在 §c1分30秒 §4§l后开始 祝你好运 glhf");
        papi.Event_msg = "§a准备阶段";
        final int[] e = {-160};
        final HashMap<Player,Player> Assigned = new HashMap<>();
        final List<Player>[] papalist = new List[]{new ArrayList<>()};
        new BukkitRunnable(){
            @Override
            public void run(){
                if(main.GameState!=State.PLAYING){
                    return;
                }
                if(e[0] <= -160){
                    Bukkit.broadcastMessage("§4§l互换 §c将在 §f30秒 §c后开始");
                    new BukkitRunnable(){
                        @Override
                        public void run(){
                            int index = 1;
                            if (Bukkit.getOnlinePlayers().size() >= 16){
                                index = 5;
                            }
                            if (Bukkit.getOnlinePlayers().size() >= 12){
                                index = 4;
                            }
                            if (Bukkit.getOnlinePlayers().size() >= 8){
                                index = 3;
                            }
                            if (Bukkit.getOnlinePlayers().size() > 4){
                                index = 2;
                            }
                            index = new Random().nextInt(index);
                            Long e = System.currentTimeMillis();
                            papalist[0] = main.playerList;
                            Random random1 = new Random();
                            if(papalist[0].size()%2 != 0){
                                papalist[0].remove(random1.nextInt(papalist[0].size()));
                            }
                            while (index > 0){
                                index--;
                                if(papalist[0].isEmpty())break;
                                Player player = papalist[0].get(random1.nextInt(papalist[0].size()));
                                papalist[0].remove(player);
                                if(papalist[0].isEmpty())break;
                                Player player1 = papalist[0].get(random1.nextInt(papalist[0].size()));
                                papalist[0].remove(player1);
                                Assigned.put(player1,player);

                            }
                            StringBuilder xingyunguanzhong = new StringBuilder();
                            for(Player p : Assigned.keySet()){
                                xingyunguanzhong.append(p.getName()+", ");
                                p.sendTitle("§c恭喜！","§7你即将跟随机人类互换");
                                xingyunguanzhong.append(Assigned.get(p)+", ");
                                Assigned.get(p).sendTitle("§c恭喜！","§7你即将跟随机人类互换");
                            }
                            Bukkit.broadcastMessage("§c本次互换位置已加载完成: 将互换§f"+ index*2 + "§c名玩家:" );
                            Bukkit.broadcastMessage(xingyunguanzhong.toString());
                            Bukkit.broadcastMessage("§c恭喜上面的玩家！请做好准备" );
                            Bukkit.getServer().getLogger().info("本次互换已经准备完毕 耗时:"+(System.currentTimeMillis() - e) + "ms");
                        }
                    }.runTaskAsynchronously(main.instance);
                    e[0] = 30;
                }
                if(e[0] == 0){
                    e[0] --;
                                Bukkit.broadcastMessage("§c互换已开始！");
                                Assigned.keySet().forEach(player -> {
                                        Location location = player.getLocation();
                                        player.teleport(Assigned.get(player).getLocation());
                                        Assigned.get(player).teleport(location);
                                });


                    for(Player p : main.playerList){
                        p.setFallDistance(0.0f);
                    }
                    SwitchTimes ++;
                    papi.Event_msg = "§c位置已互换！";
                    papi.Event_msg = "§7等待下次互换";

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
        }.runTaskTimer(main.instance,main.config.getInt("wait"), 20);
    }
}
