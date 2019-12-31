package me.skyewantsdye.chaosmod.modules.single;

import me.skyewantsdye.chaosmod.modules.ChaosModule;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.concurrent.ThreadLocalRandom;

public class TimeChangeModule extends ChaosModule {

    @Override
    public void onToggle() {
        // Generate a random time for the world.
        long time = ThreadLocalRandom.current().nextLong(24000);
        // Set the time in each world.
        for (World world : Bukkit.getWorlds()) {
            world.setTime(time);
        }
    }

    @Override
    public String getName() {
        return "Time Change";
    }

    @Override
    public Material itemMaterial() {
        return Material.CLOCK;
    }

    @Override
    public String description() {
        return "Randomly changes the time.";
    }

}