package spaceage.common.tile;

import java.util.EnumSet;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import universalelectricity.api.CompatibilityModule;
import universalelectricity.api.UniversalClass;
import universalelectricity.api.electricity.IVoltageOutput;
import universalelectricity.api.energy.EnergyStorageHandler;
import universalelectricity.api.energy.IEnergyContainer;
import universalelectricity.api.energy.IEnergyInterface;
import universalelectricity.api.vector.Vector3;

@UniversalClass
public abstract class TileGenerator extends TileEntity implements IEnergyInterface, IVoltageOutput {
	
	protected EnergyStorageHandler energy;
	
	protected boolean functioning = false;
	protected boolean enabled = true;
	
	public static final int voltage = 24000;
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		if(!this.worldObj.isRemote) {
			this.functioning = this.isFunctioning();
		}
	}
	
	@Override
	public boolean canConnect(ForgeDirection from, Object source) {
		return true;
	}

	@Override
	public long getVoltageOutput(ForgeDirection side) {
		return voltage;
	}

	@Override
	public long onExtractEnergy(ForgeDirection from, long extract,
			boolean doExtract) {
		return voltage;
	}
	
	@Override
	public long onReceiveEnergy(ForgeDirection from, long receive,
			boolean doReceive) {
		return 0;
	}
	
    protected void produce() {
        for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
            if (this.energy.getEnergy() > 0) {
                TileEntity tileEntity = new Vector3(this).translate(direction).getTileEntity(this.worldObj);

                if (tileEntity != null) {
                    long used = CompatibilityModule.receiveEnergy(tileEntity, direction.getOpposite(), this.energy.extractEnergy(this.energy.getEnergy(), false), true);
                    this.energy.extractEnergy(used, true);
                }
            }
        }
    }

    public void produceEnergy(long amount) {
        if (!this.energy.isFull()) {
            this.energy.setEnergy(this.energy.getEnergy() + amount);
        }
    }
    
    public boolean isFunctioning() {
        if (this.worldObj.isRemote) {
            return this.functioning;
        }else {
            return this.canFunction();
        }
    }
    
    public boolean canFunction() {
        return this.enabled;
    }
    
    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.energy.readFromNBT(nbt);
    }
    
    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        this.energy.writeToNBT(nbt);
    }
}
