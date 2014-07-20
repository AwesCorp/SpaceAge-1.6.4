package spaceage.planets.util;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class BiomeLayerFuzzyZoom extends BiomeLayer
{
  public BiomeLayerFuzzyZoom(long par1, BiomeLayer par3GenLayer)
  {
    super(par1);
    this.parent = par3GenLayer;
  }

  public int[] getInts(int par1, int par2, int par3, int par4)
  {
    int i1 = par1 >> 1;
    int j1 = par2 >> 1;
    int k1 = (par3 >> 1) + 3;
    int l1 = (par4 >> 1) + 3;
    int[] aint = this.parent.getInts(i1, j1, k1, l1);
    int[] aint1 = IntCache.getIntCache(k1 * 2 * l1 * 2);
    int i2 = k1 << 1;

    for (int k2 = 0; k2 < l1 - 1; k2++)
    {
      int j2 = k2 << 1;
      int l2 = j2 * i2;
      int i3 = aint[(0 + (k2 + 0) * k1)];
      int j3 = aint[(0 + (k2 + 1) * k1)];

      for (int k3 = 0; k3 < k1 - 1; k3++)
      {
        initChunkSeed(k3 + i1 << 1, k2 + j1 << 1);
        int l3 = aint[(k3 + 1 + (k2 + 0) * k1)];
        int i4 = aint[(k3 + 1 + (k2 + 1) * k1)];
        aint1[l2] = i3;
        aint1[(l2++ + i2)] = choose(i3, j3);
        aint1[l2] = choose(i3, l3);
        aint1[(l2++ + i2)] = choose(i3, l3, j3, i4);
        i3 = l3;
        j3 = i4;
      }
    }

    int[] aint2 = IntCache.getIntCache(par3 * par4);

    for (int j2 = 0; j2 < par4; j2++)
    {
      System.arraycopy(aint1, (j2 + (par2 & 0x1)) * (k1 << 1) + (par1 & 0x1), aint2, j2 * par3, par3);
    }

    return aint2;
  }

  protected int choose(int par1, int par2)
  {
    return nextInt(2) == 0 ? par1 : par2;
  }

  protected int choose(int par1, int par2, int par3, int par4)
  {
    int i1 = nextInt(4);
    return i1 == 2 ? par3 : i1 == 1 ? par2 : i1 == 0 ? par1 : par4;
  }
}