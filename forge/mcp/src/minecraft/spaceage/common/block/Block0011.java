package spaceage.common.block;

import java.util.List;
import java.util.Random;

import spaceage.common.SpaceAgeCore;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class Block0011 extends Block {

	public Block0011(int id, Material material) {
		super(id, material);
		this.setCreativeTab(SpaceAgeCore.tabSA);
		
		
		
		switch(type.ordinal()) {
			case 0:
				setHardness(1.0F).setStepSound(Block.soundMetalFootstep);
			case 1:
				setHardness(1.0F).setStepSound(Block.soundMetalFootstep);
			case 2:
				setHardness(1.0F).setStepSound(Block.soundMetalFootstep);
			case 3:
				setHardness(100.0F).setStepSound(Block.soundGrassFootstep);
		}
	}
	
	private Type type;
	
	@SideOnly(Side.CLIENT)
	private Icon[] icons;
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		icons = new Icon[5];
		
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
				return icons[1];
			case 2:
				switch(side) {
					case 0: 
						return icons[2];
					case 1:
						return icons[2];
					default: 
						return icons[3];
			}
			case 3:
				return icons[4];
				
			default:
				return icons[0];
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) {
		for(int i = 0; i < type.ordinal(); i++) {
			par3List.add(new ItemStack(par1, 1, i));
		}
	}
	
	public int damageDropped(int meta) {
		switch(type.ordinal()) {
			default:
				return meta;
		}
	}
	
	public int idDropped(int par1, Random par2Random, int par3) {
		switch(type.ordinal()) {
			default:
				return this.blockID;
		}
	}
	
	@Override
	public int quantityDropped(Random random) {
		switch(type.ordinal()) {
			default:
				return 1;
		}
	}

	public static enum Type {
		TECH_DIRT, SOLAR_LEAVES, HEAVY_ALLOY_WOOD, ORGANIC_BUILDING_BLOCK;
	}
	
}
