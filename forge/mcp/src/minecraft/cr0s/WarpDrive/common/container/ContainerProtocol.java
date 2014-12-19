package cr0s.WarpDrive.common.container;

import cr0s.WarpDrive.tile.TileEntityProtocol;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerProtocol extends Container {
	
	private TileEntityProtocol tileEntity;
	
	public ContainerProtocol(InventoryPlayer player, TileEntityProtocol tileEntity) {
		this.tileEntity = tileEntity;
		
		int across;
		
		//Main inventory area
		for (across = 0; across < 3; ++across) {
            for (int vertical = 0; vertical < 9; ++vertical) { //inventory, slot number, x position, y position 
                this.addSlotToContainer(new Slot(player, vertical + across * 9 + 9, 88/*48*/ + vertical * 18, /*84, 174*/174 + across * 18));
            }
        }
		
		//Hotbar area
        for (across = 0; across < 9; ++across) { //inventory, slot number, vertical position, horizontal position 
            this.addSlotToContainer(new Slot(player, across, /*48*/88 + across * 18, /*142, 232,187*/232));
        }
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return true;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int par2) {
		return null;
	}
}