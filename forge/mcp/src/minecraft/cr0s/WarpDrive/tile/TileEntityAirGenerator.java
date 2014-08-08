package cr0s.WarpDrive.tile;

import uedevkit.tile.TileElectricBase;
import cpw.mods.fml.common.FMLCommonHandler;
import cr0s.WarpDrive.WarpDrive;
import cr0s.WarpDrive.WarpDriveConfig;
import net.minecraftforge.common.ForgeDirection;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;

public class TileEntityAirGenerator extends TileElectricBase { //TODO receive energy
	public TileEntityAirGenerator() {
		super(MAX_ENERGY_VALUE, JOULES_MAX_RECEIVE, 0);
	}
    //public boolean addedToEnergyNet = false;

    private final static int EU_PER_AIRBLOCK = 10;
    private final static int JOULES_PER_AIRBLOCK = EU_PER_AIRBLOCK * 250;
    private final static int MAX_ENERGY_VALUE = 36 * JOULES_PER_AIRBLOCK;
    private final static int JOULES_MAX_RECEIVE = JOULES_PER_AIRBLOCK * 5;
    //private int currentEnergyValue;

    private int cooldownTicks = 0;
    private final float AIR_POLLUTION_INTERVAL = 4; // seconds

    private final int START_CONCENTRATION_VALUE = 15;
    
	int currentEnergyValue = this.getPowerInt();
	
	public void setInfo() {
		this.energy.setCapacity(MAX_ENERGY_VALUE);
		//this.energy.setMaxReceive(JOULES_MAX_RECEIVE);
		//this.energy.setMaxExtract(0);
		this.energy.setMaxTransfer(JOULES_MAX_RECEIVE);
	}

    @Override
    public void updateEntity() {

        /*if (FMLCommonHandler.instance().getEffectiveSide().isClient())
        {
            return;
        }

        if (!addedToEnergyNet && !this.tileEntityInvalid)
        {
            MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
            addedToEnergyNet = true;
        }*/ //IC2 Stuff

        // Air generator works only in spaces
        if (worldObj.provider.dimensionId != WarpDrive.instance.spaceDimID && worldObj.provider.dimensionId != WarpDrive.instance.hyperSpaceDimID)
        {
            return;
        }

        if (currentEnergyValue > JOULES_PER_AIRBLOCK)
        {
            if (cooldownTicks++ > AIR_POLLUTION_INTERVAL * 20)
            {
                cooldownTicks = 0;
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 2); // set enabled texture
                releaseAir();
            }
        }
        else
        {
            if (cooldownTicks++ > 20)
            {
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 2); // set disabled texture
                cooldownTicks = 0;
            }
        }
    }

    private void releaseAir()
    {
        if (worldObj.isAirBlock(xCoord + 1, yCoord, zCoord) && (currentEnergyValue - JOULES_PER_AIRBLOCK >= 0))
        {
            worldObj.setBlock(xCoord + 1, yCoord, zCoord, WarpDriveConfig.i.airID, START_CONCENTRATION_VALUE, 2);
            //this.energy.extractEnergy(JOULES_PER_AIRBLOCK, true);
            this.energy.extractEnergy(JOULES_PER_AIRBLOCK, true);
        }

        if (worldObj.isAirBlock(xCoord - 1, yCoord, zCoord) && (currentEnergyValue - JOULES_PER_AIRBLOCK >= 0))
        {
            worldObj.setBlock(xCoord - 1, yCoord, zCoord, WarpDriveConfig.i.airID, START_CONCENTRATION_VALUE, 2);
            this.energy.extractEnergy(JOULES_PER_AIRBLOCK, true);
        }

        if (worldObj.isAirBlock(xCoord, yCoord + 1, zCoord) && (currentEnergyValue - JOULES_PER_AIRBLOCK >= 0))
        {
            worldObj.setBlock(xCoord, yCoord + 1, zCoord, WarpDriveConfig.i.airID, START_CONCENTRATION_VALUE, 2);
            this.energy.extractEnergy(JOULES_PER_AIRBLOCK, true);
        }

        if (worldObj.isAirBlock(xCoord, yCoord - 1, zCoord) && (currentEnergyValue - JOULES_PER_AIRBLOCK >= 0))
        {
            worldObj.setBlock(xCoord, yCoord - 1, zCoord, WarpDriveConfig.i.airID, START_CONCENTRATION_VALUE, 2);
            this.energy.extractEnergy(JOULES_PER_AIRBLOCK, true);
        }

        if (worldObj.isAirBlock(xCoord, yCoord, zCoord + 1) && (currentEnergyValue - JOULES_PER_AIRBLOCK >= 0))
        {
            worldObj.setBlock(xCoord, yCoord, zCoord + 1, WarpDriveConfig.i.airID, START_CONCENTRATION_VALUE, 2);
            this.energy.extractEnergy(JOULES_PER_AIRBLOCK, true);
        }

        if (worldObj.isAirBlock(xCoord, yCoord, zCoord - 1) && (currentEnergyValue - JOULES_PER_AIRBLOCK >= 0))
        {
            worldObj.setBlock(xCoord, yCoord, zCoord - 1, WarpDriveConfig.i.airID, START_CONCENTRATION_VALUE, 2);
            this.energy.extractEnergy(JOULES_PER_AIRBLOCK, true);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        //this.currentEnergyValue = tag.getInteger("energy");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        //tag.setInteger("energy", this.getCurrentEnergyValue());
    }

    // IEnergySink methods implementation
/*    @Override
    public double demandedEnergyUnits()
    {
        return (MAX_ENERGY_VALUE - currentEnergyValue);
    }

    @Override
    public double injectEnergyUnits(ForgeDirection directionFrom, double amount)
    {
        double leftover = 0;
        currentEnergyValue += Math.round(amount);

        if (getCurrentEnergyValue() > MAX_ENERGY_VALUE)
        {
            leftover = (getCurrentEnergyValue() - MAX_ENERGY_VALUE);
            currentEnergyValue = MAX_ENERGY_VALUE;
        }

        return leftover;
    }

    @Override
    public int getMaxSafeInput()
    {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction)
    {
        return true;
    }*/

    /**
     * @return the currentEnergyValue
     */
    public int getCurrentEnergyValue()
    {
        return currentEnergyValue;
    }

    /*@Override
    public void onChunkUnload()
    {
        if (addedToEnergyNet)
        {
            MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
            addedToEnergyNet = false;
        }
    }*/

    /*@Override
    public void invalidate()
    {
        if (addedToEnergyNet)
        {
            MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
            addedToEnergyNet = false;
        }

        super.invalidate();
    }*/


}
