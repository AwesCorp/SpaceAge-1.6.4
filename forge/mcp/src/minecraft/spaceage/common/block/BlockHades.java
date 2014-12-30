package spaceage.common.block;

import java.util.List;
import java.util.Random;

import spaceage.common.SpaceAgeCore;
import spaceage.common.block.Block0011.Type;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class BlockHades extends Block {

	public BlockHades(int id, Material material) {
		super(id, material);
		this.setCreativeTab(SpaceAgeCore.tabSA);
		
		setHardness(1.0F);
		setStepSound(Block.soundGravelFootstep);
	}
	
	private Type type;
	
	@SideOnly(Side.CLIENT)
	private Icon[] icons;
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		icons = new Icon[1];
		
		for(int i = 0; i < icons.length; i++) {
			icons[i] = par1IconRegister.registerIcon(SpaceAgeCore.modid + ":hades/" + (this.getUnlocalizedName().substring(5)) + i);
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public Icon getIcon(int side, int metadata) {
		switch(metadata) {
			case 0:
				return icons[0];
			default:
				return icons[0];
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) {
		for(int i = 0; i < Type.values().length; i++) {
			par3List.add(new ItemStack(par1, 1, i));
		}
	}
	
	public int damageDropped(int meta) {
		switch(Type.values().length) {
			default:
				return meta;
		}
	}
	
	public int idDropped(int par1, Random par2Random, int par3) {
		switch(Type.values().length) {
			default:
				return this.blockID;
		}
	}
	
	@Override
	public int quantityDropped(Random random) {
		switch(Type.values().length) {
			default:
				return 1;
		}
	}

	public static enum Type {
		FAKE_DIRT;
	}
}