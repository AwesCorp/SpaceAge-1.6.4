package spaceage.common.block;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import spaceage.common.SpaceAgeCore;
import spaceage.common.tile.TileHeatGenerator;
import spaceage.common.tile.TileSolarPanel;
import universalelectricity.api.UniversalElectricity;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

/**
 * The metadata implementation of a block housing a electrical generation tile entity.
 * @author SkylordJoel
 */

public class BlockGenerator extends Block {
	
	@SideOnly(Side.CLIENT)
	public static Icon heat_front, heat_bottom, heat_top, heat_side_idle, heat_side_active;
	
	@SideOnly(Side.CLIENT)
	public static Icon solar_bottom_side, solar_top;	

	public BlockGenerator(int id, Material material) {
		super(id, UniversalElectricity.machine);
		//this.setLightValue();
	}
	
	@Override
	public boolean hasTileEntity(int meta) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		switch(Types.values()[metadata].ordinal()) {
			case 0:
				return new TileHeatGenerator();
			case 1:
				return new TileSolarPanel(); //TODO More generators
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
    	
    	if(world.isRemote) {
    		return true;
    	}else if(!player.isSneaking()) {
        	int GUIMetadata = tileEntity.getBlockMetadata();
    			player.openGui(SpaceAgeCore.instance, GUIMetadata + 2/**/, world, x, y, z);
    		}
    	return false;
    }
    
    @Override
    public void breakBlock(World world, int x, int y, int z, int id, int meta) {
    	TileEntity te = world.getBlockTileEntity(x, y, z);
    	if(te != null && te instanceof IInventory) {
    		IInventory inventory = (IInventory)te;
    		
    		for(int i = 0; i < inventory.getSizeInventory(); i++) {
    			ItemStack stack = inventory.getStackInSlotOnClosing(i);
    			
    			if(stack != null) {
    				float spawnX = x + world.rand.nextFloat();
    				float spawnY = y + world.rand.nextFloat();
    				float spawnZ = z + world.rand.nextFloat();
    				
    				EntityItem droppedItem = new EntityItem(world, spawnX, spawnY, spawnZ, stack);
    				
    				float mult = 0.05F;
    				
    				droppedItem.motionX = (-0.5F + world.rand.nextFloat()) * mult;
    				droppedItem.motionY = (4 + world.rand.nextFloat()) * mult;
    				droppedItem.motionZ = (-0.5F + world.rand.nextFloat()) * mult;
    				
    				world.spawnEntityInWorld(droppedItem);
    			}
    		}
    	}
    	
    	super.breakBlock(world, x, y, z, id, meta);
    }
    
    public static enum Types {
    	HEAT, SOLAR;
    }
    
    /*public int openedGUI(int metadata) {
    	switch(metadata) {
    		case 0:
    			return 0; //HEAT
    		case 1:
    			return 1; //SOLAR
    	}
    	return 0;
    }*/

}
