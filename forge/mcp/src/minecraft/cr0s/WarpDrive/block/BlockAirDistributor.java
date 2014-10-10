package cr0s.WarpDrive.block;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cr0s.WarpDrive.WarpDrive;
import cr0s.WarpDrive.tile.TileEntityAirDistributor;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockAirDistributor extends Block {
    private Icon[] iconBuffer;

    private final int ICON_INACTIVE_SIDE = 0, ICON_BOTTOM = 1, ICON_SIDE_ACTIVATED = 2;

    public BlockAirDistributor(int id, int texture, Material material) {
        super(id, material);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {
        iconBuffer = new Icon[3];
        iconBuffer[ICON_INACTIVE_SIDE] = par1IconRegister.registerIcon("warpdrive:airgenSideInactive");
        iconBuffer[ICON_BOTTOM] = par1IconRegister.registerIcon("warpdrive:contBottom");
        iconBuffer[ICON_SIDE_ACTIVATED] = par1IconRegister.registerIcon("warpdrive:airgenSideActive");
    }

    @Override
    public Icon getIcon(int side, int metadata) {
    	switch(side) {
    		case 0:
    			return iconBuffer[ICON_BOTTOM];
    		default:
    			switch(metadata) {
    				case 0:
    					return iconBuffer[ICON_INACTIVE_SIDE];
    				default:
    					return iconBuffer[ICON_SIDE_ACTIVATED];
    			}
    	}
    	
    	
        /*if (side == 0)
        {
            return iconBuffer[ICON_BOTTOM];
        }
        else if (side == 1)
        {
            if (metadata == 0)
            {
                return iconBuffer[ICON_INACTIVE_SIDE];
            }
            else
            {
                return iconBuffer[ICON_SIDE_ACTIVATED];
            }
        }

        if (metadata == 0) // Inactive state
        {
            return iconBuffer[ICON_INACTIVE_SIDE];
        }
        else if (metadata == 1)
        {
            return iconBuffer[ICON_SIDE_ACTIVATED];
        }

        return null;*/
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata)
    {
        return new TileEntityAirDistributor();
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    @Override
    public int quantityDropped(Random par1Random)
    {
        return 1;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    @Override
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return this.blockID;
    }

    @Override
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        if (FMLCommonHandler.instance().getEffectiveSide().isClient())
        {
            return false;
        }

        TileEntityAirDistributor gen = (TileEntityAirDistributor)par1World.getBlockTileEntity(par2, par3, par4);

        if (gen != null) {
        	if(par5EntityPlayer.isSneaking()) {
        		par5EntityPlayer.addChatMessage("[AirDistributor] Energy level: " + gen.getCurrentEnergyValue() + " Eu");
        	} else {
        		par5EntityPlayer.openGui(WarpDrive.instance, 5, par1World, par2, par3, par4);
        	}
        }

        return true;
    }

    @Override
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6) {
        TileEntity te = par1World.getBlockTileEntity(par2, par3, par4);

        /*if (te != null)
        {
            te.invalidate();
        }*/

        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }
}
