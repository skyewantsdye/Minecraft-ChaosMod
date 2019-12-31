package me.skyewantsdye.chaosmod;

import me.skyewantsdye.chaosmod.modules.single.*;
import me.skyewantsdye.chaosmod.modules.toggle.FrozenModule;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.*;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class ModuleHandler {

    private final Map<String, ChaosModule> modulesByName = new HashMap<>();
    private boolean running;
    private List<String> lastModules = new ArrayList<>();

    public ModuleHandler() {
        modulesByName.put("TimeChange", new TimeChangeModule());
        modulesByName.put("RandomItem", new RandomItemModule());
        modulesByName.put("Potato", new PotatoModule());
        modulesByName.put("DropItem", new DropItemModule());
        modulesByName.put("LaunchPlayer", new LaunchPlayerModule());
        modulesByName.put("Frozen", new FrozenModule());
        modulesByName.put("Tornado", new MobTornadoModule());
    }

    public ChaosModule getModule(String name) {
        return modulesByName.get(name);
    }

    public void activateModule(String name) {
        getModule(name).onToggle();
    }

    public void activateRandomModule() {
        // Get a random module from the modules list.
        ChaosModule module = (ChaosModule) modulesByName.values().toArray()[
                ThreadLocalRandom.current().nextInt(modulesByName.size())];
        // Toggle the module.
        module.onToggle();
        // Add the module to the last used modules.
        lastModules.add(module.getName());
        // Update the scoreboard.
        updateScoreboard();
    }

    public void begin() {
        // Make sure it's not already running.
        if (!running) {
            // Schedule the random module timer and chaos bossbar.
            Bukkit.getScheduler().scheduleSyncRepeatingTask(ChaosPlugin.instance, () -> activateRandomModule(),
                    0, 30 * 20);
            Bukkit.getScheduler().scheduleSyncRepeatingTask(ChaosPlugin.instance, ChaosPlugin.instance.bossBarTask, 0 ,20);
            // Set running to true.
            running = true;
        }
    }

    public void updateScoreboard() {
        while (lastModules.size() > 4)
            lastModules.remove(0);

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        if (scoreboard.getObjective("Events") == null)
            scoreboard.registerNewObjective("Events", "dummy", "Events");
        Objective objective = scoreboard.getObjective("Events");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        for (ChaosModule value : modulesByName.values()) {
            scoreboard.resetScores(value.getName());
        }
        for (int i = 0; i < lastModules.size(); i++) {
            Score score = objective.getScore(lastModules.get(i));
            score.setScore(i + 1);
        }

        Bukkit.getOnlinePlayers().forEach(o -> o.setScoreboard(scoreboard));
    }

    public Collection<String> getModulesNames() {
        return modulesByName.keySet();
    }

    public void end() {
        if (Bukkit.getOnlinePlayers().isEmpty()) {
            Bukkit.getScheduler().cancelTasks(ChaosPlugin.instance);
            running = false;
        }
    }

}