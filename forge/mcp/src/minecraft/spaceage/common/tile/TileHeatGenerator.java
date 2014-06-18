package spaceage.common.tile;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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
import uedevkit.tile.TileElectricInventoryBase;
import universalelectricity.api.energy.EnergyStorageHandler;

public class TileHeatGenerator extends TileElectricInventoryBase implements IFluidHandler {
	
	protected FluidTank waterTank = new FluidTank(2 * FluidContainerRegistry.BUCKET_VOLUME); 
	
	int inventorySize = 1;
	
	protected boolean creatingSteam = false; 
	
	@SideOnly(Side.CLIENT)
	public static Icon sside, bottom, top;
	
	public TileHeatGenerator() {
		inventory = new ItemStack[1];
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		
		//Allows machines to be powered by internal reserves
		this.produce();
		
		boolean powered = (worldObj.getBlockId(xCoord, yCoord - 1, zCoord) == Block.lavaStill.blockID) && (creatingSteam == true);
		
        this.creatingSteam = false;
        
        if (this.isFunctioning()) {
            if (this.waterTank != null && this.waterTank.getFluid() != null && this.waterTank.getFluidAmount() > 1 && this.waterTank.getFluid().isFluidEqual(new FluidStack(FluidRegistry.WATER, 1000))) {
                this.waterTank.drain(1, true);
                this.creatingSteam = true;
            }else {
				FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(inventory[0]);

				if(fluid != null && fluid.fluidID == FluidRegistry.WATER.getID()) {
					if(waterTank.getFluid() == null || waterTank.getFluid().amount+fluid.amount <= waterTank.getCapacity()) {
						waterTank.fill(fluid, true);
                //this.waterTank.fill(new FluidStack(FluidRegistry.WATER, 1000), true);
            }
        }
    }
}
		
		if(powered == true/* && creatingSteam == true*/) {
			if(!this.energy.isFull()) {
			Long produced = Long.valueOf(SpaceAgeCore.HEAT_ENERGY);
			this.produceEnergy(produced);
			}
		}
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
		return this.inventorySize;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		// TODO 
		return null;
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		// TODO 
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		// TODO 
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		//TODO
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
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt); //TODO
	}
}
