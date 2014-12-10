package spaceage.common.item;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlock0011 extends ItemBlock {

	public ItemBlock0011(int id) {
		super(id);
		setHasSubtypes(true);
	}
	
	public String getUnlocalizedName(ItemStack itemStack) {
		String name = "";
		switch(itemStack.getItemDamage()) {
			case 0: {
				name = "tech_dirt";
				break;
			}
			case 1: {
				name = "solar_leaves";
				break;
			}
			case 2: {
				name = "heavy_alloy_wood";
				break;
			}
			case 3: {
				name = "organic_building_block";
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
