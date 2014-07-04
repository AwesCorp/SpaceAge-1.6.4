package spaceage.common.tile;

import java.util.EnumSet;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import spaceage.common.SpaceAgeCore;
import uedevkit.tile.TileElectricBase;
import universalelectricity.api.energy.EnergyStorageHandler;

public class TileSolarPanel extends TileElectricBase {
	
	public TileSolarPanel() {
		super(SpaceAgeCore.SOLAR_CAPACITY, 0, SpaceAgeCore.SOLAR_ENERGY);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		
		this.produce();
		
		if (!this.worldObj.isRemote) {
			if (this.worldObj.canBlockSeeTheSky(xCoord, yCoord + 1, zCoord) && !this.worldObj.provider.hasNoSky) {
				if (this.worldObj.isDaytime()) {
					if (!(this.worldObj.isThundering() || this.worldObj.isRaining())) {
							if(!this.energy.isFull()) {
							Long produced = Long.valueOf(SpaceAgeCore.SOLAR_ENERGY);
							this.produceEnergy(produced);
						}
					}
				}
			}
		}
	}
	
    /** The electrical input direction.
     * 
     * @return The direction that electricity is entered into the tile. Return null for no input. By default you can accept power from all sides. */
    public EnumSet<ForgeDirection> getInputDirections()
    {
        return EnumSet.noneOf(ForgeDirection.class);
    }

    /** The electrical output direction.
     * 
     * @return The direction that electricity is output from the tile. Return null for no output. By default it will return an empty EnumSet. */
    public EnumSet<ForgeDirection> getOutputDirections()
    {
        return EnumSet.allOf(ForgeDirection.class);
    }
}
