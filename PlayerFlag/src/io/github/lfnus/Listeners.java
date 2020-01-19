package io.github.lfnus;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Listeners implements Listener {

    public static List<Player> staff = new ArrayList<>();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        if(e.getPlayer().hasPermission("playerflag.notify")) {

            int flags = FileManager.flaggedMap.size();
            String message = Main.getFileConfig().getString("messages.staff-join");
            if(message.contains("{totalflags}")) {
                message = message.replace("{totalflags}", String.valueOf(flags));
            }
            message = ChatColor.translateAlternateColorCodes('&', message);
            e.getPlayer().sendMessage(message);
            staff.add(e.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        if(e.getPlayer().hasPermission("playerflag.notify")) {
            staff.remove(e.getPlayer());
        }
    }


}
