package spaceage.common.tile;

import net.minecraft.item.ItemStack;
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
				return false;//TEMPORARY
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
		return null;
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		
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
				return CompatibilityModule.isHandler(itemstack.getItem()); //battery
			default:
				return false; //output
		}
		//return slot == 0 ? true : false;
	}

}
