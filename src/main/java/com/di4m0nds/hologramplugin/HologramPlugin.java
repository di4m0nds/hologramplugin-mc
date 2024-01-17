package com.di4m0nds.hologramplugin;

import com.di4m0nds.hologramplugin.commands.CreateHologramCommand;
import com.di4m0nds.hologramplugin.commands.RemoveHologramCommand;
import com.di4m0nds.hologramplugin.listeners.HologramClickListener;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class HologramPlugin extends JavaPlugin {

    private static HologramPlugin instance;

    private static final String DEFAULT_CONFIG_FILE = "config.yml";
    private static final String DEFAULT_COMMAND_CREATE_HOLOGRAM = "setholo";
    private static final String DEFAULT_COMMAND_DELETE_HOLOGRAM = "rmholo";

    private final double DEFAULT_JUMP_SIZE= 0.26;
    private final String DEFAULT_CONFIG_INITIAL_POSITION = "initial-position-y";
    private final String DEFAULT_CONFIG_SPACE_BETWEEN_LINES = "space-between-lines-y";

    @Override
    public void onEnable() {
        getLogger().fine("HologramPlugin v" + getDescription().getVersion() + " has been enabled!");

        // Set the plugin instance for access from other classes
        instance = this;
        
        saveDefaultConfig();
        reloadConfig();
        saveResource(DEFAULT_CONFIG_FILE, false); // just in case

        // Register commands
        final CreateHologramCommand createHologramCommand = new CreateHologramCommand(instance);
        final RemoveHologramCommand removeHologramCommand = new RemoveHologramCommand();

        registerCommand(DEFAULT_COMMAND_CREATE_HOLOGRAM, createHologramCommand);
        registerCommand(DEFAULT_COMMAND_DELETE_HOLOGRAM, removeHologramCommand);

        // Register listeners
        final HologramClickListener hologramClickListener = new HologramClickListener();
        registerListener(hologramClickListener);
    }

    @Override
    public void onDisable() {
        getLogger().info("HologramPlugin v" + getDescription().getVersion() + " has been disabled!");
    }

    /**
     * Register a command with the specified label and executor.
     *
     * @param label    The label of the command.
     * @param executor The executor for the command.
     */
    private void registerCommand(String label, CommandExecutor executor) {
        final PluginCommand command = getCommand(label);
        if (command != null) {
            command.setExecutor(executor);
        } else {
            getLogger().warning("Failed to register command '" + label + "'. Command not found!");
        }
    }

    /**
     * Register a listener with the server.
     *
     * @param listener The listener to register.
     */
    private void registerListener(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, instance);
    }

    /**
     * Retrieve the instance of the HologramPlugin.
     *
     * @return (HologramPlugin) The instance of the HologramPlugin.
     */
    public static HologramPlugin getInstance() {
        return instance;
    }

    /**
     * Gets the initial Y position for holographic displays from the configuration.
     *
     * @param plugin (HologramPlugin) The HologramPlugin instance.
     * @return (double) The initial Y position for holographic displays.
     */
    public double getInitialPositionY(HologramPlugin instance) {
      if(instance.getConfig().get(instance.DEFAULT_CONFIG_INITIAL_POSITION) != null) {
        return Double.valueOf(
                instance.getConfig()
                  .get(instance.DEFAULT_CONFIG_INITIAL_POSITION)
                  .toString()
               );
      } else {
        return instance.DEFAULT_JUMP_SIZE;
      }
    }

    /**
     * Gets the space between lines for holographic displays from the configuration.
     *
     * @param plugin (HologramPlugin) The HologramPlugin instance.
     * @return (double) The space between lines for holographic displays.
     */
    public double getSpaceBetweenLinesY(HologramPlugin instance) {
      if(instance.getConfig().get(instance.DEFAULT_CONFIG_SPACE_BETWEEN_LINES) != null) {
        return Double.valueOf(
                instance.getConfig()
                  .get(instance.DEFAULT_CONFIG_SPACE_BETWEEN_LINES)
                  .toString()
               );
      } else {
        return instance.DEFAULT_JUMP_SIZE;
      }
    }
}
