package com.kubota6646.ironblockelevator;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ElevatorCommand implements CommandExecutor {

    private final IronBlockElevator plugin;

    public ElevatorCommand(IronBlockElevator plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.GOLD + "=== Iron Block Elevator ===");
            sender.sendMessage(ChatColor.YELLOW + "Version: " + ChatColor.WHITE + plugin.getDescription().getVersion());
            sender.sendMessage(ChatColor.YELLOW + "Author: " + ChatColor.WHITE + plugin.getDescription().getAuthors());
            sender.sendMessage(ChatColor.YELLOW + "Use " + ChatColor.WHITE + "/" + label + " reload" + ChatColor.YELLOW + " to reload config");
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("ironblockelevator.reload")) {
                sender.sendMessage(ChatColor.RED + "You don't have permission to reload the config!");
                return true;
            }

            plugin.reloadElevatorConfig();
            sender.sendMessage(ChatColor.GREEN + "Configuration reloaded successfully!");
            return true;
        }

        sender.sendMessage(ChatColor.RED + "Unknown subcommand. Use /" + label + " for help.");
        return true;
    }
}
