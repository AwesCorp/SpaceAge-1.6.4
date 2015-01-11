package spaceage.common.block;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import spaceage.common.SpaceAgeCore;
import spaceage.common.block.BlockGenerator.Types;
import spaceage.common.tile.TileGasTank;
import spaceage.common.tile.TileLiquidTank;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockTank extends Block {

	public BlockTank(int id, Material material) {
		super(id, material);
	}
	
	public static final String[] textureNames = new String[] {"Liquid", "Gas"};
	
	@SideOnly(Side.CLIENT)
	private Icon[] icons;
	
	@Override
    public void registerIcons(IconRegister icon) {
		icons = new Icon[2];
		
		//for(int i = 0; i < icons.length; i++) {
			//icons[i] = icon.registerIcon(SpaceAgeCore.modid + ":" + (this.getUnlocalizedName().substring(5)) + textureNames[i]);
		//}
		icons[0] = icon.registerIcon(SpaceAgeCore.modid + ":" + getProperTextureName(0));
		icons[1] = icon.registerIcon(SpaceAgeCore.modid + ":" + getProperTextureName(1));
        //this.blockIcon = icon.registerIcon(SpaceAgeCore.modid + ":" + (this.getUnlocalizedName().substring(5)) + "." + textureNames[i]);
    }
	
	public String getProperTextureName(int iconArrayPoint) {
		switch(iconArrayPoint) {
			case 0:
				return (this.getUnlocalizedName().substring(5)) + textureNames[0];
			case 1:
				return (this.getUnlocalizedName().substring(5)) + textureNames[1];
			default:
				return "You totally fucked up your minecraft";
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Icon getIcon(int side, int metadata) {
		switch(metadata) {
			case 0:
				return icons[0];
			case 1:
				return icons[1];
			default:
				return icons[0];
		}
	}
	
	@Override
	public int getRenderType() {
		return -1;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public boolean hasTileEntity(int meta) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		switch(Types.values()[metadata].ordinal()) {
			case 0:
				return new TileLiquidTank();
			case 1:
				return new TileGasTank(); 
		}
		return null;
	}
	
	@Override
	public int idDropped(int par1, Random random, int par3) {
		return this.blockID;
	}
	
	@Override
	public int damageDropped(int par1) {
		return par1;
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int itemId, CreativeTabs tab, List list) {
	    for (int i = 0; i < Types.values().length; i++)
	        list.add(new ItemStack(itemId, 1, i));
	}
	
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitVecX, float hitVexY, float hitVecZ) {
    	TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
    	ItemStack current = player.inventory.getCurrentItem();
    	
    	if(world.isRemote) {
    		return true;
    	} else if(!player.isSneaking()) {
        	int GUIMetadata = tileEntity.getBlockMetadata();
    			player.openGui(SpaceAgeCore.instance, GUIMetadata + 2/**/, world, x, y, z);
    	}
    	return false;
    }

    public static enum Types {
    	LIQUID, GAS;
    }
}