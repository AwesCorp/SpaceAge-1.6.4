package uedevkit.tile;

import net.minecraftforge.common.ForgeDirection;
import universalelectricity.api.UniversalElectricity;
import universalelectricity.api.energy.IConductor;
import universalelectricity.api.energy.IEnergyNetwork;
import universalelectricity.api.net.IConnector;

public abstract class TileCableBase implements IConductor { //TODO

	@Override
	public Object[] getConnections() {
		return null;
	}

	@Override
	public IConnector<IEnergyNetwork> getInstance(ForgeDirection dir) {
		return this;
	}

	@Override
	public IEnergyNetwork getNetwork() {
		return null;
	}

	@Override
	public void setNetwork(IEnergyNetwork network) {
		
	}

	@Override
	public boolean canConnect(ForgeDirection from, Object source) {
		return true;
	}

	@Override
	public long onReceiveEnergy(ForgeDirection from, long receive,
			boolean doReceive) {
		return 0; //TODO
	}

	@Override
	public long onExtractEnergy(ForgeDirection from, long extract,
			boolean doExtract) {
		return 0; //TODO
	}
}
