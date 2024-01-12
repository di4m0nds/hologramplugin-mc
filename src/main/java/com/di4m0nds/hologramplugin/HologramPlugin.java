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
    private static double DEFAULT_LINE_SIZE = 0.26;

    @Override
    public void onEnable() {
        getLogger().fine("HologramPlugin v" + getDescription().getVersion() + " has been enabled!");

        // Set the plugin instance for access from other classes
        instance = this;
        
        saveDefaultConfig();
        reloadConfig();
        saveResource("config.yml", false); // just in case

        // Register commands
        registerCommand("setholo", new CreateHologramCommand(DEFAULT_LINE_SIZE, instance));
        registerCommand("rmholo", new RemoveHologramCommand());

        // Register listeners
        registerListener(new HologramClickListener());
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
        PluginCommand command = getCommand(label);
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
}
