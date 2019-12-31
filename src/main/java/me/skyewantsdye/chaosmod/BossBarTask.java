package me.skyewantsdye.chaosmod;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

public class BossBarTask implements Runnable {

    public int secondsPassed = 0;
    public double progress = 0;
    public BossBar bossBar;

    public BossBarTask() {
        bossBar = Bukkit.createBossBar("Chaos Timer", BarColor.RED, BarStyle.SEGMENTED_20);
    }

    @Override
    public void run() {
        bossBar.setProgress(progress);
        if (secondsPassed % 3 != 0)
            progress += .05;

        secondsPassed += 1;

        if (secondsPassed == 30) {
            secondsPassed = 0;
            progress = 0;
        }
    }

}