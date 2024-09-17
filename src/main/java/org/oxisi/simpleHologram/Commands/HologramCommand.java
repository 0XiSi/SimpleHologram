package org.oxisi.simpleHologram.Commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.oxisi.simpleHologram.SimpleHologram;

import java.util.List;

public class HologramCommand implements CommandExecutor {

    private final SimpleHologram plugin;

    public HologramCommand(SimpleHologram plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length < 2) {
                player.sendMessage("§cUsage: /createholo <id> <text lines...>");
                return false;
            }

            String id = args[0];
            String[] textArgs = new String[args.length - 1];
            System.arraycopy(args, 1, textArgs, 0, args.length - 1);

            List<String> lines = HologramUtils.parseArguments(textArgs);
            plugin.getConfig().set("holograms." + id + ".lines", lines);
            plugin.getConfig().set("holograms." + id + ".location", locationToString(player.getLocation()));
            plugin.saveConfig();

            plugin.removeHologramById(id);
            HologramUtils.createHologram(id, player.getLocation(), lines);

            player.sendMessage("§aHologram '" + id + "' created and saved to config!");

            return true;
        }
        return false;
    }
    private String locationToString(Location location) {
        return String.format("%s,%f,%f,%f,%f,%f",
                location.getWorld().getName(),
                location.getX(),
                location.getY(),
                location.getZ(),
                location.getYaw(),
                location.getPitch());
    }
}
