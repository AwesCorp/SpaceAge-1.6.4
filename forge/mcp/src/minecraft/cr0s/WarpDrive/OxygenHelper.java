package cr0s.WarpDrive;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class OxygenHelper {

	public static Fluid OXYGEN;
	public static FluidStack fluidstack_OXYGEN;
	
	public static FluidStack getOxygen(int amount) {
		FluidStack stack = new FluidStack(OXYGEN, amount);
		return stack;
	}

	public static int getAmount(FluidStack liquidStack) {
		if (liquidStack != null) {
			return liquidStack.amount;
		}
		return 0;
	}

	public static int getAmount(FluidTank oxygenTank) {
		if (oxygenTank != null) {
			return getAmount(oxygenTank.getFluid());
		}

		return 0;
	}
	
}
