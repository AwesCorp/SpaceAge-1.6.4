package spaceage.planets.util;

import net.minecraft.world.WorldType;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.EventBus;
import net.minecraftforge.event.terraingen.WorldTypeEvent;
import net.minecraftforge.event.terraingen.WorldTypeEvent.BiomeSize;

public abstract class BiomeLayer extends GenLayer {
  public static GenLayer[] initializeAllBiomeGenerators(long seed, WorldType worldtype, int dim) {
    int biomesize = 3;

    BiomeLayer obj = new BiomeLayerCreate(1L, false);
    obj = new BiomeLayerFuzzyZoom(2000L, obj);
    for (int i = 1; i < 3; i++) obj = new BiomeLayerZoom(2000L + i, obj);
    obj = BiomeLayerZoom.func_75915_a(1000L, obj, 0);
    obj = new BiomeLayerBiomes(200L, obj, worldtype, dim);
    obj = BiomeLayerZoom.func_75915_a(1000L, obj, 2);
    for (int j = 0; j < biomesize; j++) obj = new BiomeLayerZoom(1000L + j, obj);
    BiomeLayerVoronoiZoom genlayervoronoizoom = new BiomeLayerVoronoiZoom(10L, obj);
    obj.initWorldGenSeed(seed);
    genlayervoronoizoom.initWorldGenSeed(seed);
    return new GenLayer[] { obj, genlayervoronoizoom };
  }

  public BiomeLayer(long seed)
  {
    super(seed);
  }

  public static byte getModdedBiomeSize(WorldType worldType, byte original)
  {
    WorldTypeEvent.BiomeSize event = new WorldTypeEvent.BiomeSize(worldType, original);
    MinecraftForge.TERRAIN_GEN_BUS.post(event);
    return event.newSize;
  }
}