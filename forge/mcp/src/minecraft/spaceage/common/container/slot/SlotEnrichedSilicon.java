package spaceage.common.container.slot;

import spaceage.common.SpaceAgeCore;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;

public class SlotEnrichedSilicon extends Slot {

	public SlotEnrichedSilicon(IInventory inventory, int id, int x, int y) {
		super(inventory, id, x, y);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		ItemStack enrichedSilicon = new ItemStack(SpaceAgeCore.meta,1,12);
		return stack.itemID == enrichedSilicon.itemID;
	}
	
}
