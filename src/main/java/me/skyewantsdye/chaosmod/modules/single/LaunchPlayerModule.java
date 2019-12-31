package me.skyewantsdye.chaosmod.modules.single;

import me.skyewantsdye.chaosmod.modules.ChaosModule;
import me.skyewantsdye.chaosmod.ChaosPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.util.Vector;

import java.util.concurrent.ThreadLocalRandom;

public class LaunchPlayerModule extends ChaosModule {

    @Override
    public void onToggle() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            // Get a random height of 0-128 blocks to launch the player.
            int randomHeight = ThreadLocalRandom.current().nextInt(128);
            // Set the player's velocity so that they are launched up that amount.
            player.setVelocity(new Vector(0, randomHeight, 0));
            // Ignore fall damage for the player.
            ChaosPlugin.instance.chaosEvents.fallDamangeIgnore.add(player.getUniqueId());
        });
    }

    @Override
    public String getName() {
        return "Launch";
    }

    @Override
    public Material itemMaterial() {
        return Material.SLIME_BLOCK;
    }

    @Override
    public String description() {
        return "Launches the player up to 128 blocks in the air! Wheeee!";
    }

}