package me.skyewantsdye.chaosmod.modules.single;

import me.skyewantsdye.chaosmod.ChaosModule;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class RandomItemModule extends ChaosModule {

    @Override
    public void onToggle() {
        Bukkit.getOnlinePlayers().forEach(this::performAction);
    }

    private void performAction(Player player) {
        // Get all of the materials in the game.
        Material[] materials = Material.values();
        // Get a random material, from the list of materials.
        Material material = materials[ThreadLocalRandom.current().nextInt(materials.length)];
        // Make sure the material is an item, so the player always gets something.
        while (!material.isItem())
            material = materials[ThreadLocalRandom.current().nextInt(materials.length)];
        // Add the material to the player's inventory, with a random amount.
        player.getInventory().addItem(new ItemStack(material, ThreadLocalRandom.current().nextInt(64)));
        // Send the player a message telling them what they got.
        player.sendMessage(ChatColor.AQUA + String.format("Have fun with your %s!",
                WordUtils.capitalizeFully(material.name().replaceAll("_", " "))));
    }

    @Override
    public String getName() {
        return "Random Item";
    }

}