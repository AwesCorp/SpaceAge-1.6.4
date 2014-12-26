package spaceage.common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;

public class CreativeTabSA extends CreativeTabs {

	public CreativeTabSA(int id, String string) {
		super(id, string);
	}

	@SideOnly(Side.CLIENT)
	public int getTabIconItemIndex() {
		return SpaceAgeCore.spaceshipAlloyMeta.blockID;
	}
}
