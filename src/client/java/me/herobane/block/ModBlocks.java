package me.herobane.block;

import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {
    // Existing block
    public static final Block FAKE_ANCHOR = registerBlock(
            new Block(AbstractBlock.Settings.create()
                    .nonOpaque() // block is translucent
                    .strength(-1.0f) // can't Break
                    .dropsNothing() // Does not drop items when broken
                    .replaceable() // Replaceable like a fern
                    .sounds(BlockSoundGroup.POWDER_SNOW))); //cuz uh idk, doesn't really need a sound

    // Helper methods for adding blocks
    private static Block registerBlock(Block block) {
        registerBlockItem(block);
        return Registry.register(Registries.BLOCK, Identifier.of("herosanchoroptimizer", "fake_anchor"), block);
    }

    private static void registerBlockItem(Block block) {
        Registry.register(Registries.ITEM, Identifier.of("herosanchoroptimizer", "fake_anchor"),
                new BlockItem(block, new Item.Settings()));
    }
}
