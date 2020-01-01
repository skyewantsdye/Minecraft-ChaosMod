package me.skyewantsdye.chaosmod.modules.toggle;

import me.skyewantsdye.chaosmod.modules.TimedChaosModule;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;

public class PumpkinHeadModule extends TimedChaosModule {

    @Override
    public void onStart() {
        ItemStack pumpkinItem = new ItemStack(Material.CARVED_PUMPKIN);
        ItemMeta meta = pumpkinItem.getItemMeta();

        if (meta == null) {
            Bukkit.getLogger().severe("Couldn't make a pumpkin for... some reason.");
            return;
        }

        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        pumpkinItem.setItemMeta(meta);

        Bukkit.getOnlinePlayers().forEach(o -> o.getInventory().setHelmet(pumpkinItem));
    }

    @Override
    public void onEnd() {
        Bukkit.getOnlinePlayers().forEach(player -> player.getInventory().setHelmet(null));
    }

    @Override
    public String getName() {
        return "Pumpkin Head";
    }

    @Override
    public Duration getDuration() {
        return Duration.of(50, SECONDS);
    }

    @Override
    public Material itemMaterial() {
        return Material.PUMPKIN;
    }

    @Override
    public String description() {
        return "Hope you don't mind looking a little orange!";
    }

}