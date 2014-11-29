package spaceage.common.item;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockOres1 extends ItemBlock {

	public ItemBlockOres1(int id) {
		super(id);
		this.setHasSubtypes(true);
	}

	public String getUnlocalizedName(ItemStack itemStack) {
		String name = "";
		switch(itemStack.getItemDamage()) {
			case 0: {
				name = "oreTitanium";
				break;
			}
			case 1: {
				name = "oreSAIron";
				break;
			}
			case 2: {
				name = "oreAluminium";
				break;
			}
			case 3: {
				name = "oreLithium";
				break;
			}
			case 4: {
				name = "oreSilver";
				break;
			}
			case 5: {
				name = "oreCopper";
				break;
			}
			case 6: {
				name = "oreSAGold";
				break;
			}
			case 7: {
				name = "oreSACoal";
				break;
			}
			case 8: {
				name = "oreSADiamond";
				break;
			}
			case 9: {
				name = "oreSAEmerald";
				break;
			}
			case 10: {
				name = "oreSALapis";//TODO LAPIS
				break;
			}
			case 11: {
				name = "oreSAQuartz";
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
