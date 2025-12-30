package com.kubota6646.ironblockelevator;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class ElevatorListener implements Listener {

    private final IronBlockElevator plugin;

    public ElevatorListener(IronBlockElevator plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        // Check if plugin is enabled
        if (!plugin.getElevatorConfig().isEnabled()) {
            return;
        }

        Player player = event.getPlayer();
        
        // Get the block the player is standing on
        Block blockBelow = player.getLocation().subtract(0, 0.1, 0).getBlock();
        
        // Check if the block is the configured elevator block
        Material elevatorBlock = plugin.getElevatorConfig().getElevatorBlock();
        if (blockBelow.getType() != elevatorBlock) {
            return;
        }

        // Check GriefPrevention permissions
        if (!plugin.getGriefPreventionIntegration().canUseElevator(player, blockBelow.getLocation())) {
            if (plugin.getElevatorConfig().isDebug()) {
                plugin.getLogger().info(plugin.getMessages().getDebugCannotUseGriefPrevention(player.getName()));
            }
            return;
        }

        // Check if player is jumping (moving upward)
        if (player.getVelocity().getY() > 0) {
            // Find the next elevator block above
            Block targetBlock = findNextElevatorBlockAbove(blockBelow, plugin.getElevatorConfig().getMaxHeight());
            
            if (targetBlock != null) {
                // Check GriefPrevention permissions for the target location
                if (!plugin.getGriefPreventionIntegration().canUseElevator(player, targetBlock.getLocation())) {
                    if (plugin.getElevatorConfig().isDebug()) {
                        plugin.getLogger().info(plugin.getMessages().getDebugTargetProtected(player.getName()));
                    }
                    return;
                }
                
                // Calculate the distance to the target block
                double distance = targetBlock.getY() - player.getLocation().getY();
                
                // Apply upward velocity
                double speed = plugin.getElevatorConfig().getUpwardSpeed();
                Vector velocity = player.getVelocity();
                velocity.setY(speed);
                player.setVelocity(velocity);
                
                if (plugin.getElevatorConfig().isDebug()) {
                    plugin.getLogger().info(plugin.getMessages().getDebugMovingUp(player.getName()));
                }
            }
        }
        // Check if player is sneaking (moving downward)
        else if (player.isSneaking()) {
            // Find the next elevator block below
            Block targetBlock = findNextElevatorBlockBelow(blockBelow);
            
            if (targetBlock != null) {
                // Check GriefPrevention permissions for the target location
                if (!plugin.getGriefPreventionIntegration().canUseElevator(player, targetBlock.getLocation())) {
                    if (plugin.getElevatorConfig().isDebug()) {
                        plugin.getLogger().info(plugin.getMessages().getDebugTargetProtected(player.getName()));
                    }
                    return;
                }
                
                // Calculate the distance to the target block
                double distance = player.getLocation().getY() - targetBlock.getY();
                
                // Apply downward velocity
                double speed = -plugin.getElevatorConfig().getDownwardSpeed();
                Vector velocity = player.getVelocity();
                velocity.setY(speed);
                player.setVelocity(velocity);
                
                if (plugin.getElevatorConfig().isDebug()) {
                    plugin.getLogger().info(plugin.getMessages().getDebugMovingDown(player.getName()));
                }
            }
        }
    }

    /**
     * Find the next elevator block above the current position
     */
    private Block findNextElevatorBlockAbove(Block startBlock, int maxHeight) {
        int startY = startBlock.getY();
        int worldMaxHeight = Math.min(startBlock.getWorld().getMaxHeight(), maxHeight);
        Material elevatorBlock = plugin.getElevatorConfig().getElevatorBlock();
        
        for (int y = startY + 1; y < worldMaxHeight; y++) {
            Block block = startBlock.getWorld().getBlockAt(startBlock.getX(), y, startBlock.getZ());
            if (block.getType() == elevatorBlock) {
                // Check if there's space above the elevator block for the player
                Block above = block.getRelative(0, 1, 0);
                Block above2 = block.getRelative(0, 2, 0);
                
                if (above.getType().isAir() && above2.getType().isAir()) {
                    return block;
                }
            }
        }
        
        return null;
    }

    /**
     * Find the next elevator block below the current position
     */
    private Block findNextElevatorBlockBelow(Block startBlock) {
        int startY = startBlock.getY();
        int worldMinHeight = startBlock.getWorld().getMinHeight();
        Material elevatorBlock = plugin.getElevatorConfig().getElevatorBlock();
        
        for (int y = startY - 1; y >= worldMinHeight; y--) {
            Block block = startBlock.getWorld().getBlockAt(startBlock.getX(), y, startBlock.getZ());
            if (block.getType() == elevatorBlock) {
                // Check if there's space above the elevator block for the player
                Block above = block.getRelative(0, 1, 0);
                Block above2 = block.getRelative(0, 2, 0);
                
                if (above.getType().isAir() && above2.getType().isAir()) {
                    return block;
                }
            }
        }
        
        return null;
    }
}
