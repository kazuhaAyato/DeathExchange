package com.kazuha.de.game;

import com.google.common.collect.Lists;
import com.kazuha.de.DeathThread;
import com.kazuha.de.main;
import com.kazuha.de.papi.papi;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import static com.kazuha.de.main.GameState;

public class game {
    Long start;
    public static Long StartTime;
    public static int SwitchTimes = 0;
    public game(){
        StartTime = System.currentTimeMillis();
        GameWatcher.isAlive = Lists.newArrayList(Bukkit.getOnlinePlayers());
        start = System.currentTimeMillis();
        Objects.requireNonNull(Bukkit.getWorld("world")).setDifficulty(Difficulty.HARD);
        GameState = State.PLAYING;
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
        Bukkit.getOnlinePlayers().forEach(Player->Player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF)));
        Bukkit.broadcastMessage("§4§l互换将在 §c1分30秒 §4§l后开始 祝你好运 glhf");
        papi.Event_msg = "§a准备阶段";
        final int[] e = {-160};
        final HashMap<Player,Player> Assigned = new HashMap<>();
        final List<Player>[] papalist = new List[]{new ArrayList<>()};
        final int[] count = {30};
        new BukkitRunnable(){
            @Override
            public void run(){
                if(main.GameState!=State.PLAYING){
                    return;
                }
                if(e[0] <= (30-count[0])*5-160){
                    Bukkit.broadcastMessage("§4§l互换 §c将在 §f"+ count[0] + "秒 §c后开始");
                    count[0]--;
                    new BukkitRunnable(){
                        @Override
                        public void run(){
                            int ea = 1;
                            if(main.playerList.size() > 4)ea = main.playerList.size()/4;

                            int index = new Random().nextInt(ea);
                            if(index == 0){
                                index = 1;
                            }
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
                            Bukkit.broadcastMessage("§c本次互换位置已加载完成: 将互换§f"+ Assigned.size()*2 + "§c名玩家:" );
                            StringBuilder xingyunguanzhong = new StringBuilder();
                            for(Player p : Assigned.keySet()){
                                xingyunguanzhong.append(p.getName()+", ");
                                p.sendTitle("§c恭喜！","§7你即将跟随机人类互换");
                                xingyunguanzhong.append(Assigned.get(p).getName()+", ");
                                Assigned.get(p).sendTitle("§c恭喜！","§7你即将跟随机人类互换");
                            }

                            Bukkit.broadcastMessage(xingyunguanzhong.toString());
                            Bukkit.broadcastMessage("§c恭喜上面的玩家！请做好准备" );
                            Bukkit.getServer().getLogger().info("本次互换已经准备完毕 耗时:"+(System.currentTimeMillis() - e) + "ms");
                        }
                    }.runTaskAsynchronously(main.instance);
                    count[0]--;
                    if(count[0] < 20){
                        count[0] = 20;
                    }
                    e[0] = count[0];
                }
                if(e[0] == 1){
                    e[0] -= 2;
                    papi.Event_msg = "§a即将互换 §f- §c00:00";
                     Bukkit.broadcastMessage("§c互换已开始！");
                     Assigned.keySet().forEach(player -> {
                         Location location = player.getLocation();
                         player.teleport(Assigned.get(player).getLocation());
                         Assigned.get(player).teleport(location);
                     });
                     Assigned.clear();

                    for(Player p : main.playerList){
                        p.setFallDistance(0.0f);
                    }
                    SwitchTimes ++;
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
        }.runTaskTimer(main.instance,1800, 20);
    }
}
