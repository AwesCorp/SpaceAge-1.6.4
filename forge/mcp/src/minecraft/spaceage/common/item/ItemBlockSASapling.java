package spaceage.common.item;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockSASapling extends ItemBlock {

	public ItemBlockSASapling(int par1) {
		super(par1);
		this.setHasSubtypes(true);
	}
	
	public String getUnlocalizedName(ItemStack itemStack) {
		String name = "";
		switch(itemStack.getItemDamage()) {
			case 0: {
				name = "glowQuartz";
				break;
			}
			case 1: {
				name = "techOrganic";
				break;
			}
			case 2: {
				name = "edenTree";
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
