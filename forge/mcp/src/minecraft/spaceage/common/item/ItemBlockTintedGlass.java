package spaceage.common.item;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockTintedGlass extends ItemBlock {

	public ItemBlockTintedGlass(int id) {
		super(id);
		setHasSubtypes(true);
	}

	public String getUnlocalizedName(ItemStack itemStack) {
		String name = "";
		switch(itemStack.getItemDamage()) {
			case 0: {
				name = "clear_tinted";
				break;
			}
			case 1: {
				name = "blue_tinted";
				break;
			}
			default:
				name = "broken";
		}
		return getUnlocalizedName() + "." + name;
	}
	
	public int getMetadata(int par1) {
		return par1;
	}
}