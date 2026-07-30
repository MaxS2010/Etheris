// Utility class for converting player magic stats to/from a Map for saving and networking.
package com.etheris.player;

import java.util.HashMap;
import java.util.Map;

// Serializes and deserializes player magic stats for saving or network sync
public class PlayerMagicSerializer {
    public static final String ETHER = "Ether";
    public static final String MAX_ETHER = "MaxEther";
    public static final String PURITY = "Purity";
    public static final String MANIPULATION = "Manipulation";

    public static Map<String, Float> writeToMap(PlayerMagicData data) {
        if (data == null) {
            return new HashMap<>();
        }

        Map<String, Float> values = new HashMap<>();
        values.put(ETHER, data.getEther());
        values.put(MAX_ETHER, data.getMaxEther());
        values.put(PURITY, data.getPurity());
        values.put(MANIPULATION, data.getManipulation());
        return values;
    }

    public static void readFromMap(PlayerMagicData data, Map<String, Float> values) {
        if (data == null || values == null) {
            return;
        }

// Set MAX_ETHER first so ETHER isn't clamped by capacity limits in setEther()
        if (values.containsKey(MAX_ETHER)) {
            data.setMaxEther(values.get(MAX_ETHER));
        }

        if (values.containsKey(ETHER)) {
            data.setEther(values.get(ETHER));
        }

        if (values.containsKey(PURITY)) {
            data.setPurity(values.get(PURITY));
        }

        if (values.containsKey(MANIPULATION)) {
            data.setManipulation(values.get(MANIPULATION));
        }
    }

// Fallback default values if any keys are missing from the map
    public static PlayerMagicData fromMap(Map<String, Float> values) {
        PlayerMagicData data = new PlayerMagicData(0f, 100f, 0f, 0f);
        readFromMap(data, values);
        return data;
    }
}