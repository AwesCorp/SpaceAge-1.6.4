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
		int across;
		
		//Main inventory area
		for (across = 0; across < 3; ++across) {
            for (int vertical = 0; vertical < 9; ++vertical) {
                this.addSlotToContainer(new Slot(player, vertical + across * 9 + 9, 8 + vertical * 18, 84 + across * 18));
            }
        }
		
		//Hotbar area
        for (across = 0; across < 9; ++across) {
            this.addSlotToContainer(new Slot(player, across, 8 + across * 18, 142));
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
        Slot across = (Slot) this.inventorySlots.get(par1);

        if (across != null && across.getHasStack())
        {
            ItemStack vertical = across.getStack();
            var2 = vertical.copy();

            if (par1 != 0)
            {
                if (vertical.itemID == enrichedSilicon.itemID)
                {
                    if (!this.mergeItemStack(vertical, 0, 1, false))
                    {
                        return null;
                    }
                }
                else if (par1 >= 30 && par1 < 37 && !this.mergeItemStack(vertical, 3, 30, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(vertical, 3, 37, false))
            {
                return null;
            }

            if (vertical.stackSize == 0)
            {
                across.putStack((ItemStack) null);
            }
            else
            {
                across.onSlotChanged();
            }

            if (vertical.stackSize == var2.stackSize)
            {
                return null;
            }

            across.onPickupFromSlot(par1EntityPlayer, vertical);
        }

        return var2;
    }
	
}