package com.kubota6646.ironblockelevator;

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
        Messages messages = plugin.getMessages();
        
        if (args.length == 0) {
            sender.sendMessage(messages.getCommandHeader());
            sender.sendMessage(messages.getCommandVersion(plugin.getDescription().getVersion()));
            sender.sendMessage(messages.getCommandAuthor(plugin.getDescription().getAuthors().toString()));
            sender.sendMessage(messages.getCommandReloadUsage(label));
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("ironblockelevator.reload")) {
                sender.sendMessage(messages.getCommandNoPermission());
                return true;
            }

            plugin.reloadElevatorConfig();
            sender.sendMessage(messages.getCommandReloadSuccess());
            return true;
        }

        sender.sendMessage(messages.getCommandUnknown(label));
        return true;
    }
}
