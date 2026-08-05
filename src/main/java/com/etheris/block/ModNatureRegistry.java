package com.etheris.block;

import net.minecraft.block.Block;
import java.util.HashMap;
import java.util.Map;

public class ModNatureRegistry {
    public record NatureParams(float ether, float purity) {}

    private static final Map<Block, NatureParams> REGISTRY = new HashMap<>();

    public static void registerNatureValues(Block block, float ether, float purity) {
        REGISTRY.put(block, new NatureParams(ether, purity));
    }

    public static NatureParams getParams(Block block) {
        return REGISTRY.getOrDefault(block, new NatureParams(0.0f, 0.0f));
    }

    public static boolean isMagicNature(Block block) {
        return REGISTRY.containsKey(block);
    }
}
