package spaceage.common.container;

import spaceage.common.SpaceAgeCore;
import spaceage.common.container.slot.SlotFluidInput;
import spaceage.common.container.slot.SlotFluidOutput;
import spaceage.common.tile.TileGasTank;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ContainerGasTank extends Container {
	
	private TileGasTank tileEntity;
	
	public ContainerGasTank(InventoryPlayer player, TileGasTank tileEntity) {
		this.tileEntity = tileEntity;

		this.addSlotToContainer(new SlotFluidInput(tileEntity, 0, 56, 17));
		this.addSlotToContainer(new SlotFluidOutput(tileEntity, 1, 56, 45));
		
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
	
	//If not overriden, crash
	@Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par1) {
		return null;
    }
	
}