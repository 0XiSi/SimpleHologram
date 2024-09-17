package org.oxisi.simpleHologram.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.oxisi.simpleHologram.SimpleHologram;

public class ReloadHoloCommand implements CommandExecutor {

    private final SimpleHologram plugin;

    public ReloadHoloCommand(SimpleHologram plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        plugin.reloadConfig();
        HologramUtils.removeAllHolograms();
        plugin.loadAllHolograms();
        sender.sendMessage("§aAll holograms reloaded from config!");
        return true;
    }
}
