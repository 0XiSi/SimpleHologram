package org.oxisi.simpleHologram.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.oxisi.simpleHologram.SimpleHologram;

public class RemoveHologramCommand implements CommandExecutor {

    private final SimpleHologram plugin;

    public RemoveHologramCommand(SimpleHologram plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage("§cUsage: /removeholo <id>");
            return false;
        }

        String id = args[0];
        plugin.getConfig().set("holograms." + id, null);
        plugin.saveConfig();

        plugin.removeHologramById(id);
        sender.sendMessage("§aHologram '" + id + "' removed!");

        return true;
    }
}
