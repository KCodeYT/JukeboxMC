package org.jukeboxmc.block;

import com.nukkitx.protocol.bedrock.data.LevelEventType;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.direction.Direction;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.type.ItemToolType;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class BlockDoor extends BlockWaterlogable {

    public BlockDoor( String identifier ) {
        super( identifier );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        Block block = world.getBlock( placePosition );
        this.setDirection( Direction.fromAngle( player.getYaw() ) );

        BlockDoor blockAbove = this.newDoor();
        blockAbove.setLocation( new Location( world, placePosition.add( 0, 1, 0 ) ) );
        blockAbove.setDirection( this.getDirection() );
        blockAbove.setUpperBlock( true );
        blockAbove.setOpen( false );

        Block blockLeft = world.getBlock( placePosition ).getSide( player.getDirection().getLeftDirection() );

        if ( blockLeft.getIdentifier().equalsIgnoreCase( this.identifier ) ) {
            blockAbove.setDoorHinge( true );
            this.setDoorHinge( true );
        } else {
            blockAbove.setDoorHinge( false );
            this.setDoorHinge( false );
        }

        this.setUpperBlock( false );
        this.setOpen( false );

        world.setBlock( placePosition.add( 0, 1, 0 ), blockAbove, 0 );
        world.setBlock( placePosition, this, 0 );

        if ( block.isWater() ) {
            world.setBlock( placePosition.add( 0, 1, 0 ), block, 1 );
            world.setBlock( placePosition, block, 1 );
        }
        return true;
    }

    @Override
    public boolean onBlockBreak( Vector breakPosition ) {
        Block block = this.world.getBlock( breakPosition, 1 );
        if ( this.isUpperBlock() ) {
            this.world.setBlock( this.location.subtract( 0, 1, 0 ), block , 0);
            this.world.setBlock( this.location.subtract( 0, 1, 0 ), new BlockAir(), 1 );
        } else {
            this.world.setBlock( this.location.add( 0, 1, 0 ), block, 0 );
            this.world.setBlock( this.location.add( 0, 1, 0 ), new BlockAir(), 1 );
        }
        this.world.setBlock( this.location, block, 0 );
        this.world.setBlock( this.location, new BlockAir(), 1 );
        return true;
    }

    @Override
    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        this.setOpen( !this.isOpen() );
        this.world.sendLevelEvent( this.location, LevelEventType.SOUND_DOOR_OPEN, 0 );
        return true;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public double getHardness() {
        return 3;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.AXE;
    }

    public abstract BlockDoor newDoor();

    public BlockDoor setOpen( boolean value ) {
       return this.setState( "open_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isOpen() {
        return this.stateExists( "open_bit" ) && this.getByteState( "open_bit" ) == 1;
    }

    public BlockDoor setUpperBlock( boolean value ) {
       return this.setState( "upper_block_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isUpperBlock() {
        return this.stateExists( "upper_block_bit" ) && this.getByteState( "upper_block_bit" ) == 1;
    }

    public BlockDoor setDoorHinge( boolean value ) {
        return this.setState( "door_hinge_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isDoorHinge() {
        return this.stateExists( "door_hinge_bit" ) && this.getByteState( "door_hinge_bit" ) == 1;
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
