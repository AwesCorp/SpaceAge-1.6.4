package cr0s.WarpDrive.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cr0s.WarpDrive.TileEntityProtocol;
import cr0s.WarpDrive.common.container.ContainerProtocol;

public class WDGUI implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity tile_entity = world.getBlockTileEntity(x, y, z);
		
		switch(ID) {
			case 0: return new ContainerProtocol(player.inventory, (TileEntityProtocol) tile_entity);
			}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity tile_entity = world.getBlockTileEntity(x, y, z);
		
		switch(ID) {
			case 0: return new GUIProtocol(player.inventory, (TileEntityProtocol) tile_entity);
			}
		return null;
	}

}
