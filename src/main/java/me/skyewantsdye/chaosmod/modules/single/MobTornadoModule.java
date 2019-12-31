package me.skyewantsdye.chaosmod.modules.single;

import me.skyewantsdye.chaosmod.ChaosModule;
import me.skyewantsdye.chaosmod.ChaosPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class MobTornadoModule extends ChaosModule {

    @Override
    public void onToggle() {
        EntityType[] entities = EntityType.values();
        Bukkit.getOnlinePlayers().forEach(o -> drawTornado(o.getLocation(), 1.5f, 0.03f, entities[ThreadLocalRandom.current().nextInt(entities.length)]));
    }

    private void drawTornado(Location location, float radius, float increase, EntityType entityType) {
        float y = (float) location.getY();
        Map<Entity, Location> frozenEntities = new HashMap<>();
        for (double t = 0; t < 5; t += 0.5) {
            float x = radius * (float) Math.sin(t);
            float z = radius * (float) Math.cos(t);
            Location entityLocation = location.clone();
            entityLocation.add(x, 0, z);
            entityLocation.setY(y);
            Entity entity = location.getWorld().spawnEntity(entityLocation, entityType);
            frozenEntities.put(entity, entityLocation);
            y += .75f;
            radius += increase;
        }

        int taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(ChaosPlugin.instance, () -> {
            frozenEntities.forEach(Entity::teleport);
        }, 0, 1);

        Bukkit.getScheduler().runTaskLaterAsynchronously(ChaosPlugin.instance, () -> {
            frozenEntities.clear();
            Bukkit.getScheduler().cancelTask(taskId);
        }, 5 * 20);
    }

    @Override
    public String getName() {
        return "Mob Tornado";
    }

}