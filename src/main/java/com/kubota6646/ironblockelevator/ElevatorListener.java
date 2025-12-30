package com.kubota6646.ironblockelevator;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ElevatorListener implements Listener {

    private final IronBlockElevator plugin;
    private final Map<UUID, Long> cooldowns;
    private final Map<UUID, Long> lastCooldownMessage;

    public ElevatorListener(IronBlockElevator plugin) {
        this.plugin = plugin;
        this.cooldowns = new HashMap<>();
        this.lastCooldownMessage = new HashMap<>();
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        // Check if plugin is enabled
        if (!plugin.getElevatorConfig().isEnabled()) {
            return;
        }

        Player player = event.getPlayer();
        
        // Get the block the player is standing on (exactly 1 block below)
        Location playerLoc = player.getLocation();
        Block blockBelow = playerLoc.getWorld().getBlockAt(
            playerLoc.getBlockX(),
            playerLoc.getBlockY() - 1,
            playerLoc.getBlockZ()
        );
        
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

        // Determine what action the player is trying to do
        boolean isJumping = player.getVelocity().getY() > 0.1;
        boolean isSneaking = player.isSneaking();
        
        // Check if player is trying to use the elevator
        if (!isJumping && !isSneaking) {
            return; // Player is just standing on the block, not trying to use it
        }

        // Check cooldown only when player is trying to use the elevator
        int cooldownSeconds = plugin.getElevatorConfig().getCooldown();
        if (cooldownSeconds > 0) {
            UUID playerId = player.getUniqueId();
            long currentTime = System.currentTimeMillis();
            
            if (cooldowns.containsKey(playerId)) {
                long lastUse = cooldowns.get(playerId);
                long timeSinceLastUse = currentTime - lastUse;
                long cooldownMillis = cooldownSeconds * 1000L;
                
                if (timeSinceLastUse < cooldownMillis) {
                    // Only send message once per second to avoid spam
                    Long lastMessage = lastCooldownMessage.get(playerId);
                    if (lastMessage == null || (currentTime - lastMessage) > 1000) {
                        long remainingTime = (cooldownMillis - timeSinceLastUse) / 1000 + 1;
                        player.sendMessage(plugin.getMessages().getCooldownMessage(String.valueOf(remainingTime)));
                        lastCooldownMessage.put(playerId, currentTime);
                        
                        if (plugin.getElevatorConfig().isDebug()) {
                            plugin.getLogger().info(plugin.getMessages().getDebugCooldown(player.getName(), String.valueOf(remainingTime)));
                        }
                    }
                    return;
                }
            }
        }

        // Check if player is jumping (moving upward)
        if (isJumping) {
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
                
                // Teleport player to the target elevator block
                Location targetLoc = targetBlock.getLocation().clone().add(0.5, 1.0, 0.5);
                targetLoc.setPitch(player.getLocation().getPitch());
                targetLoc.setYaw(player.getLocation().getYaw());
                player.teleport(targetLoc);
                
                // Add small upward velocity for smooth landing
                player.setVelocity(new Vector(0, 0.5, 0));
                
                // Update cooldown
                if (plugin.getElevatorConfig().getCooldown() > 0) {
                    cooldowns.put(player.getUniqueId(), System.currentTimeMillis());
                }
                
                if (plugin.getElevatorConfig().isDebug()) {
                    plugin.getLogger().info(plugin.getMessages().getDebugMovingUp(player.getName()));
                }
            }
        }
        // Check if player is sneaking (moving downward)
        else if (isSneaking) {
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
                
                // Teleport player to the target elevator block
                Location targetLoc = targetBlock.getLocation().clone().add(0.5, 1.0, 0.5);
                targetLoc.setPitch(player.getLocation().getPitch());
                targetLoc.setYaw(player.getLocation().getYaw());
                player.teleport(targetLoc);
                
                // Update cooldown
                if (plugin.getElevatorConfig().getCooldown() > 0) {
                    cooldowns.put(player.getUniqueId(), System.currentTimeMillis());
                }
                
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
