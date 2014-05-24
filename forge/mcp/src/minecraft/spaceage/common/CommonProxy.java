package spaceage.common;

import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.world.World;

public class CommonProxy {
	
	public void load() {
		
		}
	
	public void registerRenderers() {
		
		}

	public void registerServerTickHandler() {
		TickRegistry.registerTickHandler(new ServerTickHandler(), Side.SERVER);
	}
	
	public void registerHandlers()
	  {
	    TickRegistry.registerTickHandler(new PlayerTickHandler(), Side.SERVER);
	  }
	  
	public ModelBiped getArmorModel(int id) {
		  return null;
	  }

}
