package me.skyewantsdye.chaosmod.modules;

import me.skyewantsdye.chaosmod.ChaosPlugin;
import org.bukkit.Bukkit;

import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;

public abstract class TimedChaosModule extends ChaosModule {

    public void onStart() {}
    public void onEnd() {}

    @Override
    public void onToggle() {
        onStart();
        // Schedule the onEnd() function, so that the TimedModule will end.
        Bukkit.getScheduler().runTaskLaterAsynchronously(ChaosPlugin.instance, this::onEnd,
                // Seconds * 20, to get the time in ticks.
                getDuration().getSeconds() * 20);
    }

    public Duration getDuration() {
        return Duration.of(30, SECONDS);
    }

}