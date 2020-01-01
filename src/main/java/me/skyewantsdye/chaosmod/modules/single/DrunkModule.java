package me.skyewantsdye.chaosmod.modules.single;

import me.skyewantsdye.chaosmod.modules.ChaosModule;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.ThreadLocalRandom;

public class DrunkModule extends ChaosModule {

    @Override
    public void onToggle() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            ThreadLocalRandom current = ThreadLocalRandom.current();
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, current.nextInt(20) * 20, current.nextInt(5)));
            player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, current.nextInt(20) * 20, current.nextInt(5)));
            player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, current.nextInt(20) * 20, current.nextInt(5)));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, current.nextInt(20) * 20, current.nextInt(5)));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, current.nextInt(20) * 20, current.nextInt(5)));
        });
    }

    @Override
    public String getName() {
        return "Drunk";
    }

    @Override
    public Material itemMaterial() {
        return Material.MAGMA_CREAM;
    }

    @Override
    public String description() {
        return "Just... don't fall on the floor.";
    }

}