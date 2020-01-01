package me.skyewantsdye.chaosmod.modules.single;

import me.skyewantsdye.chaosmod.modules.ChaosModule;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.util.Vector;

import java.util.concurrent.ThreadLocalRandom;

public class FireworksModule extends ChaosModule {

    @Override
    public void onToggle() {
        final int diameter = 10;
        Bukkit.getOnlinePlayers().forEach(player -> {
            // Get the location and world.
            Location location = player.getLocation();
            World world = location.getWorld();

            for (int i = 0; i < 3; i++) {
                // Get a random location, within 10 blocks of the player.
                Location newLocation = location.add(new Vector(Math.random()-0.5, 0, Math.random()-0.5).multiply(diameter)).clone();
                // Spawn a new firework, and get it's meta.
                Firework firework = (Firework) world.spawnEntity(newLocation, EntityType.FIREWORK);
                FireworkMeta fireworkMeta = firework.getFireworkMeta();

                // Set the power to a random number between 1 and 3.
                fireworkMeta.setPower(ThreadLocalRandom.current().nextInt(3));
                // Add 5 random effects to the firework.
                for (int i1 = 0; i1 < 5; i1++) {
                    fireworkMeta.addEffect(getRandomEffect());
                }
                // Set the meta of the firework.
                firework.setFireworkMeta(fireworkMeta);
            }
        });
    }

    public FireworkEffect getRandomEffect() {
        FireworkEffect.Builder builder = FireworkEffect.builder();

        // Generate a random RGB color for the color.
        builder.withColor(Color.fromRGB(ThreadLocalRandom.current().nextInt(255), ThreadLocalRandom.current().nextInt(255), ThreadLocalRandom.current().nextInt(255)));
        // Randomize whether or not it should flicker.
        builder.flicker(ThreadLocalRandom.current().nextBoolean());
        builder.with(FireworkEffect.Type.values()[ThreadLocalRandom.current().nextInt(FireworkEffect.Type.values().length)]);
        // Generate a random RGB color for the fade.
        builder.withFade(Color.fromRGB(ThreadLocalRandom.current().nextInt(255), ThreadLocalRandom.current().nextInt(255), ThreadLocalRandom.current().nextInt(255)));

        return builder.build();
    }

    @Override
    public String getName() {
        return "Fireworks";
    }

    @Override
    public Material itemMaterial() {
        return Material.FIREWORK_ROCKET;
    }

    @Override
    public String description() {
        return "Hope you weren't trying to be stealthy or anything...";
    }

}