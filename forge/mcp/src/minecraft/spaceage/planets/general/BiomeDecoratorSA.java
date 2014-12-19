package spaceage.planets.general;

import com.google.common.base.Optional;
import java.util.Random;

import spaceage.common.SpaceAgeCore;
import spaceage.planets.vulcan.WorldGenGlowstoneTree;
import spaceage.planets.vulcan.WorldGenSoulSand;
import spaceage.planets.vulcan.WorldGenVulcanVolcano;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenBigMushroom;
import net.minecraft.world.gen.feature.WorldGenCactus;
import net.minecraft.world.gen.feature.WorldGenClay;
import net.minecraft.world.gen.feature.WorldGenDeadBush;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.feature.WorldGenLiquids;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenPumpkin;
import net.minecraft.world.gen.feature.WorldGenReed;
import net.minecraft.world.gen.feature.WorldGenSand;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenWaterlily;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.EventBus;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Post;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Pre;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

public class BiomeDecoratorSA extends BiomeDecorator {
	public World currentWorlds;
	public Random random;
	public int chunk_xs;
	public int chunk_zs;
	public BiomeGenBase genBase;
  
  //VULCAN
	public WorldGenerator vulcanSoulSandGen;
  //public WorldGenerator glowstoneTreeGen;
	public WorldGenerator vulcanVolcanoGen;
	public WorldGenerator fireEssenceGen;
  
  //VULCAN
	public int vulcanSoulSandGenPerChunk;
  //public int glowstoneTreeGenPerChunk;
	public int vulcanVolcanoesPerChunk;
	public int lavaLakesPerChunk;
	public int fireEssencePerChunk;
  
  //HADES
	public WorldGenerator hadesIceGen;
  
  //HADES
	public int hadesIcePerChunk;
  
  //0011
	//public WorldGenerator organicMatterGen;
  
  //0011
	public int organicMatterPerChunk;
  
  //VANILLA
	private int coalGenPerChunk;
	private int ironGenPerChunk;
  	private int goldGenPerChunk;
  	private int redstoneGenPerChunk;
  	private int diamondGenPerChunk;
  	private int lapisGenPerChunk;

