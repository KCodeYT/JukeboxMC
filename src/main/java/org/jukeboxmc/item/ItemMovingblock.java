package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockMovingblock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemMovingblock extends Item {

    public ItemMovingblock() {
        super ( "minecraft:moving_block" );
    }

    @Override
    public BlockMovingblock getBlock() {
        return new BlockMovingblock();
    }
}
