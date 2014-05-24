package spaceage.common;

import universalelectricity.api.UniversalElectricity;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockArcReactor extends BlockContainer {

	protected BlockArcReactor(int id, Material material) {
		super(id, UniversalElectricity.machine);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return null;
	}

}
