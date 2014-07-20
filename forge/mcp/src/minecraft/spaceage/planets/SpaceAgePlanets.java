package spaceage.planets;

import java.io.File;
import java.util.logging.Level;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.DimensionManager;
import spaceage.common.LogHelper;
import spaceage.common.SpaceAgeCore;
import spaceage.planets.hades.WorldProviderHades;
import spaceage.planets.proxy.CommonProxy;
import spaceage.planets.vulcan.WorldProviderVulcan;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

/** The planet child mod of SpaceAge.
 * 
 * @author SkylordJoel
 *
 */

@Mod(modid=SpaceAgePlanets.modid, name="SpaceAge Planets/Project Cosmos", version="Alpha", dependencies="required-after:SpaceAge;required-after:WarpDrive")
@NetworkMod(clientSideRequired=true, serverSideRequired=false/*, serverPacketHandlerSpec=@NetworkMod.SidedPacketHandler(channels={"SpaceAge_C"}, packetHandler=ServerPacketHandler.class)*/)

public class SpaceAgePlanets {

	@SidedProxy(clientSide = "spaceage.planets.proxy.ClientProxy", serverSide = "spaceage.planets.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	public static final String modid = "SpaceAgePlanets";
	
	@Instance("SpaceAgePlanets")
	public static SpaceAgePlanets instance;
	
	//Dimension ID Registry
	int vulcanID = DimensionManager.getNextFreeDimId();
	int hadesID = DimensionManager.getNextFreeDimId();
	
	//Biome ID Registry
	public static int vulcanBiomeID = 255;
	public static int vulcanVolcanoBiomeID = 256;
	
	public static int hadesBiomeID = 257;
	
	public static final Configuration config = new Configuration(new File("config/AwesCorp/SpaceAgePlanets.cfg"));
	
	public void initConfiguration(FMLInitializationEvent event) {
		config.load();
		
		config.save();
	}
	
	public void preInit(FMLPreInitializationEvent event) {
		//VOID
		LogHelperPlanet.log(Level.FINEST, "Preinitialised successfully");
	}
	
	public void Init(FMLInitializationEvent event) {
		//BLOCKS AND ITEMS ARE REGISTERED IN MAIN SPACEAGE
		
		proxy.registerRenderers();
	
		registerVulcan();
		registerHades();
		
		LogHelperPlanet.log(Level.FINEST, "Initialised successfully");
	}

	public void registerVulcan() {
		DimensionManager.registerProviderType(this.vulcanID, WorldProviderVulcan.class, true);
		DimensionManager.registerDimension(vulcanID, vulcanID);
	}
	
	public void registerHades() {
		DimensionManager.registerProviderType(this.hadesID, WorldProviderHades.class, true);
		DimensionManager.registerDimension(hadesID, hadesID);
	}
}
