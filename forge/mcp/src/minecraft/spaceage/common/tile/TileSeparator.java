package spaceage.common.tile;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import spaceage.common.SpaceAgeCore;
import uedevkit.tile.TileMachineBase;
import universalelectricity.api.CompatibilityModule;

public class TileSeparator extends TileMachineBase {
	
	public TileSeparator() {
		super(SpaceAgeCore.ELECTROLYSER_CAPACITY, SpaceAgeCore.ELECTROLYSER_ENERGY_USE, 0);
		inventory = new ItemStack[7/*TEMPORARY - 1 input, 1 battery, up to 5? output*/]; 
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		// TODO Need help...
		return null;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack itemstack, int side) {
		return isItemValidForSlot(slot, itemstack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemstack, int side) {
		switch(slot) {
			case 0:
				return false;
			case 1:
				switch(side) {
					case 1:
						return (CompatibilityModule.isHandler(itemstack) && this.isBatteryEmpty(itemstack));
					default:
						return false;
				}
			default:
				switch(side) {
					case 0:
						return false;
					case 1:
						return false;
					default:
						return true;
				}
		}
		//return false;
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
		return "tileSeparator.name";
	}

	@Override
	public void openChest() {
		
	}

	@Override
	public void closeChest() {
		
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
		switch(slot) {
			case 0:
				return true; //input
			case 1:
				return (CompatibilityModule.isHandler(itemstack.getItem()) && (!this.isBatteryEmpty(itemstack))); //battery
			default:
				return false; //output
		}
		//return slot == 0 ? true : false;
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
}
