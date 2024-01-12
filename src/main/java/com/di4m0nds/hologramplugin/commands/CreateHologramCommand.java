package com.di4m0nds.hologramplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;

import com.di4m0nds.hologramplugin.HologramPlugin;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

/**
 * HologramCommand
 * 
 * This class represents the command executor for creating holograms in the Minecraft server.
 */
public class CreateHologramCommand implements CommandExecutor {

  private double lineSize;
  private HologramPlugin plugin;

  /**
   * Constructs a HologramCommand instance with a specified line size.
   * 
   * @param lineSize (double) Line size for the space of each line.
   */
    public CreateHologramCommand(double lineSize, HologramPlugin plugin) {
      this.lineSize = lineSize;
      this.plugin = plugin;
  }
  

  /**
   * Executes the hologram creation command.
   * 
   * @param sender (CommandSender) The command sender (player or console).
   * @param cmd (Command) The executed command.
   * @param label (String) The alias used for the command.
   * @param args (String[]) The arguments provided with the command.
   * @return (boolean) True if the command was handled successfully.
   */
  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!(sender instanceof Player)) {
      return true;
    }
    Player player = (Player) sender;

    if (args.length == 0) {
      player.sendMessage("§c§lOops! It looks like you forgot something...");
      player.sendMessage("§cTo create a hologram, use: /setholo <text>");
      player.sendMessage("§cFor multiple lines, try: /setholo \"<line1>\" \"<line2>\" ...");

      return true;
    }

    String text = String.join(" ", args);
    String[] splitted = text.split("\"");
    List<String> lines = new ArrayList<String>();

    for (String splittedItem : splitted) {
      if (splittedItem != "\"" && !splittedItem.isEmpty() && !splittedItem.isBlank()) {
        lines.add(splittedItem);
      }
    }

    //double jump = this.lineSize;
    double jump = (this.plugin.getConfig().get("initial-position") != null)
        ? Double.valueOf(
            this.plugin.getConfig().get("initial-position").toString()
          )
        : lineSize;

    for (int i = 1; i <= lines.size(); i++) {
      jump = createHologramEntity(player, lines, i, jump);
    }

    return true;
  }

  /**
   * Creates a holographic line entity for the specified player with the given text.
   * 
   * @param player (Player) The player for whom the holographic line is created.
   * @param lines (List<String>) The list of lines containing holographic text.
   * @param index (int) The index of the current line in the list.
   * @param jump (double) The vertical (Y) displacement for positioning the holographic line.
   * @return (double) The updated vertical displacement for the next holographic line.
   */
  private double createHologramEntity(Player player, List<String> lines, int index, double jump) {
    String pointingString = lines.get(index -1);

    ArmorStand line = (ArmorStand) player.getWorld().spawnEntity(
      player.getLocation().add(0, -(jump), 0),
      EntityType.ARMOR_STAND
    );

    // jump += this.lineSize; 
    jump += (this.plugin.getConfig().get("space-between-lines") != null)
        ? Double.valueOf(
            this.plugin.getConfig().get("space-between-lines").toString()
          )
        : lineSize;

    line.setVisible(false);
    line.setCustomNameVisible(true);
    line.setGravity(false);
    line.setCustomName(pointingString.replaceAll("&", "§"));

    // float yaw = 45.5f;
    // float pitch = 90.0f;
    // line.setRotation(yaw, pitch);

    player.sendMessage(
      ChatColor.YELLOW + "Hologram ID: " +
      "#" + ChatColor.BOLD + line.getEntityId() +
      ChatColor.RESET + " | Value: " + ChatColor.GREEN + "\"" + pointingString + "\""
    );

    return jump;
  }
}
