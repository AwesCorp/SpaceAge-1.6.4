package spaceage.common;

import java.io.File;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.logging.Level;

import org.lwjgl.input.Keyboard;

import spaceage.client.gui.SPGUI;
import spaceage.common.achievements.SpaceAgeAchievements;
import spaceage.common.block.Block0011;
import spaceage.common.block.BlockConnectedGlasses;
import spaceage.common.block.BlockGenerator;
import spaceage.common.block.BlockHades;
import spaceage.common.block.BlockOres1;
import spaceage.common.block.BlockSpaceshipAlloy;
import spaceage.common.block.BlockVulcan;
import spaceage.common.item.ItemBlock0011;
import spaceage.common.item.ItemBlockGeneratorTooltip;
import spaceage.common.item.ItemBlockHades;
import spaceage.common.item.ItemBlockSpaceshipAlloy;
import spaceage.common.item.ItemBlockVulcan;
import spaceage.common.item.ItemFireResistArmour;
import spaceage.common.item.ItemMeta;
import spaceage.common.item.ItemRepulsor;
import spaceage.common.item.ItemStarboost;
import spaceage.common.tile.TileHeatGenerator;
import spaceage.common.tile.TileSolarPanel;
import universalelectricity.api.UniversalElectricity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

/**
 * Main file of SpaceAge. Includes all important main code for SpaceAge and it's child mods. 
 * @author SkylordJoel, Reika (hooking into UniversalElectricity examples), Tihyo (armour flight), Colossali (armour HUD)
 */

@Mod(modid=SpaceAgeCore.modid, name="SpaceAge/Project Cosmos: - bringing Minecraft to the Space Age", version="Alpha", dependencies="required-after:UniversalElectricity")
@NetworkMod(clientSideRequired=true, serverSideRequired=false/*, serverPacketHandlerSpec=@NetworkMod.SidedPacketHandler(channels={"SpaceAge_C"}, packetHandler=ServerPacketHandler.class)*/)

public class SpaceAgeCore {

	public static CreativeTabs tabSA = new CreativeTabs("tabSA") {
		public ItemStack getIconItemstack() {
			return new ItemStack(spaceshipAlloyMeta,1,0);
		}
	};
	
	@SidedProxy(clientSide = "spaceage.client.ClientProxy", serverSide = "spaceage.common.CommonProxy")
	public static CommonProxy proxy;
	
	public static final String modid = "SpaceAge";
	//public String modid2 = modid.toLowerCase();
	//public static final String REFERENCE = modid + ":";
	
	@Instance("SpaceAge")
	public static SpaceAgeCore instance;
	
    @Mod.Metadata(SpaceAgeCore.modid)
    public static ModMetadata metadata;
	
    public static SPGUI guiHandler = new SPGUI();
	
	//Blocks and Items Registry 
	public static Item meta;
	
	public static Block spaceshipAlloyMeta;
	public static Block ores1;
	
	public static Item advancedSpacesuitHelmet;
	public static Item advancedSpacesuitChestplate;
	public static Item advancedSpacesuitLeggings;
	public static Item advancedSpacesuitBoots;
	public static Item repulsor;
	
	public static Item fireResistanceHelmet;
	public static Item fireResistanceChestplate;
	public static Item fireResistanceLeggings;
	public static Item fireResistanceBoots;
	
	public static Block metaGenerator;
	
	public static Block tintedGlass;
	
	public static Block vulcanSurface;
	public static Block hadesSurface;
	public static Block T0011Surface;
	
	public static Block metaSapling;
	
	//Custom ItemStacks (for crafting ease etc.)
	//GameRegistry.registerCustomItemStack("ash", new ItemStack(vulcanSurface, 1, 2));
	//In other places, forge crashes if they're here
	
	//ID Registry
	public static int metaID;
	
	public static int ores1ID;
	
	public static int spaceshipAlloyMetaID;
	
	public static int advancedSpacesuitHelmetID;
	public static int advancedSpacesuitChestplateID;
	public static int advancedSpacesuitLeggingsID;
	public static int advancedSpacesuitBootsID;
	public static int repulsorID;
	
	public static int fireResistanceHelmetID;
	public static int fireResistanceChestplateID;
	public static int fireResistanceLeggingsID;
	public static int fireResistanceBootsID;
	
	public static int metaGeneratorID;
	
	public static int tintedGlassID;
	
	public static int vulcanSurfaceID;
	public static int hadesSurfaceID;
	public static int T0011SurfaceID;
	
	public static int metaSaplingID;
	
