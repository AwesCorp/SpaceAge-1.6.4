package spaceage.common.tile;

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
}
