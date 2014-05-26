package spaceage.common.tile;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import resonant.lib.prefab.vector.Cuboid;
import spaceage.common.SpaceAgeCore;
import universalelectricity.api.energy.EnergyStorageHandler;

public class TileHeatGeneratorTest extends TileEnergyDistribution implements IFluidHandler {
	
	@SideOnly(Side.CLIENT)
	public static Icon sside, bottom;
	
	public static final int STORED_FUEL_MAX = 2000;
	protected static final int FUEL_PER_WATER_BLOCK = 20000;
	
	protected final FluidStack waterStack = new FluidStack(FluidRegistry.WATER, (int) Math.ceil(fuelToMB(STORED_FUEL_MAX)));
	protected int storedFuel = 0;
	
	public TileHeatGeneratorTest()
	{
		super(Material.iron);
		energy = new EnergyStorageHandler(SpaceAgeCore.HEAT_ENERGY * 20);
		ioMap = 728;
		textureName = "heat_top";
		bounds = new Cuboid(0, 0, 0, 1, 0.3f, 1);
		isOpaqueCube = false;
		normalRender = false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister iconReg)
	{
		sside = iconReg.registerIcon(SpaceAgeCore.modid + ":" + "heat_side");
		bottom = iconReg.registerIcon(SpaceAgeCore.modid + ":" + "heat_bottom");
		super.registerIcons(iconReg);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		switch(side) {
			case 0: return bottom;
			default: return sside;
			}
	/*{
		if (side == 0)
		{
			return solarPanel_bottom;
		}
		else if (side == 1)
		{
			return getIcon();
		}

		return solarPanel_side;*/
	}

	@Override
	public void updateEntity() {
		boolean powered = worldObj.getBlockId(xCoord, yCoord - 1, zCoord) == Block.lavaStill.blockID;
		
		if(powered == true) {
			this.energy.receiveEnergy(SpaceAgeCore.HEAT_ENERGY, true);
			markDistributionUpdate |= produce() > 0;
				}
		
		super.updateEntity();		
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
	    int amountTaken = 0;
	    if (waterStack.isFluidEqual(resource) && (this.storedFuel < STORED_FUEL_MAX) && (resource.amount > 0)) {
	      final int amountRequired = (int) Math.ceil(fuelToMB(STORED_FUEL_MAX * 2 - storedFuel)); // get some extra free lava here to not have to refill every tick
	      amountTaken = Math.min((int) fuelToMB(Math.floor(MBToFuel(resource.amount))), amountRequired);
	      if (doFill) {
	        changeStoredFuel((int) MBToFuel(amountTaken));
	      }
	    }
	    return amountTaken;
	}
	
	protected void changeStoredFuel(final int change) {
	    if (change != 0) {
	      this.storedFuel += change;
	    }
	  }

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource,
			boolean doDrain) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return ((fluid != null) && (fluid.getID() == waterStack.fluidID));
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected static final double FUEL_PER_WATER_MB = ((double) FUEL_PER_WATER_BLOCK) / ((double) FluidContainerRegistry.BUCKET_VOLUME);  // 1 bucket contains 1 lava source block
	  protected static final int MB_CAPACITY = (int) Math.ceil(STORED_FUEL_MAX * FUEL_PER_WATER_MB);
	
	 public static double fuelToMB(final double fuel) {
		    return fuel / FUEL_PER_WATER_MB;
		  }

	  public static double MBToFuel(final double milliBuckets) {
		  	return milliBuckets * FUEL_PER_WATER_MB;
		  }
}
