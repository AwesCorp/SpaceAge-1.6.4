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
		
		int var3;
		
		//Main inventory area
		for (var3 = 0; var3 < 3; ++var3) {
            for (int var4 = 0; var4 < 9; ++var4) { //inventory, slot number, x position, y position 
                this.addSlotToContainer(new Slot(player, var4 + var3 * 9 + 9, /*8*/48 + var4 * 18, /*84*/174 + var3 * 18));
            }
        }
		
		//Hotbar area
        for (var3 = 0; var3 < 9; ++var3) { //inventory, slot number, vertical position, horizontal position 
            this.addSlotToContainer(new Slot(player, var3, /*8*/48 + var3 * 18, /*142*/232));
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
