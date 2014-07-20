package spaceage.planets.vulcan;

import com.google.common.base.Optional;
import java.util.Random;

import spaceage.common.SpaceAgeCore;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenGlowstoneTree extends WorldGenerator {
  public WorldGenGlowstoneTree() {
    super();
  }

  public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5) {
    int var6 = par2Random.nextInt(3) + 5;
    boolean flag = true;

    if ((par4 >= 1) && (par4 + var6 + 1 <= 256)) {
      for (int var8 = par4; var8 <= par4 + 1 + var6; var8++) {
        byte var9 = 1;

        if (var8 == par4) {
          var9 = 0;
        }

        if (var8 >= par4 + 1 + var6 - 2) {
          var9 = 2;
        }

        for (int var10 = par3 - var9; (var10 <= par3 + var9) && (flag); var10++)
        {
          for (int var11 = par5 - var9; (var11 <= par5 + var9) && (flag); var11++)
          {
            if ((var8 >= 0) && (var8 < 256))
            {
              int var12 = par1World.getBlockId(var10, var8, var11);

              Block block = Block.blocksList[var12];

              if ((var12 != 0) && (block != null) && (!block.isLeaves(par1World, var10, var8, var11)))
              {
                flag = false;
              }
            }
            else
            {
              flag = false;
            }
          }
        }
      }

      if (!flag) {
        return false;
      }

      int var8 = par1World.getBlockId(par3, par4 - 1, par5);

      if (((var8 == Block.netherrack.blockID) || (var8 == Block.slowSand.blockID)) && (par4 < 256 - var6 - 1))
      {
        setBlock(par1World, par3, par4 - 1, par5, Block.netherrack.blockID);

        for (int var16 = par4 - 3 + var6; var16 <= par4 + var6; var16++) {
          int var10 = var16 - (par4 + var6);
          int var11 = 1 - var10 / 2;

          for (int var12 = par3 - var11; var12 <= par3 + var11; var12++) {
            int var13 = var12 - par3;

            for (int var14 = par5 - var11; var14 <= par5 + var11; var14++) {
              int var15 = var14 - par5;

              boolean lookup = Block.opaqueCubeLookup[par1World.getBlockId(var12, var16, var14)];
              if (((Math.abs(var13) != var11) || (Math.abs(var15) != var11) || ((par2Random.nextInt(2) != 0) && (var10 != 0))) && (lookup == false)) {
                  setBlockAndMetadata(par1World, var12, var16, var14, SpaceAgeCore.vulcanSurface.blockID, 0);
                }
              }
            }
          }
        }

        for (int var16 = 0; var16 < var6; var16++)
        {
          int var10 = par1World.getBlockId(par3, par4 + var16, par5);

          Block block = Block.blocksList[var10];

          if ((var10 == 0) || (block == null) || (block.isLeaves(par1World, par3, par4 + var16, par5)))
          {
            setBlockAndMetadata(par1World, par3, par4 + var16, par5, SpaceAgeCore.vulcanSurface.blockID, 1);
          }
        }

        return true;
      }
    return false;
  }
}