package com.di4m0nds.hologramplugin.utils;

import com.di4m0nds.hologramplugin.HologramPlugin;

import org.bukkit.NamespacedKey;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public final class HologramUtils {
  public static ArmorStand findHologramEntityByTag(Player player, String hologramId) {
    for (Entity entity : player.getWorld().getEntities()) {
      if (entity.getType() == EntityType.ARMOR_STAND) {
        final ArmorStand armorStand = (ArmorStand) entity;
  
        // Retrieve the hologramId from the metadata
        final PersistentDataContainer dataContainer = armorStand.getPersistentDataContainer();
        final String storedHologramId = dataContainer.get(
          new NamespacedKey(HologramPlugin.getInstance(), "hologramId"),
          PersistentDataType.STRING
        );
  
        // Check if the hologramId matches the desired value
        if (hologramId.equals(storedHologramId)) {
          return armorStand; // Found the desired hologram
        }
      }
    }

    return null; // Hologram with the specified ID not found
  }
}
