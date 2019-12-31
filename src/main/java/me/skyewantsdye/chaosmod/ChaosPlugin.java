package me.skyewantsdye.chaosmod;

import me.skyewantsdye.chaosmod.modules.ModuleHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public final class ChaosPlugin extends JavaPlugin implements Listener {

    public static ChaosPlugin instance;
    public ModuleHandler moduleHandler;
    public ChaosEvents chaosEvents;
    public BossBarTask bossBarTask;

    public ArrayList<String> disabledAbilities = new ArrayList<>();
    public static ArrayList<UUID> ignoreDamange = new ArrayList<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        this.bossBarTask = new BossBarTask();
        this.moduleHandler = new ModuleHandler();
        this.chaosEvents = new ChaosEvents();
        getServer().getPluginManager().registerEvents(chaosEvents, this);

        Objects.requireNonNull(getCommand("chaos")).setExecutor(new ChaosCommand());
    }

    @Override
    public void onDisable() {
        this.moduleHandler.end();
        this.moduleHandler = null;
        instance = null;
    }

}