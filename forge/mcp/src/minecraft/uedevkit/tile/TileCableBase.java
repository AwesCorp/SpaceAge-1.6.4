package uedevkit.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import universalelectricity.api.UniversalElectricity;
import universalelectricity.api.energy.EnergyNetworkLoader;
import universalelectricity.api.energy.IConductor;
import universalelectricity.api.energy.IEnergyNetwork;

public class TileCableBase extends TileCableParent<IConductor, IEnergyNetwork> implements IConductor {

	//public static TileCableBase workAround;
	
	public void copyFrom(TileCableBase otherCable) {
	    this.connections = otherCable.connections;
        this.currentWireConnections = otherCable.currentWireConnections;
        this.currentAcceptorConnections = otherCable.currentAcceptorConnections;
        this.setNetwork(otherCable.getNetwork());
        this.getNetwork().setBufferFor(this, otherCable.getInstance(ForgeDirection.UNKNOWN).getNetwork().getBufferOf(otherCable));
	}
	
	@Override
	public IEnergyNetwork getNetwork() {
		if(network == null) {
			setNetwork(EnergyNetworkLoader.getNewNetwork(this));
		}
		return network;
	}

	@Override
	public long onReceiveEnergy(ForgeDirection from, long receive,
			boolean doReceive) {
		return this.getNetwork().produce(this, from.getOpposite(), receive, doReceive);
	}

	@Override
	public long onExtractEnergy(ForgeDirection from, long extract,
			boolean doExtract) {
		return 0;
	}

	@Override
	public long getCurrentCapacity() {
		// TODO
		return Long.MAX_VALUE / UniversalElectricity.DEFAULT_VOLTAGE;
	}

	@Override
	protected boolean canConnectTo(TileEntity tile, ForgeDirection to) {
		Object obj = tile;
		return canConnect(to, obj);
	}

	@Override
	protected IConductor getConnector(TileEntity tile) {
        if (tile instanceof IConductor) {
            return (IConductor) ((IConductor)tile).getInstance(ForgeDirection.UNKNOWN);
        }
        
        return null;
	}

	@Override
	public float getResistance() {
		return 0;
	}
}