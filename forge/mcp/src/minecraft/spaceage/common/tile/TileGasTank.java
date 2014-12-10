package spaceage.common.tile;

import spaceage.common.prefab.tile.FluidGuiHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidContainerItem;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.ItemFluidContainer;

public class TileGasTank extends TileEntity implements ISidedInventory, IFluidHandler, FluidGuiHelper {
	
	public int tankSize = 4000;
	public FluidTank tank = new FluidTank(tankSize);
	
	ItemStack[] inventory = new ItemStack[2];

	IFluidContainerItem fluidContainerInterface;
	ItemFluidContainer fluidCont;
	
	@Override
	public void updateEntity() {
		if(!worldObj.isRemote) {
			if(this.inventory[0] != null && this.inventory[0].stackSize != 0) {
				ItemStack stack1 = inventory[0].copy();
				ItemStack stack2 = null;
				
				if(inventory[1] != null) {
					stack2 = inventory[1].copy();
				}
				
				boolean iFluid = ((stack1.getItem() instanceof IFluidContainerItem) && (stack2 != null) && (stack2.getItem() instanceof IFluidContainerItem));
				boolean fluid = ((stack1.getItem() instanceof ItemFluidContainer) && (stack2 != null) && (stack2.getItem() instanceof ItemFluidContainer));
				boolean registry = ((FluidContainerRegistry.isContainer(stack1)) && (stack2 != null) && (FluidContainerRegistry.isContainer(stack2)));

				if((inventory[1] == null) || (inventory[1].stackSize == 0) || (iFluid == true) || (fluid == true) || (registry == true)) {
					Item container = stack2.getItem();
					Item container2 = stack1.getItem();
					
					ItemStack containerStack = new ItemStack(container,stack2.stackSize + 1);
					ItemStack containerStack2 = new ItemStack(container2,stack1.stackSize - 2);
					
					if(iFluid) {
						this.tank.fill(fluidContainerInterface.getFluid(stack1), true);
					} else if(fluid) {
						this.tank.fill(fluidCont.getFluid(stack1), true);
					} else if(registry) {
						this.tank.fill(FluidContainerRegistry.getFluidForFilledItem(containerStack), true);
					}
					
					inventory[1] = containerStack;
					inventory[0] = containerStack2;
				}
			}
		} 
	}
	
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if((resource == null) || (tank.getFluidAmount() == tank.getCapacity()) || ((tank.getFluidAmount() + resource.amount) > tank.getCapacity())) {
			return 0;
		} else if(tank.getFluidAmount() > 0) {
			if(resource != tank.getFluid()) {
				return 0;
			}
		} else if(resource.getFluid().isGaseous() == true) {
			return this.tank.fill(resource, doFill);
		}
		return 0;
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

	@Override
    public int getFluidRemainingScaled(int prgPixels) {
        Double result = Long.valueOf(this.tank.getFluidAmount()).doubleValue() * Long.valueOf(prgPixels).doubleValue() / Long.valueOf(tank.getCapacity()).doubleValue();
        return result.intValue();
    }

	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return inventory[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		ItemStack itemstack = getStackInSlot(i);
		
		if(itemstack != null) {
			if(itemstack.stackSize <= j) {
				setInventorySlotContents(i, null);
			}else {
				itemstack = itemstack.splitStack(j);
				onInventoryChanged();
			}
		}
		
		return itemstack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		ItemStack item = getStackInSlot(i);
		setInventorySlotContents(i, null);
		return item;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inventory[i] = itemstack;
		
		if(itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
		
		onInventoryChanged();
	}

	@Override
	public String getInvName() {
		return "liquidTank.name";
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return true;
	}

	@Override
	public void openChest() {
		
	}

	@Override
	public void closeChest() {
		
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		switch(slot) {
			case 0:
				return ((stack.getItem() instanceof IFluidContainerItem) || (stack.getItem() instanceof ItemFluidContainer) || (FluidContainerRegistry.isContainer(stack)));
		}
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		// TODO Need help...
		return null;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		switch(side) {
			case 1:
				return isItemValidForSlot(slot, stack);
		}
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		switch(slot) {
			case 1: 
				switch(side) {
					case 0:
						return false;
					case 1:
						return false;
					default:
						return true;
				}
		}
		return false;
	}
}
