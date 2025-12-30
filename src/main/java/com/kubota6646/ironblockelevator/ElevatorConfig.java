package com.kubota6646.ironblockelevator;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

public class ElevatorConfig {

    private final IronBlockElevator plugin;
    private boolean enabled;
    private Material elevatorBlock;
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
        
        // Load elevator block type
        String blockName = config.getString("elevator-block", "IRON_BLOCK");
        try {
            this.elevatorBlock = Material.valueOf(blockName.toUpperCase());
            // Check if the material is a block
            if (!elevatorBlock.isBlock()) {
                plugin.getLogger().warning("指定されたマテリアル '" + blockName + "' はブロックではありません。IRON_BLOCK を使用します。");
                this.elevatorBlock = Material.IRON_BLOCK;
            }
        } catch (IllegalArgumentException e) {
            plugin.getLogger().warning("無効なブロックタイプ '" + blockName + "' が指定されました。IRON_BLOCK を使用します。");
            this.elevatorBlock = Material.IRON_BLOCK;
        }
        
        this.upwardSpeed = config.getDouble("upward-speed", 0.5);
        this.downwardSpeed = config.getDouble("downward-speed", 0.5);
        this.maxHeight = config.getInt("max-height", 256);
        this.debug = config.getBoolean("debug", false);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Material getElevatorBlock() {
        return elevatorBlock;
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
