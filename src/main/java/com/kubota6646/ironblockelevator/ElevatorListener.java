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
        
        // Check if the block is an iron block
        if (blockBelow.getType() != Material.IRON_BLOCK) {
            return;
        }

        // Check GriefPrevention permissions
        if (!plugin.getGriefPreventionIntegration().canUseElevator(player, blockBelow.getLocation())) {
            if (plugin.getElevatorConfig().isDebug()) {
                plugin.getLogger().info(player.getName() + " cannot use elevator due to GriefPrevention protection");
            }
            return;
        }

        // Check if player is jumping (moving upward)
        if (player.getVelocity().getY() > 0) {
            // Find the next iron block above
            Block targetBlock = findNextIronBlockAbove(blockBelow, plugin.getElevatorConfig().getMaxHeight());
            
            if (targetBlock != null) {
                // Check GriefPrevention permissions for the target location
                if (!plugin.getGriefPreventionIntegration().canUseElevator(player, targetBlock.getLocation())) {
                    if (plugin.getElevatorConfig().isDebug()) {
                        plugin.getLogger().info(player.getName() + " cannot use elevator - target location protected by GriefPrevention");
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
                    plugin.getLogger().info(player.getName() + " is moving up on iron block elevator");
                }
            }
        }
        // Check if player is sneaking (moving downward)
        else if (player.isSneaking()) {
            // Find the next iron block below
            Block targetBlock = findNextIronBlockBelow(blockBelow);
            
            if (targetBlock != null) {
                // Check GriefPrevention permissions for the target location
                if (!plugin.getGriefPreventionIntegration().canUseElevator(player, targetBlock.getLocation())) {
                    if (plugin.getElevatorConfig().isDebug()) {
                        plugin.getLogger().info(player.getName() + " cannot use elevator - target location protected by GriefPrevention");
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
                    plugin.getLogger().info(player.getName() + " is moving down on iron block elevator");
                }
            }
        }
    }

    /**
     * Find the next iron block above the current position
     */
    private Block findNextIronBlockAbove(Block startBlock, int maxHeight) {
        int startY = startBlock.getY();
        int worldMaxHeight = Math.min(startBlock.getWorld().getMaxHeight(), maxHeight);
        
        for (int y = startY + 1; y < worldMaxHeight; y++) {
            Block block = startBlock.getWorld().getBlockAt(startBlock.getX(), y, startBlock.getZ());
            if (block.getType() == Material.IRON_BLOCK) {
                // Check if there's space above the iron block for the player
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
     * Find the next iron block below the current position
     */
    private Block findNextIronBlockBelow(Block startBlock) {
        int startY = startBlock.getY();
        int worldMinHeight = startBlock.getWorld().getMinHeight();
        
        for (int y = startY - 1; y >= worldMinHeight; y--) {
            Block block = startBlock.getWorld().getBlockAt(startBlock.getX(), y, startBlock.getZ());
            if (block.getType() == Material.IRON_BLOCK) {
                // Check if there's space above the iron block for the player
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
