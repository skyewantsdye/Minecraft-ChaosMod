package me.skyewantsdye.chaosmod;

public abstract class ChaosModule {

    public abstract String getName();

    public void onToggle() {
        System.out.println(String.format("The module %s doesn't have an action bound...", getName()));
    }

}