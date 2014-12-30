package cr0s.WarpDrive.common.container;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cr0s.WarpDrive.tile.TileEntityCloakingDeviceCore;
import cr0s.WarpDrive.tile.TileEntityProtocol;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCloakingDeviceCore extends Container {
	
	private TileEntityCloakingDeviceCore tileEntity;
	
	public ContainerCloakingDeviceCore(InventoryPlayer player, TileEntityCloakingDeviceCore tileEntity) {
		this.tileEntity = tileEntity;
		
		int var3;
		
		//Main inventory area
		for (var3 = 0; var3 < 3; ++var3) {
            for (int var4 = 0; var4 < 9; ++var4) {
                this.addSlotToContainer(new Slot(player, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
            }
        }
		
		//Hotbar area
        for (var3 = 0; var3 < 9; ++var3) {
            this.addSlotToContainer(new Slot(player, var3, 8 + var3 * 18, 142));
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
	
	@Override
	public void addCraftingToCrafters(ICrafting player) {
		super.addCraftingToCrafters(player);
		
		player.sendProgressBarUpdate(this, 0, tileEntity.tier);
		player.sendProgressBarUpdate(this, 1, tileEntity.frequency);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int id, int data) {
		switch(id) {
			case 0:
				tileEntity.setFieldTier(data);
				break;
			case 1:
				tileEntity.setFieldFrequency(data);
				break;
		}
	}
	
	private int oldTier;
	private int oldFreq;
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for(Object player : crafters) {
			if(tileEntity.tier != oldTier) {
				((ICrafting)player).sendProgressBarUpdate(this, 0, tileEntity.tier);
			} 
			if(tileEntity.frequency != oldFreq) {
				((ICrafting)player).sendProgressBarUpdate(this, 1, tileEntity.frequency);
			} 
		}
		
		oldTier = tileEntity.tier;
		oldFreq = tileEntity.frequency;
	}
}