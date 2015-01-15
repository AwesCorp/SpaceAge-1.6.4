package spaceage.planets.ontarine;

import spaceage.planets.general.BiomeList;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerBiomesOntarine extends GenLayer {

	protected BiomeGenBase[] allowedBiomes = { BiomeList.ontarineMain, BiomeList.ontarineReef };

	public GenLayerBiomesOntarine(long seed, GenLayer genlayer) {
		super(seed);
		this.parent = genlayer;
	}

	public GenLayerBiomesOntarine(long seed) {
		super(seed);
	}

	@Override
	public int[] getInts(int x, int z, int width, int depth) {
		int[] dest = IntCache.getIntCache(width * depth);

		for (int dz = 0; dz < depth; dz++) {
			for (int dx = 0; dx < width; dx++) {
				this.initChunkSeed(dx + x, dz + z);
				System.out.println(dest[(dx + dz * width)] + " now equals ");
				System.out.println(this.allowedBiomes[nextInt(this.allowedBiomes.length)].biomeID);//problematic right ere
				dest[(dx + dz * width)] = this.allowedBiomes[nextInt(this.allowedBiomes.length)].biomeID;
			}
		}
		return dest;
	}
}