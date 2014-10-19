package spaceage.common.container.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidContainerItem;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.ItemFluidContainer;

public class SlotFluidInput extends Slot {

	public SlotFluidInput(IInventory inventory, int id, int x,
			int y) {
		super(inventory, id, x, y);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return ((stack.getItem() instanceof IFluidContainerItem) || (stack.getItem() instanceof ItemFluidContainer) || (FluidContainerRegistry.isContainer(stack)));
	}
}
