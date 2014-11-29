package spaceage.planets.eden;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenEdenTrees extends WorldGenerator {

	public WorldGenEdenTrees(boolean par1) {
		super(par1);
	}
	
	@Override
	public boolean generate(World world, Random random, int x, int y, int z) {
		return false;
	}
}