  	public BiomeDecoratorSA(BiomeGenBase par1BiomeGenBase) {
  		super(par1BiomeGenBase);
    
    //MISC
  		this.vulcanSoulSandGen = new WorldGenSoulSand(Block.slowSand.blockID, 48, Block.netherrack.blockID);
    //this.glowstoneTreeGen = new WorldGenGlowstoneTree();
  		this.vulcanVolcanoGen = new WorldGenVulcanVolcano();
    
  		this.hadesIceGen = new WorldGenMetaOres(Block.ice.blockID, (hadesIcePerChunk * 48), 0, SpaceAgeCore.hadesSurface.blockID, 0);
    
  		this.vulcanSoulSandGenPerChunk = 0;
    //this.glowstoneTreeGenPerChunk = 0;
  		this.vulcanVolcanoesPerChunk = 0;
    
  		this.hadesIcePerChunk = 0;
    /*this.canyonGen = new WorldGenCanyon(((Block)Blocks.redRock.get()).blockID, 48);
    this.driedDirtInSandGen = new WorldGenDriedDirt(((Block)Blocks.driedDirt.get()).blockID, 32);
    this.cloudGen = new WorldGenCloud();*/
    //VANILLA ORES
  		this.coalGen = new WorldGenMinable(Block.oreCoal.blockID, (coalGenPerChunk/*Original number - 16*/));
  		this.ironGen = new WorldGenMinable(Block.oreIron.blockID, (ironGenPerChunk/*Original number - 8*/));
  		this.goldGen = new WorldGenMinable(Block.oreGold.blockID, (goldGenPerChunk/*Original number - 8*/));
  		this.redstoneGen = new WorldGenMinable(Block.oreRedstone.blockID, (redstoneGenPerChunk/*Original number - 7*/));
  		this.diamondGen = new WorldGenMinable(Block.oreDiamond.blockID, (diamondGenPerChunk/*Original number - 7*/));
  		this.lapisGen = new WorldGenMinable(Block.oreLapis.blockID, (lapisGenPerChunk/*Original number - 6*/));
    
  		this.coalGenPerChunk = 0;
  		this.ironGenPerChunk = 0;
	  	this.goldGenPerChunk = 0;
	  	this.redstoneGenPerChunk = 0;
	  	this.diamondGenPerChunk = 0;
	  	this.lapisGenPerChunk = 0;
    
    //OWN ORES
	  	this.fireEssenceGen = new WorldGenMetaOres(SpaceAgeCore.vulcanSurface.blockID, (fireEssencePerChunk * 8), 3, Block.netherrack.blockID, 0);
	  	this.fireEssencePerChunk = 0;
	  
    /*this.field_76830_q = new WorldGenBOPFlowers(Block.plantYellow.blockID, 0);
    this.cobwebGen = new WorldGenCobwebs(Block.web.blockID, 0);
    this.dandelionGen = new WorldGenBOPFlowers(((Block)Blocks.flowers.get()).blockID, 15);
    this.field_76829_r = new WorldGenBOPFlowers(Block.plantRed.blockID, 0);
    this.plantWhiteGen = new WorldGenBOPFlowers(((Block)Blocks.flowers.get()).blockID, 9);
    this.plantBlueGen = new WorldGenBOPFlowers(((Block)Blocks.flowers.get()).blockID, 1);
    this.plantPurpleGen = new WorldGenBOPFlowers(((Block)Blocks.flowers.get()).blockID, 7);
    this.plantPinkGen = new WorldGenBOPFlowers(((Block)Blocks.flowers.get()).blockID, 6);
    this.plantOrangeGen = new WorldGenBOPFlowers(((Block)Blocks.flowers.get()).blockID, 5);
    this.rainbowflowerGen = new WorldGenBOPFlowers(((Block)Blocks.flowers.get()).blockID, 11);
    this.plantTinyGen = new WorldGenBOPFlowers(((Block)Blocks.flowers.get()).blockID, 0);
    this.plantGlowGen = new WorldGenBOPFlowers(((Block)Blocks.flowers.get()).blockID, 3);
    this.plantDeadGen = new WorldGenBOPFlowers(((Block)Blocks.plants.get()).blockID, 0);
    this.plantDesertGen = new WorldGenBOPFlowers(((Block)Blocks.plants.get()).blockID, 1);
    this.pumpkinAltGen = new WorldGenPumpkinAlt(Block.pumpkin.blockID, 0);
    this.thornGen = new WorldGenBOPFlowers(((Block)Blocks.plants.get()).blockID, 5);
    this.bushGen = new WorldGenBOPBush(((Block)Blocks.foliage.get()).blockID, 4);
    this.berryBushGen = new WorldGenBOPFlowers(((Block)Blocks.foliage.get()).blockID, 8);
    this.shrubGen = new WorldGenBOPBush(((Block)Blocks.foliage.get()).blockID, 9);
    this.wheatGrassGen = new WorldGenTallGrass(((Block)Blocks.foliage.get()).blockID, 10);
    this.wetGrassGen = new WorldGenTallGrass(((Block)Blocks.foliage.get()).blockID, 11);
    this.tinyCactusGen = new WorldGenBOPFlowers(((Block)Blocks.plants.get()).blockID, 12);
    this.aloeGen = new WorldGenBOPFlowers(((Block)Blocks.flowers.get()).blockID, 12);
    this.coralGen = new WorldGenCoral(((Block)Blocks.coral.get()).blockID, 4);
    this.hibiscusGen = new WorldGenBOPFlowers(((Block)Blocks.flowers2.get()).blockID, 0);
    this.lilyOfTheValleyGen = new WorldGenBOPFlowers(((Block)Blocks.flowers2.get()).blockID, 1);
    this.burningBlossomGen = new WorldGenBOPDarkFlowers(((Block)Blocks.flowers2.get()).blockID, 2);
    this.lavenderGen = new WorldGenBOPFlowers(((Block)Blocks.flowers2.get()).blockID, 3);
    this.goldenrodGen = new WorldGenBOPFlowers(((Block)Blocks.flowers2.get()).blockID, 4);
    this.bluebellGen = new WorldGenBOPFlowers(((Block)Blocks.flowers2.get()).blockID, 5);
    this.minersDelightGen = new WorldGenBOPDarkFlowers(((Block)Blocks.flowers2.get()).blockID, 6);
    this.icyIrisGen = new WorldGenBOPFlowers(((Block)Blocks.flowers2.get()).blockID, 7);
    this.lilyflowerGen = new WorldGenLilyflower();
    this.deathbloomGen = new WorldGenBOPFlowers(((Block)Blocks.flowers.get()).blockID, 2);
    this.hydrangeaGen = new WorldGenBOPFlowers(((Block)Blocks.flowers.get()).blockID, 4);
    this.violetGen = new WorldGenBOPFlowers(((Block)Blocks.flowers.get()).blockID, 8);
    this.duneGrassGen = new WorldGenBOPFlowers(((Block)Blocks.plants.get()).blockID, 3);
    this.holyTallGrassGen = new WorldGenBOPFlowers(((Block)Blocks.plants.get()).blockID, 4);
    this.desertSproutsGen = new WorldGenBOPFlowers(((Block)Blocks.plants.get()).blockID, 2);
    this.poisonIvyGen = new WorldGenBOPBush(((Block)Blocks.foliage.get()).blockID, 7);
    this.sunflowerGen = new WorldGenSunflower(((Block)Blocks.flowers.get()).blockID, 13);
    this.promisedWillowGen = new WorldGenPromisedWillow();
    this.netherVineGen = new WorldGenNetherVines();
    this.boneSpineGen = new WorldGenBoneSpine();
    this.boneSpine2Gen = new WorldGenBoneSpine2();
    this.cattailGen = new WorldGenCattail();
    this.crystalGen = new WorldGenCrystal1();
    this.crystalGen2 = new WorldGenCrystal2();
    this.kelpGen = new WorldGenKelp(false);
    this.shortKelpGen = new WorldGenShortKelp(false);
    this.graveGen = new WorldGenGrave();
    this.waspHiveGen = new WorldGenHive();
    this.field_76828_s = new WorldGenBOPFlowers(Block.mushroomBrown.blockID, 0);
    this.field_76827_t = new WorldGenBOPFlowers(Block.mushroomRed.blockID, 0);
    this.flatMushroomGen = new WorldGenBOPFlowers(((Block)Blocks.mushrooms.get()).blockID, 4);
    this.toadstoolGen = new WorldGenBOPFlowers(((Block)Blocks.mushrooms.get()).blockID, 0);
    this.portobelloGen = new WorldGenBOPFlowers(((Block)Blocks.mushrooms.get()).blockID, 1);
    this.blueMilkGen = new WorldGenBOPFlowers(((Block)Blocks.mushrooms.get()).blockID, 2);
    this.glowshroomGen = new WorldGenBOPFlowers(((Block)Blocks.mushrooms.get()).blockID, 3);
    this.sproutGen = new WorldGenSprout(((Block)Blocks.foliage.get()).blockID, 5);
    this.highGrassGen = new WorldGenHighGrass(((Block)Blocks.foliage.get()).blockID, 3);
    this.highCattailGen = new WorldGenHighCattail(((Block)Blocks.plants.get()).blockID, 9);
    this.outbackGen = new WorldGenOutback(((Block)Blocks.foliage.get()).blockID, 2);
    this.smolderingGrassGen = new WorldGenSmolderingGrass(((Block)Blocks.holyGrass.get()).blockID, 1);
    this.netherGrassGen = new WorldGenNetherGrass(Block.tallGrass.blockID, 1);
    this.netherWartGen = new WorldGenNetherWart(Block.netherStalk.blockID, 0);
    this.canyonGrassGen = new WorldGenCanyonGrass(((Block)Blocks.foliage.get()).blockID, 2);
    this.steppeGen = new WorldGenSteppe(Block.sand.blockID, 0);
    this.carrotGen = new WorldGenBOPFlowers(((Block)Blocks.plants.get()).blockID, 11);
    this.potatoGen = new WorldGenPotatoes(Block.tallGrass.blockID, 0);
    this.field_76826_u = new WorldGenBigMushroom();
    this.field_76825_v = new WorldGenReed();
    this.reedBOPGen = new WorldGenReedBOP();
    this.field_76824_w = new WorldGenCactus();
    this.desertCactusGen = new WorldGenDesertCactus();
    this.field_76834_x = new WorldGenWaterlily();
    this.algaeGen = new WorldGenAlgae();
    this.waterReedGen = new WorldGenWaterReeds();
    this.redwoodShrubGen = new WorldGenRedwoodShrub(0, 0);
    this.koruGen = new WorldGenTallGrass(((Block)Blocks.foliage.get()).blockID, 12);
    this.cloverPatchGen = new WorldGenCloverPatch(((Block)Blocks.foliage.get()).blockID, 13);
    this.rootGen = new WorldGenBOPTallGrass(((Block)Blocks.plants.get()).blockID, 15);
    this.stalagmiteGen = new WorldGenBOPTallGrass(((Block)Blocks.stoneFormations.get()).blockID, 0);
    this.stalactiteGen = new WorldGenBOPTallGrass(((Block)Blocks.stoneFormations.get()).blockID, 1);
    this.pitGen = new WorldGenPit(((Block)Blocks.ash.get()).blockID);*/
    /*this.field_76833_y = 0;
    this.lilyflowersPerChunk = 0;*/
    //this.glowstoneTreesPerChunk = 0;
    /*this.field_76802_A = 2;
    this.field_76803_B = 1;
    this.field_76804_C = 0;
    this.field_76799_E = 0;
    this.reedsBOPPerChunk = 0;
    this.field_76800_F = 0;
    this.field_76801_G = 1;
    this.field_76805_H = 3;
    this.oasesPerChunk = 0;
    this.oasesPerChunk2 = 0;
    this.mudPerChunk = 0;
    this.mudPerChunk2 = 0;
    this.gravelPerChunk = 0;
    this.gravelPerChunk2 = 0;
    this.field_76806_I = 1;
    this.field_76807_J = 0;
    this.rosesPerChunk = 0;
    this.whiteFlowersPerChunk = 0;
    this.blueFlowersPerChunk = 0;
    this.purpleFlowersPerChunk = 0;
    this.pinkFlowersPerChunk = 0;
    this.orangeFlowersPerChunk = 0;
    this.rainbowflowersPerChunk = 0;
    this.tinyFlowersPerChunk = 0;
    this.glowFlowersPerChunk = 0;
    this.deadGrassPerChunk = 0;
    this.desertGrassPerChunk = 0;
    this.cattailsPerChunk = 0;
    this.highCattailsPerChunk = 0;
    this.carrotsPerChunk = 0;
    this.potatoesPerChunk = 0;
    this.thornsPerChunk = 0;
    this.toadstoolsPerChunk = 0;
    this.portobellosPerChunk = 0;
    this.blueMilksPerChunk = 0;
    this.glowshroomsPerChunk = 0;
    this.sunflowersPerChunk = 0;
    this.sproutsPerChunk = 0;
    this.bushesPerChunk = 0;
    this.berryBushesPerChunk = 0;
    this.shrubsPerChunk = 0;
    this.wheatGrassPerChunk = 0;
    this.tinyCactiPerChunk = 0;
    this.poisonIvyPerChunk = 0;
    this.aloePerChunk = 0;
    this.deathbloomsPerChunk = 0;
    this.hydrangeasPerChunk = 0;
    this.violetsPerChunk = 0;
    this.duneGrassPerChunk = 0;
    this.holyTallGrassPerChunk = 0;
    this.desertSproutsPerChunk = 0;
    this.desertCactiPerChunk = 0;
    this.highGrassPerChunk = 0;
    this.outbackPerChunk = 0;
    this.smolderingGrassPerChunk = 0;
    this.netherGrassPerChunk = 0;
    this.netherWartPerChunk = 0;
    this.canyonGrassPerChunk = 0;
    this.steppePerChunk = 0;
    this.promisedWillowPerChunk = 0;
    this.netherVinesPerChunk = 0;
    this.algaePerChunk = 0;
    this.pondsPerChunk = 0;
    this.waterLakesPerChunk = 0;
    this.lavaLakesPerChunk = 0;
    this.netherLavaPerChunk = 0;
    this.hotSpringsPerChunk = 0;
    this.poisonWaterPerChunk = 0;
    this.crystalsPerChunk = 0;
    this.crystals2PerChunk = 0;
    this.boneSpinesPerChunk = 0;
    this.boneSpines2PerChunk = 0;
    this.cobwebsPerChunk = 0;
    this.kelpPerChunk = 0;
    this.kelpThickPerChunk = 0;
    this.shortKelpPerChunk = 0;
    this.gravesPerChunk = 0;
    this.pumpkinsPerChunk = 0;
    this.coralPerChunk = 0;
    this.hibiscusPerChunk = 0;
    this.lilyOfTheValleysPerChunk = 0;
    this.burningBlossomsPerChunk = 0;
    this.lavenderPerChunk = 0;
    this.goldenrodsPerChunk = 0;
    this.bluebellsPerChunk = 0;
    this.minersDelightPerChunk = 2;
    this.icyIrisPerChunk = 0;
    this.waterReedsPerChunk = 0;
    this.redwoodShrubsPerChunk = 0;
    this.koruPerChunk = 0;
    this.cloverPatchesPerChunk = 0;
    this.waspHivesPerChunk = 0;
    this.rootsPerChunk = 9;
    this.stalagmitesPerChunk = 3;
    this.stalactitesPerChunk = 6;
    this.cloudsPerChunk = 0;
    this.field_76808_K = true;
    this.generateAsh = false;
    this.generateMycelium = false;
    this.generateSandInGrass = false;
    this.generateStoneInGrass = false;
    this.generateStoneInGrass2 = false;
    this.generateSandInStone = false;
    this.generateDriedDirtInSand = false;
    this.generateClayInClay = false;
    this.generateClayInClay2 = false;
    this.generateClayInStone = false;
    this.generateClayInStone2 = false;
    this.generateQuagmire = false;
    this.generateCanyon = false;
    this.generatePumpkins = true;
    this.generateMelons = false;
    this.generateBoulders = false;
    this.generateClouds = false;
    this.generateQuicksand = false;
    this.generateSponge = false;
    this.generateMossySkystone = false;
    this.generateUndergroundLakes = true;*/
	  	this.genBase = par1BiomeGenBase;
  }

