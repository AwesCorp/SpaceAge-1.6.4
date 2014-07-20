package uedevkit.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/** For any electrical block, extend this class.
 * 
 * @author SkylordJoel
 *
 */
public abstract class BlockElectricBase extends Block {

	public BlockElectricBase(int id, Material material) {
		super(id, material);
	}
	
    /** Used for creating tile entities. Supports metadata.
     *
     * @param world
     * @param metadata For a switch for creating a different tile entity for different metadata
     * @return A instance of a class extending TileEntity or a switch with multiple instances of multiple classes extending TileEntity
     */
	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return null;
	}
	
	/** Technically, every block that is electrical has to have a tile entity, so I returned true here so you don't have to.
	 * 
	 * @param metadata Used for switches for having blocks that have tile entities attached to a certain metadata. 
	 * @return True in this case, but otherwise either if it's true or false, with a switch attached to the metadata. 
	 */
	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}

}
