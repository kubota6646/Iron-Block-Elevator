package com.kubota6646.ironblockelevator;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class IronBlockElevator extends JavaPlugin {

    private ElevatorConfig elevatorConfig;
    private GriefPreventionIntegration griefPreventionIntegration;
    private Messages messages;

    @Override
    public void onEnable() {
        // Save default config if not exists
        saveDefaultConfig();
        
        // Load messages
        messages = new Messages(this);
        
        // Load configuration
        elevatorConfig = new ElevatorConfig(this);
        
        // Initialize GriefPrevention integration
        griefPreventionIntegration = new GriefPreventionIntegration(this);
        
        // Register event listener
        getServer().getPluginManager().registerEvents(new ElevatorListener(this), this);
        
        // Register command
        PluginCommand command = getCommand("ironblockelevator");
        if (command != null) {
            command.setExecutor(new ElevatorCommand(this));
        }
        
        getLogger().info(messages.getPluginEnabled());
    }

    @Override
    public void onDisable() {
        getLogger().info(messages.getPluginDisabled());
    }

    public ElevatorConfig getElevatorConfig() {
        return elevatorConfig;
    }

    public GriefPreventionIntegration getGriefPreventionIntegration() {
        return griefPreventionIntegration;
    }

    public Messages getMessages() {
        return messages;
    }

    public void reloadElevatorConfig() {
        reloadConfig();
        elevatorConfig = new ElevatorConfig(this);
        messages.reload();
    }
}
