package spaceage.common.tile;

import java.util.EnumSet;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.Icon;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import spaceage.common.SpaceAgeCore;
import spaceage.common.block.BlockGenerator;
import spaceage.common.prefab.tile.FluidGuiHelper;
import uedevkit.tile.TileElectricInventoryBase;
import uedevkit.tile.TileElectricInventoryNetworked;
import universalelectricity.api.energy.EnergyStorageHandler;

/**
 * The Geothermal Turbine tile entity. Implemented in metadata block form in {@link BlockGenerator}.
 * @author SkylordJoel
 */

public class TileHeatGenerator extends TileElectricInventoryNetworked implements IFluidHandler, FluidGuiHelper {
	
	public int waterTankSize = 4000;
	public FluidTank waterTank = new FluidTank(waterTankSize); 
	
	protected boolean creatingSteam = false; 
	
	public TileHeatGenerator() {
		super(SpaceAgeCore.HEAT_CAPACITY, 100);
		inventory = new ItemStack[1];
	}
    
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		boolean powered = ((worldObj.getBlockId(xCoord, yCoord - 1, zCoord) == Block.lavaStill.blockID) || (worldObj.getBlockId(xCoord, yCoord - 1, zCoord) == Block.fire.blockID)) && (creatingSteam == true);
		
        this.creatingSteam = false;
        
        if (this.isFunctioning()) {
            if (this.waterTank != null && this.waterTank.getFluid() != null && this.waterTank.getFluidAmount() > 1 && this.waterTank.getFluid().isFluidEqual(new FluidStack(FluidRegistry.WATER, 1000))) {
                this.waterTank.drain(1, true);
                this.creatingSteam = true;
            } else {
				FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(inventory[0]);

				if(fluid != null && fluid.fluidID == FluidRegistry.WATER.getID()) {
					if(waterTank.getFluid() == null || waterTank.getFluid().amount+fluid.amount <= waterTank.getCapacity()) {
						waterTank.fill(fluid, true);
                //this.waterTank.fill(new FluidStack(FluidRegistry.WATER, 1000), true);
            }
        }
    }
            this.updateTexture();
}
		
		if(powered == true/* && creatingSteam == true*/) {
			this.energy.receiveEnergy(SpaceAgeCore.HEAT_ENERGY, true);
			markDistributionUpdate |= produceReturn() > 0;
			
			Long produced = Long.valueOf(SpaceAgeCore.HEAT_ENERGY);
			this.produceEnergy(produced);
			this.produce();
		}
	}
	
    private void updateTexture() {
		
	}

	public boolean isFunctioning() {
		return (!this.energy.isFull());
	}

	public boolean canFunction()
    {
    	boolean powered = (worldObj.getBlockId(xCoord, yCoord - 1, zCoord) == Block.lavaStill.blockID) && (creatingSteam == true);
        return powered == true;
    }

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if(resource.fluidID == FluidRegistry.WATER.getID()) {
			return waterTank.fill(resource, doFill);
		}
		
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource,
			boolean doDrain) {
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return fluid != null && fluid == FluidRegistry.WATER;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[] {waterTank.getInfo()};
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		//TODO Need help on this...
		return null;
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j) {
		return isItemValidForSlot(i, itemstack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		if(FluidContainerRegistry.isEmptyContainer(itemstack)) {
			return true;
		}
	return false;
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
		return "geothermalTurbine.name";
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return (FluidContainerRegistry.getFluidForFilledItem(itemstack) != null && FluidContainerRegistry.getFluidForFilledItem(itemstack).fluidID == FluidRegistry.WATER.getID());
	}

	@Override
	public void openChest() {
		
	}

	@Override
	public void closeChest() {
		
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt); //TODO
		waterTank.readFromNBT(nbt);
		
		NBTTagList items = nbt.getTagList("Items");
		
		for(int i = 0; i < items.tagCount(); i++) {
			NBTTagCompound item = (NBTTagCompound)items.tagAt(i);
			int slot = item.getByte("Slot");
			
			if(slot >= 0 && slot < getSizeInventory()) {
				setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(item));
			}
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt); //TODO
		waterTank.writeToNBT(nbt);
		
		NBTTagList items = new NBTTagList();
		
		for(int i = 0; i < getSizeInventory(); i++) {
			ItemStack stack = getStackInSlot(i);
			
			if(stack != null) {
				NBTTagCompound item = new NBTTagCompound();
				item.setByte("Slot", (byte)i);
				stack.writeToNBT(nbt);
				items.appendTag(item);
			}
		}
		
		nbt.setTag("Items", items);
		
	}
	
	@Override
    public int getFluidRemainingScaled(int prgPixels) {
        Double result = Long.valueOf(this.waterTank.getFluidAmount()).doubleValue() * Long.valueOf(prgPixels).doubleValue() / Long.valueOf(waterTankSize).doubleValue();
        return result.intValue();
    }
	
	//@Override
	public int getLightValue() {
		return this.isFunctioning() ? 14 : 0;
	}
	
	public int getType() {
	    return BlockGenerator.Types.HEAT.ordinal();
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
}
