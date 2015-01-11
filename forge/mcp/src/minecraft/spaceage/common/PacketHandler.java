package spaceage.common;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import uedevkit.tile.TileCableBase;
import uedevkit.tile.TileCableParent;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler {
	//TileCableParent parent = new TileCableParent();
	
	@Override
	public void onPacketData(INetworkManager manager,
			Packet250CustomPayload packet, Player player) {
		if(packet.channel.equals("SpaceAge")) {
			//Stub Method TODO
		} else if(packet.channel.equals("SpaceAge_UpdateCables")) {
			handleUpdateCables(packet, (EntityPlayer)player);
		}
	}

	public void handleUpdateCables(Packet250CustomPayload packet, EntityPlayer player) {
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
		
		byte currentWireConnections;
		byte currentAcceptorConnections;
		
		try {
			currentWireConnections = inputStream.readByte();
			currentAcceptorConnections = inputStream.readByte();
			
			//TileCableParent.workAround.currentWireConnections = currentWireConnections;
			//TileCableParent.setCurrentWireConnections(currentWireConnections);
			//TileCableParent.workAround.currentAcceptorConnections = currentAcceptorConnections;
			
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
}
