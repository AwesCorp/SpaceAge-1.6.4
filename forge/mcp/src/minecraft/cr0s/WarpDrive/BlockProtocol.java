package cr0s.WarpDrive;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;

import spaceage.common.SpaceAgeCore;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockProtocol extends Block
{
    private Icon[] iconBuffer;

    private final int ICON_INACTIVE_SIDE = 0, ICON_BOTTOM = 1, ICON_TOP = 2, ICON_SIDE_ACTIVATED = 3;
    //private final int ANIMATION_
    //private int currentTexture;

    public BlockProtocol(int id, int texture, Material material)
    {
        super(id, material);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        iconBuffer = new Icon[9];
        // Solid textures
        iconBuffer[ICON_INACTIVE_SIDE] = par1IconRegister.registerIcon("warpdrive:contSideInactive");
        iconBuffer[ICON_BOTTOM] = par1IconRegister.registerIcon("warpdrive:contBottom");
        iconBuffer[ICON_TOP] = par1IconRegister.registerIcon("warpdrive:contTop");
        // Animated textures
        iconBuffer[ICON_SIDE_ACTIVATED] = par1IconRegister.registerIcon("warpdrive:contSideActive1");
        iconBuffer[ICON_SIDE_ACTIVATED + 1] = par1IconRegister.registerIcon("warpdrive:contSideActive2");
        iconBuffer[ICON_SIDE_ACTIVATED + 2] = par1IconRegister.registerIcon("warpdrive:contSideActive3");
        iconBuffer[ICON_SIDE_ACTIVATED + 3] = par1IconRegister.registerIcon("warpdrive:contSideActive4");
        iconBuffer[ICON_SIDE_ACTIVATED + 4] = par1IconRegister.registerIcon("warpdrive:contSideActive5");
        iconBuffer[ICON_SIDE_ACTIVATED + 5] = par1IconRegister.registerIcon("warpdrive:contSideActive6");
    }

    @Override
    public Icon getIcon(int side, int metadata) {
    	switch(side) {
    		case 0: 
    			return iconBuffer[ICON_BOTTOM];
    		case 1:
    			return iconBuffer[ICON_TOP];
    	}
    	
        /*if (side == 0)
        {
            return iconBuffer[ICON_BOTTOM];
        }
        else if (side == 1)
        {
            return iconBuffer[ICON_TOP];
        }*/

        if (metadata == 0) // Inactive state
        {
            return iconBuffer[ICON_INACTIVE_SIDE];
        }
        else if (metadata > 0)    // Activated, in metadata stored mode number
        {
            return iconBuffer[ICON_SIDE_ACTIVATED + metadata - 1];
        }

        return null;
    }

    @Override
    public TileEntity createTileEntity(World var1, int meta)
    {
        return new TileEntityProtocol();
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
    /**
     * Called upon block activation (right click on the block.)
     */
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
        /*if (FMLCommonHandler.instance().getEffectiveSide().isClient())
        {
            return false;
        }

        TileEntityProtocol controller = (TileEntityProtocol)world.getBlockTileEntity(par2, par3, par4);

        if (controller != null)
        {
            controller.attachPlayer(player);
            player.addChatMessage("[WarpCtrlr] Attached players: " + controller.getAttachedPlayersList());
        }

        return true;
    }*/
    	TileEntityProtocol controller = (TileEntityProtocol)world.getBlockTileEntity(x, y, z);
    	
    	if(world.isRemote) {
    		return true;
    	}else if(!player.isSneaking()) {
    			player.openGui(WarpDrive.instance, 0, world, x, y, z);
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
}