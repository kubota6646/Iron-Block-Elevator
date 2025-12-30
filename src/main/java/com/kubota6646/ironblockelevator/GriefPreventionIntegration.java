package com.kubota6646.ironblockelevator;

import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class GriefPreventionIntegration {

    private final IronBlockElevator plugin;
    private final boolean enabled;

    public GriefPreventionIntegration(IronBlockElevator plugin) {
        this.plugin = plugin;
        this.enabled = plugin.getServer().getPluginManager().isPluginEnabled("GriefPrevention");
        
        if (enabled) {
            plugin.getLogger().info(plugin.getMessages().getGriefPreventionEnabled());
        }
    }

    /**
     * Check if the player can use the elevator at the given location
     * Returns true if:
     * - GriefPrevention is not installed
     * - The location is not claimed
     * - The player owns the claim
     * - The player has accesstrust on the claim
     */
    public boolean canUseElevator(Player player, Location location) {
        // If GriefPrevention is not enabled, allow usage
        if (!enabled) {
            return true;
        }

        try {
            // Get the claim at this location
            Claim claim = GriefPrevention.instance.dataStore.getClaimAt(location, false, null);
            
            // If no claim exists, allow usage
            if (claim == null) {
                return true;
            }

            // Check if player is the owner
            if (claim.getOwnerID() != null && claim.getOwnerID().equals(player.getUniqueId())) {
                return true;
            }

            // Check if player has access trust
            // Access trust allows player to use buttons, levers, beds, etc.
            @SuppressWarnings("deprecation")
            String errorMessage = claim.allowAccess(player);
            
            // If errorMessage is null, the player has access
            return errorMessage == null;
            
        } catch (Exception e) {
            // If there's any error, log it and deny access to be safe
            plugin.getLogger().warning(plugin.getMessages().getErrorGriefPreventionCheck(e.getMessage()));
            return false;
        }
    }
}
