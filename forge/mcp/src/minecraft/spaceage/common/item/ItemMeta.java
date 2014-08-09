package spaceage.common.item;

import java.util.List;

import spaceage.common.SpaceAgeCore;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;

public class ItemMeta extends Item {

	public ItemMeta(int id) {
		super(id);
		setHasSubtypes(true);
		this.setCreativeTab(SpaceAgeCore.tabSA);
	}
	
	@SideOnly(Side.CLIENT)
	private Icon[] icons;
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1) {
		//this.blockIcon = par1.registerIcon(MoreFood.modid + ":" + (this.getUnlocalizedName().substring(5)));
		icons = new Icon[15];
		
		for(int i = 0; i < icons.length; i++) {
			icons[i] = par1.registerIcon(SpaceAgeCore.modid + ":" + (this.getUnlocalizedName().substring(5)) + namesTexture);
		}
		
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List dataList, boolean bool) {
		ItemStack titanium = new ItemStack(SpaceAgeCore.meta,1,0);
		ItemStack aluminium = new ItemStack(SpaceAgeCore.meta,1,1);
		ItemStack vanadium = new ItemStack(SpaceAgeCore.meta,1,2);
		ItemStack heavyI = new ItemStack(SpaceAgeCore.meta,1,3);
		ItemStack arc = new ItemStack(SpaceAgeCore.meta,1,4);
		ItemStack heavyP = new ItemStack(SpaceAgeCore.meta,1,5);
		ItemStack basicC = new ItemStack(SpaceAgeCore.meta,1,6);
		ItemStack advC = new ItemStack(SpaceAgeCore.meta,1,7);
		ItemStack wire = new ItemStack(SpaceAgeCore.meta,1,8);
		ItemStack oxygen = new ItemStack(SpaceAgeCore.meta,1,9);
		ItemStack thruster = new ItemStack(SpaceAgeCore.meta,1,10);
		ItemStack siliconR = new ItemStack(SpaceAgeCore.meta,1,11);
		ItemStack siliconE = new ItemStack(SpaceAgeCore.meta,1,12);
		ItemStack lithium = new ItemStack(SpaceAgeCore.meta,1,13);
		ItemStack fireessence = new ItemStack(SpaceAgeCore.meta,1,14);
		ItemStack overClocker = new ItemStack(SpaceAgeCore.meta,1,15);
		
		switch(itemStack.getItemDamage()) {
			case 0:
				dataList.add("Ti");
			case 1:
				dataList.add("Al");
			case 2:
				dataList.add("V");
			case 3:
				dataList.add("Ti6Al4V"); //6% aluminium, 4% vanadium, 90% titanium
			//case 4:
				//dataList.add("");
			case 5:
				dataList.add("Ti6Al4V");
			case 11:
				dataList.add("Si");
			case 12:
				dataList.add("Si2H");
			case 13:
				dataList.add("Li");
			case 14:
				dataList.add("");
		}
		
		//dataList.add("Precious Gemstone");
	}
	
	public static final String[] names = new String[] {"Titanium Ingot", "Aluminium Ingot", "Vanadium Ingot", "Heavy Duty Ingot", "Arc Reactor", "Heavy Duty Plate", "Basic Circuit", "Advanced Circuit", "Aluminium Wire", "Oxygen Apparatus", "Thruster Pack", "Raw Silicon", "Enriched Silicon", "Lithium Dust", "Fire-Infused Essence", "Overclocking Chip"};
	public static final String[] namesTexture = new String[] {"titaniumIngot", "aluminiumIngot", "vanadiumIngot", "heavyDutyIngot", "arcReactor", "heavyDutyPlate", "basicCircuit", "advancedCircuit", "aluminiumWire", "oxygenApparatus", "thrusterPack", "rawSilicon", "enrichedSilicon", "lithiumDust", "fire-InfusedEssence", "overclockingChip"};
	
	public String getUnlocalizedName(ItemStack par1) {
		int i = MathHelper.clamp_int(par1.getItemDamage(), 0, 15);
		return super.getUnlocalizedName() + "." + names[i];
	}
	
	public Icon getIconFromDamage(int par1) {
		return icons[par1];
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs par2, List par3) {
		for(int i = 0; i < 15; i++) {
			par3.add(new ItemStack(par1, 1, i));
		}
	}

}
