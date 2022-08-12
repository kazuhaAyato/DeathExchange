package com.kazuha.de.command;

import com.kazuha.de.main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class de implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        commandSender.sendMessage("§f=========================================");
        commandSender.sendMessage("§f");
        commandSender.sendMessage("§3Death§bExchange §f- v"+ main.instance.getDescription().getVersion() + " §7By §3Kazuha§bAyato");
        commandSender.sendMessage("§7§o遊生夢死 才能ない脳内 唱えよシスターズ");
        commandSender.sendMessage("§f");
        commandSender.sendMessage("§fBUG反馈/提交建议 -> §nKazuhaAyato@qq.com");
        commandSender.sendMessage("§f");
        commandSender.sendMessage("§f=========================================");
        return true;
    }
}
