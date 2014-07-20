package spaceage.planets.util;

import com.google.common.base.Optional;
import java.util.ArrayList;

import spaceage.planets.SpaceAgePlanets;
import spaceage.planets.general.BiomeList;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class BiomeLayerBiomes extends BiomeLayer {
  private int dimension = 0;
  private static ArrayList<BiomeGenBase> vulcanBiomes = new ArrayList();
  private static ArrayList<BiomeGenBase> hadesBiomes = new ArrayList();

  public BiomeLayerBiomes(long par1, BiomeLayer par3GenLayer, WorldType par4WorldType, int dim) {
    super(par1);
    this.parent = par3GenLayer;
    this.dimension = dim;
    
    vulcanBiomes.add(BiomeList.vulcanMain);
    vulcanBiomes.add(BiomeList.vulcanVolcano);
  }

  public int[] getInts(int par1, int par2, int par3, int par4) {
    int[] var5 = this.parent.getInts(par1, par2, par3, par4);
    int[] var6 = IntCache.getIntCache(par3 * par4);

    if(dimension == 2) {
    	for (int var7 = 0; var7 < par4; var7++) {
    		for (int var8 = 0; var8 < par3; var8++) {
    			initChunkSeed(var8 + par1, var7 + par2);
    			int var9 = var5[(var8 + var7 * par3)];
 
          //var6[(var8 + var7 * par3)] = ((BiomeGenBase)promisedBiomes.get(nextInt(promisedBiomes.size()))).biomeID;
    			var6[(var8 + var7 * par3)] = ((BiomeGenBase)vulcanBiomes.get(nextInt(vulcanBiomes.size()))).biomeID;
      	}
      }
    }
    return var6;
  }
}