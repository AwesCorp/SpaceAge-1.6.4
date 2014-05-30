package spaceage.client;

import java.util.EnumSet;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.world.World;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import spaceage.client.model.ModelStarboost;
import spaceage.common.CommonProxy;
import spaceage.common.PlayerTickHandler;

public class ClientProxy extends CommonProxy {
	
	private static final ModelStarboost advancedSpacesuitChestplate = new ModelStarboost(1.0F);
	private static final ModelStarboost advancedSpacesuitLeggings = new ModelStarboost(0.5F);

	@Override
	public ModelBiped getArmorModel(int id){
	switch (id) {
	case 0:
	return advancedSpacesuitChestplate;
	case 1:
	return advancedSpacesuitLeggings;
	default:
	break;
	}
	return advancedSpacesuitChestplate; //default, if whenever you should have passed on a wrong id
	}
	
	@Override
	public void registerRenderers() {
		
	}
	
	public void load() {
		TickRegistry.registerTickHandler(new SpaceAgeHUDHandler(), Side.CLIENT);
		//TickRegistry.registerTickHandler(new SpaceAgeTickHandler(EnumSet.of(TickType.CLIENT)), Side.CLIENT);
	}
	
	  public void registerHandlers()
	  {
	    TickRegistry.registerTickHandler(new PlayerTickHandler(), Side.SERVER);
	    TickRegistry.registerTickHandler(new PlayerTickHandler(), Side.CLIENT);
	  }

}
