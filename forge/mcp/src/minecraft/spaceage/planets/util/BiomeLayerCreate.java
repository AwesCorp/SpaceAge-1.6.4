package spaceage.planets.util;

import com.google.common.base.Optional;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.IntCache;

public class BiomeLayerCreate extends BiomeLayer
{
  //private final int ABYSS_CHANCE = 2;
  //private final int CORAL_CHANCE = 5;
  //private final int KELP_CHANCE = 5;

  public BiomeLayerCreate(long par1, boolean o)
  {
    super(par1);
  }

  public int[] getInts(int par1, int par2, int par3, int par4)
  {
    int[] var5 = IntCache.getIntCache(par3 * par4);

    //boolean ocean = BOPConfigurationBiomeGen.oceanGen;
    //boolean abyss = Biomes.oceanAbyss.isPresent();
    //boolean coral = Biomes.oceanCoral.isPresent();
    //boolean kelp = Biomes.oceanKelp.isPresent();

    for (int var6 = 0; var6 < par4; var6++)
    {
      for (int var7 = 0; var7 < par3; var7++)
      {
        initChunkSeed(par1 + var7, par2 + var6);
          var5[(var7 + var6 * par3)] = 1;
      }
    }

    if ((par1 > -par3) && (par1 <= 0) && (par2 > -par4) && (par2 <= 0))
    {
      var5[(-par1 + -par2 * par3)] = 1;
    }

    return var5;
  }
}