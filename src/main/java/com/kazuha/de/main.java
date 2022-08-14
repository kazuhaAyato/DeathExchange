package com.kazuha.de;

import com.kazuha.de.chat.Chat;
import com.kazuha.de.command.de;
import com.kazuha.de.game.*;
import com.kazuha.de.papi.papi;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class main extends JavaPlugin {

    public static State GameState;
    public static JavaPlugin instance;
    public static FileConfiguration config;
    public static List<Player> rank = new ArrayList<>();
    public static int uid;
    public static List<Location> tplocs = new ArrayList<>();
    public static List<Player> playerList = new ArrayList<>();
    @Override
    public void onEnable(){
        saveDefaultConfig();
        config = getConfig();
        GameState = State.WATING;
        instance = this;
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<8;i++) {
            int number = random.nextInt(9);
            sb.append(number);
        }
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new papi(this).register();
        }
        Bukkit.getPluginManager().registerEvents(new Chat(),this);
        Bukkit.getPluginCommand("de").setExecutor(new de());
        uid = Integer.parseInt(sb.toString());
        Bukkit.getPluginManager().registerEvents(new StartGame(),this);
        Bukkit.getPluginManager().registerEvents(new GameWatcher(),this);
        Bukkit.getPluginManager().registerEvents(new actdisabler(),this);
        getLogger().info("§f正在初始化..");
        new BukkitRunnable(){
            @Override
            public void run(){
                for(int i = 0; i<= Bukkit.getMaxPlayers(); i++){
                    Location loc = new Location(Bukkit.getWorld("world"),new Random().nextInt(5000)-2500,100,new Random().nextInt(5000)-2500);
                    for(int e = 200; e>=0; e--){
                        if(loc.getBlock().getType() != Material.AIR){
                            loc.setY(loc.getY()-1);
                        }else{
                            break;
                        }
                    }
                    if(loc.getBlock().getType() == Material.WATER || loc.getBlock().getType() == Material.LAVA){
                        loc.getBlock().setType(Material.STONE);
                    }
                    tplocs.add(loc.add(0,1,0));
                }
            }
        }.runTaskAsynchronously(this);
    }
    public static int getUid(){
        return uid;
    }
}
