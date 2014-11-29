package spaceage.integration;
import cpw.mods.fml.common.Loader;

public class IntegrationManager {
	
	public static AEIntegration ae;
	public static RotaryIntegration rotary;
	
	public void init() {
		if(Loader.isModLoaded("AppliedEnergistics")) {
			ae.getAEItems();
		}
		
		if(Loader.isModLoaded("RotaryCraft")) {
			rotary.getRotaryItems();
		}
	}
}
