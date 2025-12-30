package com.kubota6646.ironblockelevator;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Messages {

    private final IronBlockElevator plugin;
    private FileConfiguration messageConfig;
    private File messageFile;

    public Messages(IronBlockElevator plugin) {
        this.plugin = plugin;
        loadMessages();
    }

    private void loadMessages() {
        // Create plugin data folder if it doesn't exist
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        // Create message.yml file if it doesn't exist
        messageFile = new File(plugin.getDataFolder(), "message.yml");
        if (!messageFile.exists()) {
            plugin.saveResource("message.yml", false);
        }

        // Load the message configuration
        messageConfig = YamlConfiguration.loadConfiguration(messageFile);

        // Load defaults from the jar
        InputStream defaultStream = plugin.getResource("message.yml");
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(
                    new InputStreamReader(defaultStream, StandardCharsets.UTF_8));
            messageConfig.setDefaults(defaultConfig);
        }
    }

    public void reload() {
        loadMessages();
    }

    public String getMessage(String path) {
        String message = messageConfig.getString(path);
        if (message == null) {
            plugin.getLogger().warning("Message not found: " + path);
            return path;
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public String getMessage(String path, String... replacements) {
        String message = getMessage(path);
        
        // Apply replacements
        for (int i = 0; i < replacements.length; i += 2) {
            if (i + 1 < replacements.length) {
                message = message.replace("{" + replacements[i] + "}", replacements[i + 1]);
            }
        }
        
        return message;
    }

    // Convenience methods for common messages
    public String getPluginEnabled() {
        return getMessage("plugin-enabled");
    }

    public String getPluginDisabled() {
        return getMessage("plugin-disabled");
    }

    public String getGriefPreventionEnabled() {
        return getMessage("griefprevention-enabled");
    }

    public String getCommandHeader() {
        return getMessage("command.header");
    }

    public String getCommandVersion(String version) {
        return getMessage("command.version", "version", version);
    }

    public String getCommandAuthor(String author) {
        return getMessage("command.author", "author", author);
    }

    public String getCommandReloadUsage(String label) {
        return getMessage("command.reload-usage", "label", label);
    }

    public String getCommandNoPermission() {
        return getMessage("command.no-permission");
    }

    public String getCommandReloadSuccess() {
        return getMessage("command.reload-success");
    }

    public String getCommandUnknown(String label) {
        return getMessage("command.unknown-command", "label", label);
    }

    public String getCooldownMessage(String time) {
        return getMessage("cooldown.message", "time", time);
    }

    public String getDebugCannotUseGriefPrevention(String player) {
        return getMessage("debug.cannot-use-griefprevention", "player", player);
    }

    public String getDebugTargetProtected(String player) {
        return getMessage("debug.target-protected", "player", player);
    }

    public String getDebugMovingUp(String player) {
        return getMessage("debug.moving-up", "player", player);
    }

    public String getDebugMovingDown(String player) {
        return getMessage("debug.moving-down", "player", player);
    }

    public String getDebugCooldown(String player, String time) {
        return getMessage("debug.cooldown", "player", player, "time", time);
    }

    public String getErrorGriefPreventionCheck(String error) {
        return getMessage("error.griefprevention-check", "error", error);
    }
}
