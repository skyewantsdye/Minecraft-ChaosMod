package me.skyewantsdye.chaosmod.gui;

import me.skyewantsdye.chaosmod.ChaosPlugin;
import me.skyewantsdye.chaosmod.modules.ChaosModule;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.ChatPaginator;

import java.util.ArrayList;
import java.util.List;

public class ModuleUI extends GUI {

    public ModuleUI(Player player) {
        super("Chaos Mod - Events");

        for (ChaosModule module : ChaosPlugin.instance.moduleHandler.getModules()) {
            // Create the item.
            ItemStack itemStack = new ItemStack(module.itemMaterial(), 1);
            ItemMeta meta = itemStack.getItemMeta();
            // Set the display name.
            final List<ChaosModule> disabledModules = ChaosPlugin.instance.moduleHandler.disabledModules;
            meta.setDisplayName((disabledModules.contains(module) ?
                    ChatColor.RED : ChatColor.GREEN) + module.getName());

            // Hacky but oh well.
            String[] lines = ChatPaginator.wordWrap(module.description(), 40);
            ArrayList<String> lores = new ArrayList<>();
            for (String line : lines)
                lores.add(ChatColor.WHITE + line);

            if (player.hasPermission("chaos.command")) {
                lores.add("");
                lores.add(ChatColor.GRAY + "Use `" + module.getName().replaceAll(" ", "_").toUpperCase() + "` in /chaos!");
                lores.add(ChatColor.LIGHT_PURPLE + "Click to toggle!");
            }

            // Set the lores to the very hacky lore list.
            meta.setLore(lores);
            itemStack.setItemMeta(meta);
            if (player.hasPermission("chaos.command")) {
                addItem(itemStack, new ClickHandler() {
                    @Override
                    public void doClick(Player player, GUI gui) {

                        System.out.println(disabledModules.size());
                        System.out.println(ChaosPlugin.instance.moduleHandler.getModules().size());
                        if (disabledModules.size() + 1 == ChaosPlugin.instance.moduleHandler.getModules().size()) {
                            player.sendMessage("You can't disable any more modules! Please re-enable some modules, then try again.");
                            return;
                        }

                        if (disabledModules.contains(module))
                            disabledModules.remove(module);
                        else
                            disabledModules.add(module);
                    }
                });
            } else {
                addItem(itemStack);
            }
        }

        open(player);
    }

}