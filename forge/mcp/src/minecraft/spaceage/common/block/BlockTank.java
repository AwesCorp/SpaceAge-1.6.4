package spaceage.common.block;

import java.util.List;
import java.util.Random;

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
import net.minecraft.world.World;

public class BlockTank extends Block {

	public BlockTank(int id, Material material) {
		super(id, material);
	}
	
	public static final String[] textureNames = new String[] {"liquid", "gas"};
	
	@Override
    public void registerIcons(IconRegister icon) {
        this.blockIcon = icon.registerIcon(SpaceAgeCore.modid + ":" + (this.getUnlocalizedName().substring(5)) + "." + textureNames);
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
	
	@Override
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
