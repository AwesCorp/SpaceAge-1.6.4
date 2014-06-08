package spaceage.common.block;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import spaceage.common.SpaceAgeCore;
import spaceage.common.tile.TileHeatGenerator;
import universalelectricity.api.UniversalElectricity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockHeatGenerator extends BlockContainer {
	
	private static boolean keepInventory;
	
    // Is the random generator used by furnace to drop the inventory contents in
    // random directions.
    private final Random heatTileEntity = new Random();

    // Tile entity class that gets instanced in the world.
    private TileHeatGenerator lastPlacedTileEntity;

	public BlockHeatGenerator(int ID) {
		super(ID, UniversalElectricity.machine);
		this.setCreativeTab(SpaceAgeCore.tabSA);
		this.setStepSound(Block.soundMetalFootstep);
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, int blockID, int metaData) {
		if(!keepInventory) {
			TileHeatGenerator tileheatgenerator = (TileHeatGenerator) world.getBlockTileEntity(x, y, z);

            if (tileheatgenerator != null)
            {
                for (int j1 = 0; j1 < tileheatgenerator.getSizeInventory(); ++j1)
                {
                    ItemStack itemstack = tileheatgenerator.getStackInSlot(j1);

                    if (itemstack != null)
                    {
                        float f = this.heatTileEntity.nextFloat() * 0.8F + 0.1F;
                        float f1 = this.heatTileEntity.nextFloat() * 0.8F + 0.1F;
                        float f2 = this.heatTileEntity.nextFloat() * 0.8F + 0.1F;

                        while (itemstack.stackSize > 0)
                        {
                            int k1 = this.heatTileEntity.nextInt(21) + 10;

                            if (k1 > itemstack.stackSize)
                            {
                                k1 = itemstack.stackSize;
                            }

                            itemstack.stackSize -= k1;
                            EntityItem entityitem = new EntityItem(world, x + f, y + f1, z + f2, new ItemStack(itemstack.itemID, k1, itemstack.getItemDamage()));

                            if (itemstack.hasTagCompound())
                            {
                                entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
                            }

                            float f3 = 0.05F;
                            entityitem.motionX = (float) this.heatTileEntity.nextGaussian() * f3;
                            entityitem.motionY = (float) this.heatTileEntity.nextGaussian() * f3 + 0.2F;
                            entityitem.motionZ = (float) this.heatTileEntity.nextGaussian() * f3;

                            world.spawnEntityInWorld(entityitem);
                        }
                    }
                }

                world.func_96440_m(x, y, z, blockID);
            }
        }

        super.breakBlock(world, x, y, z, blockID, metaData);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileHeatGenerator();
	}
	
    @Override
    public int damageDropped(int par1) {
        // Makes blocks drop the correct metadata in the world.
        return par1;
    }
    
    public TileEntity getBlockEntity()
    {
        // Returns the TileEntity used by this block.
        return new TileHeatGenerator();
    }
    
    @Override
    public boolean hasTileEntity(int meta)
    {
        return true;
    }

    // Returns the ID of the items to drop on destruction.
    @Override
    public int idDropped(int par1, Random par2Random, int par3) {
        return SpaceAgeCore.heatGenerator.blockID;
    }

    @Override
    @SideOnly(Side.CLIENT)
    /**
     * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
     */
    public int idPicked(World par1World, int par2, int par3, int par4) {
        return SpaceAgeCore.heatGenerator.blockID;
    }

    @Override
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer player, int par6, float par7, float par8, float par9) {
        // Called upon block activation (right click on the block.)
        if (par1World.isRemote) {
            return true;
        }else if (!player.isSneaking()) {
            // Open GUI on the client...
            TileHeatGenerator tileheatgenerator = (TileHeatGenerator) par1World.getBlockTileEntity(par2, par3, par4);

            if (tileheatgenerator != null)
            {
                player.openGui(SpaceAgeCore.instance, this.blockID, par1World, par2, par3, par4);
            }
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z)
    {
        super.onBlockAdded(world, x, y, z);
        this.setDefaultDirection(world, x, y, z);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase living, ItemStack stack)
    {
        super.onBlockPlacedBy(world, x, y, z, living, stack);
        lastPlacedTileEntity = (TileHeatGenerator) world.getBlockTileEntity(x, y, z);
        int dir = MathHelper.floor_double((living.rotationYaw * 4F) / 360F + 0.5D) & 3;
        world.setBlockMetadataWithNotify(x, y, z, dir, 0);
    }

    // This is the icon to use for showing the block in your hand.
    @Override
    public void registerIcons(IconRegister icon) {
        this.blockIcon = icon.registerIcon(SpaceAgeCore.modid + ":" + "heatGenerator_side"); //TODO
    }

    // Set a blocks direction
    private void setDefaultDirection(World par1World, int par2, int par3, int par4)
    {
        if (!par1World.isRemote)
        {
            int l = par1World.getBlockId(par2, par3, par4 - 1);
            int i1 = par1World.getBlockId(par2, par3, par4 + 1);
            int j1 = par1World.getBlockId(par2 - 1, par3, par4);
            int k1 = par1World.getBlockId(par2 + 1, par3, par4);
            byte b0 = 3;

            if (Block.opaqueCubeLookup[l] && !Block.opaqueCubeLookup[i1])
            {
                b0 = 3;
            }

            if (Block.opaqueCubeLookup[i1] && !Block.opaqueCubeLookup[l])
            {
                b0 = 2;
            }

            if (Block.opaqueCubeLookup[j1] && !Block.opaqueCubeLookup[k1])
            {
                b0 = 5;
            }

            if (Block.opaqueCubeLookup[k1] && !Block.opaqueCubeLookup[j1])
            {
                b0 = 4;
            }

            par1World.setBlockMetadataWithNotify(par2, par3, par4, b0, 2);
        }
    }

}
