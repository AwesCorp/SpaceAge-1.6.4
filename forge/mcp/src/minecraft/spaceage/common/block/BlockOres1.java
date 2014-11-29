package spaceage.common.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import spaceage.common.SpaceAgeCore;
import spaceage.integration.AEIntegration;
import spaceage.planets.SpaceAgePlanets;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockOres1 extends Block { 
	//Ores - 
	//[TITANIUM IS ILMENITE, ON VULCAN, POSSIBLY EDEN], 1, 2
	//[IRON IS MAGNETITE ORE, ON VULCAN, POSSIBLY EDEN], 3, 4
	//[ALUMINIUM IS BAUXITE ORE, POSSIBLY EDEN], 5
	//[VANADIUM FOUND IN MAGNETITE ORE], 
	//[LITHIUM IS PEGMATITE, ON VULCAN], 6
	//[SILVER IS ARGENTITE ORE, POSSIBLY EDEN], 7
	//[COPPER IS CHALCOPYRITE ORE, ON VULCAN], 8
	//[GOLD IS SYLVANITE ORE, POSSIBLY EDEN], 9
	//[COAL, DEFINITELY EDEN], 10
	//[DIAMOND (KIMBERLITE), VULCAN], 11
	//[EMERALD IS PEGMATITE, ON VULCAN], 12
	//[LAPIS LAZULI (LAZURITE), needs coral so ocean planet], 13, 14
	//[QUARTZ (quartzite?), POSSIBLY VULCAN], 15

	public BlockOres1(int ID, Material material) {
		super(ID, material);
		this.setCreativeTab(SpaceAgeCore.tabSA);
	}
	
   /* @SideOnly(Side.CLIENT)
    private Icon iconTitaniumOverlay;
    
    @SideOnly(Side.CLIENT)
    private Icon iconIronOverlay;
    
    @SideOnly(Side.CLIENT)
    private Icon iconAluminiumOverlay;
    
    @SideOnly(Side.CLIENT)
    private Icon iconVanadiumOverlay;
    
    @SideOnly(Side.CLIENT)
    private Icon iconLithiumOverlay;
    
    @SideOnly(Side.CLIENT)
    private Icon iconSilverOverlay;
    
    @SideOnly(Side.CLIENT)
    private Icon iconCopperOverlay;
    
    @SideOnly(Side.CLIENT)
    private Icon iconGoldOverlay;
    
    @SideOnly(Side.CLIENT)
    private Icon iconCoalOverlay;
    
    @SideOnly(Side.CLIENT)
    private Icon iconDiamondOverlay;
    
    @SideOnly(Side.CLIENT)
    private Icon iconEmeraldOverlay;
    
    @SideOnly(Side.CLIENT)
    private Icon iconLapisOverlay;
    
    @SideOnly(Side.CLIENT)
    private Icon iconQuartzOverlay;*/
	
	@SideOnly(Side.CLIENT)
	private Icon[] icons;
	
	public World worldObj;
	
	public Random rand;
	
	public static final String[] iconNames = new String[] {"vulcanTitanium"/*1*/, "edenTitanium"/*1*/, "vulcanIron"/*2*/, "edenIron"/*2*/, "edenAluminium"/*3*/, "vulcanLithium"/*4*/, "edenSilver"/*5*/, "vulcanCopper"/*6*/, "edenGold"/*7*/, "edenCoal"/*8*/, "vulcanDiamond"/*9*/, "vulcanEmerald"/*10*/, "ontarineLapis"/*11*/, "hadesLapis"/*11*/, "vulcanQuartz"/*12*/};
	
	//TODO - customise amount of ores, etc.
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		icons = new Icon[15];
		
		for(int i = 0; i < icons.length; i++) {
			icons[i] = par1IconRegister.registerIcon(SpaceAgeCore.modid + ":" + iconNames);
		}
		
		/*iconTitaniumOverlay = par1IconRegister.registerIcon(SpaceAgeCore.modid + ":titaniumOverlay");
		iconAluminiumOverlay = par1IconRegister.registerIcon(SpaceAgeCore.modid + ":aluminiumOverlay");
		iconIronOverlay = par1IconRegister.registerIcon(SpaceAgeCore.modid + ":ironOverlay");
		iconVanadiumOverlay = par1IconRegister.registerIcon(SpaceAgeCore.modid + ":vanadiumOverlay");
		iconLithiumOverlay = par1IconRegister.registerIcon(SpaceAgeCore.modid + ":lithiumOverlay");
		iconSilverOverlay = par1IconRegister.registerIcon(SpaceAgeCore.modid + ":silverOverlay");
		iconCopperOverlay = par1IconRegister.registerIcon(SpaceAgeCore.modid + ":copperOverlay");
		iconGoldOverlay = par1IconRegister.registerIcon(SpaceAgeCore.modid + ":goldOverlay");
		iconCoalOverlay = par1IconRegister.registerIcon(SpaceAgeCore.modid + ":coalOverlay");
		iconDiamondOverlay = par1IconRegister.registerIcon(SpaceAgeCore.modid + ":diamondOverlay");
		iconEmeraldOverlay = par1IconRegister.registerIcon(SpaceAgeCore.modid + ":emeraldOverlay");
		iconLapisOverlay = par1IconRegister.registerIcon(SpaceAgeCore.modid + ":lapisOverlay");
		iconQuartzOverlay = par1IconRegister.registerIcon(SpaceAgeCore.modid + ":quartzOverlay");*/
	}
	
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int par1, int par2) {
		switch(par2) {
			case 0:
				if(this.worldObj.provider.dimensionId == SpaceAgePlanets.i.vulcanID) {
					return icons[0];
				} else if(this.worldObj.provider.dimensionId == SpaceAgePlanets.i.edenID) {
					return icons[1];
				}
				return icons[0];
			case 1:
				if(this.worldObj.provider.dimensionId == SpaceAgePlanets.i.vulcanID) {
					return icons[2];
				} else if(this.worldObj.provider.dimensionId == SpaceAgePlanets.i.edenID) {
					return icons[3];
				}
				return icons[2];
			case 2:
				return icons[4];
			case 3:
				return icons[5];
			case 4:
				return icons[6];
			case 5:
				return icons[7];
			case 6:
				return icons[8];
			case 7:
				return icons[9];
			case 8:
				return icons[10];
			case 9:
				return icons[11];
			case 10:
				if(this.worldObj.provider.dimensionId == SpaceAgePlanets.i.ontarineID) {
					return icons[12];
				} else if(this.worldObj.provider.dimensionId == SpaceAgePlanets.i.hadesID) {
					return icons[13];
				}
				return icons[12];
			case 11:
				return icons[14];
			default: {
				System.out.println("Invalid metadata for " + this.getUnlocalizedName());
				return icons[0];
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) {
		for(int i = 0; i < 12; i++) {
			par3List.add(new ItemStack(par1, 1, i));
		}		
	}
	
	@Override
	public int damageDropped(int par1) {
		switch(par1) {
			case 11:
				return 0;
			default:
				return par1;
		}
	}
	
	@Override
	public int idDropped(int meta, Random random, int par3) {
		switch(meta) {
			case 11:
				return Item.netherQuartz.itemID;
			default:
				return this.blockID;
		}
	}
	
	@Override
	public void onBlockDestroyedByPlayer(World par1World, int par2, int par3, int par4, int par5) {
		if(!par1World.isRemote) {
			int blockMetaData = par5;
			
			switch(blockMetaData) {
				case 11:
					if(Loader.isModLoaded("AppliedEnergistics")) {
						par1World.spawnEntityInWorld(new EntityItem(par1World, par2, par3, par4, SpaceAgeCore.integration.ae.quartzStack));
						par1World.spawnEntityInWorld(new EntityItem(par1World, par2, par3, par4, SpaceAgeCore.integration.ae.quartzStack));
						par1World.spawnEntityInWorld(new EntityItem(par1World, par2, par3, par4, SpaceAgeCore.integration.ae.quartzStack));
					return;
					}
			}
			return;	
		}			
	}
	
	@Override
	public int quantityDropped(int meta, int fortune, Random random) {
		switch(meta) {
			case 11:
				return 3;
		}
		return 1;
	}
}
