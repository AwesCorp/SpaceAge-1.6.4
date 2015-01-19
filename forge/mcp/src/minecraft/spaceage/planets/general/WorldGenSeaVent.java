package spaceage.planets.general;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenSeaVent extends WorldGenerator {
	
	private int blockID;
	private int numberOfBlocks;
	private int blockIDToReplace;
	private int blockMetaToReplace;
	
	public WorldGenSeaVent(int id, int howMany, int blockIDToReplace) {
		this.blockID = id;
		this.numberOfBlocks = howMany;
		this.blockIDToReplace = blockIDToReplace;
		this.blockMetaToReplace = 0;
	}
	
	public WorldGenSeaVent(int id, int howMany, int blockIDToReplace, int blockMetaToReplace) {
		this.blockID = id;
		this.numberOfBlocks = howMany;
		this.blockIDToReplace = blockIDToReplace;
		this.blockMetaToReplace = blockMetaToReplace;
	}

	@Override
	public boolean generate(World world, Random random, int x, int y, int z) {
		return false;
	}
}