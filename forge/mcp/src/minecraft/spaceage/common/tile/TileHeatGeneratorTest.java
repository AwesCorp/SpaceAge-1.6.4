package spaceage.common.tile;

import java.util.ArrayList;

import com.google.common.io.ByteArrayDataInput;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
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
import resonant.lib.prefab.vector.Cuboid;
import spaceage.common.SpaceAgeCore;
import universalelectricity.api.energy.EnergyStorageHandler;

public class TileHeatGeneratorTest extends TileMekanismMethod {
	
	public FluidTank waterTank = new FluidTank(24000);
	
	@SideOnly(Side.CLIENT)
	public static Icon sside, bottom;
	
	public TileHeatGeneratorTest()
	{
		super(Material.iron);
		inventory = new ItemStack[1];
		energy = new EnergyStorageHandler(SpaceAgeCore.HEAT_ENERGY * 20);
		ioMap = 728;
		textureName = "heat_top";
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister iconReg)
	{
		sside = iconReg.registerIcon(SpaceAgeCore.modid + ":" + "heat_side");
		bottom = iconReg.registerIcon(SpaceAgeCore.modid + ":" + "heat_bottom");
		super.registerIcons(iconReg);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		switch(side) {
			case 0: return bottom;
			default: return sside;
			}
	/*{
		if (side == 0)
		{
			return solarPanel_bottom;
		}
		else if (side == 1)
		{
			return getIcon();
		}

		return solarPanel_side;*/
	}
	
	/*public boolean waterInTank() {
		return(getTankAmount() > 0);
	}*/

	@Override
	public void updateEntity() {
		boolean powered = (worldObj.getBlockId(xCoord, yCoord - 1, zCoord) == Block.lavaStill.blockID)/* && (getTankAmount)*/;
		
		if(powered == true) {
			this.energy.receiveEnergy(SpaceAgeCore.HEAT_ENERGY, true);
			markDistributionUpdate |= produce() > 0;
				}
		
		super.updateEntity();	
		
		if(!worldObj.isRemote) {
			if(inventory[0] != null) {
				FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(inventory[0]);
				
				if(fluid != null && fluid.fluidID == FluidRegistry.WATER.getID()) {
					if(waterTank.getFluid() == null || waterTank.getFluid().amount+fluid.amount <= waterTank.getCapacity()) {
						waterTank.fill(fluid, true);
						
						if(inventory[0].getItem().getContainerItemStack(inventory[0]) != null) {
							inventory[0] = inventory[0].getItem().getContainerItemStack(inventory[0]);
						}else {
							inventory[0].stackSize--;
						}
						
						if(inventory[0].stackSize == 0) {
							inventory[0] = null;
						}
					}
				}else {
					int fuel = getFuel(inventory[0]);
					
					if(fuel > 0) {
						int fuelNeeded = waterTank.getCapacity() - (waterTank.getFluid() != null ? waterTank.getFluid().amount : 0);
						
						if(fuel <= fuelNeeded) {
							waterTank.fill(new FluidStack(FluidRegistry.WATER, fuel), true);
							
							if(inventory[0].getItem().getContainerItemStack(inventory[0]) != null) {
								inventory[0] = inventory[0].getItem().getContainerItemStack(inventory[0]);
							}else {
								inventory[0].stackSize--;
							}
							
							if(inventory[0].stackSize == 0) {
				inventory[0] = null;
			}
						}
					}
				}
			}
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbtTags) {
		super.readFromNBT(nbtTags);
		
		if(nbtTags.hasKey("waterTank")) {
			waterTank.readFromNBT(nbtTags.getCompoundTag("waterTank"));
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtTags) {
		super.writeToNBT(nbtTags);
		
		if(waterTank.getFluid() != null) {
			nbtTags.setTag("waterTank", waterTank.writeToNBT(new NBTTagCompound()));
		}
	}
	
	//@Override
	public int getFuel(ItemStack itemstack) {
		if(itemstack.itemID == Item.bucketWater.itemID) {
			return 1000;
		}
		
		return TileEntityFurnace.getItemBurnTime(itemstack);
		
	}
	
	//@Override
	public int getScaledFuelLevel(int i) {
		return (waterTank.getFluid() != null ? waterTank.getFluid().amount : 0)*i / waterTank.getCapacity();
	}
	
	@Override
	public void handlePacketData(ByteArrayDataInput dataStream) {
		super.handlePacketData(dataStream);
		
		int amount = dataStream.readInt();
		
		if(amount != 0) {
			waterTank.setFluid(new FluidStack(FluidRegistry.WATER, amount));
		}else {
			waterTank.setFluid(null);
		}
	}
	
	@Override
	public ArrayList getNetworkedData(ArrayList data) {
		super.getNetworkedData(data);
		
		if(waterTank.getFluid() != null) {
			data.add(waterTank.getFluid().amount);
		}else {
			data.add(0);
		}
		
		return data;
		
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
		return fluid == FluidRegistry.WATER;
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
	public int getSizeInventory() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getInvName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isInvNameLocalized() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void openChest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeChest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		switch(i) {
			case 0: return getFuel(itemstack) > 0 || (FluidContainerRegistry.getFluidForFilledItem(itemstack) != null && FluidContainerRegistry.getFluidForFilledItem(itemstack).fluidID == FluidRegistry.WATER.getID());
		}
		return true;
	}
	
	public boolean canOperate() {
		return waterTank.getFluid() != null && waterTank.getFluid().amount >= 10;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		return null; //TODO, will do eventually. Need help on this...
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		switch(i) {
			case 0: return FluidContainerRegistry.isEmptyContainer(itemstack);
		}
		return false;
	}
}
