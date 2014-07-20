package spaceage.planets.general;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import spaceage.planets.SpaceAgePlanets;
import spaceage.planets.hades.BiomeGenHadesMain;
import spaceage.planets.vulcan.BiomeGenVulcanMain;
import spaceage.planets.vulcan.BiomeGenVulcanVolcano;

public class BiomeList {
	
	public static BiomeGenBase vulcanMain;
	public static BiomeGenBase vulcanVolcano;
	
	public static BiomeGenBase hadesMain;
	
	public static void init() {
		initialiseBiomes();
		
		registerBiomes();
		
		addToBiomeDictionary();
	}

	public static void addToBiomeDictionary() {
		BiomeDictionary.registerBiomeType(vulcanMain, new BiomeDictionary.Type[] { BiomeDictionary.Type.NETHER, BiomeDictionary.Type.FOREST });
		BiomeDictionary.registerBiomeType(vulcanVolcano, new BiomeDictionary.Type[] { BiomeDictionary.Type.NETHER, BiomeDictionary.Type.MOUNTAIN });
		
		BiomeDictionary.registerBiomeType(hadesMain, new BiomeDictionary.Type[] { BiomeDictionary.Type.FROZEN, BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.WASTELAND });
	}

	public static void registerBiomes() {
	}

	public static void initialiseBiomes() {
		vulcanMain = new BiomeGenVulcanMain(SpaceAgePlanets.vulcanBiomeID).setBiomeName("Vulcan").setTemperatureRainfall(2.0F, 0.0F).setDisableRain();
		vulcanVolcano = new BiomeGenVulcanVolcano(SpaceAgePlanets.vulcanVolcanoBiomeID).setBiomeName("Vulcan Volcano").setTemperatureRainfall(2.0F, 0.0F).setDisableRain();
		
		hadesMain = new BiomeGenHadesMain(SpaceAgePlanets.hadesBiomeID).setBiomeName("Hades").setTemperatureRainfall(0.0F, 0.5F).setEnableSnow();
	}

}