  	public void decorate(World par1World, Random par2Random, int par3, int par4) {
  		if (this.currentWorlds != null) {
  			return;
  		}

  		this.currentWorlds = par1World;
  		this.random = par2Random;
  		this.chunk_xs = par3;
  		this.chunk_zs = par4;
  		decorate();
  		this.currentWorlds = null;
  		this.random = null;
  }

  	@Override
  	protected void decorate() {
  		MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(currentWorlds, this.random, chunk_xs, chunk_zs));

  		boolean doGen = TerrainGen.decorate(currentWorlds, this.random, chunk_xs, chunk_zs, DecorateBiomeEvent.Decorate.EventType.SAND);

  		generateOres();
    
  		for (int var2 = 0; var2 < this.vulcanSoulSandGenPerChunk; var2++) {
  			int var3 = chunk_xs + this.random.nextInt(16) + 8;
  			int var4 = this.random.nextInt(256);
  			int var5 = chunk_zs + this.random.nextInt(16) + 8;
  			this.vulcanSoulSandGen.generate(currentWorlds, this.random, var3, var4, var5);
  		}
    
  		doGen = TerrainGen.decorate(currentWorlds, random, chunk_xs, chunk_zs, DecorateBiomeEvent.Decorate.EventType.TREE);
  		for (int j = 0; doGen && j < this.treesPerChunk; ++j) {
  			int k = this.chunk_xs + this.random.nextInt(16) + 8;
  			int l = this.chunk_zs + this.random.nextInt(16) + 8;
  			WorldGenerator worldgenerator = this.biome.getRandomWorldGenForTrees(this.random);
        //worldgenerator.setScale(1.0D, 1.0D, 1.0D);
  			worldgenerator.generate(this.currentWorld, this.random, k, this.currentWorld.getHeightValue(k, l), l);
    }
    
