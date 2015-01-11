package spaceage.common.block;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import spaceage.common.SpaceAgeCore;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;

public class BlockConnectedGlasses extends BlockGlass {
	
	public static Icon[] texturesClear = new Icon[47];
	public static int[] textureRefByID = { 0, 0, 6, 6, 0, 0, 6, 6, 3, 3, 19, 15, 3, 3, 19, 15, 1, 1, 18, 18, 1, 1,
										 13, 13, 2, 2, 23, 31, 2, 2, 27, 14, 0, 0, 6, 6, 0, 0, 6, 6, 3, 3, 19,
										 15, 3, 3, 19, 15, 1, 1, 18, 18, 1, 1, 13, 13, 2, 2, 23, 31, 2, 2, 27,
										 14, 4, 4, 5, 5, 4, 4, 5, 5, 17, 17, 22, 26, 17, 17, 22, 26, 16, 16, 20,
										 20, 16, 16, 28, 28, 21, 21, 46, 42, 21, 21, 43, 38, 4, 4, 5, 5, 4, 4,
										 5, 5, 9, 9, 30, 12, 9, 9, 30, 12, 16, 16, 20, 20, 16, 16, 28, 28, 25,
										 25, 45, 37, 25, 25, 40, 32, 0, 0, 6, 6, 0, 0, 6, 6, 3, 3, 19, 15, 3, 3,
										 19, 15, 1, 1, 18, 18, 1, 1, 13, 13, 2, 2, 23, 31, 2, 2, 27, 14, 0, 0,
										 6, 6, 0, 0, 6, 6, 3, 3, 19, 15, 3, 3, 19, 15, 1, 1, 18, 18, 1, 1, 13,
										 13, 2, 2, 23, 31, 2, 2, 27, 14, 4, 4, 5, 5, 4, 4, 5, 5, 17, 17, 22, 26,
										 17, 17, 22, 26, 7, 7, 24, 24, 7, 7, 10, 10, 29, 29, 44, 41, 29, 29, 39,
										 33, 4, 4, 5, 5, 4, 4, 5, 5, 9, 9, 30, 12, 9, 9, 30, 12, 7, 7, 24, 24,
										 7, 7, 10, 10, 8, 8, 36, 35, 8, 8, 34, 11 };

	public static Icon[] texturesBlue = new Icon[47];

	public BlockConnectedGlasses(int id, Material material) {
		super(id, Material.glass, false);
		this.setHardness(100F);
		this.setLightValue(1F);
	}

	@Override
	public void registerIcons(IconRegister iconRegistry) {
		for (int i = 0; i < 47; i++) {
			texturesClear[i] = iconRegistry.registerIcon(SpaceAgeCore.modid + ":glass/reinforcedGlass_" + (i+1));
		}
	 
		for (int i = 0; i < 47; i++) {
			texturesBlue[i] = iconRegistry.registerIcon(SpaceAgeCore.modid + ":glass/blueGlass_" + (i+1));
		}
	}

