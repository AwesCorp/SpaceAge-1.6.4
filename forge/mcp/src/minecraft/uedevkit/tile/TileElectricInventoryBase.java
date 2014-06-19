package uedevkit.tile;

import java.util.EnumSet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import universalelectricity.api.CompatibilityModule;
import universalelectricity.api.UniversalClass;
import universalelectricity.api.energy.EnergyStorageHandler;
import universalelectricity.api.energy.IEnergyContainer;
import universalelectricity.api.energy.IEnergyInterface;
import universalelectricity.api.vector.Vector3;

public abstract class TileElectricInventoryBase extends TileElectricBase implements ISidedInventory {
	
	public ItemStack[] inventory;
	
    public TileElectricInventoryBase()
    {
        this(0);
    }

    public TileElectricInventoryBase(long capacity)
    {
        this(capacity, capacity, capacity);
    }

    public TileElectricInventoryBase(long energyCapacity, long transferRate)
    {
        energy = new EnergyStorageHandler(energyCapacity, transferRate);
    }

    public TileElectricInventoryBase(long capacity, long maxReceive, long maxExtract)
    {
        energy = new EnergyStorageHandler(capacity, maxReceive, maxExtract);
    }

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return true;
	}
	
    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        this.energy.readFromNBT(nbt);
    }
    
    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        this.energy.writeToNBT(nbt);
    }
	
}