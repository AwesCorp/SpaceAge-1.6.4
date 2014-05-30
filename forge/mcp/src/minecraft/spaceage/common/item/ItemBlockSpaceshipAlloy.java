package spaceage.common.item;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockSpaceshipAlloy extends ItemBlock {
	
	public ItemBlockSpaceshipAlloy(int id) {
		super(id);
		setHasSubtypes(true);
	}
	
	public String getUnlocalizedName(ItemStack itemStack) {
		String name = "";
		switch(itemStack.getItemDamage()) {
			case 0: {
				name = "blackAlloy";
				break;
			}
			case 1: {
				name = "redAlloy";
				break;
			}
			case 2: {
				name = "greenAlloy";
				break;
			}
			case 3: {
				name = "brownAlloy";
				break;
			}
			case 4: {
				name = "blueAlloy";
				break;
			}
			case 5: {
				name = "purpleAlloy";
				break;
			}
			case 6: {
				name = "cyanAlloy";
				break;
			}
			case 7: {
				name = "silverAlloy";
				break;
			}
			case 8: {	
				name = "grayAlloy";
				break;
			}
			case 9: {
				name = "pinkAlloy";
				break;
			}
			case 10: {
				name = "limeAlloy";
				break;
			}
			case 11: {
				name = "yellowAlloy";
				break;
			}
			case 12: {
				name = "lightBlueAlloy";
				break;
			}
			case 13: {
				name = "magentaAlloy";
				break;
			}
			case 14: {
				name = "orangeAlloy";
				break;
			}
			case 15: {
				name = "whiteAlloy";
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
