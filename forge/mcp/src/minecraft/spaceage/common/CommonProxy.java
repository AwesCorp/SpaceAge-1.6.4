package spaceage.common;

import spaceage.common.tile.TileSolarPanel;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.world.World;

/**
 * The CommonProxy of SpaceAge. 
 * @author SkylordJoel
 */

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
	  
	/*public ModelBiped getFemaleBinaryArmorModel(int id) {
		  return null;
	  }*/

	public ModelBiped getStarboostArmorModel(int id) {
		return null;
	}

	public int getBlockRenderID(int blockID) {
		return -1;
	}
}