    /*for (int var2 = 0; var2 < this.glowstoneTreeGenPerChunk; var2++) {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.glowstoneTreeGen.generate(currentWorlds, this.random, var3, var4, var5);
    }*/

    /*for (int var2 = 0; var2 < this.waterLakesPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(this.random.nextInt(240) + 8);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      new WorldGenLakes(Block.waterMoving.blockID).generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.lavaLakesPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(this.random.nextInt(this.random.nextInt(112) + 8) + 8);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      new WorldGenLakes(Block.lavaMoving.blockID).generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.netherLavaPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(this.random.nextInt(this.random.nextInt(112) + 8) + 8);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      new WorldGenNetherLava(Block.lavaMoving.blockID).generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.hotSpringsPerChunk; var2++)
    {
      if (BOPConfigurationTerrainGen.springWaterGen)
      {
        int var3 = chunk_xs + this.random.nextInt(16) + 8;
        int var4 = this.random.nextInt(this.random.nextInt(this.random.nextInt(112) + 8) + 8);
        int var5 = chunk_zs + this.random.nextInt(16) + 8;
        new WorldGenLakes(((Block)Fluids.springWater.get()).blockID).generate(currentWorlds, this.random, var3, var4, var5);
      }
    }

    for (var2 = 0; var2 < 5; var2++)
    {
      int var9999 = this.random.nextInt(96);

      if (BOPConfigurationTerrainGen.springWaterGen)
      {
        if (var9999 == 1)
        {
          if (this.generateUndergroundLakes)
          {
            int var3 = chunk_xs + this.random.nextInt(16) + 8;
            int var4 = this.random.nextInt(this.random.nextInt(this.random.nextInt(32) + 8) + 8);
            int var5 = chunk_zs + this.random.nextInt(16) + 8;
            new WorldGenLakes(((Block)Fluids.springWater.get()).blockID).generate(currentWorlds, this.random, var3, var4, var5);
          }
        }
      }
    }

    for (var2 = 0; var2 < this.poisonWaterPerChunk; var2++)
    {
      if (BOPConfigurationTerrainGen.poisonWaterGen)
      {
        int var3 = chunk_xs + this.random.nextInt(16) + 8;
        int var4 = this.random.nextInt(this.random.nextInt(this.random.nextInt(112) + 8) + 8);
        int var5 = chunk_zs + this.random.nextInt(16) + 8;
        new WorldGenLakes(((Block)Fluids.liquidPoison.get()).blockID).generate(currentWorlds, this.random, var3, var4, var5);
      }
    }

    for (var2 = 0; var2 < 5; var2++)
    {
      int var9998 = this.random.nextInt(32);

      if (BOPConfigurationTerrainGen.poisonWaterGen)
      {
        if (var9998 == 1)
        {
          if (this.generateUndergroundLakes)
          {
            int var3 = chunk_xs + this.random.nextInt(16) + 8;
            int var4 = this.random.nextInt(this.random.nextInt(this.random.nextInt(32) + 8) + 8);
            int var5 = chunk_zs + this.random.nextInt(16) + 8;
            new WorldGenLakes(((Block)Fluids.liquidPoison.get()).blockID).generate(currentWorlds, this.random, var3, var4, var5);
          }
        }
      }
    }

    if (this.generateAsh)
    {
      genStandardOre1(10, this.ashGen, 0, 128);
    }

    if (this.generateGrass)
    {
      genStandardOre1(20, this.grassMesaGen, 0, 128);
    }

    if (this.generateSand)
    {
      genStandardOre1(15, this.sandMesaGen, 0, 128);
    }

    if (this.generateMycelium)
    {
      genStandardOre1(10, this.myceliumGen, 0, 128);
    }

    if (this.generateSandInGrass)
    {
      genStandardOre1(8, this.sandInGrassGen, 64, 128);
    }

    if (this.generateStoneInGrass)
    {
      genStandardOre1(15, this.stoneInGrassGen, 64, 128);
    }

    if (this.generateStoneInGrass2)
    {
      genStandardOre1(20, this.stoneInGrassGen2, 64, 128);
    }

    if (this.generateSandInStone)
    {
      genStandardOre1(10, this.sandInStoneGen, 64, 128);
    }

    if (this.generateDriedDirtInSand)
    {
      genStandardOre1(8, this.driedDirtInSandGen, 64, 128);
    }

    if (this.generateClayInClay)
    {
      genStandardOre1(20, this.clayInClayGen, 64, 128);
    }

    if (this.generateClayInClay2)
    {
      genStandardOre1(20, this.clayInClay2Gen, 64, 128);
    }

    if (this.generateClayInStone)
    {
      genStandardOre1(20, this.clayInStoneGen, 64, 128);
    }

    if (this.generateClayInStone2)
    {
      genStandardOre1(20, this.clayInStone2Gen, 64, 128);
    }

    if (this.generateQuagmire)
    {
      genStandardOre1(15, this.quagmireGen, 64, 128);
    }

    if (this.generateMossySkystone)
    {
      genStandardOre1(15, this.mossySkystoneGen, 0, 80);
    }

    if (this.generateCanyon)
    {
      genStandardOre1(15, this.canyonGen, 64, 128);
    }

    if (this.generateQuicksand)
    {
      if (BOPConfigurationTerrainGen.quicksandGen)
      {
        genStandardOre1(5, this.quicksandGen, 64, 128);
      }
    }

    if (this.generateSponge)
    {
      genStandardOre1(5, this.spongeGen, 0, 64);
    }

    if (this.generateClouds)
    {
      genCloudMain(1, this.cloudGen, 0, 35);
    }

    if (this.generatePits)
    {
      int var4 = chunk_xs + this.random.nextInt(16) + 8;
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      int var6 = currentWorlds.getTopSolidOrLiquidBlock(var4, var5);

      if (var6 > 0);
      this.pitGen.generate(currentWorlds, this.random, var4, var6, var5);
    }

    for (int var1 = 0; var1 < this.field_76805_H; var1++)
    {
      var2 = chunk_xs + this.random.nextInt(16) + 8;
      int var3 = chunk_zs + this.random.nextInt(16) + 8;
      this.field_76810_g.generate(currentWorlds, this.random, var2, currentWorlds.getTopSolidOrLiquidBlock(var2, var3), var3);
    }

    for (var1 = 0; (doGen) && (var1 < this.field_76805_H); var1++)
    {
      var2 = chunk_xs + this.random.nextInt(16) + 8;
      int var3 = chunk_zs + this.random.nextInt(16) + 8;
      this.field_76810_g.generate(currentWorlds, this.random, var2, currentWorlds.getTopSolidOrLiquidBlock(var2, var3), var3);
    }

    for (var1 = 0; var1 < this.mudPerChunk2; var1++)
    {
      var2 = chunk_xs + this.random.nextInt(16) + 8;
      int var3 = chunk_zs + this.random.nextInt(16) + 8;
      this.mudGen.generate(currentWorlds, this.random, var2, currentWorlds.getTopSolidOrLiquidBlock(var2, var3), var3);
    }

    for (var1 = 0; var1 < this.gravelPerChunk2; var1++)
    {
      var2 = chunk_xs + this.random.nextInt(16) + 8;
      int var3 = chunk_zs + this.random.nextInt(16) + 8;
      this.gravelShoreGen.generate(currentWorlds, this.random, var2, currentWorlds.getTopSolidOrLiquidBlock(var2, var3), var3);
    }

    doGen = TerrainGen.decorate(currentWorlds, this.random, chunk_xs, chunk_zs, DecorateBiomeEvent.Decorate.EventType.CLAY);
    for (var1 = 0; (doGen) && (var1 < this.field_76806_I); var1++)
    {
      var2 = chunk_xs + this.random.nextInt(16) + 8;
      int var3 = chunk_zs + this.random.nextInt(16) + 8;
      this.field_76809_f.generate(currentWorlds, this.random, var2, currentWorlds.getTopSolidOrLiquidBlock(var2, var3), var3);
    }

    doGen = TerrainGen.decorate(currentWorlds, this.random, chunk_xs, chunk_zs, DecorateBiomeEvent.Decorate.EventType.SAND_PASS2);
    for (var1 = 0; (doGen) && (var1 < this.field_76801_G); var1++)
    {
      var2 = chunk_xs + this.random.nextInt(16) + 8;
      int var3 = chunk_zs + this.random.nextInt(16) + 8;
      this.field_76810_g.generate(currentWorlds, this.random, var2, currentWorlds.getTopSolidOrLiquidBlock(var2, var3), var3);
    }

    for (var1 = 0; var1 < this.oasesPerChunk; var1++)
    {
      var2 = chunk_xs + this.random.nextInt(16) + 8;
      int var3 = chunk_zs + this.random.nextInt(16) + 8;
      this.oasesGen.generate(currentWorlds, this.random, var2, currentWorlds.getTopSolidOrLiquidBlock(var2, var3), var3);
    }

    for (var1 = 0; var1 < this.mudPerChunk; var1++)
    {
      var2 = chunk_xs + this.random.nextInt(16) + 8;
      int var3 = chunk_zs + this.random.nextInt(16) + 8;
      this.mudGen.generate(currentWorlds, this.random, var2, currentWorlds.getTopSolidOrLiquidBlock(var2, var3), var3);
    }

    for (var1 = 0; var1 < this.gravelPerChunk; var1++)
    {
      var2 = chunk_xs + this.random.nextInt(16) + 8;
      int var3 = chunk_zs + this.random.nextInt(16) + 8;
      this.gravelShoreGen.generate(currentWorlds, this.random, var2, currentWorlds.getTopSolidOrLiquidBlock(var2, var3), var3);
    }

    var1 = this.treesPerChunk;

    if (this.random.nextInt(10) == 0)
    {
      var1++;
    }

    doGen = TerrainGen.decorate(currentWorlds, this.random, chunk_xs, chunk_zs, DecorateBiomeEvent.Decorate.EventType.TREE);
    for (var2 = 0; (doGen) && (var2 < var1); var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = chunk_zs + this.random.nextInt(16) + 8;
      WorldGenerator var7 = this.field_76812_e.getRandomWorldGenForTrees(this.random);
      var7.setScale(1.0D, 1.0D, 1.0D);
      var7.generate(currentWorlds, this.random, var3, currentWorlds.getHeightValue(var3, var4), var4);
    }

    doGen = TerrainGen.decorate(currentWorlds, this.random, chunk_xs, chunk_zs, DecorateBiomeEvent.Decorate.EventType.BIG_SHROOM);
    for (var2 = 0; (doGen) && (var2 < this.field_76807_J); var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = chunk_zs + this.random.nextInt(16) + 8;
      this.field_76826_u.generate(currentWorlds, this.random, var3, currentWorlds.getHeightValue(var3, var4), var4);
    }

    doGen = TerrainGen.decorate(currentWorlds, this.random, chunk_xs, chunk_zs, DecorateBiomeEvent.Decorate.EventType.FLOWERS);
    for (var2 = 0; (doGen) && (var2 < this.field_76802_A); var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.field_76830_q.generate(currentWorlds, this.random, var3, var4, var5);

      if (this.random.nextInt(6) == 0)
      {
        var3 = chunk_xs + this.random.nextInt(16) + 8;
        var4 = this.random.nextInt(256);
        var5 = chunk_zs + this.random.nextInt(16) + 8;
        this.dandelionGen.generate(currentWorlds, this.random, var3, var4, var5);
      }

      if (this.random.nextInt(4) == 0)
      {
        var3 = chunk_xs + this.random.nextInt(16) + 8;
        var4 = this.random.nextInt(256);
        var5 = chunk_zs + this.random.nextInt(16) + 8;
        this.field_76829_r.generate(currentWorlds, this.random, var3, var4, var5);
      }
    }

    for (var2 = 0; var2 < this.rosesPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.field_76829_r.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.cobwebsPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.cobwebGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.sunflowersPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.sunflowerGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.gravesPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.graveGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.pumpkinsPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.pumpkinAltGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.kelpPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16);
      int var4 = this.random.nextInt(64);
      int var5 = chunk_zs + this.random.nextInt(16);
      this.kelpGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.kelpThickPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(8);
      int var4 = this.random.nextInt(64);
      int var5 = chunk_zs + this.random.nextInt(8);
      this.kelpGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.shortKelpPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16);
      int var4 = this.random.nextInt(64);
      int var5 = chunk_zs + this.random.nextInt(16);
      this.shortKelpGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.rainbowflowersPerChunk; var2++)
    {
      int var956 = this.random.nextInt(10);

      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;

      if (var956 == 0)
      {
        this.rainbowflowerGen.generate(currentWorlds, this.random, var3, var4, var5);
      }
    }

    for (var2 = 0; var2 < this.boneSpinesPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.boneSpineGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.boneSpines2PerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(64) + 64;
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.boneSpine2Gen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.crystalsPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(50);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.crystalGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.crystals2PerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(50);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.crystalGen2.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.promisedWillowPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(70);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.promisedWillowGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.netherVinesPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.netherVineGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.whiteFlowersPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.plantWhiteGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.coralPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.coralGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.blueFlowersPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.plantBlueGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.hibiscusPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.hibiscusGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.lilyOfTheValleysPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.lilyOfTheValleyGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    if (BOPConfigurationTerrainGen.burningBlossomGen)
    {
      for (var2 = 0; var2 < this.burningBlossomsPerChunk; var2++)
      {
        int var3 = chunk_xs + this.random.nextInt(16) + 8;
        int var4 = this.random.nextInt(256);
        int var5 = chunk_zs + this.random.nextInt(16) + 8;
        this.burningBlossomGen.generate(currentWorlds, this.random, var3, var4, var5);
      }
    }

    for (var2 = 0; var2 < this.lavenderPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.lavenderGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.goldenrodsPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.goldenrodGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.bluebellsPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.bluebellGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.minersDelightPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(45);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.minersDelightGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.icyIrisPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.icyIrisGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.hydrangeasPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.hydrangeaGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.violetsPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.violetGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.duneGrassPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.duneGrassGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.holyTallGrassPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.holyTallGrassGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.desertSproutsPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.desertSproutsGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.purpleFlowersPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.plantPurpleGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.pinkFlowersPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.plantPinkGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.bushesPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.bushGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.berryBushesPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.berryBushGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.shrubsPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.shrubGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.koruPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.koruGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.cloverPatchesPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.cloverPatchGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.rootsPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.rootGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.stalagmitesPerChunk; var2++)
    {
      if (BOPConfigurationTerrainGen.stoneFormationGen)
      {
        int var3 = chunk_xs + this.random.nextInt(16) + 8;
        int var4 = this.random.nextInt(60);
        int var5 = chunk_zs + this.random.nextInt(16) + 8;
        this.stalagmiteGen.generate(currentWorlds, this.random, var3, var4, var5);
      }
    }

    for (var2 = 0; var2 < this.stalactitesPerChunk; var2++)
    {
      if (BOPConfigurationTerrainGen.stoneFormationGen)
      {
        int var3 = chunk_xs + this.random.nextInt(16) + 8;
        int var4 = this.random.nextInt(60);
        int var5 = chunk_zs + this.random.nextInt(16) + 8;
        this.stalactiteGen.generate(currentWorlds, this.random, var3, var4, var5);
      }
    }

    for (var2 = 0; var2 < this.cloudsPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(32);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.cloudGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.waspHivesPerChunk; var2++)
    {
      int var420 = this.random.nextInt(4);

      if (var420 != 0)
      {
        int var3 = chunk_xs + this.random.nextInt(16) + 8;
        int var4 = this.random.nextInt(64) + 50;
        int var5 = chunk_zs + this.random.nextInt(16) + 8;
        this.waspHiveGen.generate(currentWorlds, this.random, var3, var4, var5);
      }
    }

    for (var2 = 0; var2 < this.wheatGrassPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      int var99999 = this.random.nextInt(2);

      if (var99999 == 0)
      {
        this.wetGrassGen.generate(currentWorlds, this.random, var3, var4, var5);
      }
      else
      {
        this.wheatGrassGen.generate(currentWorlds, this.random, var3, var4, var5);
      }
    }

    for (var2 = 0; var2 < this.poisonIvyPerChunk; var2++)
    {
      if (BOPConfigurationTerrainGen.poisonIvyGen)
      {
        int var3 = chunk_xs + this.random.nextInt(16) + 8;
        int var4 = this.random.nextInt(256);
        int var5 = chunk_zs + this.random.nextInt(16) + 8;
        this.poisonIvyGen.generate(currentWorlds, this.random, var3, var4, var5);
      }
    }

    for (var2 = 0; var2 < this.orangeFlowersPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.plantOrangeGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.tinyCactiPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.tinyCactusGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.aloePerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.aloeGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.deathbloomsPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.deathbloomGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.toadstoolsPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.toadstoolGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.portobellosPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.portobelloGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.blueMilksPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.blueMilkGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.glowshroomsPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(80);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.glowshroomGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.sproutsPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.sproutGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.tinyFlowersPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.plantTinyGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.glowFlowersPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.plantGlowGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.deadGrassPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.plantDeadGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.desertGrassPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.plantDesertGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    doGen = TerrainGen.decorate(currentWorlds, this.random, chunk_xs, chunk_zs, DecorateBiomeEvent.Decorate.EventType.GRASS);
    for (var2 = 0; (doGen) && (var2 < this.field_76803_B); var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      WorldGenerator var6 = this.field_76812_e.getRandomWorldGenForGrass(this.random);
      var6.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.outbackPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.outbackGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.smolderingGrassPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.smolderingGrassGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.netherGrassPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.netherGrassGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.netherWartPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.netherWartGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.canyonGrassPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.canyonGrassGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.steppePerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.steppeGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.highGrassPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.highGrassGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.carrotsPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.carrotGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.potatoesPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.potatoGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.reedsBOPPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = chunk_zs + this.random.nextInt(16) + 8;
      int var5 = this.random.nextInt(256);
      this.reedBOPGen.generate(currentWorlds, this.random, var3, var5, var4);
    }

    for (var2 = 0; var2 < this.thornsPerChunk; var2++)
    {
      if (BOPConfigurationTerrainGen.thornGen)
      {
        int var3 = chunk_xs + this.random.nextInt(16) + 8;
        int var4 = this.random.nextInt(256);
        int var5 = chunk_zs + this.random.nextInt(16) + 8;
        this.thornGen.generate(currentWorlds, this.random, var3, var4, var5);
      }
    }

    for (var2 = 0; var2 < this.cattailsPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.cattailGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.highCattailsPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.highCattailGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.redwoodShrubsPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(50) + 70;
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.redwoodShrubGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    doGen = TerrainGen.decorate(currentWorlds, this.random, chunk_xs, chunk_zs, DecorateBiomeEvent.Decorate.EventType.DEAD_BUSH);
    for (var2 = 0; (doGen) && (var2 < this.field_76804_C); var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      new WorldGenDeadBush(Block.deadBush.blockID).generate(currentWorlds, this.random, var3, var4, var5);
    }

    doGen = TerrainGen.decorate(currentWorlds, this.random, chunk_xs, chunk_zs, DecorateBiomeEvent.Decorate.EventType.SHROOM);
    for (var2 = 0; (doGen) && (var2 < this.field_76798_D); var2++)
    {
      if (this.random.nextInt(4) == 0)
      {
        int var3 = chunk_xs + this.random.nextInt(16) + 8;
        int var4 = chunk_zs + this.random.nextInt(16) + 8;
        int var5 = currentWorlds.getHeightValue(var3, var4);
        this.field_76828_s.generate(currentWorlds, this.random, var3, var5, var4);
      }

      if (this.random.nextInt(6) == 0)
      {
        int var3 = chunk_xs + this.random.nextInt(16) + 8;
        int var4 = chunk_zs + this.random.nextInt(16) + 8;
        int var5 = this.random.nextInt(256);
        this.flatMushroomGen.generate(currentWorlds, this.random, var3, var5, var4);
      }

      if (this.random.nextInt(8) == 0)
      {
        int var3 = chunk_xs + this.random.nextInt(16) + 8;
        int var4 = chunk_zs + this.random.nextInt(16) + 8;
        int var5 = this.random.nextInt(256);
        this.field_76827_t.generate(currentWorlds, this.random, var3, var5, var4);
      }
    }

    if (this.random.nextInt(4) == 0)
    {
      var2 = chunk_xs + this.random.nextInt(16) + 8;
      int var3 = this.random.nextInt(256);
      int var4 = chunk_zs + this.random.nextInt(16) + 8;
      this.field_76828_s.generate(currentWorlds, this.random, var2, var3, var4);
    }

    if (this.random.nextInt(8) == 0)
    {
      var2 = chunk_xs + this.random.nextInt(16) + 8;
      int var3 = this.random.nextInt(256);
      int var4 = chunk_zs + this.random.nextInt(16) + 8;
      this.field_76827_t.generate(currentWorlds, this.random, var2, var3, var4);
    }

    doGen = TerrainGen.decorate(currentWorlds, this.random, chunk_xs, chunk_zs, DecorateBiomeEvent.Decorate.EventType.REED);
    for (var2 = 0; (doGen) && (var2 < this.field_76799_E); var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = chunk_zs + this.random.nextInt(16) + 8;
      int var5 = this.random.nextInt(256);
      this.field_76825_v.generate(currentWorlds, this.random, var3, var5, var4);
    }

    for (var2 = 0; (doGen) && (var2 < 10); var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.field_76825_v.generate(currentWorlds, this.random, var3, var4, var5);
    }

    if ((this.generatePumpkins) && (this.random.nextInt(32) == 0))
    {
      var2 = chunk_xs + this.random.nextInt(16) + 8;
      int var3 = this.random.nextInt(256);
      int var4 = chunk_zs + this.random.nextInt(16) + 8;

      if (BOPConfigurationTerrainGen.pumpkinGen)
      {
        new WorldGenBOPPumpkin().generate(currentWorlds, this.random, var2, var3, var4);
      }
      else
      {
        new WorldGenPumpkin().generate(currentWorlds, this.random, var2, var3, var4);
      }
    }

    if ((this.generateMelons) && (this.random.nextInt(32) == 0))
    {
      var2 = chunk_xs + this.random.nextInt(16) + 8;
      int var3 = this.random.nextInt(256);
      int var4 = chunk_zs + this.random.nextInt(16) + 8;
      new WorldGenMelon().generate(currentWorlds, this.random, var2, var3, var4);
    }

    if ((this.generateBoulders) && (this.random.nextInt(32) == 0))
    {
      var2 = chunk_xs + this.random.nextInt(16) + 8;
      int var3 = this.random.nextInt(256);
      int var4 = chunk_zs + this.random.nextInt(16) + 8;
      new WorldGenBoulder().generate(currentWorlds, this.random, var2, var3, var4);
    }

    for (var2 = 0; var2 < this.field_76800_F; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.field_76824_w.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; var2 < this.desertCactiPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.desertCactusGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    if (this.field_76808_K)
    {
      for (var2 = 0; var2 < 50 + this.pondsPerChunk; var2++)
      {
        int var3 = chunk_xs + this.random.nextInt(16) + 8;
        int var4 = this.random.nextInt(this.random.nextInt(120) + 8);
        int var5 = chunk_zs + this.random.nextInt(16) + 8;
        new WorldGenLiquids(Block.waterMoving.blockID).generate(currentWorlds, this.random, var3, var4, var5);
      }

      for (var2 = 0; var2 < 20; var2++)
      {
        int var3 = chunk_xs + this.random.nextInt(16) + 8;
        int var4 = this.random.nextInt(this.random.nextInt(this.random.nextInt(112) + 8) + 8);
        int var5 = chunk_zs + this.random.nextInt(16) + 8;
        new WorldGenLiquids(Block.lavaMoving.blockID).generate(currentWorlds, this.random, var3, var4, var5);
      }
    }

    for (var2 = 0; var2 < this.lilyflowersPerChunk; var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = this.random.nextInt(256);
      int var5 = chunk_zs + this.random.nextInt(16) + 8;
      this.lilyflowerGen.generate(currentWorlds, this.random, var3, var4, var5);
    }

    for (var2 = 0; (doGen) && (var2 < this.algaePerChunk); var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = chunk_zs + this.random.nextInt(16) + 8;

      for (int var5 = this.random.nextInt(256); (var5 > 0) && (currentWorlds.getBlockId(var3, var5 - 1, var4) == 0); var5--);
      this.algaeGen.generate(currentWorlds, this.random, var3, var5, var4);
    }

    for (var2 = 0; (doGen) && (var2 < this.waterReedsPerChunk); var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = chunk_zs + this.random.nextInt(16) + 8;

      for (int var5 = this.random.nextInt(256); (var5 > 0) && (currentWorlds.getBlockId(var3, var5 - 1, var4) == 0); var5--);
      this.waterReedGen.generate(currentWorlds, this.random, var3, var5, var4);
    }

    doGen = TerrainGen.decorate(currentWorlds, this.random, chunk_xs, chunk_zs, DecorateBiomeEvent.Decorate.EventType.LILYPAD);
    for (var2 = 0; (doGen) && (var2 < this.field_76833_y); var2++)
    {
      int var3 = chunk_xs + this.random.nextInt(16) + 8;
      int var4 = chunk_zs + this.random.nextInt(16) + 8;

      for (int var5 = this.random.nextInt(256); (var5 > 0) && (currentWorlds.getBlockId(var3, var5 - 1, var4) == 0); var5--);
      this.field_76834_x.generate(currentWorlds, this.random, var3, var5, var4);
    }*/

