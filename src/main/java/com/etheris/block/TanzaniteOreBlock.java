package com.etheris.block;

import net.minecraft.block.Block;
import net.minecraft.block.AbstractBlock;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class TanzaniteOreBlock extends Block {
    private final UniformIntProvider experienceDropped;

    /**
     TODO(Артур):
     * Блок танзанітової руди з випадінням предмету при видобутку
     * Параметри:
     * - settings: конфігурація блоку (твердість, звуки, тощо)
     * - experienceDropped: кількість досвіду, що випадає (використовуємо діапазон)
     */
    public TanzaniteOreBlock(AbstractBlock.Settings settings, UniformIntProvider experienceDropped) {
        super(settings);
        this.experienceDropped = experienceDropped;
    }

    public UniformIntProvider getExperienceDropped() {
        return experienceDropped;
    }
}