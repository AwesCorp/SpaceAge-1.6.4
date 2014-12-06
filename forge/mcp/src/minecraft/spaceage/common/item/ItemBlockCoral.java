package spaceage.common.item;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockCoral extends ItemBlock {

	public ItemBlockCoral(int par1) {
		super(par1);
		this.setHasSubtypes(true);
	}
	
	public String getUnlocalizedName(ItemStack stack) {
		String name = "";
		
		switch(stack.getItemDamage()) {
			case 0: {
				name = "brainCoral";
				break;
			}
			case 1: {
				name = "staghornCoral";
				break; 
			}
			case 2: {
				name = "pillarCoral";
				break;
			}
			case 3: {
				name = "mazeCoral";
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