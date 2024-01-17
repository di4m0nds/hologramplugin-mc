package com.di4m0nds.hologramplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;

/**
 * RemoveHologramCommand
 * 
 * This class represents the command executor for removing holograms in the Minecraft server.
 */
public final class RemoveHologramCommand implements CommandExecutor {

  /**
   * Executes the hologram deletion command.
   * 
   * @param sender (CommandSender) The command sender (player or console).
   * @param cmd (Command) The executed command.
   * @param label (String) The alias used for the command.
   * @param args (String[]) The arguments provided with the command.
   * @return (boolean) True if the command was handled successfully.
   */
  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!(sender instanceof Player) && args == null) {
      return true;
    }

    final Player player = (Player) sender;

    if (args.length == 0) {
        player.sendMessage("§c§l\u26D4 §r§cPlease provide a hologram ID. Usage: /rmholo <hologramID>");
        return true;
    }

    String hologramTag = args[0].replaceAll("#", "");

    for (Entity entity : player.getWorld().getEntities()) {
      if (entity instanceof ArmorStand) {
        if (String.valueOf(entity.getEntityId()).equals(hologramTag)) {
          entity.remove();
          player.sendMessage("§a§l\u2705 §r§aHologram removed with ID: §e#§l" + entity.getEntityId());

          return true;
        }
      }
    }

    player.sendMessage("§c§l\u26D4 §r§cHologram with ID §e#§l" + hologramTag + " §cnot found.");
    return true;
  }

}
