package spaceage.common.block;

import spaceage.common.SpaceAgeCore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockSolarPanel extends BlockContainer {
	
	@SideOnly(Side.CLIENT)
	public static Icon sideIcon, bottomIcon, topIcon;

	protected BlockSolarPanel(int par1, Material par2Material) {
		super(par1, par2Material);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister iconReg)
	{
		sideIcon = iconReg.registerIcon(SpaceAgeCore.modid + ":" + "solarPanel_side");
		bottomIcon = iconReg.registerIcon(SpaceAgeCore.modid + ":" + "solarPanel_bottom");
		topIcon = iconReg.registerIcon(SpaceAgeCore.modid + ":" + "solarPanel_top");
		super.registerIcons(iconReg);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		if (side == 0) {
			return bottomIcon;
		}
		else if(side == /*TOP*/ 2) {
			return topIcon;
		}

		return sideIcon;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return null/*TileSolarPanel*/;
	}

}
