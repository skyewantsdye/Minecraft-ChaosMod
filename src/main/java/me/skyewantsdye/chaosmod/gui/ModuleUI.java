package me.skyewantsdye.chaosmod.gui;

import me.skyewantsdye.chaosmod.ChaosPlugin;
import me.skyewantsdye.chaosmod.modules.ChaosModule;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.ChatPaginator;

import java.util.ArrayList;

public class ModuleUI extends GUI{

    public ModuleUI(Player player) {
        super("Chaos Mod - Events");

        for (ChaosModule modules : ChaosPlugin.instance.moduleHandler.getModules()) {
            // Create the item.
            ItemStack itemStack = new ItemStack(modules.itemMaterial(), 1);
            ItemMeta meta = itemStack.getItemMeta();
            // Set the display name.
            meta.setDisplayName(ChatColor.GREEN + modules.getName());

            // Hacky but oh well.
            String[] lines = ChatPaginator.wordWrap(modules.description(), 40);
            ArrayList<String> lores = new ArrayList<>();
            for (String line : lines)
                lores.add(ChatColor.WHITE + line);

            // Set the lores to the very hacky lore list.
            meta.setLore(lores);
            itemStack.setItemMeta(meta);
            addItem(itemStack);
        }

        open(player);
    }

}