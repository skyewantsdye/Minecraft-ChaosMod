package me.skyewantsdye.chaosmod;

import me.skyewantsdye.chaosmod.gui.GUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
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

        // By ConnorLinfoot!
        if (GUI.ignoreClosing.contains(event.getPlayer().getUniqueId()))
            GUI.ignoreClosing.remove(event.getPlayer().getUniqueId());
        if (GUI.guis.containsKey(event.getPlayer().getUniqueId()))
            GUI.guis.remove(event.getPlayer().getUniqueId());
        if (GUI.pageTracker.containsKey(event.getPlayer().getUniqueId()))
            GUI.pageTracker.remove(event.getPlayer().getUniqueId());
    }

    /** The InventoryEvents were created by ConnorLinfoot! */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (GUI.guis.containsKey(event.getWhoClicked().getUniqueId())) {
            event.setCancelled(true);
            if (event.getCurrentItem() == null || event.getRawSlot() != event.getSlot() || event.getCurrentItem().getType().equals(Material.AIR))
                return;
            GUI gui = GUI.guis.get(event.getWhoClicked().getUniqueId());
            gui.handleClick((Player) event.getWhoClicked(), event.getSlot());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onInventoryClose(InventoryCloseEvent event) {
        if (GUI.ignoreClosing.contains(event.getPlayer().getUniqueId())) {
            GUI.ignoreClosing.remove(event.getPlayer().getUniqueId());
            return;
        }
        if (GUI.guis.containsKey(event.getPlayer().getUniqueId()))
            GUI.guis.remove(event.getPlayer().getUniqueId());
        if (GUI.pageTracker.containsKey(event.getPlayer().getUniqueId()))
            GUI.pageTracker.remove(event.getPlayer().getUniqueId());
    }

}