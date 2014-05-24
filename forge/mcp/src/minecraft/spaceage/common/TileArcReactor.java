//Credit to RotaryCraft
package spaceage.common;

import universalelectricity.api.UniversalClass;
import universalelectricity.api.electricity.IVoltageOutput;
import universalelectricity.api.energy.IEnergyInterface;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

@UniversalClass
public class TileArcReactor extends TileEntity implements IEnergyInterface, IVoltageOutput {
	
	/*@Override
	public MachineRegistry getMachine() {
		return MachineRegistry.GENERATOR;
	}*/

	@Override
	public boolean canConnect(ForgeDirection from, Object source) {
		return false;
	}

	@Override
	public long onReceiveEnergy(ForgeDirection from, long receive, boolean doReceive) {
		return 0;
	}

	@Override
	public long onExtractEnergy(ForgeDirection from, long extract, boolean doExtract) {
		return 0;
	}

	@Override
	public long getVoltageOutput(ForgeDirection side) {
		return 0;
	}

}
