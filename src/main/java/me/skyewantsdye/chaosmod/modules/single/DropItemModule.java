package me.skyewantsdye.chaosmod.modules.single;

import me.skyewantsdye.chaosmod.ChaosModule;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DropItemModule extends ChaosModule {

    @Override
    public void onToggle() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

            // Make sure the player has an item in their hand.
            if (itemInMainHand.getType() == Material.AIR) {
                // Tell the player they didn't lose an item.
                player.sendMessage(ChatColor.GRAY + "You have been saved from losing an item for now...");
                continue;
            }

            // Remove the item from the player's main hand.
            player.getInventory().remove(itemInMainHand);
            // Drop the item in the world.
            player.getWorld().dropItemNaturally(player.getLocation(), itemInMainHand);
            // Send the player a message saying what item was dropped.
            player.sendMessage(ChatColor.GRAY + "Oh no... where did your " + ChatColor.ITALIC
                    + WordUtils.capitalizeFully(itemInMainHand.getType().name().replaceAll("_", " ")
                    + ChatColor.GRAY + " go..."));
        }
    }

    @Override
    public String getName() {
        return "Drop";
    }
}