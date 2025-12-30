package com.kubota6646.ironblockelevator;

import org.bukkit.plugin.java.JavaPlugin;

public class IronBlockElevator extends JavaPlugin {

    private static IronBlockElevator instance;
    private ElevatorConfig elevatorConfig;
    private GriefPreventionIntegration griefPreventionIntegration;

    @Override
    public void onEnable() {
        instance = this;
        
        // Save default config if not exists
        saveDefaultConfig();
        
        // Load configuration
        elevatorConfig = new ElevatorConfig(this);
        
        // Initialize GriefPrevention integration
        griefPreventionIntegration = new GriefPreventionIntegration(this);
        
        // Register event listener
        getServer().getPluginManager().registerEvents(new ElevatorListener(this), this);
        
        // Register command
        getCommand("ironblockelevator").setExecutor(new ElevatorCommand(this));
        
        getLogger().info("IronBlockElevator has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("IronBlockElevator has been disabled!");
    }

    public static IronBlockElevator getInstance() {
        return instance;
    }

    public ElevatorConfig getElevatorConfig() {
        return elevatorConfig;
    }

    public GriefPreventionIntegration getGriefPreventionIntegration() {
        return griefPreventionIntegration;
    }

    public void reloadElevatorConfig() {
        reloadConfig();
        elevatorConfig = new ElevatorConfig(this);
    }
}
