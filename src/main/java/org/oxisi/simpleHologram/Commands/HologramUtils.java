package org.oxisi.simpleHologram.Commands;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HologramUtils {

    private static final Map<String, List<ArmorStand>> holograms = new HashMap<>();


    public static void createHologram(String id, Location location, List<String> lines) {
        removeHologramById(id);
        List<ArmorStand> stands = spawnHologramLines(location, lines);
        holograms.put(id, stands);
    }


    public static void removeHologramById(String id) {
        if (holograms.containsKey(id)) {
            for (ArmorStand stand : holograms.get(id)) {
                stand.remove();
            }
            holograms.remove(id);
        }
    }


    public static void removeAllHolograms() {
        for (List<ArmorStand> stands : holograms.values()) {
            for (ArmorStand stand : stands) {
                stand.remove();
            }
        }
        holograms.clear();
    }

    private static List<ArmorStand> spawnHologramLines(Location location, List<String> lines) {
        List<ArmorStand> stands = new ArrayList<>();
        double lineHeight = 0.3; // spacing
        Location topLocation = location.clone().add(0, (lines.size() - 1) * lineHeight, 0);

        for (int i = 0; i < lines.size(); i++) {
            String text = lines.get(i);
            Location lineLocation = topLocation.clone().subtract(0, i * lineHeight, 0);
            ArmorStand hologram = (ArmorStand) lineLocation.getWorld().spawnEntity(lineLocation, EntityType.ARMOR_STAND);
            hologram.setCustomName("§6" + text);
            hologram.setCustomNameVisible(true);
            hologram.setGravity(false);
            hologram.setVisible(false);
            hologram.setMarker(true);
            stands.add(hologram);
        }
        return stands;
    }

    public static List<String> parseArguments(String[] args) {
        List<String> lines = new ArrayList<>();
        StringBuilder currentLine = new StringBuilder();
        boolean insideQuotes = false;

        for (String arg : args) {
            if (arg.startsWith("\"")) {
                insideQuotes = true;
                currentLine.append(arg.substring(1));
            } else if (arg.endsWith("\"")) {
                insideQuotes = false;
                currentLine.append(" ").append(arg.substring(0, arg.length() - 1));
                lines.add(currentLine.toString().trim());
                currentLine.setLength(0);
            } else if (insideQuotes) {
                currentLine.append(" ").append(arg);
            } else {
                lines.add(arg);
            }
        }

        if (currentLine.length() > 0 && insideQuotes) {
            lines.add(currentLine.toString().trim());
        }

        return lines;
    }
}
