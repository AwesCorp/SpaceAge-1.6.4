package spaceage.common.item;

import net.minecraft.item.ItemStack;
import spaceage.common.SpaceAgeCore;
import universalelectricity.api.item.ItemElectric;

public class ItemElectricCell extends ItemElectric {

	public ItemElectricCell(int id) {
		super(id);
	}

	@Override
	public long getEnergyCapacity(ItemStack theItem) {
		return SpaceAgeCore.CELL_CAPACITY;
	}

}
