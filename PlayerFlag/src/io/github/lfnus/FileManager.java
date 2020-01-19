package io.github.lfnus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class FileManager {

    public static YamlConfiguration flagsConfig;
    public static File rawFlags;
    public static HashMap<String, Object> flaggedMap;

    public static void intitiateFiles() {

        rawFlags = new File(Bukkit.getPluginManager().getPlugin("PlayerFlag").getDataFolder(), "flags.yml");

        if(!Bukkit.getPluginManager().getPlugin("PlayerFlag").getDataFolder().exists()) {
            Bukkit.getPluginManager().getPlugin("PlayerFlag").getDataFolder().mkdir();
        }
        if(!rawFlags.exists()) {
            try {
                rawFlags.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        flagsConfig = YamlConfiguration.loadConfiguration(rawFlags);

        flaggedMap = new HashMap<>();
        if(flagsConfig.get("Flagged") == null) {
            System.out.println("Flagged section created");
            flagsConfig.createSection("Flagged");
        } else {
            System.out.println("Flagged contained stuff");
            for(String key : flagsConfig.getConfigurationSection("Flagged").getKeys(false)) {
                flaggedMap.put(key, flagsConfig.getString("Flagged." + key));
                System.out.println("Added: " + key + " : " + flagsConfig.getString("Flagged." + key));
            }
        }
        saveFile();
    }

    public static void addPlayer(Player target, String message) {
        flaggedMap.put(target.getUniqueId().toString(), message);
        for(int i = 0; i < flaggedMap.size(); i++) {
            Bukkit.getPlayer("lfn").sendMessage(flaggedMap.keySet().toString());
        }
        flagsConfig.set("Flagged", flaggedMap);
        saveFile();
    }

    public static void removePlayer(Player player, Player target) {
        if(flaggedMap.get(target.getUniqueId().toString()) != null) {
            flagsConfig.set("Flagged." + target.getUniqueId().toString(), null);
            flaggedMap.remove(target.getUniqueId().toString());
            player.sendMessage("Done.");
            saveFile();
        } else {
            player.sendMessage(ChatColor.RED + "That player does not have a active flag.");
        }
    }

    public static YamlConfiguration getFile() {
        return flagsConfig;
    }

    public static void saveFile() {
        try {
            getFile().save(rawFlags);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
