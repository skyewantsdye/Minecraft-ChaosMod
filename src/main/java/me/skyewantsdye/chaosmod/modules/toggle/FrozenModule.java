package me.skyewantsdye.chaosmod.modules.toggle;

import me.skyewantsdye.chaosmod.ChaosPlugin;
import me.skyewantsdye.chaosmod.TimedChaosModule;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;

import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;

public class FrozenModule extends TimedChaosModule {

    @Override
    public void onStart() {
        ChaosPlugin.instance.disabledAbilities.add("MOVEMENT");
        Bukkit.broadcastMessage(ChatColor.AQUA + "FROZEN!");
    }

    @Override
    public void onEnd() {
        ChaosPlugin.instance.disabledAbilities.remove("MOVEMENT");
        Bukkit.broadcastMessage(ChatColor.GOLD + "Move again if you dare...");
    }

    @Override
    public String getName() {
        return "Frozen";
    }

    @Override
    public Duration getDuration() {
        return Duration.of(20, SECONDS);
    }
}