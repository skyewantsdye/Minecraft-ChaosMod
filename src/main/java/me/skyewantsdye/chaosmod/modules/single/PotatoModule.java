package me.skyewantsdye.chaosmod.modules.single;

import me.skyewantsdye.chaosmod.modules.ChaosModule;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PotatoModule extends ChaosModule {

    @Override
    public void onToggle() {
        Bukkit.getOnlinePlayers().forEach(this::performAction);
    }

    private void performAction(Player player) {
        for (int i = 0; i < player.getInventory().getContents().length; i++) {
            // Get the original item in the player's inventory.
            ItemStack originalItem = player.getInventory().getItem(i);
            // Make sure the item exists.
            if (originalItem == null) continue;
            // Create a new potato item, with that amount of items.
            ItemStack potato = new ItemStack(Material.POTATO, originalItem.getAmount());
            // Set the item to the new potato item.
            player.getInventory().setItem(i, potato);
        }

        // Send the player a message.
        player.sendMessage(ChatColor.LIGHT_PURPLE + "Every item in your inventory has been turned into a "
                + ChatColor.RED + "POTATO! >:D");
    }

    @Override
    public String getName() {
        return "Potato";
    }

}