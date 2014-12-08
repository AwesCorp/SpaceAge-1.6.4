package spaceage.common.block;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import spaceage.common.SpaceAgeCore;
import spaceage.common.block.BlockGenerator.Types;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockEden extends Block {

	public BlockEden(int id, Material material) {
		super(id, material);
		this.setCreativeTab(SpaceAgeCore.tabSA);
	}
	
	private Type type;
	
	public Random random;
	
	@SideOnly(Side.CLIENT)
	private Icon[] icons;
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		icons = new Icon[3];
		
		for(int i = 0; i < icons.length; i++) {
			icons[i] = par1IconRegister.registerIcon(SpaceAgeCore.modid + ":" + (this.getUnlocalizedName().substring(5)) + i);
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public Icon getIcon(int side, int metadata) {
		switch(metadata) {
		case 0:
			return icons[0];
		case 1:
			switch(side) {
				case 0: 
					return icons[1];
				case 1:
					return icons[1];
				default: 
					return icons[2];
			}
		default: {
			System.out.println("Invalid metadata for " + this.getUnlocalizedName());
			return icons[0];
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) {
		for(int i = 0; i < Types.values().length; i++) {
			par3List.add(new ItemStack(par1, 1, i));
		}

		
	}
	
	public int damageDropped(int meta) {
		switch(type.ordinal()) {
			case 0:
				return 0;
			default:
				return meta;
		}
	}
	
	public int idDropped(int par1, Random par2Random, int par3) {
		switch(type.ordinal()) {
			case 0: 
				return Item.glowstone.itemID;
			default:
				return this.blockID;
		}
	}
	
	@Override
	public int quantityDropped(Random random) {
		switch(type.ordinal()) {
			case 0:
				return 2 + random.nextInt(3);
			default:
				return 1;
		}
	}
	
	@Override
	public void onBlockDestroyedByPlayer(World par1World, int par2, int par3, int par4, int par5) {
		if(!par1World.isRemote) {
			int blockMetaData = par5;
			
			switch(blockMetaData) {
				case 0:
					if(this.random.nextInt(3) == 0) {
						par1World.spawnEntityInWorld(new EntityItem(par1World, par2, par3, par4, (new ItemStack(SpaceAgeCore.metaSaplingID, 1, 2))));
						return;
					}
			}
			return;	
		}			
	}

	public static enum Type {
		EDEN_LEAVES, EDEN_WOOD;
	}
}