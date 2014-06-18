package uedevkit.tile;

import java.util.EnumSet;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import universalelectricity.api.CompatibilityModule;
import universalelectricity.api.UniversalClass;
import universalelectricity.api.energy.EnergyStorageHandler;
import universalelectricity.api.energy.IEnergyContainer;
import universalelectricity.api.energy.IEnergyInterface;
import universalelectricity.api.vector.Vector3;

@UniversalClass
public abstract class TileElectricBase extends TileEntity implements IEnergyInterface, IEnergyContainer
{
    protected EnergyStorageHandler energy;

    public TileElectricBase()
    {
        this(0);
    }

    public TileElectricBase(long capacity)
    {
        this(capacity, capacity, capacity);
    }

    public TileElectricBase(long energyCapacity, long transferRate)
    {
        energy = new EnergyStorageHandler(energyCapacity, transferRate);
    }

    public TileElectricBase(long capacity, long maxReceive, long maxExtract)
    {
        energy = new EnergyStorageHandler(capacity, maxReceive, maxExtract);
    }

    @Override
    public boolean canConnect(ForgeDirection direction, Object obj)
    {
        return true;
    }

    protected void consume()
    {
        for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS)
        {
            if (this.energy.getEnergy() < this.energy.getEnergyCapacity())
            {
                TileEntity tileEntity = new Vector3(this).translate(direction).getTileEntity(this.worldObj);

                if (tileEntity != null)
                {
                    long maxRecieve = this.energy.getMaxReceive();
                    long used = CompatibilityModule.extractEnergy(tileEntity, direction.getOpposite(), this.energy.receiveEnergy(maxRecieve, false), true);
                    this.energy.receiveEnergy(used, true);
                }
            }
        }
    }

    public void consumeEnergy(long amount)
    {
        if (!this.energy.isEmpty())
        {
            this.energy.setEnergy(Math.max((this.energy.getEnergy() - amount), 0));
        }
    }

    @Override
    public long getEnergy(ForgeDirection from)
    {
        return this.energy.getEnergy();
    }

    @Override
    public long getEnergyCapacity(ForgeDirection from)
    {
        return this.energy.getEnergyCapacity();
    }

    /** The electrical input direction.
     * 
     * @return The direction that electricity is entered into the tile. Return null for no input. By default you can accept power from all sides. */
    public EnumSet<ForgeDirection> getInputDirections()
    {
        return EnumSet.allOf(ForgeDirection.class);
    }

    /** The electrical output direction.
     * 
     * @return The direction that electricity is output from the tile. Return null for no output. By default it will return an empty EnumSet. */
    public EnumSet<ForgeDirection> getOutputDirections()
    {
        return EnumSet.noneOf(ForgeDirection.class);
    }

    public int getPowerRemainingScaled(int prgPixels)
    {
        Double result = Long.valueOf(this.getEnergy(ForgeDirection.UNKNOWN)).doubleValue() * Long.valueOf(prgPixels).doubleValue() / Long.valueOf(this.getEnergyCapacity(ForgeDirection.UNKNOWN)).doubleValue();
        return result.intValue();
    }

    public boolean isPowered()
    {
        return this.energy.getEnergy() > 0;
    }

    @Override
    public long onExtractEnergy(ForgeDirection from, long extract, boolean doExtract)
    {
        return this.energy.extractEnergy(extract, doExtract);
    }

    @Override
    public void onInventoryChanged()
    {
        super.onInventoryChanged();
    }

    @Override
    public long onReceiveEnergy(ForgeDirection from, long receive, boolean doReceive)
    {
        return this.energy.receiveEnergy(receive, doReceive);
    }

    protected void produce()
    {
        for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS)
        {
            if (this.energy.getEnergy() > 0)
            {
                TileEntity tileEntity = new Vector3(this).translate(direction).getTileEntity(this.worldObj);

                if (tileEntity != null)
                {
                    long used = CompatibilityModule.receiveEnergy(tileEntity, direction.getOpposite(), this.energy.extractEnergy(this.energy.getEnergy(), false), true);
                    this.energy.extractEnergy(used, true);
                }
            }
        }
    }

    public void produceEnergy(long amount)
    {
        if (!this.energy.isFull())
        {
            this.energy.setEnergy(this.energy.getEnergy() + amount);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        this.energy.readFromNBT(nbt);
    }

    @Override
    public void setEnergy(ForgeDirection from, long energy)
    {
        this.energy.setEnergy(energy);
    }

    public void setEnergyCapacity(long energy)
    {
        this.energy.setCapacity(energy);
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();

        // Accept energy if we are allowed to do so.
        if (this.energy.checkReceive())
        {
            this.consume();
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        this.energy.writeToNBT(nbt);
    }
}