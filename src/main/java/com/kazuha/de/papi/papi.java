package com.kazuha.de.papi;

import com.kazuha.de.game.game;
import com.kazuha.de.main;
import me.clip.placeholderapi.PlaceholderHook;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class papi extends PlaceholderExpansion {
    public static String Event_msg;
    public static String well;
    private final main plugin;

    public papi(main plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "death";
    }

    @Override
    public @NotNull String getAuthor() {
        return "KazuhaAyato";
    }

    @Override
    public @NotNull String getVersion() {
        return main.instance.getDescription().getVersion();
    }
    @Override
    public boolean persist() {
        return true;
    }
    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if(params.equalsIgnoreCase("event")){
            return Event_msg;
        }
        if(params.equalsIgnoreCase("rounds")) {
            return String.valueOf(game.SwitchTimes);
        }
        if(params.equalsIgnoreCase("uid")){
            return String.valueOf(main.uid);
        }
        return null; // Placeholder is unknown by the Expansion
    }

}
