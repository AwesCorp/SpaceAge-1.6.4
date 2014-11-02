package uedevkit.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import universalelectricity.api.energy.IConductor;
import universalelectricity.api.energy.IEnergyNetwork;
import universalelectricity.api.net.IConnector;

public abstract class TileCableBase extends TileEntity implements IConductor {

	@Override
	public Object[] getConnections() {
		// TODO
		return null;
	}

	@Override
	public IConnector<IEnergyNetwork> getInstance(ForgeDirection dir) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IEnergyNetwork getNetwork() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setNetwork(IEnergyNetwork network) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean canConnect(ForgeDirection from, Object source) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long onReceiveEnergy(ForgeDirection from, long receive,
			boolean doReceive) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long onExtractEnergy(ForgeDirection from, long extract,
			boolean doExtract) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getResistance() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getCurrentCapacity() {
		// TODO
		return 0;
	}
	
}