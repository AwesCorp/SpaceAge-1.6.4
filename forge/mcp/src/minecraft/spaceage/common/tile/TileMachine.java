package spaceage.common.tile;

import universalelectricity.api.electricity.IVoltageInput;
import universalelectricity.api.energy.EnergyStorageHandler;
import universalelectricity.api.energy.IEnergyInterface;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

public abstract class TileMachine extends TileEntity 
implements IEnergyInterface, IVoltageInput, ISidedInventory {
	
	protected EnergyStorageHandler energy;
	
	protected boolean functioning = false;
	protected boolean enabled = true;
	
	public static final int voltage = 24000;
	
	public ItemStack[] inventory;

	@Override
	public boolean canConnect(ForgeDirection from, Object source) {
		return true;
	}

	@Override
	public long getVoltageInput(ForgeDirection direction) {
		// TODO
		return 0;
	}

	@Override
	public void onWrongVoltage(ForgeDirection direction, long voltage) {
		// TODO
		
	}

	@Override
	public long onReceiveEnergy(ForgeDirection from, long receive,
			boolean doReceive) {
		// TODO
		return 0; //Maybe hasPower = true?
	}

	@Override
	public long onExtractEnergy(ForgeDirection from, long extract,
			boolean doExtract) {
		// TODO
		return voltage;
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return true;
	}

}
