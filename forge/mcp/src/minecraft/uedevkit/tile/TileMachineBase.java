package uedevkit.tile;

import java.util.EnumSet;

import universalelectricity.api.energy.EnergyStorageHandler;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;

public abstract class TileMachineBase extends TileElectricInventoryBase {
	
    /**
     * Used for blocks that can do anything - use energy, store energy, generate energy, everything. Put a 0 in place of the maxExtract variable.
     * @author SkylordJoel
     */
    public TileMachineBase(long capacity, long maxReceive, long maxExtract) {
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
        return EnumSet.allOf(ForgeDirection.class);
    }

    /** The electrical output direction.
     * 
     * @return The direction that electricity is output from the tile. Return null for no output. By default it will return an empty EnumSet. */
    public EnumSet<ForgeDirection> getOutputDirections() {
        return EnumSet.noneOf(ForgeDirection.class);
    }
    
	public long onExtractEnergy(ForgeDirection from, long extract, boolean doExtract) {
		return 0;
	}
}
