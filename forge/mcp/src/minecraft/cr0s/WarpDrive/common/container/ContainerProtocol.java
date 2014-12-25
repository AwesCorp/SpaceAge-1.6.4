package cr0s.WarpDrive.common.container;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cr0s.WarpDrive.tile.TileEntityProtocol;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
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
	
	@Override
	public void addCraftingToCrafters(ICrafting player) {
		super.addCraftingToCrafters(player);
		
		player.sendProgressBarUpdate(this, 0, tileEntity.getFront());
		player.sendProgressBarUpdate(this, 1, tileEntity.getBack());
		player.sendProgressBarUpdate(this, 2, tileEntity.getLeft());
		//System.out.println("LEFT CONTAINER ADDCRAFTINGTOCRAFTERS REGISTERED");
		player.sendProgressBarUpdate(this, 3, tileEntity.getRight());
		player.sendProgressBarUpdate(this, 4, tileEntity.getUp());
		player.sendProgressBarUpdate(this, 5, tileEntity.getDown());
		player.sendProgressBarUpdate(this, 6, tileEntity.getUseableMode());
		player.sendProgressBarUpdate(this, 7, tileEntity.getDistance());
		player.sendProgressBarUpdate(this, 8, tileEntity.antiTransposedDirection());
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int id, int data) {
		switch(id) {
			case 0:
				tileEntity.setFront(data);
				break;
			case 1:
				tileEntity.setBack(data);
				break;
			case 2:
				tileEntity.setLeft(data);
				break;
			case 3:
				tileEntity.setRight(data);
				break;
			case 4:
				tileEntity.setUp(data);
				break;
			case 5:
				tileEntity.setDown(data);
				break;
			case 6:
				tileEntity.setMode(tileEntity.getTranscribedMode(data));
				break;
			case 7:
				tileEntity.setJumpDistance(data);
				break;
			case 8:
				tileEntity.setDirection(tileEntity.transposedDirection(data));
				break;
		}
	}
	
	private int oldFrontHeight;
	private int oldBackHeight;
	private int oldLeftHeight;
	private int oldRightHeight;
	private int oldUpHeight;
	private int oldBottomHeight;
	private int oldMode;
	private int oldDistance;
	private int oldDir;
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for(Object player : crafters) {
			if(tileEntity.getFront() != oldFrontHeight) {
				((ICrafting)player).sendProgressBarUpdate(this, 0, tileEntity.getFront());
			} 
			if(tileEntity.getBack() != oldBackHeight) {
				((ICrafting)player).sendProgressBarUpdate(this, 1, tileEntity.getBack());
			} 
			if(tileEntity.getLeft() != oldLeftHeight) {
				((ICrafting)player).sendProgressBarUpdate(this, 2, tileEntity.getLeft());
			} 
			if(tileEntity.getRight() != oldRightHeight) {
				((ICrafting)player).sendProgressBarUpdate(this, 3, tileEntity.getRight());
			}
			if(tileEntity.getUp() != oldUpHeight) {
				((ICrafting)player).sendProgressBarUpdate(this, 4, tileEntity.getUp());
			} 
			if(tileEntity.getDown() != oldBottomHeight) {
				((ICrafting)player).sendProgressBarUpdate(this, 5, tileEntity.getDown());
			}
			if(tileEntity.getUseableMode() != oldMode) {
				((ICrafting)player).sendProgressBarUpdate(this, 6, tileEntity.getUseableMode());
			}
			if(tileEntity.getDistance() != oldDistance) {
				((ICrafting)player).sendProgressBarUpdate(this, 7, tileEntity.getDistance());
			}
			if(tileEntity.antiTransposedDirection() != oldDir) {
				((ICrafting)player).sendProgressBarUpdate(this, 8, tileEntity.antiTransposedDirection());
			}
		}
		
		oldFrontHeight = tileEntity.getFront();
		oldBackHeight = tileEntity.getBack();
		oldLeftHeight = tileEntity.getLeft();
		oldRightHeight = tileEntity.getRight();
		oldUpHeight = tileEntity.getUp();
		oldBottomHeight = tileEntity.getDown();
		oldMode = tileEntity.getUseableMode();
		oldDistance = tileEntity.getDistance();
		oldDir = tileEntity.antiTransposedDirection();
	}
}