package uedevkit.tile;

import java.util.EnumSet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import universalelectricity.api.CompatibilityModule;
import universalelectricity.api.UniversalClass;
import universalelectricity.api.energy.EnergyStorageHandler;
import universalelectricity.api.energy.IEnergyContainer;
import universalelectricity.api.energy.IEnergyInterface;
import universalelectricity.api.vector.Vector3;

/**
 * The base class fit for all electrical blocks. Mutliple variable constructors, no inventory. Just the basics.
 * @author SkylordJoel, Maxwolf (helping with errors)
 */

@UniversalClass
public abstract class TileElectricBase extends TileEntity implements IEnergyInterface, IEnergyContainer {
    protected EnergyStorageHandler energy;
    
    protected EntityPlayer player;

    /**
     * Used for temporary constructors. Not recommended to be used. 
     * @author SkylordJoel
     */
    public TileElectricBase() {
        this(0);
    }

    /**
     * Used for blocks that can only hold energy, and possibly output to an item. Not recommended. 
     * @author SkylordJoel
     */
    public TileElectricBase(long capacity) {
        this(capacity, capacity, capacity);
        this.energy.setCapacity(capacity);
    }

    /**
     * Used for blocks that can only hold energy, and possibly output to an item, but can adjust their transfer rate. Recommended. 
     * @author SkylordJoel
     */
    public TileElectricBase(long energyCapacity, long transferRate) {
        energy = new EnergyStorageHandler(energyCapacity, transferRate);
        this.energy.setCapacity(energyCapacity);
        this.energy.setMaxTransfer(transferRate);
    }

    /**
     * Used for blocks that can do anything - use energy, store energy, generate energy, everything. For having the block produce energy only or vice versa, put a 0 in place of the unwanted variable. Highly recommended. 
     * @author SkylordJoel
     */
    public TileElectricBase(long capacity, long maxReceive, long maxExtract) {
        energy = new EnergyStorageHandler(capacity, maxReceive, maxExtract);
        this.energy.setCapacity(capacity);
        this.energy.setMaxReceive(maxReceive);
        this.energy.setMaxExtract(maxExtract);
    }

    @Override
    public boolean canConnect(ForgeDirection direction, Object obj) {
        return true;
    }

