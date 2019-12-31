package me.skyewantsdye.chaosmod;

import me.skyewantsdye.chaosmod.gui.ModuleUI;
import me.skyewantsdye.chaosmod.modules.ChaosModule;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChaosCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0 || !sender.hasPermission("chaos.command")) {
            if (sender instanceof Player)
                new ModuleUI((Player) sender);
            return true;
        }

        ChaosModule module = ChaosPlugin.instance.moduleHandler.getModule(args[0]);
        if (module == null) {
            sender.sendMessage("Invalid module!");
            return true;
        }

        module.onToggle();
        return true;
    }

}