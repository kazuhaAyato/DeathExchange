package com.kazuha.de;

import com.kazuha.de.command.de;
import com.kazuha.de.game.GameWatcher;
import com.kazuha.de.game.StartGame;
import com.kazuha.de.game.State;
import com.kazuha.de.game.actdisabler;
import com.kazuha.de.papi.papi;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class main extends JavaPlugin {

    public static State GameState;
    public static JavaPlugin instance;
    public static FileConfiguration config;
    public static List<Player> rank = new ArrayList<>();
    public static int uid;
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
        Bukkit.getPluginCommand("de").setExecutor(new de());
        uid = Integer.parseInt(sb.toString());
        Bukkit.getPluginManager().registerEvents(new StartGame(),this);
        Bukkit.getPluginManager().registerEvents(new GameWatcher(),this);
        Bukkit.getPluginManager().registerEvents(new actdisabler(),this);
    }
    public static int getUid(){
        return uid;
    }
}
