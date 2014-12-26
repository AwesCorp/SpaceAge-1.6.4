package cr0s.WarpDrive;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;

public class WarpDriveTab extends CreativeTabs {

	public WarpDriveTab(int id, String string) {
		super(id, string);
	}

	@SideOnly(Side.CLIENT)
	public int getTabIconItemIndex() {
		return WarpDrive.warpCore.blockID;
	}
}
