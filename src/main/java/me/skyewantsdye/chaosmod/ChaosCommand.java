package me.skyewantsdye.chaosmod;

import me.skyewantsdye.chaosmod.modules.ChaosModule;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ChaosCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("Please provide a valid module name, modules: ");
            StringBuilder modules = new StringBuilder();
            for (String modulesName : ChaosPlugin.instance.moduleHandler.getModulesNames()) {
                modules.append(modulesName).append(", ");
            }
            String s = modules.toString();
            sender.sendMessage(s.substring(0, s.length() - 2));
            return true;
        }

        if (!sender.hasPermission("chaos.command")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission! :(");
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