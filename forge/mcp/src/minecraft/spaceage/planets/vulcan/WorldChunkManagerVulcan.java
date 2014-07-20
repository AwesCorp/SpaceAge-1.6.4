package spaceage.planets.vulcan;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Random;

import spaceage.planets.util.BiomeLayer;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import net.minecraft.world.storage.WorldInfo;

public class WorldChunkManagerVulcan extends WorldChunkManager {
  private GenLayer genBiomes;
  private GenLayer biomeIndexLayer;
  private BiomeCacheVulcan biomeCache;

  protected WorldChunkManagerVulcan()
  {
    this.biomeCache = new BiomeCacheVulcan(this);
  }

  public WorldChunkManagerVulcan(long par1, WorldType par3WorldType) {
    this();
    GenLayer[] var4 = BiomeLayer.initializeAllBiomeGenerators(par1, par3WorldType, 2);
    this.genBiomes = var4[0];
    this.biomeIndexLayer = var4[1];
  }

  public WorldChunkManagerVulcan(World par1World) {
    this(par1World.getSeed(), par1World.getWorldInfo().getTerrainType());
  }

  public BiomeGenBase getBiomeGenAt(int par1, int par2) {
    return this.biomeCache.getBiomeGenAt(par1, par2);
  }

  public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] par1ArrayOfBiomeGenBase, int par2, int par3, int par4, int par5) {
    IntCache.resetIntCache();

    if ((par1ArrayOfBiomeGenBase == null) || (par1ArrayOfBiomeGenBase.length < par4 * par5)) {
      par1ArrayOfBiomeGenBase = new BiomeGenBase[par4 * par5];
    }

    int[] var6 = this.genBiomes.getInts(par2, par3, par4, par5);

    for (int var7 = 0; var7 < par4 * par5; var7++) {
      par1ArrayOfBiomeGenBase[var7] = BiomeGenBase.biomeList[var6[var7]];
    }

    return par1ArrayOfBiomeGenBase;
  }

  public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] par1ArrayOfBiomeGenBase, int par2, int par3, int par4, int par5)
  {
    return getBiomeGenAt(par1ArrayOfBiomeGenBase, par2, par3, par4, par5, true);
  }

  public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] par1ArrayOfBiomeGenBase, int par2, int par3, int par4, int par5, boolean par6)
  {
    IntCache.resetIntCache();

    if ((par1ArrayOfBiomeGenBase == null) || (par1ArrayOfBiomeGenBase.length < par4 * par5))
    {
      par1ArrayOfBiomeGenBase = new BiomeGenBase[par4 * par5];
    }

    if ((par6) && (par4 == 16) && (par5 == 16) && ((par2 & 0xF) == 0) && ((par3 & 0xF) == 0))
    {
      BiomeGenBase[] var9 = this.biomeCache.getCachedBiomes(par2, par3);
      System.arraycopy(var9, 0, par1ArrayOfBiomeGenBase, 0, par4 * par5);
      return par1ArrayOfBiomeGenBase;
    }

    int[] var7 = this.biomeIndexLayer.getInts(par2, par3, par4, par5);

    for (int var8 = 0; var8 < par4 * par5; var8++)
    {
      par1ArrayOfBiomeGenBase[var8] = BiomeGenBase.biomeList[var7[var8]];
    }

    return par1ArrayOfBiomeGenBase;
  }

  public boolean areBiomesViable(int par1, int par2, int par3, List par4List)
  {
    IntCache.resetIntCache();
    int var5 = par1 - par3 >> 2;
    int var6 = par2 - par3 >> 2;
    int var7 = par1 + par3 >> 2;
    int var8 = par2 + par3 >> 2;
    int var9 = var7 - var5 + 1;
    int var10 = var8 - var6 + 1;
    int[] var11 = this.genBiomes.getInts(var5, var6, var9, var10);

    for (int var12 = 0; var12 < var9 * var10; var12++)
    {
      BiomeGenBase var13 = BiomeGenBase.biomeList[var11[var12]];

      if (!par4List.contains(var13)) {
        return false;
      }
    }
    return true;
  }

  public ChunkPosition findBiomePosition(int par1, int par2, int par3, List par4List, Random par5Random)
  {
    IntCache.resetIntCache();
    int var6 = par1 - par3 >> 2;
    int var7 = par2 - par3 >> 2;
    int var8 = par1 + par3 >> 2;
    int var9 = par2 + par3 >> 2;
    int var10 = var8 - var6 + 1;
    int var11 = var9 - var7 + 1;
    int[] var12 = this.genBiomes.getInts(var6, var7, var10, var11);
    ChunkPosition var13 = null;
    int var14 = 0;

    for (int var15 = 0; var15 < var10 * var11; var15++)
    {
      int var16 = var6 + var15 % var10 << 2;
      int var17 = var7 + var15 / var10 << 2;
      BiomeGenBase var18 = BiomeGenBase.biomeList[var12[var15]];

      if ((par4List.contains(var18)) && ((var13 == null) || (par5Random.nextInt(var14 + 1) == 0)))
      {
        var13 = new ChunkPosition(var16, 0, var17);
        var14++;
      }
    }

    return var13;
  }

  public void cleanupCache()
  {
    this.biomeCache.cleanupCache();
  }
}