	@Override
	public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int side) {
		boolean[] bitMatrix = new boolean[8];
		int meta = world.getBlockMetadata(x, y, z);
	
		this.matchBlockIdAndMeta(world, x - 1, y, z - 1, meta);
	 
		if (side == 0 || side == 1) {
			bitMatrix[0] = this.matchBlockIdAndMeta(world, x - 1, y, z - 1, meta);
			bitMatrix[1] = this.matchBlockIdAndMeta(world, x, y, z - 1, meta);
			bitMatrix[2] = this.matchBlockIdAndMeta(world, x + 1, y, z - 1, meta);
			bitMatrix[3] = this.matchBlockIdAndMeta(world, x - 1, y, z, meta);
		 	bitMatrix[4] = this.matchBlockIdAndMeta(world, x + 1, y, z, meta);
		 	bitMatrix[5] = this.matchBlockIdAndMeta(world, x - 1, y, z + 1, meta);
		 	bitMatrix[6] = this.matchBlockIdAndMeta(world, x, y, z + 1, meta);
		 	bitMatrix[7] = this.matchBlockIdAndMeta(world, x + 1, y, z + 1, meta);
		}
		if (side == 2 || side == 3) {
			bitMatrix[0] = this.matchBlockIdAndMeta(world, x + (side == 2 ? 1 : -1), y + 1, z, meta);
			bitMatrix[1] = this.matchBlockIdAndMeta(world, x, y + 1, z, meta);
			bitMatrix[2] = this.matchBlockIdAndMeta(world, x + (side == 3 ? 1 : -1), y + 1, z, meta);
			bitMatrix[3] = this.matchBlockIdAndMeta(world, x + (side == 2 ? 1 : -1), y, z, meta);
			bitMatrix[4] = this.matchBlockIdAndMeta(world, x + (side == 3 ? 1 : -1), y, z, meta);
			bitMatrix[5] = this.matchBlockIdAndMeta(world, x + (side == 2 ? 1 : -1), y - 1, z, meta);
			bitMatrix[6] = this.matchBlockIdAndMeta(world, x, y - 1, z, meta);
			bitMatrix[7] = this.matchBlockIdAndMeta(world, x + (side == 3 ? 1 : -1), y - 1, z, meta);
		}
		if (side == 4 || side == 5) {
			bitMatrix[0] = this.matchBlockIdAndMeta(world, x, y + 1, z + (side == 5 ? 1 : -1), meta);
			bitMatrix[1] = this.matchBlockIdAndMeta(world, x, y + 1, z, meta);
			bitMatrix[2] = this.matchBlockIdAndMeta(world, x, y + 1, z + (side == 4 ? 1 : -1), meta);
			bitMatrix[3] = this.matchBlockIdAndMeta(world, x, y, z + (side == 5 ? 1 : -1), meta);
		 	bitMatrix[4] = this.matchBlockIdAndMeta(world, x, y, z + (side == 4 ? 1 : -1), meta);
		 	bitMatrix[5] = this.matchBlockIdAndMeta(world, x, y - 1, z + (side == 5 ? 1 : -1), meta);
		 	bitMatrix[6] = this.matchBlockIdAndMeta(world, x, y - 1, z, meta);
		 	bitMatrix[7] = this.matchBlockIdAndMeta(world, x, y - 1, z + (side == 4 ? 1 : -1), meta);
		}
	
		int idBuilder = 0;

		for (int i = 0; i <= 7; i++) {
			idBuilder = idBuilder + (bitMatrix[i] ? 
					(i == 0 ? 1 : 
						(i == 1 ? 2 : 
							(i == 2 ? 4 : 
								(i == 3 ? 8 : 
									(i == 4 ? 16 : 
										(i == 5 ? 32 : 
											(i == 6 ? 64 : 128))))))) : 0);
		}
		 
		return meta == 0 ? (idBuilder > 255 || idBuilder < 0 ? texturesClear[0] : texturesClear[textureRefByID[idBuilder]]) : (idBuilder > 255 || idBuilder < 0 ? texturesBlue[0] : texturesBlue[textureRefByID[idBuilder]]);
	}

	public boolean matchBlockIdAndMeta(IBlockAccess world, int x, int y, int z, int meta) {
		return world.getBlockId(x, y, z) == this.blockID && world.getBlockMetadata(x, y, z) == meta;
	}

	@Override
	public Icon getIcon(int side, int meta) {
		switch(meta) {
			case 0:
				return texturesClear[0];
			case 1:
				return texturesBlue[0];
		}
		return texturesClear[0];
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) {
		for(int i = 0; i < 2; i++) {
			par3List.add(new ItemStack(par1, 1, i));
		}
	}
	
	@Override
	public int damageDropped(int meta) {
		switch(meta) {
			default:
				return meta;
		}
	}
	
	@Override
	public int idDropped(int meta, Random random, int par3) {
		switch(meta) {
			default:
				return this.blockID;
		}
	}
	
	@Override
	public int quantityDropped(Random random) {
		return 1;
	}
	
	@Override
    public int getRenderBlockPass() {
        return 1;
    }
}