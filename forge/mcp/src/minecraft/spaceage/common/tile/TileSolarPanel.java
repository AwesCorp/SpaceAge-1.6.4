package spaceage.common.tile;

import java.util.EnumSet;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.Icon;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import spaceage.common.SpaceAgeCore;
import spaceage.common.block.BlockGenerator;
import uedevkit.tile.TileElectricBase;
import uedevkit.tile.TileElectricInventoryNetworked;
import universalelectricity.api.energy.EnergyStorageHandler;

public class TileSolarPanel extends TileElectricInventoryNetworked {
	
	public TileSolarPanel() {
		super(SpaceAgeCore.SOLAR_CAPACITY, 100);
		inventory = new ItemStack[1];
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt); //TODO
		
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
	public void updateEntity() {
		//super.updateEntity();
		//this.produce();
		if (!this.worldObj.isRemote) {
			if (this.worldObj.canBlockSeeTheSky(xCoord, yCoord + 1, zCoord) && !this.worldObj.provider.hasNoSky) {
				if (this.worldObj.isDaytime()) {
					if (!(this.worldObj.isThundering() || this.worldObj.isRaining())) {
						this.energy.receiveEnergy(SpaceAgeCore.SOLAR_ENERGY, true);
						markDistributionUpdate |= produceReturn() > 0;
						
						Long produced = Long.valueOf(SpaceAgeCore.SOLAR_ENERGY);
						this.produceEnergy(produced);
						this.produce();
					}
				}
			}
		}
		super.updateEntity();
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		// TODO 
		return null;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack itemstack, int side) {
		ItemStack enrichedSilicon = new ItemStack(SpaceAgeCore.meta,1,12);
		int enrichedSiliconDamage = 12;
		
		return ((itemstack.itemID == enrichedSilicon.itemID) || (itemstack.getItemDamage() == enrichedSiliconDamage));
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemstack, int side) {
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
	public ItemStack decrStackSize(int i, int count) {
		ItemStack itemstack = getStackInSlot(i);
		
		if(itemstack != null) {
			if(itemstack.stackSize <= count) {
				setInventorySlotContents(i, null);
			}else {
				itemstack = itemstack.splitStack(count);
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
		return "photovoltaicPanel.name";
	}

	@Override
	public void openChest() {
		// TODO	
	}

	@Override
	public void closeChest() {
		// TODO
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		ItemStack enrichedSilicon = new ItemStack(SpaceAgeCore.meta,1,12);
		int enrichedSiliconDamage = 12;
		
		return ((itemstack.itemID == enrichedSilicon.itemID) || (itemstack.getItemDamage() == enrichedSiliconDamage));
	}
    
	public int getType() {
	    return BlockGenerator.Types.SOLAR.ordinal();
	  }

	@Override
	public boolean isInvNameLocalized() {
		return false;
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
