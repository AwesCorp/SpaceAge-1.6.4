/*Main file of SpaceAge. 
 * -SkylordJoel*/
//Many thanks to Reika, Tihyo and Colossali for example references for certain code I couldn't work out
package spaceage.common;

import java.io.File;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.logging.Level;

import org.lwjgl.input.Keyboard;

import spaceage.client.gui.SPGUI;
import spaceage.common.block.BlockHeatGenerator;
//import resonant.lib.content.ContentRegistry;
//import resonant.lib.content.IDManager;
import spaceage.common.block.BlockOres1;
import spaceage.common.block.BlockSpaceshipAlloy;
import spaceage.common.item.ItemBlockSpaceshipAlloy;
import spaceage.common.item.ItemBlockTooltip;
import spaceage.common.item.ItemMeta;
import spaceage.common.item.ItemStarboost;
import spaceage.common.tile.TileHeatGenerator;
import spaceage.common.tile.TileSolarPanel;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
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

@Mod(modid=SpaceAgeCore.modid, name="SpaceAge - bringing Minecraft to the Space Age", version="Beta 0.1", dependencies="required-after:UniversalElectricity")
@NetworkMod(clientSideRequired=true, serverSideRequired=false/*, serverPacketHandlerSpec=@NetworkMod.SidedPacketHandler(channels={"SpaceAge_C"}, packetHandler=ServerPacketHandler.class)*/)

public class SpaceAgeCore {

	public static CreativeTabs tabSA = new CreativeTabs("tabSA") {
		public ItemStack getIconItemstack() {
			return new ItemStack(spaceshipAlloyMeta);
		}
	};
	
	@SidedProxy(clientSide = "spaceage.client.ClientProxy", serverSide = "spaceage.common.CommonProxy")
	public static CommonProxy proxy;
	
	public static final String modid = "SpaceAge";
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
	public static BlockContainer heatGenerator;
	
	//Custom ItemStacks (for crafting ease etc.)
	
	//ID Registry
	public static int metaID;
	public static int ores1ID;
	public static int spaceshipAlloyMetaID;
	public static int advancedSpacesuitHelmetID;
	public static int advancedSpacesuitChestplateID;
	public static int advancedSpacesuitLeggingsID;
	public static int advancedSpacesuitBootsID;
	public static int SOLAR_ENERGY;
	public static int HEAT_ENERGY;
	public static int heatGeneratorID;
	
	//Test UE registry
	//public static Block blockSolarPanel;
	//public static final ContentRegistry contentRegistry = new ContentRegistry(/*Settings.CONFIGURATION*/SpaceAgeCore.config, /*Settings.idManager, ID*/SpaceAgeCore.idManager, modid).setPrefix(SpaceAgeCore.REFERENCE).setTab(SpaceAgeCore.tabSA);
	
	public static final Configuration config = new Configuration(new File("config/AwesCorp/SpaceAgeCore.cfg"));
	
	//public static IDManager idManager;
	
	//Fluid stuff
	//public static FluidStack FLUIDSTACK_WATER;

	//public static int glidingKey;
	
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
		SOLAR_ENERGY = config.get("Energy", "How much energy the solar panel generates - do not edit this to play on the server", 50).getInt();
		HEAT_ENERGY = config.get("Energy", "How much energy the geothermal turbine generates - do not edit this to play on the server", 50).getInt();
		heatGeneratorID = config.get("Blocks", "Value of the geothermal turbine - do not edit this to play on the server", 502).getInt();
		
		//UE crap
		//idManager = new IDManager(config.get(Configuration.CATEGORY_BLOCK, "UE crap", 1200).getInt(), config.get(Configuration.CATEGORY_ITEM, "More UE crap", 20150).getInt());
		
		//glidingKey = config.get("Keybinds", "Gliding Key", 42).getInt();
		
