package me.skyewantsdye.chaosmod.modules;

import com.google.common.reflect.ClassPath;
import me.skyewantsdye.chaosmod.ChaosPlugin;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class ModuleHandler {

    private final Map<String, ChaosModule> modulesByName = new HashMap<>();
    private boolean running;
    private List<String> lastModules = new ArrayList<>();

    @SuppressWarnings({"deprecation", "UnstableApiUsage"})
    public ModuleHandler() {
        final ClassLoader loader = getClass().getClassLoader();
        try {
            // Get the classpath from our class loader.
            ClassPath classPath = ClassPath.from(loader);
            // Get all of the classes in the modules package.
            for (ClassPath.ClassInfo topLevelClass : classPath.getTopLevelClassesRecursive("me.skyewantsdye.chaosmod.modules")) {
                String clazzName = topLevelClass.getName();
                // Make sure the class is in either the single or toggle subpackage.
                if (!clazzName.startsWith("me.skyewantsdye.chaosmod.modules.single.") &&
                        !clazzName.contains("me.skyewantsdye.chaosmod.modules.toggle."))
                    continue;
                // Load the class
                Class<?> moduleClazz = topLevelClass.load();
                // Make sure the class isn't null
                if (moduleClazz == null) continue;
                // Make a new instance of the class.
                Object module = moduleClazz.newInstance();
                // Make sure the class is a ChaosModule, then register it.
                if (module instanceof ChaosModule)
                    modulesByName.put(((ChaosModule) module).getName().replace(" ", "_").toUpperCase(), (ChaosModule) module);
            }
        } catch (IOException | InstantiationException | IllegalAccessException ex) {
            ex.printStackTrace();
        }

        modulesByName.keySet().forEach(System.out::println);
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
            Bukkit.getScheduler().scheduleSyncRepeatingTask(ChaosPlugin.instance, this::activateRandomModule,
                    0, 30 * 20);
            Bukkit.getScheduler().scheduleSyncRepeatingTask(ChaosPlugin.instance, ChaosPlugin.instance.bossBarTask, 0 ,20);
            // Set running to true.
            running = true;
        }
    }

    public void updateScoreboard() {
        while (lastModules.size() > 4)
            lastModules.remove(0);

        if (Bukkit.getScoreboardManager() == null) {
            Bukkit.getLogger().severe("Couldn't get the ScoreboardManager!");
            return;
        }

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        if (scoreboard.getObjective("Events") == null)
            scoreboard.registerNewObjective("Events", "dummy", "Events");
        Objective objective = scoreboard.getObjective("Events");

        if (objective == null) {
            Bukkit.getLogger().severe("Couldn't get the `Events` objective!");
            return;
        }

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

    public Collection<ChaosModule> getModules() {
        return modulesByName.values();
    }

    public void end() {
        if (Bukkit.getOnlinePlayers().isEmpty()) {
            Bukkit.getScheduler().cancelTasks(ChaosPlugin.instance);
            running = false;
        }
    }

}