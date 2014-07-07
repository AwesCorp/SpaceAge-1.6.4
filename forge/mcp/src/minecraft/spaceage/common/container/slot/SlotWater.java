package spaceage.common.container.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;

public class SlotWater extends Slot {

	public SlotWater(IInventory inventory, int id, int x, int y) {
		super(inventory, id, x, y);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return (FluidContainerRegistry.getFluidForFilledItem(stack) != null && FluidContainerRegistry.getFluidForFilledItem(stack).fluidID == FluidRegistry.WATER.getID());
	}
	
}
