package spaceage.common.block;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import spaceage.common.SpaceAgeCore;
import spaceage.common.tile.TileAluminiumCable;
import spaceage.common.tile.TileCopperCable;
import spaceage.common.tile.TileSilverCable;
import uedevkit.tile.TileCableBase;
import universalelectricity.api.UniversalElectricity;
import universalelectricity.api.vector.Vector3;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockCable extends Block {

	private Types type;
	public Vector3 minVector = new Vector3(0.3, 0.3, 0.3);
	public Vector3 maxVector = new Vector3(0.7, 0.7, 0.7);
	
	public BlockCable(int id, Material material) {
		super(id, UniversalElectricity.machine);
	}
	
	@Override
	public boolean hasTileEntity(int meta) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		switch(Types.values()[metadata].ordinal()) {
			case 0:
				return new TileCopperCable();
			case 1:
				return new TileAluminiumCable(); 
			case 2:
				return new TileSilverCable(); //TODO More cables
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
	public void onNeighborTileChange(World world, int x, int y, int z, int tileX, int tileY, int tileZ) {
		super.onNeighborTileChange(world, x, y, z, tileX, tileY, tileZ);
		
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if(tile instanceof TileCableBase) {
			((TileCableBase)tile).refresh();
		}
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int neighbourBlockID) {
		super.onNeighborBlockChange(world, x, y, z, neighbourBlockID);
		
		this.setBlockBoundsBasedOnState(world, x, y, z);
		
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if(tile instanceof TileCableBase) {
			((TileCableBase)tile).refresh();
		}
	}
	
	@Override
    public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if(tile instanceof TileCableBase) {
			((TileCableBase)tile).refresh();
		}
    }
	
	@SideOnly(Side.CLIENT)
	private Icon[] icons;
	
	@Override
	@SideOnly(Side.CLIENT) //TODO fix
	public Icon getBlockTexture(IBlockAccess par1IBlockAccess, int x, int y, int z, int side) {
		Vector3 thisVec = new Vector3(x, y, z).modifyPositionFromSide(ForgeDirection.getOrientation(side));
		final int idAtSide = thisVec.getBlockID(par1IBlockAccess);

		if (idAtSide == SpaceAgeCore.cable.blockID) {
			/*if (par1IBlockAccess.getBlockTileEntity(x, y, z) == TileCopperCable) {
				return this.icons[0];
			}
			
			if (par1IBlockAccess.getBlockTileEntity(x, y, z) == TileAluminiumCable) {
				return this.icons[1];
			}
			
			if (par1IBlockAccess.getBlockTileEntity(x, y, z) == TileSilverCable) {
				return this.icons[2];
			}*/
			switch(par1IBlockAccess.getBlockMetadata(x, y, z)) {
				case 0:
					return icons[0];
				case 1:
					return icons[1];
				case 2:
					return icons[2];
			}
		}

		return this.icons[0];
	}
	
	@Override
	public int getRenderType() {//TODO fix
		return SpaceAgeCore.proxy.getBlockRenderID(this.blockID);
	}
	
	@Override
	@SideOnly(Side.CLIENT) //TODO fix textures
	public void registerIcons(IconRegister par1IconRegister) {
		this.icons = new Icon[3];

		for (int i = 0; i < icons.length; i++) {
			this.icons[i] = par1IconRegister.registerIcon(SpaceAgeCore.modid + ":" + (this.getUnlocalizedName().substring(5)) + i);
		}
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
		return true;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this
	 * box can change after the pool has been cleared to be reused)
	 */
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		this.setBlockBoundsBasedOnState(world, x, y, z);
		return super.getCollisionBoundingBoxFromPool(world, x, y, z);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k) {
		return this.getCollisionBoundingBoxFromPool(world, i, j, k);
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		TileEntity[] connectable = new TileEntity[6];

		if (tileEntity != null) {
			connectable = ((TileCableBase)tileEntity).getConnections();

			float minX = (float) this.minVector.x;
			float minY = (float) this.minVector.y;
			float minZ = (float) this.minVector.z;
			float maxX = (float) this.maxVector.x;
			float maxY = (float) this.maxVector.y;
			float maxZ = (float) this.maxVector.z;

			if (connectable[0] != null) {
				minY = 0.0F;
			}

			if (connectable[1] != null) {
				maxY = 1.0F;
			}

			if (connectable[2] != null) {
				minZ = 0.0F;
			}

			if (connectable[3] != null) {
				maxZ = 1.0F;
			}

			if (connectable[4] != null) {
				minX = 0.0F;
			}

			if (connectable[5] != null) {
				maxX = 1.0F;
			}

			this.setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
		}
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axisalignedbb, List list, Entity entity) {
		this.setBlockBounds((float) this.minVector.x, (float) this.minVector.y, (float) this.minVector.z, (float) this.maxVector.x, (float) this.maxVector.y, (float) this.maxVector.z);
		super.addCollisionBoxesToList(world, x, y, z, axisalignedbb, list, entity);

		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if (tileEntity != null) {
			TileEntity[] connectable;
			
			connectable = ((TileCableBase)tileEntity).getConnections();
	
			
			
			if (connectable[4] != null) {
				this.setBlockBounds(0, (float) this.minVector.y, (float) this.minVector.z, (float) this.maxVector.x, (float) this.maxVector.y, (float) this.maxVector.z);
				super.addCollisionBoxesToList(world, x, y, z, axisalignedbb, list, entity);
			}
	
			if (connectable[5] != null) {
				this.setBlockBounds((float) this.minVector.x, (float) this.minVector.y, (float) this.minVector.z, 1, (float) this.maxVector.y, (float) this.maxVector.z);
				super.addCollisionBoxesToList(world, x, y, z, axisalignedbb, list, entity);
			}
	
			if (connectable[0] != null) {
				this.setBlockBounds((float) this.minVector.x, 0, (float) this.minVector.z, (float) this.maxVector.x, (float) this.maxVector.y, (float) this.maxVector.z);
				super.addCollisionBoxesToList(world, x, y, z, axisalignedbb, list, entity);
			}
	
			if (connectable[1] != null) {
				this.setBlockBounds((float) this.minVector.x, (float) this.minVector.y, (float) this.minVector.z, (float) this.maxVector.x, 1, (float) this.maxVector.z);
				super.addCollisionBoxesToList(world, x, y, z, axisalignedbb, list, entity);
			}
	
			if (connectable[2] != null) {
				this.setBlockBounds((float) this.minVector.x, (float) this.minVector.y, 0, (float) this.maxVector.x, (float) this.maxVector.y, (float) this.maxVector.z);
				super.addCollisionBoxesToList(world, x, y, z, axisalignedbb, list, entity);
			}
	
			if (connectable[3] != null) {
				this.setBlockBounds((float) this.minVector.x, (float) this.minVector.y, (float) this.minVector.z, (float) this.maxVector.x, (float) this.maxVector.y, 1);
				super.addCollisionBoxesToList(world, x, y, z, axisalignedbb, list, entity);
			}
		}
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}
	
    public static enum Types {
    	COPPER, ALUMINIUM, SILVER;
    }
}