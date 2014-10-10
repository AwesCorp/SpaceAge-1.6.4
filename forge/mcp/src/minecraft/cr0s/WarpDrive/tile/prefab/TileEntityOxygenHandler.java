package cr0s.WarpDrive.tile.prefab;

import cr0s.WarpDrive.OxygenHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidHandler;

public abstract class TileEntityOxygenHandler extends TileEntity implements IFluidHandler {

	public int oxygenTankSize = 2000;
	public FluidTank oxygenTank = new FluidTank(OxygenHelper.fluidstack_OXYGEN, oxygenTankSize); 
	
}
