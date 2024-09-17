package org.oxisi.simpleHologram;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.oxisi.simpleHologram.Commands.HologramCommand;
import org.oxisi.simpleHologram.Commands.HologramUtils;
import org.oxisi.simpleHologram.Commands.ReloadHoloCommand;
import org.oxisi.simpleHologram.Commands.RemoveHologramCommand;

import java.util.List;
import java.util.Set;

import static org.oxisi.simpleHologram.Commands.HologramUtils.createHologram;

public class SimpleHologram extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        getCommand("createholo").setExecutor(new HologramCommand(this));
        getCommand("removeholo").setExecutor(new RemoveHologramCommand(this));
        getCommand("reloadholo").setExecutor(new ReloadHoloCommand(this));

        loadAllHolograms();

        getLogger().info("Hologram plugin enabled!");
    }

    @Override
    public void onDisable() {
        HologramUtils.removeAllHolograms();
        getLogger().info("Hologram plugin disabled!");
    }

    public void loadAllHolograms() {
        FileConfiguration config = this.getConfig();

        // Check if the "holograms" section exists
        if (config.getConfigurationSection("holograms") == null) {
            getLogger().warning("No holograms found in the configuration file.");
            return;
        }

        ConfigurationSection hologramsSection = config.getConfigurationSection("holograms");
        for (String holoId : hologramsSection.getKeys(false)) {

            ConfigurationSection holoSection = hologramsSection.getConfigurationSection(holoId);
            List<String> lines = holoSection.getStringList("lines");
            Location location = getLocationFromConfig(holoSection.getString("location"));

            createHologram(holoId, location, lines);
            getLogger().info("Loaded hologram: " + holoId);
        }
    }

    private Location getLocationFromConfig(String locationString) {
        // Example format: "world,100,65,200,0,0"
        String[] parts = locationString.split(",");

        if (parts.length != 6) {
            throw new IllegalArgumentException("Location string is not correctly formatted: " + locationString);
        }

        String worldName = parts[0];
        double x = parseDouble(parts[1]);
        double y = parseDouble(parts[2]);
        double z = parseDouble(parts[3]);
        float yaw = (float) parseDouble(parts[4]);
        float pitch = (float) parseDouble(parts[5]);

        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            throw new IllegalArgumentException("World not found: " + worldName);
        }

        return new Location(world, x, y, z, yaw, pitch);
    }

    private double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Failed to parse double value: " + value, e);
        }
    }

    public void removeHologramById(String id) {
        HologramUtils.removeHologramById(id);
    }
}