	//ENERGY VALUES
	//GENERATORS
	public static int SOLAR_ENERGY = 400; //TODO Temp values - 8 REDSTONE FLUX
	public static int SOLAR_CAPACITY = 2000000; //TODO - 40 000 REDSTONE FLUX (DYNAMO IS 40 000 FLUX - FURNACE HOLDS 24 000)
	public static int HEAT_ENERGY = 400; //TODO - 8 REDSTONE FLUX
	public static int HEAT_CAPACITY = 2000000; //TODO - 40 000 REDSTONE FLUX (DYNAMO IS 40 000 FLUX - FURNACE HOLDS 24 000)
	
	//MACHINES
	public static int ELECTROLYSER_ENERGY_USE = 200; //TODO Temp values - 4 REDSTONE FLUX
	public static int ELECTROLYSER_CAPACITY = 1000000; //TODO 20 000 REDSTONE FLUX - HALF OF DYNAMO, SIMILAR TO FURNACE
	
	//BATTERIES
	//public static int   = ; //TODO
	
	//CELLS
	public static int CELL_CAPACITY = 1000000; //TODO Temp values - 20 000 REDSTONE FLUX
	
	public static final Configuration config = new Configuration(new File("config/AwesCorp/SpaceAgeCore.cfg"));
	
	
	public void initConfiguration(FMLInitializationEvent event) {
		//Configuration config = new Configuration(new File("config/AwesCorp/SpaceAgeCore.cfg"));
		config.load();
		
		metaID = config.get("Items", "Value of the basic item - do not edit this to play on the server", 5000).getInt();
		ores1ID = config.get("Blocks", "Value of ores, no 1 - do not edit this to play on the server", 501).getInt();
		spaceshipAlloyMetaID = config.get("Blocks", "Value of the Spaceship Alloy - do not edit this to play on the server", 500).getInt();
		advancedSpacesuitHelmetID = config.get("Items", "Value of the advanced spacesuit helmet- do not edit this to play on the server", 5001).getInt();
		advancedSpacesuitChestplateID = config.get("Items", "Value of the advanced spacesuit chestplate - do not edit this to play on the server", 5002).getInt();
		advancedSpacesuitLeggingsID = config.get("Items", "Value of the advanced spacesuit legs - do not edit this to play on the server", 5003).getInt();
		advancedSpacesuitBootsID = config.get("Items", "Value of the advanced spacesuit boots - do not edit this to play on the server", 5004).getInt();
		repulsorID = config.get("Items", "Value of the integrated laser cannon - do not edit this to play on the server", 5005).getInt();
		fireResistanceHelmetID = config.get("Items", "Value of the fire resistance helmet - do not edit this to play on the server", 5006).getInt();
		fireResistanceChestplateID = config.get("Items", "Value of the fire resistance chestplate - do not edit this to play on the server", 5007).getInt();
		fireResistanceLeggingsID = config.get("Items", "Value of the fire resistance legs - do not edit this to play on the server", 5008).getInt();
		fireResistanceBootsID = config.get("Items", "Value of the fire resistance boots - do not edit this to play on the server", 5009).getInt();
		//SOLAR_ENERGY = config.get("Energy", "How much energy the solar panel generates - do not edit this to play on the server", 50).getInt();
		//HEAT_ENERGY = config.get("Energy", "How much energy the geothermal turbine generates - do not edit this to play on the server", 50).getInt();
		//SOLAR_CAPACITY = config.get("Energy", "How much energy the solar panel can store - do not edit this to play on the server", 250).getInt();
		//HEAT_CAPACITY = config.get("Energy", "How much energy the geothermal turbime can store - do not edit this to play on the server", 250).getInt();
		metaGeneratorID = config.get("Blocks", "Value of the generator - do not edit this to play on the server", 502).getInt();
		tintedGlassID = config.get("Blocks", "Value of the reinforced glass - do not edit to play on the server", 503).getInt();
		vulcanSurfaceID = config.get("Blocks", "Value of the majority of Vulcan related blocks - do not edit this to play on the server", 255).getInt();
		hadesSurfaceID = config.get("Blocks", "Value of the majority of Hades related blocks - do not edit to play on the server", 254).getInt();
		T0011SurfaceID = config.get("Blocks", "Value of the majority of 0011 related blocks - do not edit to play on the server", 253).getInt(); //TODO remember that worldgen blocks are below 256 and also change meta so surface is first
		metaSaplingID = config.get("Saplings", "Value of the saplings - do not edit to play on the server", 504).getInt();
		
		config.save();
	}	
	
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
	    SpaceAgeAchievements.init();
		
