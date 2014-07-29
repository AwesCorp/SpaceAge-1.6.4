package spaceage.integration;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.common.Loader;

public class RotaryIntegration {
	
	public static FluidStack lubricant;
	
	public void getRotaryItem() {
		if(Loader.isModLoaded("RotaryCraft")) {
			lubricant = new FluidStack(FluidRegistry.getFluid("lubricant"), 1000);	
		}
	}
}
