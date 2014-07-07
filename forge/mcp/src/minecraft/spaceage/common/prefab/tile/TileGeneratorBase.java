package spaceage.common.prefab.tile;

import java.util.EnumSet;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;
import uedevkit.tile.TileElectricInventoryBase;

public abstract class TileGeneratorBase extends TileElectricInventoryBase {

	public TileGeneratorBase(int capacity, int maxReceive, int maxExtract) {
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}
	
    /** The electrical input direction.
     * 
     * @return The direction that electricity is entered into the tile. Return null for no input. By default you can accept power from all sides. */
    public EnumSet<ForgeDirection> getInputDirections()
    {
        return EnumSet.noneOf(ForgeDirection.class);
    }

    /** The electrical output direction.
     * 
     * @return The direction that electricity is output from the tile. Return null for no output. By default it will return an empty EnumSet. */
    public EnumSet<ForgeDirection> getOutputDirections()
    {
        return EnumSet.allOf(ForgeDirection.class);
    }
	
}
