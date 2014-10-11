package spaceage.common.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileLiquidTank extends TileEntity implements IFluidHandler {
	
	public int tankSize = 4000;
	public FluidTank tank = new FluidTank(tankSize);

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if((resource == null) || (tank.getFluidAmount() == tank.getCapacity()) || ((tank.getFluidAmount() + resource.amount) > tank.getCapacity())) {
			return 0;
		} else if(tank.getFluidAmount() > 0) {
			if(resource != tank.getFluid()) {
				return 0;
			}
		}
		return this.tank.fill(resource, doFill);
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		if(resource == null) {
			return null;
		} else {
			return tank.drain(resource.amount, doDrain);
		}
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return tank.drain(maxDrain, doDrain);
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
		return new FluidTankInfo[] { tank.getInfo() };
	}
}
