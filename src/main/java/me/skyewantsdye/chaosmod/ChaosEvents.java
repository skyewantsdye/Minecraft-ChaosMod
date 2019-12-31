package me.skyewantsdye.chaosmod;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class ChaosEvents implements Listener {

    public ArrayList<UUID> fallDamangeIgnore = new ArrayList<>();

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            UUID uuid = event.getEntity().getUniqueId();
            if (fallDamangeIgnore.contains(uuid)) {
                event.setCancelled(true);
                fallDamangeIgnore.remove(uuid);
            }
        }
    }

    @EventHandler
    public void onMovement(PlayerMoveEvent event) {
        if (!ChaosPlugin.instance.disabledAbilities.contains("MOVEMENT")) return;
        if (event.getTo().getBlockX() == event.getFrom().getBlockX()
                && event.getTo().getBlockY() == event.getFrom().getBlockY()
                && event.getTo().getBlockZ() == event.getFrom().getBlockZ()) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        ChaosPlugin.instance.moduleHandler.begin();
        ChaosPlugin.instance.bossBarTask.bossBar.addPlayer(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        ChaosPlugin.instance.moduleHandler.end();
        ChaosPlugin.instance.bossBarTask.bossBar.removePlayer(event.getPlayer());
    }

}