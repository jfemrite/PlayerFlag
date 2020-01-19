package io.github.lfnus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerflagCmd implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(player.hasPermission("playerflag.cmd") || player.hasPermission("playerflag.create") || player.hasPermission("playerflag.remove")) {
                if(args.length > 0) {
                    if(args[0].equalsIgnoreCase("list")) {

                        if(FileManager.flaggedMap.size() == 0) {
                            player.sendMessage(ChatColor.YELLOW + "There are no active player flags!");
                            return false;
                        }

                        Main.sendCenteredMessage((Player) sender, "&b&m-----------------------------------------------------");
                        for(String key : FileManager.flaggedMap.keySet()) {
                            String p = Bukkit.getPlayer(UUID.fromString(key)).getDisplayName();
                            Player pl = Bukkit.getPlayer(p);
                            if(Bukkit.getOnlinePlayers().contains(pl)) {
                                p = ChatColor.GREEN + p;
                            } else {
                                p = ChatColor.RED + p;
                            }
                            String m = (String) FileManager.flaggedMap.get(key);
                            player.sendMessage(p + ": "+ ChatColor.GRAY + m);
                        }

                        Main.sendCenteredMessage((Player) sender, "&b&m-----------------------------------------------------");

                    } else if(args[0].equalsIgnoreCase("create") && player.hasPermission("playerflag.create")) {
                        System.out.println("1");
                        if(args.length > 2) {
                            System.out.println("2");
                            StringBuilder sb = new StringBuilder();
                            for(int i = 2; i < args.length; i++) {
                                sb.append(args[i]);
                                sb.append(" ");
                            }
                            createPlayerFlag(player, args[1], sb.toString());
                        } else {
                            player.sendMessage(ChatColor.RED + "Wrong usage! Use /playerflag for help!");
                        }
                    } else if(args[0].equalsIgnoreCase("remove") && player.hasPermission("playerflag.remove")) {
                        if(args.length > 1) {
                            if(Bukkit.getPlayer(args[1]) != null) {
                                Player target = Bukkit.getPlayer(args[1]);
                                FileManager.removePlayer(player, target);
                            } else {
                                player.sendMessage(ChatColor.RED + "That player does not exist!");
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "Wrong usage! Use /playerflag for help!");
                        }
                    }
                } else {
                    sendHelpMessage(player);
                }
            } else {
                player.sendMessage(ChatColor.RED + "No permission!");
            }
        } else {
            System.out.println("You have to be in-game to execute this command!");
        }
        return false;
    }

    private void sendHelpMessage(Player player) {
        List<String> messages = new ArrayList<>();
        messages.add(ChatColor.GREEN + "Player Flag Help:");
        messages.add(ChatColor.YELLOW + "- /playerflag");
        messages.add(ChatColor.YELLOW + "- /playerflag list " +  ChatColor.GRAY + "- Gives a list of all player flags with messages");
        messages.add(ChatColor.YELLOW + "- /playerflag create <player> <message> " + ChatColor.GRAY + "- Creates a new player flag");
        messages.add(ChatColor.YELLOW + "- /playerflag remove <player> " +  ChatColor.GRAY + "- Removes a player flag");

        for(int i = 0; i < messages.size(); i++) {
            player.sendMessage(messages.get(i));
        }
    }

    private void createPlayerFlag(Player player, String arg1, String message) {
        Player target = Bukkit.getPlayer(arg1);
        if(target != null) {
            FileManager.addPlayer(target, message);

            String notification = Main.getFileConfig().getString("messages.notification");
            notification = ChatColor.translateAlternateColorCodes('&', notification);
            if(notification.contains("{player}")) {
                notification = notification.replace("{player}", player.getDisplayName());
            }
            if(notification.contains("{target}")) {
                notification = notification.replace("{target}", arg1);
            }
            if(notification.contains("{message}")) {
                notification = notification.replace("{message}", message);
            }

            for(Player p : Listeners.staff) {
                p.sendMessage(notification);
            }

        } else {
            player.sendMessage(ChatColor.RED + "That player does not exist!");
        }
    }
}
