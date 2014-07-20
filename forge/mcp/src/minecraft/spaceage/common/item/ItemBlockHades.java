package spaceage.common.item;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockHades extends ItemBlock {

	public ItemBlockHades(int id) {
		super(id);
		setHasSubtypes(true);
	}
	
	public String getUnlocalizedName(ItemStack itemStack) {
		String name = "";
		switch(itemStack.getItemDamage()) {
			case 0: {
				name = "fake_dirt";
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