		config.save();
	}	
	
	/*public static int getFluidAmount(FluidStack fluid) {
		if(fluid != null) {
			return fluid.amount;
		}
		return 0;
	}*/
	
	//public static final PacketTile PACKET_TILE = new PacketTile("spaceage");
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		//FLUIDSTACK_WATER = new FluidStack(FluidRegistry.WATER, 0);
	       metadata.modId = this.modid;
	       metadata.name = "SpaceAge";
	       metadata.description = "SpaceAge: Bringing Minecraft to the Space Age";
	       metadata.url = "Not released yet";
	       metadata.logoFile = "assets/spaceage/logo.png";
	       metadata.version = "Beta 0.1";
	       metadata.authorList = Arrays.asList(new String[] { 
	    		   "SkylordJoel", "big_fat_bunny", "Super_Jen_Bot" 
	    		   });
	       metadata.credits = "Thanks to many for ideas!";
	       metadata.autogenerated = false;
		LogHelper.log(Level.INFO, "Preinitialised successfully"); //about the preinitialiSed, I'm australian
	}
	
	@EventHandler
	public void Init(FMLInitializationEvent event) {
		this.initConfiguration(event);
		
		EnumArmorMaterial armourADVANCEDSPACESUIT = EnumHelper.addArmorMaterial("ADVANCEDSPACESUIT", 50, new int[]{8, 20, 10, 6}, 30);
		
		meta = new ItemMeta/*meta*/(this.metaID).setUnlocalizedName("basicItem");
		
		spaceshipAlloyMeta = new BlockSpaceshipAlloy(this.spaceshipAlloyMetaID, Material.rock).setUnlocalizedName("spaceshipAlloy");
		
		ores1 = new BlockOres1(this.ores1ID, Material.rock).setUnlocalizedName("spaceAgeOres1");
		
		advancedSpacesuitHelmet = new ItemStarboost(this.advancedSpacesuitHelmetID, armourADVANCEDSPACESUIT, 0, 0).setUnlocalizedName("advHelmet");
		advancedSpacesuitChestplate = new ItemStarboost(this.advancedSpacesuitChestplateID, armourADVANCEDSPACESUIT, 0, 1).setUnlocalizedName("advChestplate");
		advancedSpacesuitLeggings = new ItemStarboost(this.advancedSpacesuitLeggingsID, armourADVANCEDSPACESUIT, 0, 2).setUnlocalizedName("advLeggings");
		advancedSpacesuitBoots = new ItemStarboost(this.advancedSpacesuitBootsID, armourADVANCEDSPACESUIT, 0, 3).setUnlocalizedName("advBoots");
		
		//Machines
		heatGenerator = (BlockContainer) new BlockHeatGenerator(heatGeneratorID).setUnlocalizedName("heatGenerator");
		
		//UE Test stuff
		//blockSolarPanel = contentRegistry.newBlock(TileSolarPanel.class);
		
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
	    
	    NetworkRegistry.instance().registerGuiHandler(SpaceAgeCore.instance, SpaceAgeCore.guiHandler);
		
		proxy.registerRenderers();
		proxy.load();
	    proxy.registerServerTickHandler();
	    proxy.registerHandlers();
	    //proxy.getArmorModel();
		//TickRegistry.registerTickHandler(new SpaceAgeServerTickHandler(EnumSet.of(TickType.SERVER)), Side.SERVER);
		
		LogHelper.log(Level.INFO, "Initialised successfully");
}

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
		OreDictionary.registerOre("titaniumAluminumVanadiumAlloyPlate", heavyPlate);
		
		
	}

	private void blockHarvest() {
		//MinecraftForge.setBlockHarvestLevel(block, toolClass, harvestLevel)
		MinecraftForge.setBlockHarvestLevel(spaceshipAlloyMeta, "pickaxe", 2);
		MinecraftForge.setBlockHarvestLevel(spaceshipAlloyMeta, "pickaxe", 3);
		MinecraftForge.setBlockHarvestLevel(heatGenerator, "pickaxe", 2);
		MinecraftForge.setBlockHarvestLevel(heatGenerator, "pickaxe", 3);
	}

	private void smeltingRecipes() {
		//GameRegistry.addSmelting(input, output, xp)
		
	}

	private void craftingRecipes() {
		
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
		
		//GameRegistry.addShapedRecipe(output, params)
		//GameRegistry.addShapelessRecipe(output, params)
		//GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(output), params))
		//GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(output), params))
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(advancedSpacesuitHelmet), new Object[] {
			"HHH",
			"HGH",
			"CWC", Character.valueOf('H'), heavyPlate, Character.valueOf('C'), advancedCircuits, Character.valueOf('G'), Block.glass, Character.valueOf('W'), wire
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(advancedSpacesuitChestplate), new Object[] {
			"HOH",
			"HAH",
			"HTH", Character.valueOf('H'), heavyPlate, Character.valueOf('T'), thrusterPack, Character.valueOf('A'), arcReactor, Character.valueOf('O'), oxygenApparatus
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(advancedSpacesuitLeggings), new Object[] {
			"HWH",
			"H H",
			"H H", Character.valueOf('H'), heavyPlate, Character.valueOf('W'), wire
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(advancedSpacesuitBoots), new Object[] {
			" W ",
			"H H",
			"HTH", Character.valueOf('H'), heavyPlate, Character.valueOf('T'), thrusterPack, Character.valueOf('W'), wire
		}));
		
	}

	private void languageRegisters() {
		//LanguageRegistry.addName(objectToName, name)
		LanguageRegistry.instance().addStringLocalization("itemGroup.tabSA", "en_US", "SpaceAge");
		LanguageRegistry.addName(advancedSpacesuitHelmet, "Advanced Helmet");
		LanguageRegistry.addName(advancedSpacesuitChestplate, "Advanced Chestplate");
		LanguageRegistry.addName(advancedSpacesuitLeggings, "Advanced Leggings");
		LanguageRegistry.addName(advancedSpacesuitBoots, "Advanced Boots");
		
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
		
		LanguageRegistry.addName(heatGenerator, "Geothermal Turbine");
		
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
		
	}

	private void gameRegisters() {
		//GameRegistry.registerBlock(block, name)
		//GameRegistry.registerItem(item, name) (used only for non meta items)
		GameRegistry.registerItem(advancedSpacesuitHelmet, "Advanced Helmet");
		GameRegistry.registerItem(advancedSpacesuitChestplate, "Advanced Chestplate");
		GameRegistry.registerItem(advancedSpacesuitLeggings, "Advanced Leggings");
		GameRegistry.registerItem(advancedSpacesuitBoots, "Advanced Boots");
		
		GameRegistry.registerBlock(heatGenerator, ItemBlockTooltip.class, "heatGenerator");
		GameRegistry.registerTileEntity(TileHeatGenerator.class, "heatGenerator");
		
		GameRegistry.registerBlock(spaceshipAlloyMeta, ItemBlockSpaceshipAlloy.class, modid + (spaceshipAlloyMeta.getUnlocalizedName().substring(5)));
		
	}
	
	  public boolean onTickInGame(float f, Minecraft minecraft) {
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
	    
	  }
	
}
