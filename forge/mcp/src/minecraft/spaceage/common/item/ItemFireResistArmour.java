package spaceage.common.item;

import spaceage.common.SpaceAgeCore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;

public class ItemFireResistArmour extends ItemArmor implements ISpecialArmor {
	
	private String texturePath = SpaceAgeCore.modid + ":" + "textures/armour/";

	public ItemFireResistArmour(int par1, EnumArmorMaterial par2EnumArmorMaterial,
			int par3, int par4) {
		super(par1, par2EnumArmorMaterial, par3, par4);
		this.setCreativeTab(SpaceAgeCore.tabSA);
	}
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register) {
		this.itemIcon = register.registerIcon(SpaceAgeCore.modid + ":" + this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".")+1));
	}
	
	@Override
	public String getArmorTexture(ItemStack itemstack, Entity entity, int slot,
	String type) {
	switch(slot){
		case 2:
			return this.texturePath + "fire_resist_2.png";
	//2 should be the slot for legs
		default:
			return this.texturePath + "fire_resist_1.png";
		}
	}

	@Override
	public ArmorProperties getProperties(EntityLivingBase player,
			ItemStack armor, DamageSource source, double damage, int slot) {
		if(source == source.lava || source == source.inFire || source == source.onFire) {
			return new ArmorProperties(1, 1, MathHelper.floor_double(damage * .25D));
		}
		return new ArmorProperties(0, 0, 0);
	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		return 0; //might need this
	}

	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack,
			DamageSource source, int damage, int slot) { //might need this
	}
}
