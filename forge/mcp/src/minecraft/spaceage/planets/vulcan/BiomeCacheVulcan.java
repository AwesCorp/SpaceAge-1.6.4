package spaceage.planets.vulcan;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.LongHashMap;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeCacheVulcan {
  private final WorldChunkManagerVulcan chunkManager;
  private long lastCleanupTime = 0L;

  private LongHashMap cacheMap = new LongHashMap();

  private List cache = new ArrayList();

  public BiomeCacheVulcan(WorldChunkManagerVulcan par1WorldChunkManager) {
    this.chunkManager = par1WorldChunkManager;
  }

  public BiomeCacheBlockVulcan getBiomeCacheBlock(int par1, int par2)
  {
    par1 >>= 4;
    par2 >>= 4;
    long var3 = par1 & 0xFFFFFFFF | (par2 & 0xFFFFFFFF) << 32;
    BiomeCacheBlockVulcan var5 = (BiomeCacheBlockVulcan)this.cacheMap.getValueByKey(var3);

    if (var5 == null)
    {
      var5 = new BiomeCacheBlockVulcan(this, par1, par2);
      this.cacheMap.add(var3, var5);
      this.cache.add(var5);
    }

    var5.lastAccessTime = System.currentTimeMillis();
    return var5;
  }

  public BiomeGenBase getBiomeGenAt(int par1, int par2)
  {
    return getBiomeCacheBlock(par1, par2).getBiomeGenAt(par1, par2);
  }

  public void cleanupCache()
  {
    long var1 = System.currentTimeMillis();
    long var3 = var1 - this.lastCleanupTime;

    if ((var3 > 7500L) || (var3 < 0L))
    {
      this.lastCleanupTime = var1;

      for (int var5 = 0; var5 < this.cache.size(); var5++)
      {
        BiomeCacheBlockVulcan var6 = (BiomeCacheBlockVulcan)this.cache.get(var5);
        long var7 = var1 - var6.lastAccessTime;

        if ((var7 > 30000L) || (var7 < 0L))
        {
          this.cache.remove(var5--);
          long var9 = var6.xPosition & 0xFFFFFFFF | (var6.zPosition & 0xFFFFFFFF) << 32;
          this.cacheMap.remove(var9);
        }
      }
    }
  }

  public BiomeGenBase[] getCachedBiomes(int par1, int par2)
  {
    return getBiomeCacheBlock(par1, par2).biomes;
  }

  static WorldChunkManagerVulcan getChunkManager(BiomeCacheVulcan par0BiomeCache)
  {
    return par0BiomeCache.chunkManager;
  }
}