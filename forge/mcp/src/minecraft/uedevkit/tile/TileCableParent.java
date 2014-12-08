package uedevkit.tile;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import universalelectricity.api.CompatibilityModule;
import universalelectricity.api.net.IConnector;
import universalelectricity.api.net.INodeNetwork;
import universalelectricity.api.vector.Vector3;
import universalelectricity.api.vector.VectorHelper;

public abstract class TileCableParent<C extends IConnector<N>, N extends INodeNetwork> extends TileEntity implements IConnector<N> {

	public static TileCableParent workAround;
	
	protected N network;
	
	protected Object[] connections = new Object[6];
	
	@Override
	public void setNetwork(N network) {
		this.network = network;
	}

	@Override
	public boolean canConnect(ForgeDirection from, Object source) {
		return CompatibilityModule.canConnect(source, from.getOpposite(), this);
	}

	@Override
    public TileEntity[] getConnections() {
        TileEntity[] connections = new TileEntity[6];

        if (worldObj != null) {
            for (byte i = 0; i < 6; i++) {
                ForgeDirection side = ForgeDirection.getOrientation(i);
                TileEntity tileEntity = VectorHelper.getTileEntityFromSide(worldObj, new Vector3(this), side);

                if (isCurrentlyConnected(side)) {
                    connections[i] = tileEntity;
                }
            }

        }
        return connections;
    }
	
    /** Bitmask connections */
    public byte currentWireConnections = 0x00;
    public byte currentAcceptorConnections = 0x00;

	public boolean isCurrentlyConnected(ForgeDirection side) {
        return connectionMapContainsSide(getAllCurrentConnections(), side);
	}

    public static boolean connectionMapContainsSide(byte connections, ForgeDirection side) {
        byte tester = (byte) (1 << side.ordinal());
        return ((connections & tester) > 0);
    }

	public byte getAllCurrentConnections() {
        return (byte) (currentWireConnections | currentAcceptorConnections);
    }
	
    public void refresh() {
        if (!worldObj.isRemote) {
            byte possibleWireConnections = getPossibleWireConnections();
            byte possibleAcceptorConnections = getPossibleAcceptorConnections();

            if (possibleWireConnections != this.currentWireConnections) {
                byte or = (byte) (possibleWireConnections | this.currentWireConnections);

                // Connections have been removed
                if (or != possibleWireConnections) {
                    this.getNetwork().removeConnector(this);
                    this.getNetwork().split(this);
                    setNetwork(null);
                }

                for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
                    if (connectionMapContainsSide(possibleWireConnections, side)) {
                        TileEntity tileEntity = VectorHelper.getConnectorFromSide(worldObj, new Vector3(this), side, this);

                        if (getConnector(tileEntity) != null) {
                            getNetwork().merge(getConnector(tileEntity).getNetwork());
                        }
                    }
                }

                this.currentWireConnections = possibleWireConnections;
            }

            this.currentAcceptorConnections = possibleAcceptorConnections;
            this.getNetwork().reconstruct();
            this.sendConnectionUpdate();
        }

        worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
    }
    
	public void sendConnectionUpdate() {
		final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		final DataOutputStream data = new DataOutputStream(bytes);
		
		try {
			data.writeByte(currentWireConnections);
			data.writeByte(currentAcceptorConnections);

		} catch (final IOException e) {
			e.printStackTrace();
		}

		final Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "SA_UpdateCables";
		packet.data = bytes.toByteArray();
		packet.length = packet.data.length;
	}

	/** CONNECTION LOGIC CODE */
    protected abstract boolean canConnectTo(TileEntity tile, ForgeDirection to);
    
    /** OTHER CONNECTION LOGIC CODE */
    protected abstract C getConnector(TileEntity tile);
    
    public byte getPossibleWireConnections() {
        byte connections = 0x00;

        for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
            TileEntity tileEntity = VectorHelper.getTileEntityFromSide(worldObj, new Vector3(this), side);

            if (getConnector(tileEntity) != null && canConnectBothSides(tileEntity, side)) {
                connections |= 1 << side.ordinal();
            }
        }

        return connections;
    }

    public byte getPossibleAcceptorConnections() {
        byte connections = 0x00;

        for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
            TileEntity tileEntity = VectorHelper.getTileEntityFromSide(worldObj, new Vector3(this), side);

            if (canConnectTo(tileEntity, side) && canConnectBothSides(tileEntity, side)) {
                connections |= 1 << side.ordinal();
            }
        }

        return connections;
    }
    
    public boolean canConnectBothSides(TileEntity tile, ForgeDirection side) {
        boolean notPrevented = !isConnectionPrevented(tile, side);

        if (getConnector(tile) != null) {
            notPrevented &= getConnector(tile).canConnect(side.getOpposite(), this);
        }

        return notPrevented;
    }
    
    /** Override if there are ways of preventing a connection
     * 
     * @param tile The TileEntity on the given side
     * @param side The side we're checking
     * @return Whether we're preventing connections on given side or to given tileEntity */
    public boolean isConnectionPrevented(TileEntity tile, ForgeDirection side) {
        return (!this.canConnectTo(tile, side));
    }

	@Override
	public IConnector<N> getInstance(ForgeDirection dir) {
		return this;
	}
}