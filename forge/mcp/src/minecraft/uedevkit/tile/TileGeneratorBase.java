package uedevkit.tile;

import java.util.EnumSet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;
import spaceage.common.SpaceAgeCore;
import spaceage.common.block.BlockGenerator;
import universalelectricity.api.energy.EnergyStorageHandler;

/**
 * The base tile entity for generators, due to the redo of the power input and output directions. Extend this class for a generator.
 * @author SkylordJoel
 */

public abstract class TileGeneratorBase extends TileElectricInventoryBase {
	
    /**
     * Used for blocks that can do anything - use energy, store energy, generate energy, everything. Put a 0 in place of the maxReceive variable.
     * @author SkylordJoel
     */
    public TileGeneratorBase(long capacity, long maxReceive, long maxExtract) {
        energy = new EnergyStorageHandler(capacity, maxReceive, maxExtract);
    }

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	/** The electrical input direction.
     * 
     * @return The direction that electricity is entered into the tile. Return null for no input. By default you can accept power from all sides. */
    public EnumSet<ForgeDirection> getInputDirections() {
        return EnumSet.noneOf(ForgeDirection.class);
    }

    /** The electrical output direction.
     * 
     * @return The direction that electricity is output from the tile. Return null for no output. By default it will return an empty EnumSet. */
    public EnumSet<ForgeDirection> getOutputDirections() {
        return EnumSet.allOf(ForgeDirection.class);
    }
    
	public int getLightValue() {
		return 0;
	}
	
	public long onReceiveEnergy(ForgeDirection from, long receive, boolean doReceive) {
		return 0;
	}
}