	       metadata.modId = this.modid;
	       metadata.name = "SpaceAge/Project Cosmos";
	       metadata.description = "SpaceAge/Project Cosmos: Bringing Minecraft to the Space Age";
	       metadata.url = "https://sites.google.com/site/spaceageminecraft/";
	       metadata.logoFile = "assets/spaceage/logo_nathan_test-2.png";
	       metadata.version = "Alpha";
	       metadata.authorList = Arrays.asList(new String[] { 
	    		   "SkylordJoel", "big_fat_bunny"/*, "Super_Jen_Bot"*/ 
	    		   });
	       metadata.credits = "Many, notable include Calclavia, cr0s, Reika";
	       metadata.autogenerated = false;
		LogHelper.log(Level.FINEST, "Preinitialised successfully"); //about the preinitialiSed, I'm australian
	}
	
	@EventHandler
	public void Init(FMLInitializationEvent event) {
		this.initConfiguration(event);
		LogHelper.log(Level.FINEST, "Loaded configuration successfully");
		
		EnumArmorMaterial armourADVANCEDSPACESUIT = EnumHelper.addArmorMaterial("ADVANCEDSPACESUIT", 50, new int[]{8, 20, 10, 6}, 0);
		EnumArmorMaterial armourFIRERESISTANCE = EnumHelper.addArmorMaterial("FIRERESISTANCE", 50, new int[]{8, 20, 10, 6}, 0);
		
		
		meta = new ItemMeta/*meta*/(this.metaID).setUnlocalizedName("basicItem");
		
		spaceshipAlloyMeta = new BlockSpaceshipAlloy(this.spaceshipAlloyMetaID, Material.rock).setUnlocalizedName("spaceshipAlloy");
		
		vulcanSurface = new BlockVulcan(this.vulcanSurfaceID, Material.rock).setUnlocalizedName("vulcanBlock");
		
		hadesSurface = new BlockHades(this.hadesSurfaceID, Material.rock).setUnlocalizedName("hadesBlock");
		
		T0011Surface = new Block0011(this.T0011SurfaceID, Material.rock).setUnlocalizedName("0011Block");
		
		ores1 = new BlockOres1(this.ores1ID, Material.rock).setUnlocalizedName("spaceAgeOres1");
		
		advancedSpacesuitHelmet = new ItemStarboost(this.advancedSpacesuitHelmetID, armourADVANCEDSPACESUIT, 0, 0).setUnlocalizedName("advHelmet");
		advancedSpacesuitChestplate = new ItemStarboost(this.advancedSpacesuitChestplateID, armourADVANCEDSPACESUIT, 0, 1).setUnlocalizedName("advChestplate");
		advancedSpacesuitLeggings = new ItemStarboost(this.advancedSpacesuitLeggingsID, armourADVANCEDSPACESUIT, 0, 2).setUnlocalizedName("advLeggings");
		advancedSpacesuitBoots = new ItemStarboost(this.advancedSpacesuitBootsID, armourADVANCEDSPACESUIT, 0, 3).setUnlocalizedName("advBoots");
		repulsor = new ItemRepulsor(this.repulsorID).setUnlocalizedName("laserCannon").setCreativeTab(tabSA);
		
		fireResistanceHelmet = new ItemFireResistArmour(this.fireResistanceHelmetID, armourFIRERESISTANCE, 0, 0).setUnlocalizedName("fireHelmet");
		fireResistanceChestplate = new ItemFireResistArmour(this.fireResistanceChestplateID, armourFIRERESISTANCE, 0, 1).setUnlocalizedName("fireChestplate");
		fireResistanceLeggings = new ItemFireResistArmour(this.fireResistanceLeggingsID, armourFIRERESISTANCE, 0, 2).setUnlocalizedName("fireLeggings");
		fireResistanceBoots = new ItemFireResistArmour(this.fireResistanceBootsID, armourFIRERESISTANCE, 0, 3).setUnlocalizedName("fireBoots");
		
		tintedGlass = new BlockConnectedGlasses(this.tintedGlassID, Material.glass).setUnlocalizedName("reinforcedGlass");
		
		//Machines
		metaGenerator = new BlockGenerator(metaGeneratorID, UniversalElectricity.machine).setUnlocalizedName("metaGenerator").setCreativeTab(tabSA);
		
		gameRegisters();
		languageRegisters();
		craftingRecipes();
		smeltingRecipes();
		blockHarvest();
		registerOre();
		
		MinecraftForge.EVENT_BUS.register(new ServerTickHandler());
	    TickRegistry.registerTickHandler(new ServerTickHandler(), Side.CLIENT);

	    MinecraftForge.EVENT_BUS.register(new PlayerTickHandler());
	    TickRegistry.registerTickHandler(new PlayerTickHandler(), Side.CLIENT);
		//FMLCommonHandler.instance().bus().register(new YourFMLEventHandler());
	    MinecraftForge.EVENT_BUS.register(new SpaceAgeAchievements());
	    
	    NetworkRegistry.instance().registerGuiHandler(SpaceAgeCore.instance, SpaceAgeCore.guiHandler);
		
	    LogHelper.log(Level.FINEST, "Registered all handlers");
	    
		proxy.registerRenderers();
		proxy.load();
	    proxy.registerServerTickHandler();
	    proxy.registerHandlers();
	    

	    //proxy.getArmorModel();
		//TickRegistry.registerTickHandler(new SpaceAgeServerTickHandler(EnumSet.of(TickType.SERVER)), Side.SERVER);
		
		LogHelper.log(Level.FINEST, "Initialised successfully");
	}
	
	/*public boolean isMFFSLoaded() {
		return Loader.isModLoaded("MFFS");
	}*/

	public void registerOre() {
		ItemStack titaniumIngot = new ItemStack(this.meta,1,0);
		ItemStack aluminiumIngot = new ItemStack(this.meta,1,1);
		ItemStack vanadiumIngot = new ItemStack(this.meta,1,2);
		ItemStack heavyIngot = new ItemStack(this.meta,1,3);
		ItemStack arcReactor = new ItemStack(this.meta,1,4);
		ItemStack heavyPlate = new ItemStack(this.meta,1,5);
		ItemStack basicCircuits = new ItemStack(this.meta,1,6);
		ItemStack advancedCircuits = new ItemStack(this.meta,1,7);
		ItemStack wire = new ItemStack(this.meta,1,8);
		ItemStack oxygenApparatus = new ItemStack(this.meta,1,9);
		ItemStack thrusterPack = new ItemStack(this.meta,1,10);
		ItemStack silicon = new ItemStack(this.meta,1,11);
		ItemStack enrichedSilicon = new ItemStack(this.meta,1,12);
		ItemStack lithiumDust = new ItemStack(this.meta,1,13);
		ItemStack fireessence = new ItemStack(this.meta,1,14);
		ItemStack overClocker = new ItemStack(this.meta,1,15);
		
		//OreDictionary.registerOre(id, ore)
		OreDictionary.registerOre("titaniumIngot", titaniumIngot);
		OreDictionary.registerOre("aluminiumIngot", aluminiumIngot);
		OreDictionary.registerOre("aluminumIngot", aluminiumIngot);
		OreDictionary.registerOre("vanadiumIngot", vanadiumIngot);
		OreDictionary.registerOre("tavAlloyIngot", heavyIngot);
		OreDictionary.registerOre("heavyAlloyIngot", heavyIngot);
		OreDictionary.registerOre("heavyIngot", heavyIngot);
		OreDictionary.registerOre("titaniumaluminiumvanadiumIngot", heavyIngot);
		OreDictionary.registerOre("titaniumaluminumvanadiumIngot", heavyIngot);
		OreDictionary.registerOre("titaniumaluminiumvanadiumAlloyIngot", heavyIngot);
		OreDictionary.registerOre("titaniumaluminumvanadiumAlloyIngot", heavyIngot);
		OreDictionary.registerOre("titaniumAluminiumVanadiumIngot", heavyIngot);
		OreDictionary.registerOre("titaniumAluminumVanadiumIngot", heavyIngot);
		OreDictionary.registerOre("titaniumAluminiumVanadiumAlloyIngot", heavyIngot);
		OreDictionary.registerOre("titaniumAluminumVanadiumAlloyIngot", heavyIngot);
		OreDictionary.registerOre("wire", wire);
		OreDictionary.registerOre("aluminiumWire", wire);
		OreDictionary.registerOre("advancedCircuit", advancedCircuits);
		OreDictionary.registerOre("advancedCircuits", advancedCircuits);
		OreDictionary.registerOre("basicCircuit", basicCircuits);
		OreDictionary.registerOre("basicCircuits", basicCircuits);
		OreDictionary.registerOre("tavAlloyPlate", heavyPlate);
		OreDictionary.registerOre("heavyAlloyPlate", heavyPlate);
		OreDictionary.registerOre("heavyPlate", heavyPlate);
		OreDictionary.registerOre("titaniumaluminiumvanadiumPlate", heavyPlate);
		OreDictionary.registerOre("titaniumaluminumvanadiumPlate", heavyPlate);
		OreDictionary.registerOre("titaniumaluminiumvanadiumAlloyPlate", heavyPlate);
		OreDictionary.registerOre("titaniumaluminumvanadiumAlloyPlate", heavyPlate);
		OreDictionary.registerOre("titaniumAluminiumVanadiumPlate", heavyPlate);
		OreDictionary.registerOre("titaniumAluminumVanadiumPlate", heavyPlate);
		OreDictionary.registerOre("titaniumAluminiumVanadiumAlloyPlate", heavyPlate);
		OreDictionary.registerOre("titaniumAluminumVanadiumAlloyPlate", heavyPlate); //
		OreDictionary.registerOre("ingotTitanium", titaniumIngot);
		OreDictionary.registerOre("ingotAluminium", aluminiumIngot);
		OreDictionary.registerOre("ingotAluminum", aluminiumIngot);
		OreDictionary.registerOre("ingotVanadium", vanadiumIngot);
		OreDictionary.registerOre("IngottavAlloy", heavyIngot);
		OreDictionary.registerOre("ingotHeavyAlloy", heavyIngot);
		OreDictionary.registerOre("ingotHeavy", heavyIngot);
		OreDictionary.registerOre("ingottitaniumaluminiumvanadium", heavyIngot);
		OreDictionary.registerOre("ingottitaniumaluminumvanadium", heavyIngot);
		OreDictionary.registerOre("ingottitaniumaluminiumvanadiumAlloy", heavyIngot);
		OreDictionary.registerOre("ingottitaniumaluminumvanadiumAlloy", heavyIngot);
		OreDictionary.registerOre("ingotTitaniumAluminiumVanadium", heavyIngot);
		OreDictionary.registerOre("ingotTitaniumAluminumVanadium", heavyIngot);
		OreDictionary.registerOre("ingotTitaniumAluminiumVanadiumAlloy", heavyIngot);
		OreDictionary.registerOre("ingotTitaniumAluminumVanadiumAlloy", heavyIngot);
		OreDictionary.registerOre("wire", wire);
		OreDictionary.registerOre("wireAluminium", wire);
		OreDictionary.registerOre("circuitAdvanced", advancedCircuits);
		OreDictionary.registerOre("circuitsAdvanced", advancedCircuits);
		OreDictionary.registerOre("circuitBasic", basicCircuits);
		OreDictionary.registerOre("circuitsBasic", basicCircuits);
		OreDictionary.registerOre("platetavAlloy", heavyPlate);
		OreDictionary.registerOre("plateHeavyAlloy", heavyPlate);
		OreDictionary.registerOre("plateHeavy", heavyPlate);
		OreDictionary.registerOre("platetitaniumaluminiumvanadium", heavyPlate);
		OreDictionary.registerOre("platetitaniumaluminumvanadium", heavyPlate);
		OreDictionary.registerOre("platetitaniumaluminiumvanadiumAlloy", heavyPlate);
		OreDictionary.registerOre("platetitaniumaluminumvanadiumAlloy", heavyPlate);
		OreDictionary.registerOre("plateTitaniumAluminiumVanadium", heavyPlate);
		OreDictionary.registerOre("plateTitaniumAluminumVanadium", heavyPlate);
		OreDictionary.registerOre("plateTitaniumAluminiumVanadiumAlloy", heavyPlate);
		OreDictionary.registerOre("plateTitaniumAluminumVanadiumAlloy", heavyPlate);
		OreDictionary.registerOre("dustLithium", lithiumDust);
		OreDictionary.registerOre("lithiumDust", lithiumDust);
		
	}

	private void blockHarvest() {
		//MinecraftForge.setBlockHarvestLevel(block, toolClass, harvestLevel)
		MinecraftForge.setBlockHarvestLevel(spaceshipAlloyMeta, "pickaxe", 2);
		MinecraftForge.setBlockHarvestLevel(spaceshipAlloyMeta, "pickaxe", 3);
		MinecraftForge.setBlockHarvestLevel(metaGenerator, "pickaxe", 2);
		MinecraftForge.setBlockHarvestLevel(metaGenerator, "pickaxe", 3);
	}

	private void smeltingRecipes() {
		//GameRegistry.addSmelting(input, output, xp)
		
	}

	private void craftingRecipes() {
		
		ItemStack titaniumIngot = new ItemStack(this.meta,1,0); //FROM TITANIUM ORE TODO
		ItemStack aluminiumIngot = new ItemStack(this.meta,1,1); //FROM BAUXITE - COMPLEX PROPER REFINING METHOD
		ItemStack vanadiumIngot = new ItemStack(this.meta,1,2); //FROM VANADIUM ORE
		ItemStack heavyIngot = new ItemStack(this.meta,1,3); //FROM TITANIUM, ALUMINIUM AND VANADIUM, ALLOYER MACHINE?
		ItemStack arcReactor = new ItemStack(this.meta,1,4); //?
		ItemStack heavyPlate = new ItemStack(this.meta,1,5); //FROM HEAVY INGOT
		ItemStack basicCircuits = new ItemStack(this.meta,1,6); //?
		ItemStack advancedCircuits = new ItemStack(this.meta,1,7); //?
		ItemStack wire = new ItemStack(this.meta,1,8); //?
		ItemStack oxygenApparatus = new ItemStack(this.meta,1,9); //?
		ItemStack thrusterPack = new ItemStack(this.meta,1,10); //?
		ItemStack silicon = new ItemStack(this.meta,1,11); //CREATED FROM SAND
		ItemStack enrichedSilicon = new ItemStack(this.meta,1,12); //CREATED FROM HYDROGEN
		ItemStack lithiumDust = new ItemStack(this.meta,1,13); //DO SEPARATION FROM CLAY IN A MACHINE
		ItemStack fireessence = new ItemStack(this.meta,1,14); //DROPS DIRECTLY FROM BLOCK
		ItemStack overClocker = new ItemStack(this.meta,1,15); //CIRCUITS and reprogrammed chip
		
		//GameRegistry.addShapedRecipe(output, params)
		//GameRegistry.addShapelessRecipe(output, params)
		//GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(output), params))
		//GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(output), params))
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(advancedSpacesuitHelmet), new Object[] {
			"HHH",
			"HGH",
			"CWC", Character.valueOf('H'), "heavyPlate", Character.valueOf('C'), "advancedCircuits", Character.valueOf('G'), this.tintedGlass, Character.valueOf('W'), "wire"
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(advancedSpacesuitChestplate), new Object[] {
			"HOH",
			"HAH",
			"HTH", Character.valueOf('H'), "heavyPlate", Character.valueOf('T'), thrusterPack, Character.valueOf('A'), arcReactor, Character.valueOf('O'), oxygenApparatus
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(advancedSpacesuitLeggings), new Object[] {
			"HWH",
			"H H",
			"H H", Character.valueOf('H'), "heavyPlate", Character.valueOf('W'), "wire"
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(advancedSpacesuitBoots), new Object[] {
			" W ",
			"H H",
			"HTH", Character.valueOf('H'), "heavyPlate", Character.valueOf('T'), thrusterPack, Character.valueOf('W'), "wire"
		}));
		
		/*GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Item.blazeRod), new Object[] {
			Block.netherrack, Block.netherrack, Block.netherrack, Item.blazePowder
		}));*/
		
	}

	private void languageRegisters() {
		//LanguageRegistry.addName(objectToName, name)
		LanguageRegistry.instance().addStringLocalization("itemGroup.tabSA", "en_US", "SpaceAge");
		
		LanguageRegistry.instance().addStringLocalization("spaceage.achSpace", "en_US", "The Final Frontier");
		LanguageRegistry.instance().addStringLocalization("spaceage.achSpaceDeath", "en_US", "In Space, no-one can hear you scream.");
		
		LanguageRegistry.addName(advancedSpacesuitHelmet, "Advanced Helmet");
		LanguageRegistry.addName(advancedSpacesuitChestplate, "Advanced Chestplate");
		LanguageRegistry.addName(advancedSpacesuitLeggings, "Advanced Leggings");
		LanguageRegistry.addName(advancedSpacesuitBoots, "Advanced Boots");
		
		LanguageRegistry.addName(fireResistanceHelmet, "Flame Resistant Helmet");
		LanguageRegistry.addName(fireResistanceChestplate, "Flame Resistant Chestplate");
		LanguageRegistry.addName(fireResistanceLeggings, "Flame Resistant Leggings");
		LanguageRegistry.addName(fireResistanceBoots, "Flame Resistant Boots");
		
		LanguageRegistry.addName(repulsor, "Laser Cannon");
		
		LanguageRegistry.addName(new ItemStack(spaceshipAlloyMeta, 1, 0), "Black Alloy");
		LanguageRegistry.addName(new ItemStack(spaceshipAlloyMeta, 1, 1), "Red Alloy");
		LanguageRegistry.addName(new ItemStack(spaceshipAlloyMeta, 1, 2), "Green Alloy");
		LanguageRegistry.addName(new ItemStack(spaceshipAlloyMeta, 1, 3), "Brown Alloy");
		LanguageRegistry.addName(new ItemStack(spaceshipAlloyMeta, 1, 4), "Blue Alloy");
		LanguageRegistry.addName(new ItemStack(spaceshipAlloyMeta, 1, 5), "Purple Alloy");
		LanguageRegistry.addName(new ItemStack(spaceshipAlloyMeta, 1, 6), "Cyan Alloy");
		LanguageRegistry.addName(new ItemStack(spaceshipAlloyMeta, 1, 7), "Silver Alloy");
		LanguageRegistry.addName(new ItemStack(spaceshipAlloyMeta, 1, 8), "Gray Alloy");
		LanguageRegistry.addName(new ItemStack(spaceshipAlloyMeta, 1, 9), "Pink Alloy");
		LanguageRegistry.addName(new ItemStack(spaceshipAlloyMeta, 1, 10), "Lime Alloy");
		LanguageRegistry.addName(new ItemStack(spaceshipAlloyMeta, 1, 11), "Yellow Alloy");
		LanguageRegistry.addName(new ItemStack(spaceshipAlloyMeta, 1, 12), "Light Blue Alloy");
		LanguageRegistry.addName(new ItemStack(spaceshipAlloyMeta, 1, 13), "Magenta Alloy");
		LanguageRegistry.addName(new ItemStack(spaceshipAlloyMeta, 1, 14), "Orange Alloy");
		LanguageRegistry.addName(new ItemStack(spaceshipAlloyMeta, 1, 15), "White Alloy");
		
		LanguageRegistry.addName(new ItemStack(vulcanSurface, 1, 0), "Volcanic Ash");
		LanguageRegistry.addName(new ItemStack(vulcanSurface, 1, 1), "Quartz Encrusted Log");
		LanguageRegistry.addName(new ItemStack(vulcanSurface, 1, 2), "Glowdust Leaves");
		LanguageRegistry.addName(new ItemStack(vulcanSurface, 1, 3), "Fire Essence");
		
		LanguageRegistry.addName(new ItemStack(hadesSurface, 1, 0), "Dirt");
		
		LanguageRegistry.addName(new ItemStack(T0011Surface, 1, 0), "Cyberdirt");
		LanguageRegistry.addName(new ItemStack(T0011Surface, 1, 1), "Photovoltaic Leaves");
		LanguageRegistry.addName(new ItemStack(T0011Surface, 1, 2), "Heavy Alloy Encrusted Log");
		LanguageRegistry.addName(new ItemStack(T0011Surface, 1, 3), "Chlorophyll");
		
		LanguageRegistry.addName(new ItemStack(metaGenerator, 1, 0), "Geothermal Turbine");
		LanguageRegistry.addName(new ItemStack(metaGenerator, 1, 1), "Photovoltaic Panel");
		
		LanguageRegistry.addName(new ItemStack(meta, 1, 0), "Titanium Ingot");
		LanguageRegistry.addName(new ItemStack(meta, 1, 1), "Aluminium Ingot");
		LanguageRegistry.addName(new ItemStack(meta, 1, 2), "Vanadium Ingot");
		LanguageRegistry.addName(new ItemStack(meta, 1, 3), "Heavy Duty Ingot");
		LanguageRegistry.addName(new ItemStack(meta, 1, 4), "Arc Reactor");
		LanguageRegistry.addName(new ItemStack(meta, 1, 5), "Heavy Duty Plate");
		LanguageRegistry.addName(new ItemStack(meta, 1, 6), "Basic Circuit");
		LanguageRegistry.addName(new ItemStack(meta, 1, 7), "Advanced Circuit");
		LanguageRegistry.addName(new ItemStack(meta, 1, 8), "Aluminium Wire");
		LanguageRegistry.addName(new ItemStack(meta, 1, 9), "Oxygen Apparatus");
		LanguageRegistry.addName(new ItemStack(meta, 1, 10), "Thruster Pack");
		LanguageRegistry.addName(new ItemStack(meta, 1, 11), "Raw Silicon");
		LanguageRegistry.addName(new ItemStack(meta, 1, 12), "Enriched Silicon");
		LanguageRegistry.addName(new ItemStack(meta, 1, 13), "Lithium Dust");
		LanguageRegistry.addName(new ItemStack(meta, 1, 14), "Fire-Infused Essence");
		LanguageRegistry.addName(new ItemStack(meta, 1, 15), "Overclocking Chip");
		
		LanguageRegistry.addName(tintedGlass, "Reinforced Glass");
		
	}

	private void gameRegisters() {
		//GameRegistry.registerBlock(block, name)
		//GameRegistry.registerItem(item, name) (used only for non meta items)
		GameRegistry.registerItem(advancedSpacesuitHelmet, "Advanced Helmet");
		GameRegistry.registerItem(advancedSpacesuitChestplate, "Advanced Chestplate");
		GameRegistry.registerItem(advancedSpacesuitLeggings, "Advanced Leggings");
		GameRegistry.registerItem(advancedSpacesuitBoots, "Advanced Boots");
		
		GameRegistry.registerItem(fireResistanceHelmet, "Flame Resistant Helmet");
		GameRegistry.registerItem(fireResistanceChestplate, "Flame Resistant Chestplate");
		GameRegistry.registerItem(fireResistanceLeggings, "Flame Resistant Leggings");
		GameRegistry.registerItem(fireResistanceBoots, "Flame Resistant Boots");
		
		GameRegistry.registerItem(repulsor, "Laser Cannon");
		
		//GameRegistry.registerBlock(metaGenerator, ItemBlockGeneratorTooltip.class, "metaGenerator");
		this.metaRegister(metaGenerator, ItemBlockGeneratorTooltip.class, metaGenerator.getUnlocalizedName());
		GameRegistry.registerTileEntity(TileHeatGenerator.class, "heatGenerator");
		GameRegistry.registerTileEntity(TileSolarPanel.class, "solarPanel");
		
		GameRegistry.registerBlock(tintedGlass, "Reinforced Glass");
		
		//GameRegistry.registerBlock(spaceshipAlloyMeta, ItemBlockSpaceshipAlloy.class, modid + (spaceshipAlloyMeta.getUnlocalizedName().substring(5)));
		this.metaRegister(spaceshipAlloyMeta, ItemBlockSpaceshipAlloy.class, spaceshipAlloyMeta.getUnlocalizedName());
		//GameRegistry.registerBlock(vulcanSurface, ItemBlockVulcan.class, modid + (vulcanSurface.getUnlocalizedName().substring(5)));
		
		this.metaRegister(vulcanSurface, ItemBlockVulcan.class, vulcanSurface.getUnlocalizedName());
		this.metaRegister(hadesSurface, ItemBlockHades.class, hadesSurface.getUnlocalizedName());
		this.metaRegister(T0011Surface, ItemBlock0011.class, T0011Surface.getUnlocalizedName());
	}
	public void metaRegister(Block block, Class<? extends ItemBlock> itemclass, String unlocalisedName) {
		GameRegistry.registerBlock(block, itemclass, modid + (unlocalisedName.substring(5)));
	}
	
/*	  public boolean onTickInGame(float f, Minecraft minecraft) {
	    ItemStack boots = minecraft.thePlayer.inventory.armorInventory[0];
	    ItemStack legs = minecraft.thePlayer.inventory.armorInventory[1];
	    ItemStack chest = minecraft.thePlayer.inventory.armorInventory[2];
	    ItemStack helm = minecraft.thePlayer.inventory.armorInventory[3];
	    
	    if (minecraft.thePlayer.inventory.armorItemInSlot(2) != null)
	    {
	      ItemStack itemstack = minecraft.thePlayer.inventory.armorItemInSlot(2);
	      if ((itemstack.itemID == advancedSpacesuitChestplate.itemID) && (minecraft.thePlayer.motionY < 0.0D) && 
	        (minecraft.currentScreen == null) && (Keyboard.isKeyDown(33)))
	      {
	        minecraft.thePlayer.motionY /= 1.600000023841858D;
	        minecraft.thePlayer.fallDistance = 0.0F;
	      }

	    }
	    
	    if (minecraft.thePlayer.inventory.armorItemInSlot(0) != null)
	    {
	      ItemStack itemstack = minecraft.thePlayer.inventory.armorItemInSlot(0);
	      if (itemstack.itemID == advancedSpacesuitBoots.itemID)
	      {
	        if ((minecraft.currentScreen == null) && (Keyboard.isKeyDown(minecraft.gameSettings.keyBindJump.keyCode)))
	        {
	          if (minecraft.thePlayer.motionY > 0.0D)
	          {
	            minecraft.thePlayer.motionY += 0.08499999910593033D;
	          }
	          else
	          {
	            minecraft.thePlayer.motionY += 0.1169999991059303D;
	          }

	          minecraft.theWorld.spawnParticle("flame", minecraft.thePlayer.posX, minecraft.thePlayer.posY - 1.03D, minecraft.thePlayer.posZ, 0.0D, 0.1D, 0.0D);
	          minecraft.theWorld.spawnParticle("flame", minecraft.thePlayer.posX, minecraft.thePlayer.posY - 1.3D, minecraft.thePlayer.posZ, 0.0D, 0.1D, 0.0D);
	          minecraft.theWorld.spawnParticle("flame", minecraft.thePlayer.posX, minecraft.thePlayer.posY - 1.3D, minecraft.thePlayer.posZ, 0.0D, 0.1D, 0.0D);
	        }

	        if (minecraft.thePlayer.motionY < 0.0D)
	        {
	          minecraft.thePlayer.motionY /= 1.149999976158142D;
	        }

	        if (!minecraft.thePlayer.onGround)
	        {
	          minecraft.thePlayer.motionX *= 1.069999961853027D;
	          minecraft.thePlayer.motionZ *= 1.069999961853027D;
	        }

	      }

	    }
	    
	    return true;
	    
	  }*/
}
