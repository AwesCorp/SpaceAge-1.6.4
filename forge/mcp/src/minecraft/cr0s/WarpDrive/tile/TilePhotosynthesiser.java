package cr0s.WarpDrive.tile;

import cr0s.WarpDrive.OxygenHelper;
import uedevkit.tile.TileElectricBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TilePhotosynthesiser extends TileEntity implements IFluidHandler {
	
	public int oxygenTankSize = 2000;
	public FluidTank oxygenTank = new FluidTank(OxygenHelper.fluidstack_OXYGEN, oxygenTankSize); 
	
	public int waterTankSize = 2000;
	public FluidTank waterTank = new FluidTank(new FluidStack(FluidRegistry.WATER, 0), waterTankSize);
	
	int WATER_PER_OXYGEN = 1;
	
	@Override
	public void updateEntity() {
		if(oxygenTank.getFluidAmount() != 0) {
			sendOxygenToPipes();
		}
		
		if(((this.worldObj.isDaytime()) && (this.worldObj.canBlockSeeTheSky(xCoord, yCoord, zCoord))) || (this.worldObj.getLightBrightness(xCoord, yCoord, zCoord) == 15)) {
			if(waterTank.getCapacity() >= WATER_PER_OXYGEN) {
				oxygenTank.fill(OxygenHelper.getOxygen(1), true);
				waterTank.drain(WATER_PER_OXYGEN, true);
			}
		}
	}

	public boolean sendOxygenToPipes() {
		TileEntity t = worldObj.getBlockTileEntity(xCoord, yCoord, zCoord);
		
		if(ForgeDirection.UP == t.) {
			
		}
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if(resource.isFluidEqual(OxygenHelper.fluidstack_OXYGEN)) {
			return this.oxygenTank.fill(resource, doFill);
		} else if(resource.isFluidEqual(new FluidStack(FluidRegistry.WATER, 0))) {
			return this.waterTank.fill(resource, doFill);
		}
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource,
			boolean doDrain) {
		if (OxygenHelper.fluidstack_OXYGEN.isFluidEqual(resource)) {
            return this.oxygenTank.drain(resource.amount, doDrain);
        } else if(new FluidStack(FluidRegistry.WATER, 0).isFluidEqual(resource)) {
        	return this.waterTank.drain(resource.amount, doDrain);
        }
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return oxygenTank.drain(maxDrain, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[] {oxygenTank.getInfo(), waterTank.getInfo()};
	}

}
