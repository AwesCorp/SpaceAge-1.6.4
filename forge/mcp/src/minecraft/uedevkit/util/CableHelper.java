package uedevkit.util;

import universalelectricity.api.net.IConnector;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

public class CableHelper {
	
	/**
	 * 
	 * @param tile
	 * @return connections adjacent
	 * @author micdoodle8
	 */
	public static TileEntity[] getAdjacentConnections(TileEntity tile) {
		TileEntity[] adjacentConnections = new TileEntity[ForgeDirection.VALID_DIRECTIONS.length];

		BlockVec3 thisVec = new BlockVec3(tile);
		for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
			TileEntity tileEntity = thisVec.getTileEntityOnSide(tile.worldObj, direction);

			if (tileEntity instanceof IConnector) {
				if (((IConnector) tileEntity).canConnect(direction.getOpposite(), ((IConnector) tileEntity))) {
					adjacentConnections[direction.ordinal()] = tileEntity;
				}
			}
		}
		return adjacentConnections;
	}
}
