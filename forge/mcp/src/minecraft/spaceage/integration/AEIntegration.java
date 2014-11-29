package spaceage.integration;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import appeng.api.Materials;
import cpw.mods.fml.common.Loader;

public class AEIntegration {

	public static int quartzID;
	public static ItemStack quartzStack;
	//public static Item quartz;
	
	public void getAEItems() {
		if(Loader.isModLoaded("AppliedEnergistics")) {
			quartzID = Materials.matQuartz.itemID;
			quartzStack = Materials.matQuartz;
			//quartz = Materials.matQuartz.getItem();
		}
	}

}
