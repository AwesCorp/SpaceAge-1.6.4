package spaceage.integration;
import cpw.mods.fml.common.Loader;

public class IntegrationManager {
	
	public static AEIntegration ae = new AEIntegration();
	public static RotaryIntegration rotary = new RotaryIntegration();
	
	public void init() {
		if(Loader.isModLoaded("AppliedEnergistics")) {
			ae.getAEItems();
		}
		
		if(Loader.isModLoaded("RotaryCraft")) {
			rotary.getRotaryItems();
		}
	}
}
