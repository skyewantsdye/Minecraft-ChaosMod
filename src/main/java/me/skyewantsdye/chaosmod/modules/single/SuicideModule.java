package me.skyewantsdye.chaosmod.modules.single;

import me.skyewantsdye.chaosmod.modules.ChaosModule;
import org.bukkit.Bukkit;
import org.bukkit.Material;

public class SuicideModule extends ChaosModule {

    @Override
    public void onToggle() {
        Bukkit.getOnlinePlayers().forEach(player -> player.setHealth(0));
    }

    @Override
    public String getName() {
        return "Suicide";
    }

    @Override
    public Material itemMaterial() {
        return Material.TNT;
    }

    @Override
    public String description() {
        return "Kills the player.";
    }

}