package com.di4m0nds.hologramplugin.listeners;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
/**
 * HologramClickListener is an event listener for handling player interactions with entities.
 */
public class HologramClickListener implements Listener {
  /**
   * Handles the PlayerInteractAtEntityEvent, triggered when a player interacts with an entity.
   *
   * @param event The event triggered when a player interacts with an entity.
   */
  @EventHandler
  public void onPlayerInteractEntity(PlayerInteractAtEntityEvent event) {
    Entity clickedEntity = event.getRightClicked();
    Player player = event.getPlayer();

    // Check if the clicked entity is an ARMOR_STAND and has the hologramId tag
    if (clickedEntity instanceof ArmorStand) {
      ArmorStand armorStand = (ArmorStand) clickedEntity;
      player.sendMessage("§a§l\u2705 §r§aYou interacted with a hologram (ID: #§e§l" + armorStand.getEntityId() + "§a).");
    }
  }
}
