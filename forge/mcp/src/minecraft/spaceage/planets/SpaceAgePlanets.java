package spaceage.planets;

import java.io.File;
import java.util.logging.Level;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.oredict.OreDictionary;
import spaceage.common.LogHelper;
import spaceage.common.SAItemStack;
import spaceage.common.SpaceAgeCore;
import spaceage.common.block.BlockOres1;
import spaceage.common.item.ItemBlockOres1;
import spaceage.planets.eden.WorldProviderEden;
import spaceage.planets.general.BiomeList;
import spaceage.planets.hades.WorldProviderHades;
import spaceage.planets.ontarine.WorldProviderOntarine;
import spaceage.planets.proxy.CommonProxy;
import spaceage.planets.technoorganic.WorldProvider0011;
import spaceage.planets.vulcan.WorldProviderVulcan;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;

/** The planet child mod of SpaceAge.
 * 
 * @author SkylordJoel
 *
 */

@Mod(modid=SpaceAgePlanets.modid, name="SpaceAge Planets", version="Alpha", dependencies="required-after:SpaceAge;after:RotaryCraft")
@NetworkMod(clientSideRequired=true, serverSideRequired=false/*, serverPacketHandlerSpec=@NetworkMod.SidedPacketHandler(channels={"SpaceAge_C"}, packetHandler=ServerPacketHandler.class)*/)

public class SpaceAgePlanets {

	@SidedProxy(clientSide = "spaceage.planets.proxy.ClientProxy", serverSide = "spaceage.planets.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	public static BiomeList biomes = new BiomeList();
	
	public static SpaceAgePlanets i;
	
	public static Block ores1;	
	public static int ores1ID;
	
	public static final String modid = "SpaceAgePlanets";
	
	@Instance("SpaceAgePlanets")
	public static SpaceAgePlanets instance;
	
	//Dimension ID Registry
	public int vulcanID;
	public int hadesID;
	public int T0011ID;
	public int edenID;
	public int ontarineID;
	
	//Biome ID Registry (CANNOT BE 23 - SPACE IS 23)
	public static int vulcanBiomeID = 25; 
	public static int vulcanVolcanoBiomeID = 26;
	
	public static int hadesBiomeID = 27;
	
	public static int T0011BiomeID = 28;
	public static int T0011ClearBiomeID = 29;

	public static int edenBiomeID = 30;
	
	public static int ontarineBiomeID = 31;
	public static int ontarineReefID = 32;
	
	public static final Configuration config = new Configuration(new File("config/AwesCorp/SpaceAgePlanets.cfg"));
	
	public void initConfiguration(FMLPreInitializationEvent event) {
		config.load();
		
		ores1ID = config.get("Blocks", "Value of ores, no 1 - do not edit this to play on the server", 501).getInt();
		
		config.save();
	}
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ores1 = new BlockOres1(this.ores1ID, Material.rock).setUnlocalizedName("spaceAgeOres1");
		
		metaRegister(ores1, ItemBlockOres1.class, ores1.getUnlocalizedName());
		//VOID
		LogHelperPlanet.log(Level.FINEST, "Preinitialised successfully");
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		//BLOCKS AND ITEMS ARE REGISTERED IN MAIN SPACEAGE
		
		proxy.registerRenderers();
	
		registerVulcan();
		registerHades();
		register0011();
		registerEden();
		registerOntarine();
		
		doCrapWithExternalItemStacks();
		registerOres();
		
		biomes.init();
		
		LogHelperPlanet.log(Level.FINEST, "Initialised successfully");
	}
	
	public void metaRegister(Block block, Class<? extends ItemBlock> itemclass, String unlocalisedName) {
		GameRegistry.registerBlock(block, itemclass, modid + (unlocalisedName.substring(5)));
	}
	
