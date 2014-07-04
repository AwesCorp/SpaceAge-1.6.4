package cr0s.WarpDrive;

import java.util.Random;

import spaceage.common.SpaceAgeCore;

public class WorldGenStructure
{
	public static int getStoneBlock(boolean corrupted, Random rand)
	{
		if (corrupted && (rand.nextInt(15) == 1))
			return 0;
		return SpaceAgeCore.spaceshipAlloyMeta.blockID;
	}

	public static int getGlassBlock(boolean corrupted, Random rand)
	{
		if (corrupted && (rand.nextInt(30) == 1))
			return 0;
		return SpaceAgeCore.tintedGlass.blockID;
	}
}
