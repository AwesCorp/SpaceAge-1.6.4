package spaceage.common.tile;

import java.util.ArrayList;

import com.google.common.io.ByteArrayDataInput;

import net.minecraft.block.material.Material;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.IFluidHandler;

public abstract class TileMekanismMethod extends TileEnergyDistribution implements IFluidHandler, ISidedInventory {
	
	public ItemStack[] inventory;
	
	public boolean isActive;
	
	public abstract boolean canOperate();
	
	public TileMekanismMethod(Material material) {
	}

	public void handlePacketData(ByteArrayDataInput dataStream) {
		super.handlePacketData(dataStream);
	}

	public ArrayList getNetworkedData(ArrayList data) {
		return data;
		
	}

}
