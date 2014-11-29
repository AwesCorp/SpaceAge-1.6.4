package spaceage.planets.general;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import spaceage.planets.SpaceAgePlanets;
import spaceage.planets.hades.BiomeGenHadesMain;
import spaceage.planets.technoorganic.BiomeGen0011Clearing;
import spaceage.planets.technoorganic.BiomeGen0011Main;
import spaceage.planets.vulcan.BiomeGenVulcanMain;
import spaceage.planets.vulcan.BiomeGenVulcanVolcano;

public class BiomeList {
	
	public static BiomeGenBase vulcanMain;
	public static BiomeGenBase vulcanVolcano;
	
	public static BiomeGenBase hadesMain;
	
	public static BiomeGenBase T0011Main;
	public static BiomeGenBase T0011Clearing;
	
	public static BiomeGenBase edenMain;
	
	public static BiomeGenBase ontarineMain;
	
	public static void init() {
		initialiseBiomes();
		
		registerBiomes();
		
		addToBiomeDictionary();
	}

	public static void addToBiomeDictionary() {
		BiomeDictionary.registerBiomeType(vulcanMain, new BiomeDictionary.Type[] { BiomeDictionary.Type.NETHER, BiomeDictionary.Type.FOREST });
		BiomeDictionary.registerBiomeType(vulcanVolcano, new BiomeDictionary.Type[] { BiomeDictionary.Type.NETHER, BiomeDictionary.Type.MOUNTAIN });
		
		BiomeDictionary.registerBiomeType(hadesMain, new BiomeDictionary.Type[] { BiomeDictionary.Type.FROZEN, BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.WASTELAND });
		
		BiomeDictionary.registerBiomeType(T0011Main, new BiomeDictionary.Type[] { BiomeDictionary.Type.JUNGLE });
		BiomeDictionary.registerBiomeType(T0011Clearing, new BiomeDictionary.Type[] { BiomeDictionary.Type.PLAINS });
		
		BiomeDictionary.registerBiomeType(edenMain, new BiomeDictionary.Type[] { BiomeDictionary.Type.JUNGLE });
		
		BiomeDictionary.registerBiomeType(ontarineMain, new BiomeDictionary.Type[] { BiomeDictionary.Type.WATER });
	}

	public static void registerBiomes() {
	}

	public static void initialiseBiomes() {
		vulcanMain = new BiomeGenVulcanMain(SpaceAgePlanets.vulcanBiomeID).setBiomeName("Vulcan").setTemperatureRainfall(2.0F, 0.0F).setDisableRain().setMinMaxHeight(-0.2F, 0.4F);
		vulcanVolcano = new BiomeGenVulcanVolcano(SpaceAgePlanets.vulcanVolcanoBiomeID).setBiomeName("Vulcan Volcano").setTemperatureRainfall(2.0F, 0.0F).setDisableRain().setMinMaxHeight(-0.2F, 0.4F);
		
		hadesMain = new BiomeGenHadesMain(SpaceAgePlanets.hadesBiomeID).setBiomeName("Hades").setTemperatureRainfall(0.0F, 0.5F).setEnableSnow().setMinMaxHeight(-0.2F, 0.4F);
		
		T0011Main = new BiomeGen0011Main(SpaceAgePlanets.T0011BiomeID).setBiomeName("0011").setTemperatureRainfall(0.7F, 0.0F).setDisableRain().setMinMaxHeight(-0.2F, 0.4F);
		T0011Clearing = new BiomeGen0011Clearing(SpaceAgePlanets.T0011ClearBiomeID).setBiomeName("0011 Clearing").setTemperatureRainfall(0.7F, 0.0F).setDisableRain().setMinMaxHeight(-0.2F, 0.4F);
		
		edenMain = new BiomeGenEdenMain(SpaceAgePlanets.edenBiomeID).setBiomeName("Eden");
		
		ontarineMain = new BiomeGenOntarineMain(SpaceAgePlanets.ontarineBiomeID).setBiomeName("Ontarine");
	}

}
