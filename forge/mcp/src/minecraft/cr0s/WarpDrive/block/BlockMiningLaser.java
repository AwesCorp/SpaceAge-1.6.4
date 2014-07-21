package cr0s.WarpDrive.block;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cr0s.WarpDrive.WarpDrive;
import cr0s.WarpDrive.tile.TileEntityMiningLaser;
import cr0s.WarpDrive.tile.TileEntityProtocol;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockMiningLaser extends Block
{
    private Icon[] iconBuffer;

    private final int ICON_SIDE = 0;

    public BlockMiningLaser(int id, int texture, Material material)
    {
        super(id, material);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        iconBuffer = new Icon[2];
        // Solid textures
        iconBuffer[0] = par1IconRegister.registerIcon("warpdrive:particleBoosterTopBottom");
        iconBuffer[1] = par1IconRegister.registerIcon("warpdrive:miningLaserSide0");
    }

    @Override
    public Icon getIcon(int side, int metadata) {
    	switch(side) {
    		case 0:
    			return iconBuffer[0];
    		case 1:
    			return iconBuffer[0];
			default:
				return iconBuffer[metadata + 1];
    	}
        //return iconBuffer[metadata + 1];
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata)
    {
        return new TileEntityMiningLaser();
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
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
    	TileEntityMiningLaser controller = (TileEntityMiningLaser)world.getBlockTileEntity(x, y, z);
    	
    	if(world.isRemote) {
    		return true;
    	}else if(!player.isSneaking()) {
    			player.openGui(WarpDrive.instance, 4, world, x, y, z);
    		}
    	return false;
    }
}