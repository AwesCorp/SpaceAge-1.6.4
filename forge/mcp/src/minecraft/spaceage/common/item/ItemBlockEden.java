package spaceage.common.item;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockEden extends ItemBlock {

	public ItemBlockEden(int id) {
		super(id);
		setHasSubtypes(true);
	}

	public String getUnlocalizedName(ItemStack stack) {
		String name = "";
		
		switch(stack.getItemDamage()) {
			case 0: {
				name = "edenLeaves";
				break;
			}
			case 1: {
				name = "edenWood";
				break; 
			}
			default:
				name = "broken";
		}
		return getUnlocalizedName() + "." + name;
	}
	
	@Override
	public int getMetadata(int par1) {
		return par1;
	}
}