	public void registerOres() {
		ItemStack titanium = new ItemStack(ores1, 1, 0);
		ItemStack magnetite = new ItemStack(ores1, 1, 1);
		ItemStack aluminium = new ItemStack(ores1, 1, 2);
		ItemStack lithiumP = new ItemStack(ores1, 1, 3);
		ItemStack silverOre = new ItemStack(ores1, 1, 4);
		ItemStack copper = new ItemStack(ores1, 1, 5);
		ItemStack gold = new ItemStack(ores1, 1, 6);
		ItemStack coal = new ItemStack(ores1, 1, 7);
		ItemStack diamond = new ItemStack(ores1, 1, 8);
		ItemStack emerald = new ItemStack(ores1, 1, 9);
		ItemStack lapis = new ItemStack(ores1, 1, 10);
		ItemStack quartz = new ItemStack(ores1, 1, 11);
		
		OreDictionary.registerOre("oreTitanium", titanium);
		OreDictionary.registerOre("oreIlmenite", titanium);
		OreDictionary.registerOre("titaniumOre", titanium);
		OreDictionary.registerOre("ilmeniteOre", titanium);
		
		OreDictionary.registerOre("oreMagnetite", magnetite);
		OreDictionary.registerOre("oreIron", magnetite);
		OreDictionary.registerOre("magnetiteOre", magnetite);
		OreDictionary.registerOre("ironOre", magnetite);
		
		OreDictionary.registerOre("oreAluminium", aluminium);
		OreDictionary.registerOre("oreBauxite", aluminium);
		OreDictionary.registerOre("aluminiumOre", aluminium);
		OreDictionary.registerOre("bauxiteOre", aluminium);
		
		OreDictionary.registerOre("pegmatiteLithium", lithiumP);
		OreDictionary.registerOre("lithiumPegmatite", lithiumP);
		
		OreDictionary.registerOre("oreSilver", silverOre);
		OreDictionary.registerOre("oreArgentite", silverOre);
		OreDictionary.registerOre("silverOre", silverOre);
		OreDictionary.registerOre("argentiteOre", silverOre);
		
		OreDictionary.registerOre("oreCopper", copper);
		OreDictionary.registerOre("oreChalcopyrite", copper);
		OreDictionary.registerOre("copperOre", copper);
		OreDictionary.registerOre("chalcopyriteOre", copper);
		
		OreDictionary.registerOre("oreGold", gold);
		OreDictionary.registerOre("oreSylvanite", gold);
		OreDictionary.registerOre("goldOre", gold);
		OreDictionary.registerOre("sylvaniteOre", gold);
		
		OreDictionary.registerOre("oreCoal", coal);
		OreDictionary.registerOre("coalOre", coal);
		
		OreDictionary.registerOre("kimberliteDiamond", diamond);
		OreDictionary.registerOre("diamondKimberlite", diamond);
		
		OreDictionary.registerOre("pegmatiteEmerald", emerald);
		OreDictionary.registerOre("emeraldPegmatite", emerald);
		
		OreDictionary.registerOre("oreLazurite", lapis);
		OreDictionary.registerOre("oreLapis", lapis);
		OreDictionary.registerOre("lazuriteOre", lapis);
		OreDictionary.registerOre("lapisOre", lapis);
		
		OreDictionary.registerOre("oreQuartzite", quartz);
		OreDictionary.registerOre("oreQuartz", quartz);
		OreDictionary.registerOre("quartziteOre", quartz);
		OreDictionary.registerOre("quartzOre", quartz);
	}

	public void doCrapWithExternalItemStacks() {
		SAItemStack.titaniumOre = new ItemStack(ores1, 1, 0);
		SAItemStack.ironOre = new ItemStack(ores1, 1, 1);
		SAItemStack.aluminiumOre = new ItemStack(ores1, 1, 2);
		SAItemStack.lithiumP = new ItemStack(ores1, 1, 3);
		SAItemStack.silverOre = new ItemStack(ores1, 1, 4);
		SAItemStack.copperOre = new ItemStack(ores1, 1, 5);
		SAItemStack.goldOre = new ItemStack(ores1, 1, 6);
		SAItemStack.coalOre = new ItemStack(ores1, 1, 7);
		SAItemStack.diamondOre = new ItemStack(ores1, 1, 8);
		SAItemStack.emeraldOre = new ItemStack(ores1, 1, 9);
		SAItemStack.lapisOre = new ItemStack(ores1, 1, 10);
		SAItemStack.quartzOre = new ItemStack(ores1, 1, 11);
	}

	public void registerVulcan() {
		vulcanID = DimensionManager.getNextFreeDimId();
		
		DimensionManager.registerProviderType(this.vulcanID, WorldProviderVulcan.class, true);
		DimensionManager.registerDimension(vulcanID, vulcanID);
	}
	
	public void registerHades() {
		hadesID = DimensionManager.getNextFreeDimId();
		
		DimensionManager.registerProviderType(this.hadesID, WorldProviderHades.class, true);
		DimensionManager.registerDimension(hadesID, hadesID);
	}
	
	public void register0011() {
		T0011ID = DimensionManager.getNextFreeDimId();
		
		DimensionManager.registerProviderType(this.T0011ID, WorldProvider0011.class, true);
		DimensionManager.registerDimension(T0011ID, T0011ID);
	}
	
	public void registerEden() {
		edenID = DimensionManager.getNextFreeDimId();
		
		DimensionManager.registerProviderType(this.edenID, WorldProviderEden.class, true);
		DimensionManager.registerDimension(edenID, edenID);
	}
	
	public void registerOntarine() {
		ontarineID = DimensionManager.getNextFreeDimId();
		
		DimensionManager.registerProviderType(this.ontarineID, WorldProviderOntarine.class, true);
		DimensionManager.registerDimension(ontarineID, ontarineID);
	}
}
