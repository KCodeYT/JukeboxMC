package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockChest;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemChest extends Item implements Burnable {

    public ItemChest() {
        super ( "minecraft:chest" );
    }

    @Override
    public BlockChest getBlock() {
        return new BlockChest();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 300 );
    }
}
