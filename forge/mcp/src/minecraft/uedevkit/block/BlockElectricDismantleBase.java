package uedevkit.block;

import java.util.ArrayList;
import java.util.Random;

import uedevkit.block.util.IDismantleable;
import uedevkit.block.util.IReconfigurableFacing;
import uedevkit.helper.RandomHelpers;
import uedevkit.item.util.IWrench;
import uedevkit.tile.TileElectricBase;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public abstract class BlockElectricDismantleBase extends Block
  implements IDismantleable, IReconfigurableFacing {
  
	public BlockElectricDismantleBase(int id, Material material) {
    super(id, material);
    this.setBlockUnbreakable();
  }

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int hitSide, float hitX, float hitY, float hitZ) {
		Item equipped = player.getCurrentEquippedItem() != null ? player.getCurrentEquippedItem().getItem() : null;

		if (player.isSneaking()) {
			if (isHoldingUsableWrench(player, x, y, z)) {
				if ((RandomHelpers.isServerWorld(world)) && (canDismantle(player, world, x, y, z))) {
					dismantleBlock(player, world, x, y, z, false);
				}
				((IWrench)equipped).wrenchUsed(player, x, y, z);
				return true;
			}
			return false;
		}
		TileElectricBase tile = (TileElectricBase)world.getBlockTileEntity(x, y, z);

		if (tile == null) {
			return false;
		}
		if (isHoldingUsableWrench(player, x, y, z)) {
			if (RandomHelpers.isServerWorld(world)) {
				tile.onWrench(player, hitSide);
			}
			((IWrench)equipped).wrenchUsed(player, x, y, z);
			return true;
		}
	}

	 public static boolean isHoldingUsableWrench(EntityPlayer player, int x, int y, int z) {
	    Item equipped = player.getCurrentEquippedItem() != null ? player.getCurrentEquippedItem().getItem() : null;
	    return ((equipped instanceof IWrench)) && (((IWrench)equipped).canWrench(player, x, y, z));
	  }

	 public ItemStack dismantleBlock(EntityPlayer player, NBTTagCompound nbt, World world, int x, int y, int z, boolean returnBlock, boolean simulate) {
		 int bMeta = world.getBlockMetadata(x, y, z);
		 ItemStack dropBlock = new ItemStack(this.blockID, 1, bMeta);

		 if (!simulate) {
			 TileEntity tile = world.getBlockTileEntity(x, y, z);
			 if ((tile instanceof TileElectricBase)) {
				 ((TileElectricBase)tile).blockDismantled();
			 }
			 if (nbt != null) {
				 dropBlock.setTagCompound(nbt);
			 }
			 	world.setBlockToAir(x, y, z);

			 	if ((dropBlock != null) && (!returnBlock)) {
			 		float f = 0.3F;
			 		double x2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
			 		double y2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
			 		double z2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
			 		EntityItem item = new EntityItem(world, x + x2, y + y2, z + z2, dropBlock);
			 		item.delayBeforeCanPickup = 10;
			 		world.spawnEntityInWorld(item);

			 		if (player != null) {
			 			dismantleLog(player.username, this.blockID, bMeta, x, y, z);
			 		}
			 	}
		 }
		 return dropBlock;
	 }

  private void dismantleLog(String username, int blockID, int bMeta, int x,
		int y, int z) {
	  System.out.println(username + " dismantled blockID " + blockID + " with metadata " + bMeta + " at coordinates " + x + ", " + y + ", " + z);
  }
  
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase living, ItemStack stack) {

		TileEntity tile = world.getBlockTileEntity(x, y, z);

		if (tile instanceof IReconfigurableFacing) {
			IReconfigurableFacing reconfig = (IReconfigurableFacing) tile;
			int quadrant = MathHelper.floor(living.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

			switch (quadrant) {
			case 0:
				reconfig.setFacing(2);
				return;
			case 1:
				reconfig.setFacing(5);
				return;
			case 2:
				reconfig.setFacing(3);
				return;
			case 3:
				reconfig.setFacing(4);
				return;
			case 4:
				reconfig.setFacing(1);
				return;
			case 5:
				reconfig.setFacing(0);
				return;
			}
		}
	}
	
	@Override
	public boolean rotateBlock(World world, int x, int y, int z, ForgeDirection axis) {

		TileEntity tile = world.getBlockTileEntity(x, y, z);

		return tile instanceof IReconfigurableFacing ? ((IReconfigurableFacing) tile).rotateBlock() : false;
	}
	
	@Override
	public ArrayList<ItemStack> dismantleBlock(EntityPlayer player, World world, int x, int y, int z, boolean returnDrops) {

		return dismantleBlock(player, getItemStackTag(world, x, y, z), world, x, y, z, returnDrops, false);
	}

	@Override
	public boolean canDismantle(EntityPlayer player, World world, int x, int y, int z) {

		TileEntity tile = world.getBlockTileEntity(x, y, z);

		if (tile instanceof TileElectricBase) {
			return ((TileElectricBase) tile).canPlayerDismantle(player);
		}
		return true;
	}

}