package spaceage.planets.hades;

import com.google.common.base.Optional;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.awt.Color;
import java.util.List;
import java.util.Random;

import spaceage.common.SpaceAgeCore;
import spaceage.planets.general.BiomeDecoratorSA;
import spaceage.planets.general.BiomePlanetBase;
import spaceage.planets.general.WorldGenLavaSpring;
import net.minecraft.block.Block;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.SpawnListEntry;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeGenHadesMain extends BiomePlanetBase {
  private WorldGenerator frozenSpring;
  private BiomeDecoratorSA customBiomeDecorator;

  public BiomeGenHadesMain(int par1) {
    super(par1);
    this.theBiomeDecorator = new BiomeDecoratorSA(this);
    this.customBiomeDecorator = ((BiomeDecoratorSA)this.theBiomeDecorator);
    
    this.topBlock = ((byte)SpaceAgeCore.hadesSurface.blockID);
    this.fillerBlock = ((byte)SpaceAgeCore.hadesSurface.blockID);
    
    this.customBiomeDecorator.hadesIcePerChunk = 1;
    this.customBiomeDecorator.treesPerChunk = -999;
    this.customBiomeDecorator.generateLakes = false;
    this.customBiomeDecorator.flowersPerChunk = -999;
    this.customBiomeDecorator.grassPerChunk = -999;
    this.customBiomeDecorator.deadBushPerChunk = -999;
    this.customBiomeDecorator.reedsPerChunk = -999;
    this.customBiomeDecorator.cactiPerChunk = -999;
    this.customBiomeDecorator.mushroomsPerChunk = -999;
    this.customBiomeDecorator.clayPerChunk = -999;
    this.customBiomeDecorator.waterlilyPerChunk = -999;
    this.customBiomeDecorator.flowersPerChunk = -999;
    this.customBiomeDecorator.sandPerChunk = -999;
    this.customBiomeDecorator.sandPerChunk2 = -999;
    this.customBiomeDecorator.bigMushroomsPerChunk = -999;
    this.spawnableCreatureList.clear();
    this.spawnableWaterCreatureList.clear();
    this.spawnableMonsterList.clear();
    this.spawnableCaveCreatureList.clear();
    
    this.spawnableMonsterList.add(new SpawnListEntry(EntitySkeleton.class, 30, 4, 4)); //TODO check if works!!!
    this.spawnableMonsterList.add(new SpawnListEntry(EntityGhast.class, 30, 4, 4));

    this.frozenSpring = new WorldGenLavaSpring(Block.ice.blockID, 16, SpaceAgeCore.hadesSurface.blockID);
    
    this.setTemperatureRainfall(0.0F, 0.5F);
    this.setEnableSnow();
  }
  
  @Override
  public WorldGenerator getRandomWorldGenForTrees(Random par1Random) {
    return new WorldGenIceBlade(false);
  }
  
  @SideOnly(Side.CLIENT)
  public int getBiomeGrassColor() {
      double d0 = (double)MathHelper.clamp_float(this.getFloatTemperature(), 0.0F, 1.0F);
      double d1 = (double)MathHelper.clamp_float(this.getFloatRainfall(), 0.0F, 1.0F);
      return getModdedBiomeGrassColor(ColorizerGrass.getGrassColor(d0, d1));
  }

  @SideOnly(Side.CLIENT)
  public int getBiomeFoliageColor() {
      double d0 = (double)MathHelper.clamp_float(this.getFloatTemperature(), 0.0F, 1.0F);
      double d1 = (double)MathHelper.clamp_float(this.getFloatRainfall(), 0.0F, 1.0F);
      return getModdedBiomeFoliageColor(ColorizerFoliage.getFoliageColor(d0, d1));
  }

  public void decorate(World par1World, Random par2Random, int par3, int par4) {
    super.decorate(par1World, par2Random, par3, par4);
    int var5 = 100;

    for (var5 = 0; var5 < 10; var5++)
    {
      int var6 = par3 + par2Random.nextInt(16);
      int var7 = par2Random.nextInt(60);
      int var8 = par4 + par2Random.nextInt(16);
      this.frozenSpring.generate(par1World, par2Random, var6, var7, var8);
    }
  }
  
  @Override
  public boolean getEnableSnow() {
      return true;
  }
  
  @Override
  public boolean canSpawnLightningBolt() {
      return true;
  }
}