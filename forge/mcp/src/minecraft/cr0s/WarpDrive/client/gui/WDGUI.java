package cr0s.WarpDrive.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cr0s.WarpDrive.common.container.ContainerCloakingDeviceCore;
import cr0s.WarpDrive.common.container.ContainerMiningLaser;
import cr0s.WarpDrive.common.container.ContainerProtocol;
import cr0s.WarpDrive.common.container.ContainerRadar;
import cr0s.WarpDrive.common.container.ContainerReactor;
import cr0s.WarpDrive.tile.TileEntityCloakingDeviceCore;
import cr0s.WarpDrive.tile.TileEntityMiningLaser;
import cr0s.WarpDrive.tile.TileEntityProtocol;
import cr0s.WarpDrive.tile.TileEntityRadar;
import cr0s.WarpDrive.tile.TileEntityReactor;

public class WDGUI implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity tile_entity = world.getBlockTileEntity(x, y, z);
		
		switch(ID) {
			case 0: return new ContainerProtocol(player.inventory, (TileEntityProtocol) tile_entity);
			case 1: return new ContainerReactor(player.inventory, (TileEntityReactor) tile_entity);
			case 2: return new ContainerRadar(player.inventory, (TileEntityRadar) tile_entity);
			case 3: return new ContainerCloakingDeviceCore(player.inventory, (TileEntityCloakingDeviceCore) tile_entity);
			case 4: return new ContainerMiningLaser(player.inventory, (TileEntityMiningLaser) tile_entity);
			}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity tile_entity = world.getBlockTileEntity(x, y, z);
		
		switch(ID) {
			case 0: return new GUIProtocol(player.inventory, (TileEntityProtocol) tile_entity);
			case 1: return new GUIReactor(player.inventory, (TileEntityReactor) tile_entity);
			case 2: return new GUIRadar(player.inventory, (TileEntityRadar) tile_entity);
			case 3: return new GUICloakingDeviceCore(player.inventory, (TileEntityCloakingDeviceCore) tile_entity);
			case 4: return new GUIMiningLaser(player.inventory, (TileEntityMiningLaser) tile_entity);
			}
		return null;
	}

}