    /**
     * Consume the energy if is allowed. Call this or consumeEnergy(long amount) in your updateEntity method. 
     * @author SkylordJoel
     */
    protected void consume() {
        for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
            if (this.energy.getEnergy() < this.energy.getEnergyCapacity()) {
                TileEntity tileEntity = new Vector3(this).translate(direction).getTileEntity(this.worldObj);

                if (tileEntity != null) {
                    long maxRecieve = this.energy.getMaxReceive();
                    long used = CompatibilityModule.extractEnergy(tileEntity, direction.getOpposite(), this.energy.receiveEnergy(maxRecieve, false), true);
                    this.energy.receiveEnergy(used, true);
                }
            }
        }
    }

    /**
     * Consumes a set amount of energy. Call this or consume() in your updateEntity method. 
     * @author SkylordJoel
     */
    public void consumeEnergy(long amount) {
        if (!this.energy.isEmpty()) {
            this.energy.setEnergy(Math.max((this.energy.getEnergy() - amount), 0));
        }
    }

    /**
     * Gets the current amount of energy from a side. Not recommended to be used, use getPowerInt instead.
     * @author SkylordJoel, Maxwolf
     */
    @Override
    public long getEnergy(ForgeDirection from) {
        return this.energy.getEnergy();
    }

    /**
     * Gets the capacity of the energy from a certain side. 
     * @author SkylordJoel
     */
    @Override
    public long getEnergyCapacity(ForgeDirection from) {
        return this.energy.getEnergyCapacity();
    }

    /** The electrical input direction.
     * 
     *Note: This is intended for machines; for a generator, you have to specify the input directions as noneOf. For a buffer (battery) block, you must specify which directions are inputs and outputs.
     * 
     * @return The direction that electricity is entered into the tile. Return null for no input. By default you can accept power from all sides. 
     * @author SkylordJoel*/
    public EnumSet<ForgeDirection> getInputDirections() {
        return EnumSet.allOf(ForgeDirection.class);
    }

    /** The electrical output direction.
     * 
     * Note: This is intended for machines; for a generator, you have to specify the output directions as allOf. For a buffer (battery) block, you must specify which directions are inputs and outputs.
     * 
     * @return The direction that electricity is output from the tile. Return null for no output. By default it will return an empty EnumSet. 
     * @author SkylordJoel*/
    public EnumSet<ForgeDirection> getOutputDirections() {
        return EnumSet.noneOf(ForgeDirection.class);
    }
    
    /** A calculator used for scaling power, usually for drawing pixels in GUIs, but also to get percentages. 
     * 
     * @param capacityScaledTo
     * @return The power currently in the block in relation to the new capacity.
     * @author SkylordJoel
     */
    public int getPowerRemainingScaled(int capacityScaledTo) {
        Double result = Long.valueOf(this.getEnergy(ForgeDirection.UNKNOWN)).doubleValue() * Long.valueOf(capacityScaledTo).doubleValue() / Long.valueOf(this.getEnergyCapacity(ForgeDirection.UNKNOWN)).doubleValue();
        return result.intValue();
    }
    
    /** The best way to get the current energy stored in the block. Does not scale or rely on input/output directions. 
     * 
     * @return The power currently in the block.
     * @author SkylordJoel
     */
    public int getPowerInt() {
    	Double result = Long.valueOf(this.getEnergy(ForgeDirection.UNKNOWN)).doubleValue();
    	return result.intValue();
    }

    /** A simple check to see whether the block has energy stored in it or not.
     * 
     * @return Whether power is in the block or not.
     * @author SkylordJoel
     */
    public boolean isPowered() {
        return this.energy.getEnergy() > 0;
    }

    @Override
    public long onExtractEnergy(ForgeDirection from, long extract, boolean doExtract) {
        return this.energy.extractEnergy(extract, doExtract);
    }

    @Override
    public void onInventoryChanged() {
        super.onInventoryChanged();
    }

    @Override
    public long onReceiveEnergy(ForgeDirection from, long receive, boolean doReceive) {
        return this.energy.receiveEnergy(receive, doReceive);
    }

    /**
     * Produces energy if is allowed. Call this or produceEnergy(long amount) in your updateEntity method. 
     * @author SkylordJoel
     */
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

    /**
     * Produces a set amount of energy. Call this or produce() in your updateEntity method. 
     * @author SkylordJoel
     */
    public void produceEnergy(long amount) {
        if (!this.energy.isFull()) {
            this.energy.setEnergy(this.energy.getEnergy() + amount);
        }
    }

    /** Reads the energy from NBT, so you don't have to specify it yourself.
     * @author SkylordJoel
     */
    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.energy.readFromNBT(nbt);
    }

    @Override
    public void setEnergy(ForgeDirection from, long energy) {
        this.energy.setEnergy(energy);
    }

    /** Basically useless method. Set your energy in your constructor. 
     * 
     * @param energy
     * @author Maxwolf
     */
    public void setEnergyCapacity(long energy) {
        this.energy.setCapacity(energy);
    }

    /** Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count ticks and creates a new spawn inside its implementation.
     * Consumes energy if allowed. 
     * @author Minecraft Team, SkylordJoel
     */
    @Override
    public void updateEntity() {
        super.updateEntity();

        // Accept energy if we are allowed to do so.
        if (this.energy.checkReceive()) {
            this.consume();
        }
    }

    /** Saves the energy to NBT, so you don't have to specify it yourself.
     * @author SkylordJoel
     */
    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        this.energy.writeToNBT(nbt);
    }
    
	public void blockDismantled() {
		blockBroken();
	}
	
	public boolean onWrench(EntityPlayer player, int hitSide) {
		return false;
	}
	
	public boolean canPlayerDismantle(EntityPlayer player) {
		return true;
	}
	
	public void blockBroken() {
	}
}