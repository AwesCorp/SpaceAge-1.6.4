package spaceage.common;

import java.util.List;

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
		icons = new Icon[6];
		
		for(int i = 0; i < icons.length; i++) {
			icons[i] = par1.registerIcon(SpaceAgeCore.modid + ":" + (this.getUnlocalizedName().substring(5)) + i);
		}
		
	}
	
	/*@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer player, List dataList, boolean bool) {
		dataList.add("Precious Gemstone");
	}*/
	
	public static final String[] names = new String[] {"Titanium Ingot", "Aluminium Ingot", "Vanadium Ingot", "Heavy Duty Ingot", "Arc Reactor", "Heavy Duty Plate"};
	
	public String getUnlocalizedName(ItemStack par1) {
		int i = MathHelper.clamp_int(par1.getItemDamage(), 0, 15);
		return super.getUnlocalizedName() + "." + names[i];
	}
	
	public Icon getIconFromDamage(int par1) {
		return icons[par1];
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs par2, List par3) {
		for(int i = 0; i < 6; i++) {
			par3.add(new ItemStack(par1, 1, i));
		}
	}

}
