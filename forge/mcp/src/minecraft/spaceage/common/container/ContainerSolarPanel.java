package spaceage.common.container;

import spaceage.common.SpaceAgeCore;
import spaceage.common.container.slot.SlotEnrichedSilicon;
import spaceage.common.tile.TileSolarPanel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ContainerSolarPanel extends Container {
	
	private TileSolarPanel tileEntity;
	
	public ContainerSolarPanel(InventoryPlayer player, TileSolarPanel tileEntity) {
		this.tileEntity = tileEntity;
		
		//Boost slot
		this.addSlotToContainer(new SlotEnrichedSilicon(tileEntity, 0, 56, 17));
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
		return this.tileEntity.isUseableByPlayer(entityplayer);
	}
	
	//If not overriden, crash
	@Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par1)
    {
		ItemStack enrichedSilicon = new ItemStack(SpaceAgeCore.meta,1,12);
		
        ItemStack var2 = null;
        Slot var3 = (Slot) this.inventorySlots.get(par1);

        if (var3 != null && var3.getHasStack())
        {
            ItemStack var4 = var3.getStack();
            var2 = var4.copy();

            if (par1 != 0)
            {
                if (var4.itemID == enrichedSilicon.itemID)
                {
                    if (!this.mergeItemStack(var4, 0, 1, false))
                    {
                        return null;
                    }
                }
                else if (par1 >= 30 && par1 < 37 && !this.mergeItemStack(var4, 3, 30, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(var4, 3, 37, false))
            {
                return null;
            }

            if (var4.stackSize == 0)
            {
                var3.putStack((ItemStack) null);
            }
            else
            {
                var3.onSlotChanged();
            }

            if (var4.stackSize == var2.stackSize)
            {
                return null;
            }

            var3.onPickupFromSlot(par1EntityPlayer, var4);
        }

        return var2;
    }
	
}