  		MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(currentWorlds, this.random, chunk_xs, chunk_zs));
  	}

  	protected void genStandardOre1(int par1, WorldGenerator par2WorldGenerator, int par3, int par4) {
  		for (int var5 = 0; var5 < par1; var5++) {
  			int var6 = chunk_xs + this.random.nextInt(16);
  			int var7 = this.random.nextInt(par4 - par3) + par3;
  			int var8 = chunk_zs + this.random.nextInt(16);
  			par2WorldGenerator.generate(currentWorlds, this.random, var6, var7, var8);
  		}
  	}

  	protected void genStandardOre2(int par1, WorldGenerator par2WorldGenerator, int par3, int par4) {
  		for (int var5 = 0; var5 < par1; var5++) {
  			int var6 = chunk_xs + this.random.nextInt(16);
  			int var7 = this.random.nextInt(par4) + this.random.nextInt(par4) + (par3 - par4);
  			int var8 = chunk_zs + this.random.nextInt(16);
  			par2WorldGenerator.generate(currentWorlds, this.random, var6, var7, var8);
  		}
  	}

  	@Override
  	protected void generateOres() {
  		MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Pre(currentWorlds, this.random, chunk_xs, chunk_zs));

  		if (TerrainGen.generateOre(currentWorlds, this.random, this.dirtGen, chunk_xs, chunk_zs, OreGenEvent.GenerateMinable.EventType.DIRT)) {
  			genStandardOre1(20, this.dirtGen, 0, 256);
  		}

  		if (TerrainGen.generateOre(currentWorlds, this.random, this.gravelGen, chunk_xs, chunk_zs, OreGenEvent.GenerateMinable.EventType.GRAVEL)) {
  			genStandardOre1(10, this.gravelGen, 0, 256);
  		}

  		if (TerrainGen.generateOre(currentWorlds, this.random, this.coalGen, chunk_xs, chunk_zs, OreGenEvent.GenerateMinable.EventType.COAL)) {
  			genStandardOre1(20, this.coalGen, 0, 128);
  		}

  		if (TerrainGen.generateOre(currentWorlds, this.random, this.ironGen, chunk_xs, chunk_zs, OreGenEvent.GenerateMinable.EventType.IRON)) {
  			genStandardOre1(20, this.ironGen, 0, 64);
  		}

  		if (TerrainGen.generateOre(currentWorlds, this.random, this.goldGen, chunk_xs, chunk_zs, OreGenEvent.GenerateMinable.EventType.GOLD)) {
  			genStandardOre1(2, this.goldGen, 0, 32);
  		}

  		if (TerrainGen.generateOre(currentWorlds, this.random, this.redstoneGen, chunk_xs, chunk_zs, OreGenEvent.GenerateMinable.EventType.REDSTONE)) {
  			genStandardOre1(8, this.redstoneGen, 0, 16);
  		}

  		if (TerrainGen.generateOre(currentWorlds, this.random, this.diamondGen, chunk_xs, chunk_zs, OreGenEvent.GenerateMinable.EventType.DIAMOND)) {
  			genStandardOre1(1, this.diamondGen, 0, 16);
  		}

  		if (TerrainGen.generateOre(currentWorlds, this.random, this.lapisGen, chunk_xs, chunk_zs, OreGenEvent.GenerateMinable.EventType.LAPIS)) {
  			genStandardOre2(1, this.lapisGen, 16, 16);
  		}

  		MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Post(currentWorlds, this.random, chunk_xs, chunk_zs));
  	}
}