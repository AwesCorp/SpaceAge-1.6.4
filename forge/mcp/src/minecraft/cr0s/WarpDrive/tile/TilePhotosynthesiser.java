package cr0s.WarpDrive.tile;

import cr0s.WarpDrive.OxygenHelper;
import cr0s.WarpDrive.tile.prefab.TileEntityOxygenHandler;
import spaceage.common.prefab.tile.FluidGuiHelper;
import uedevkit.tile.TileElectricBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TilePhotosynthesiser extends TileEntityOxygenHandler implements IFluidHandler, FluidGuiHelper {
	
	//public int oxygenTankSize = 2000;
	//public FluidTank oxygenTank = new FluidTank(OxygenHelper.fluidstack_OXYGEN, oxygenTankSize); 
	
	public int waterTankSize = 2000;
	public FluidTank waterTank = new FluidTank(new FluidStack(FluidRegistry.WATER, 0), waterTankSize);
	
	int WATER_PER_OXYGEN = 1;
	
	@Override
	public void updateEntity() {
		if(oxygenTank.getFluidAmount() > 0) {
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
		int amount = this.oxygenTank.getFluidAmount();
		
		TileEntity up = worldObj.getBlockTileEntity(xCoord, yCoord + 1, zCoord);
		TileEntity down = worldObj.getBlockTileEntity(xCoord, yCoord - 1, zCoord);
		TileEntity left = worldObj.getBlockTileEntity(xCoord + 1, yCoord, zCoord);
		TileEntity right = worldObj.getBlockTileEntity(xCoord - 1, yCoord, zCoord);
		TileEntity front = worldObj.getBlockTileEntity(xCoord, yCoord, zCoord + 1);
		TileEntity back = worldObj.getBlockTileEntity(xCoord, yCoord, zCoord - 1);
		
		if(up instanceof TileEntityOxygenHandler) {
			if(((TileEntityOxygenHandler)up).oxygenTank.getCapacity() > amount + ((TileEntityOxygenHandler)up).oxygenTank.getFluidAmount()) {
				int amountFixed = (((TileEntityOxygenHandler) up).oxygenTank.getCapacity() - ((TileEntityOxygenHandler) up).oxygenTank.getFluidAmount());
				int amountNotToPush = amount - amountFixed;
				int amountToPush = amount - amountNotToPush;
				
				this.oxygenTank.drain(amountToPush, true);
				((TileEntityOxygenHandler) up).oxygenTank.fill(new FluidStack(OxygenHelper.OXYGEN, amountToPush), true);
				
				return true;
			} else {
				this.oxygenTank.drain(amount, true);
				((TileEntityOxygenHandler) up).oxygenTank.fill(new FluidStack(OxygenHelper.OXYGEN, amount), true);
				
				return true;
			}
		}
		
		if(down instanceof TileEntityOxygenHandler) {
			if(((TileEntityOxygenHandler)down).oxygenTank.getCapacity() > amount + ((TileEntityOxygenHandler)down).oxygenTank.getFluidAmount()) {
				int amountFixed = (((TileEntityOxygenHandler) down).oxygenTank.getCapacity() - ((TileEntityOxygenHandler) down).oxygenTank.getFluidAmount());
				int amountNotToPush = amount - amountFixed;
				int amountToPush = amount - amountNotToPush;
				
				this.oxygenTank.drain(amountToPush, true);
				((TileEntityOxygenHandler) down).oxygenTank.fill(new FluidStack(OxygenHelper.OXYGEN, amountToPush), true);
				
				return true;
			} else {
				this.oxygenTank.drain(amount, true);
				((TileEntityOxygenHandler) down).oxygenTank.fill(new FluidStack(OxygenHelper.OXYGEN, amount), true);
				
				return true;
			}
		}
		
		if(left instanceof TileEntityOxygenHandler) {
			if(((TileEntityOxygenHandler)left).oxygenTank.getCapacity() > amount + ((TileEntityOxygenHandler)left).oxygenTank.getFluidAmount()) {
				int amountFixed = (((TileEntityOxygenHandler) left).oxygenTank.getCapacity() - ((TileEntityOxygenHandler) left).oxygenTank.getFluidAmount());
				int amountNotToPush = amount - amountFixed;
				int amountToPush = amount - amountNotToPush;
				
				this.oxygenTank.drain(amountToPush, true);
				((TileEntityOxygenHandler) left).oxygenTank.fill(new FluidStack(OxygenHelper.OXYGEN, amountToPush), true);
				
				return true;
			} else {
				this.oxygenTank.drain(amount, true);
				((TileEntityOxygenHandler) left).oxygenTank.fill(new FluidStack(OxygenHelper.OXYGEN, amount), true);
				
				return true;
			}
		}
		
		if(right instanceof TileEntityOxygenHandler) {
			if(((TileEntityOxygenHandler)right).oxygenTank.getCapacity() > amount + ((TileEntityOxygenHandler)right).oxygenTank.getFluidAmount()) {
				int amountFixed = (((TileEntityOxygenHandler) right).oxygenTank.getCapacity() - ((TileEntityOxygenHandler) right).oxygenTank.getFluidAmount());
				int amountNotToPush = amount - amountFixed;
				int amountToPush = amount - amountNotToPush;
				
				this.oxygenTank.drain(amountToPush, true);
				((TileEntityOxygenHandler) right).oxygenTank.fill(new FluidStack(OxygenHelper.OXYGEN, amountToPush), true);
				
				return true;
			} else {
				this.oxygenTank.drain(amount, true);
				((TileEntityOxygenHandler) right).oxygenTank.fill(new FluidStack(OxygenHelper.OXYGEN, amount), true);
				
				return true;
			}
		}
		
		if(front instanceof TileEntityOxygenHandler) {
			if(((TileEntityOxygenHandler)front).oxygenTank.getCapacity() > amount + ((TileEntityOxygenHandler)front).oxygenTank.getFluidAmount()) {
				int amountFixed = (((TileEntityOxygenHandler) front).oxygenTank.getCapacity() - ((TileEntityOxygenHandler) front).oxygenTank.getFluidAmount());
				int amountNotToPush = amount - amountFixed;
				int amountToPush = amount - amountNotToPush;
				
				this.oxygenTank.drain(amountToPush, true);
				((TileEntityOxygenHandler) front).oxygenTank.fill(new FluidStack(OxygenHelper.OXYGEN, amountToPush), true);
				
				return true;
			} else {
				this.oxygenTank.drain(amount, true);
				((TileEntityOxygenHandler) front).oxygenTank.fill(new FluidStack(OxygenHelper.OXYGEN, amount), true);
				
				return true;
			}
		}
		
		if(back instanceof TileEntityOxygenHandler) {
			if(((TileEntityOxygenHandler)back).oxygenTank.getCapacity() > amount + ((TileEntityOxygenHandler)back).oxygenTank.getFluidAmount()) {
				int amountFixed = (((TileEntityOxygenHandler) back).oxygenTank.getCapacity() - ((TileEntityOxygenHandler) back).oxygenTank.getFluidAmount());
				int amountNotToPush = amount - amountFixed;
				int amountToPush = amount - amountNotToPush;
				
				this.oxygenTank.drain(amountToPush, true);
				((TileEntityOxygenHandler) back).oxygenTank.fill(new FluidStack(OxygenHelper.OXYGEN, amountToPush), true);
				
				return true;
			} else {
				this.oxygenTank.drain(amount, true);
				((TileEntityOxygenHandler) back).oxygenTank.fill(new FluidStack(OxygenHelper.OXYGEN, amount), true);
				
				return true;
			}
		}
		
		return false;
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

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		waterTank.writeToNBT(tag);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		waterTank.readFromNBT(tag);
	}

	@Override
	public int getFluidRemainingScaled(int capacityScaledTo) {
        Double result = Long.valueOf(this.oxygenTank.getFluidAmount()).doubleValue() * Long.valueOf(capacityScaledTo).doubleValue() / Long.valueOf(oxygenTankSize).doubleValue();
        return result.intValue();
	}
}
