package spaceage.planets.general;

import net.minecraft.world.biome.BiomeGenBase;

public class BiomePlanetBase extends BiomeGenBase {

	public BiomePlanetBase(int par1) {
		super(par1);
		this.spawnableCaveCreatureList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableMonsterList.clear();
		this.spawnableWaterCreatureList.clear();
	}
	
}