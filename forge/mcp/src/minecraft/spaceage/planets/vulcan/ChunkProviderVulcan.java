package spaceage.planets.vulcan;

import com.google.common.base.Optional;
import java.util.List;
import java.util.Random;

import spaceage.common.SpaceAgeCore;
import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.EventBus;
import net.minecraftforge.event.terraingen.ChunkProviderEvent;
import net.minecraftforge.event.terraingen.ChunkProviderEvent.ReplaceBiomeBlocks;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent.Post;
import net.minecraftforge.event.terraingen.PopulateChunkEvent.Pre;

public class ChunkProviderVulcan
  implements IChunkProvider
{
  private Random endRNG;
  private World endWorld;
  private double[] densities;
  private BiomeGenBase[] biomesForGeneration;
  double[] field_4185_d;
  double[] field_4184_e;
  double[] field_4183_f;
  double[] field_4182_g;
  double[] field_4181_h;
  int[][] field_73203_h = new int[32][32];

  public ChunkProviderVulcan(World par1World, long par2)
  {
    this.endWorld = par1World;
    this.endRNG = new Random(par2);
  }

  public void generateTerrain(int par1, int par2, byte[] par3ArrayOfByte, BiomeGenBase[] par4ArrayOfBiomeGenBase) {
    byte var5 = 2;
    int var6 = var5 + 1;
    byte var7 = 33;
    int var8 = var5 + 1;
    //this.densities = initializeNoiseField(this.densities, par1 * var5, 0, par2 * var5, var6, var7, var8);

    for (int var9 = 0; var9 < var5; var9++)
    {
      for (int var10 = 0; var10 < var5; var10++)
      {
        for (int var11 = 0; var11 < 32; var11++)
        {
          double var12 = 0.25D;
          double var14 = this.densities[(((var9 + 0) * var8 + var10 + 0) * var7 + var11 + 0)];
          double var16 = this.densities[(((var9 + 0) * var8 + var10 + 1) * var7 + var11 + 0)];
          double var18 = this.densities[(((var9 + 1) * var8 + var10 + 0) * var7 + var11 + 0)];
          double var20 = this.densities[(((var9 + 1) * var8 + var10 + 1) * var7 + var11 + 0)];
          double var22 = (this.densities[(((var9 + 0) * var8 + var10 + 0) * var7 + var11 + 1)] - var14) * var12;
          double var24 = (this.densities[(((var9 + 0) * var8 + var10 + 1) * var7 + var11 + 1)] - var16) * var12;
          double var26 = (this.densities[(((var9 + 1) * var8 + var10 + 0) * var7 + var11 + 1)] - var18) * var12;
          double var28 = (this.densities[(((var9 + 1) * var8 + var10 + 1) * var7 + var11 + 1)] - var20) * var12;

          for (int var30 = 0; var30 < 4; var30++)
          {
            double var31 = 0.125D;
            double var33 = var14;
            double var35 = var16;
            double var37 = (var18 - var14) * var31;
            double var39 = (var20 - var16) * var31;

            for (int var41 = 0; var41 < 8; var41++)
            {
              int var42 = var41 + var9 * 8 << 11 | 0 + var10 * 8 << 7 | var11 * 4 + var30;
              short var43 = 128;
              double var44 = 0.125D;
              double var46 = var33;
              double var48 = (var35 - var33) * var44;

              for (int var50 = 0; var50 < 8; var50++)
              {
                int var51 = 0;

                par3ArrayOfByte[var42] = ((byte)var51);
                var42 += var43;
                var46 += var48;
              }

              var33 += var37;
              var35 += var39;
            }

            var14 += var22;
            var16 += var24;
            var18 += var26;
            var20 += var28;
          }
        }
      }
    }
  }

  public void replaceBlocksForBiome(int par1, int par2, byte[] par3ArrayOfByte, BiomeGenBase[] par4ArrayOfBiomeGenBase)
  {
    byte var98 = 63;

    ChunkProviderEvent.ReplaceBiomeBlocks event = new ChunkProviderEvent.ReplaceBiomeBlocks(this, par1, par2, par3ArrayOfByte, par4ArrayOfBiomeGenBase);
    MinecraftForge.EVENT_BUS.post(event);
    if (event.getResult() == Result.DENY) return;

    for (int var5 = 0; var5 < 16; var5++)
    {
      for (int var6 = 0; var6 < 16; var6++)
      {
        BiomeGenBase var99 = par4ArrayOfBiomeGenBase[(var6 + var5 * 16)];
        byte var7 = 1;
        int var8 = -1;
        byte var9 = var99.topBlock;
        byte var10 = var99.fillerBlock;

        for (int var11 = 127; var11 >= 0; var11--)
        {
          int var12 = (var6 * 16 + var5) * 128 + var11;
          byte var13 = par3ArrayOfByte[var12];

          if (var13 == 0)
          {
            var8 = -1;
          }
          /*else if (var13 == (byte)((Block)Blocks.holyStone.get()).blockID)
          {
            if (var8 == -1)
            {
              if (var7 <= 0)
              {
                var9 = 0;
                var10 = (byte)((Block)Blocks.holyStone.get()).blockID;
              }
              else if ((var11 >= var98 - 4) && (var11 <= var98 + 1))
              {
                var9 = var99.topBlock;
                var10 = var99.fillerBlock;
              }

              if ((var11 < var98) && (var9 == 0))
              {
                var9 = (byte)Block.waterStill.blockID;
              }

              var8 = var7;

              if (var11 >= 0)
              {
                par3ArrayOfByte[var12] = var9;
              }
              else
              {
                par3ArrayOfByte[var12] = var10;
              }
            }
            else if (var8 > 0)
            {
              var8--;
              par3ArrayOfByte[var12] = var10;
            }
          }*/
        }
      }
    }
  }

  public Chunk loadChunk(int par1, int par2) {
    return provideChunk(par1, par2);
  }

  public Chunk provideChunk(int par1, int par2) {
    this.endRNG.setSeed(par1 * 341873128712L + par2 * 132897987541L);
    byte[] var3 = new byte[32768];
    this.biomesForGeneration = this.endWorld.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, par1 * 16, par2 * 16, 16, 16);
    generateTerrain(par1, par2, var3, this.biomesForGeneration);
    replaceBlocksForBiome(par1, par2, var3, this.biomesForGeneration);
    Chunk var4 = new Chunk(this.endWorld, var3, par1, par2);
    byte[] var5 = var4.getBiomeArray();

    for (int var6 = 0; var6 < var5.length; var6++) {
      var5[var6] = ((byte)this.biomesForGeneration[var6].biomeID);
    }

    var4.generateSkylightMap();
    return var4;
  }

  public boolean chunkExists(int par1, int par2) {
    return true;
  }

  public void populate(IChunkProvider par1IChunkProvider, int par2, int par3) {
    net.minecraft.block.BlockSand.fallInstantly = true;

    MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(par1IChunkProvider, this.endWorld, this.endWorld.rand, par2, par3, false));

    int var4 = par2 * 16;
    int var5 = par3 * 16;
    BiomeGenBase var6 = this.endWorld.getBiomeGenForCoords(var4 + 16, var5 + 16);

    for (int a = 0; a < 25; a++)
    {
      int x = var4 + this.endWorld.rand.nextInt(16);
      int y = this.endWorld.rand.nextInt(30) + 30;
      int z = var5 + this.endWorld.rand.nextInt(16);
      int b = this.endWorld.getBlockId(x, y, z);

      if (b == Block.netherrack.blockID) {
    	  this.endWorld.setBlock(x, y, z, SpaceAgeCore.vulcanSurfaceID, 3, 2);
        //this.endWorld.setBlock(x, y, z, ((Block)Blocks.amethystOre.get()).blockID, 0, 2);
      }
    }

    var6.decorate(this.endWorld, this.endWorld.rand, var4, var5);

    MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Post(par1IChunkProvider, this.endWorld, this.endWorld.rand, par2, par3, false));

    net.minecraft.block.BlockSand.fallInstantly = false;
  }

  public boolean saveChunks(boolean par1, IProgressUpdate par2IProgressUpdate)
  {
    return true;
  }

  public boolean unloadQueuedChunks()
  {
    return false;
  }

  public boolean unload100OldestChunks()
  {
    return false;
  }

  public boolean canSave()
  {
    return true;
  }

  public String makeString()
  {
    return "RandomLevelSource";
  }

  public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4)
  {
    BiomeGenBase var5 = this.endWorld.getBiomeGenForCoords(par2, par4);
    return var5 == null ? null : var5.getSpawnableList(par1EnumCreatureType); //TODO check if works, else try and write own list
  }

  public ChunkPosition findClosestStructure(World par1World, String par2Str, int par3, int par4, int par5)
  {
    return null;
  }

  public int getLoadedChunkCount()
  {
    return 0;
  }

  public void recreateStructures(int par1, int par2)
  {
  }

  public void saveExtraData()
  {
  }
}