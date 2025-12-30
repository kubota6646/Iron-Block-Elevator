package com.kubota6646.ironblockelevator;

import org.bukkit.configuration.file.FileConfiguration;

public class ElevatorConfig {

    private final IronBlockElevator plugin;
    private boolean enabled;
    private double upwardSpeed;
    private double downwardSpeed;
    private int maxHeight;
    private boolean debug;

    public ElevatorConfig(IronBlockElevator plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    private void loadConfig() {
        FileConfiguration config = plugin.getConfig();
        this.enabled = config.getBoolean("enabled", true);
        this.upwardSpeed = config.getDouble("upward-speed", 0.5);
        this.downwardSpeed = config.getDouble("downward-speed", 0.5);
        this.maxHeight = config.getInt("max-height", 256);
        this.debug = config.getBoolean("debug", false);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public double getUpwardSpeed() {
        return upwardSpeed;
    }

    public double getDownwardSpeed() {
        return downwardSpeed;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public boolean isDebug() {
        return debug;
    }
}
