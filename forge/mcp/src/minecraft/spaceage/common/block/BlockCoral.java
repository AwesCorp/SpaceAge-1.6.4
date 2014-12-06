package spaceage.common.block;

import java.util.List;
import java.util.Random;
import java.util.logging.Level;

import spaceage.common.LogHelper;
import spaceage.common.SpaceAgeCore;
import spaceage.common.block.BlockGenerator.Types;
import spaceage.common.tile.TileBrainCoral;
import spaceage.common.tile.TileMaze;
import spaceage.common.tile.TilePillar;
import spaceage.common.tile.TileStaghorn;
import spaceage.planets.LogHelperPlanet;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockCoral extends Block {

	private Types type;
	
	public BlockCoral(int id, Material material) {
		super(id, material);
	}
	
	@Override
	public TileEntity createTileEntity(World world, int meta) {
		switch(meta) {
			case 0:
				return new TileBrainCoral();
			case 1:
				return new TileStaghorn();
			case 2:
				return new TilePillar();
			case 3:
				return new TileMaze();
		}
		return null;
	}
	
	@Override
	public boolean hasTileEntity(int meta) {
		return true;
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

/*	@SideOnly(Side.CLIENT)
	private Icon[] icons;*/
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		this.blockIcon = par1IconRegister.registerIcon(SpaceAgeCore.modid + ":model" + getTextureFile());
		
		//icons = new Icon[4];
		/*for(int i = 0; i < icons.length; i++) {
			icons[i] = par1IconRegister.registerIcon(SpaceAgeCore.modid + ":modelCoral" + i);
		}*/
	}
	
/*	@SideOnly(Side.CLIENT)
	@Override
	public Icon getIcon(int side, int metadata) {
		switch(metadata) {
			case 0:
				return icons[0];
			case 1:
				return icons[1];
			case 2:
				return icons[2];
			case 3:
				return icons[3];
			default:
				return icons[0];
		}
	}*/
	
	public String getTextureFile() {
		switch(type.ordinal()) {
			case 0:
				return "Brain";
			case 1:
				return "Staghorn";
			case 2:
				return "Pillar";
			case 3:
				return "Maze";
			default:
				System.out.println("Mojang: Your Minecraft is totally fucked up.");
				LogHelper.log(Level.SEVERE, "Cannot continue functioning. Minecraft uncreatable code error.");
				LogHelperPlanet.log(Level.SEVERE, "Parent Mod 'SpaceAge' function inhibited.");
				return "TOTALLY FUCKED UP";
		}
	}

	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) {
		for(int i = 0; i < 4; i++) {
			par3List.add(new ItemStack(par1, 1, i));
		}
	}
	
	public int damageDropped(int meta) {
		switch(meta) {
			default:
				return meta;
		}
	}
	
	public int idDropped(int par1, Random par2Random, int par3) {
		return this.blockID;
	}
	
	@Override
	public int quantityDropped(Random random) {
		return 1;
	}
	
	@Override
    public void onBlockAdded(World world, int x, int y, int z) {
		checkCanStay(world, x, y, z);
	}
	
	@Override
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
        super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
        checkCanStay(par1World, par2, par3, par4);
    }

    @Override
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
    	checkCanStay(par1World, par2, par3, par4);
    }
	
    public void checkCanStay(World world, int x, int y, int z) {
    	if(world.getBlockId(x, y + 1, z) != Block.waterStill.blockID) {
            this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
            world.setBlock(x, y, z, 0, 0, 2);
		}
	}

	public static enum Types {
    	BRAIN, STAGHORN, PILLAR, MAZE;
    }
}