package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.direction.Direction;
import org.jukeboxmc.block.type.AnvilDamage;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemAnvil;
import org.jukeboxmc.item.type.ItemToolType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockAnvil extends BlockWaterlogable {

    public BlockAnvil() {
        super( "minecraft:anvil" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        this.setDirection( player.getDirection().getRightDirection() );
        return super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
    }

    @Override
    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        player.openInventory( player.getAnvilInventory(), blockPosition );
        return true;
    }

    @Override
    public ItemAnvil toItem() {
        return new ItemAnvil( this.runtimeId );
    }

    @Override
    public BlockType getType() {
        return BlockType.ANVIL;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public double getHardness() {
        return 5;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }

    @Override
    public boolean canBreakWithHand() {
        return false;
    }

    public BlockAnvil setDamage( AnvilDamage damage ) {
        return this.setState( "damage", damage.name().toLowerCase() );
    }

    public AnvilDamage getDamage() {
        return this.stateExists( "damage" ) ? AnvilDamage.valueOf( this.getStringState( "damage" ) ) : AnvilDamage.UNDAMAGED;
    }

    public void setDirection( Direction direction ) {
        switch ( direction ) {
            case SOUTH:
                this.setState( "direction", 0 );
                break;
            case WEST:
                this.setState( "direction", 1 );
                break;
            case NORTH:
                this.setState( "direction", 2 );
                break;
            case EAST:
                this.setState( "direction", 3 );
                break;
        }
    }

    public Direction getDirection() {
        int value = this.stateExists( "direction" ) ? this.getIntState( "direction" ) : 0;
        switch ( value ) {
            case 0:
                return Direction.SOUTH;
            case 1:
                return Direction.WEST;
            case 2:
                return Direction.NORTH;
            default:
                return Direction.EAST;
        }
    }

}
