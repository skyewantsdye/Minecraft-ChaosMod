package me.skyewantsdye.chaosmod.modules;

import org.bukkit.Material;

public abstract class ChaosModule {

    public abstract String getName();

    public void onToggle() {
        System.out.println(String.format("The module %s doesn't have an action bound...", getName()));
    }

    public Material itemMaterial() {
        return Material.BARRIER;
    }

    public String description() {
        return "This is a placeholder description, it will be updated soon.";
    }

}