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
import net.minecraft.world.World;

public class ItemStarboost extends ItemArmor {
	
	private String texturePath = SpaceAgeCore.modid + ":" + "textures/armour/";

	public ItemStarboost(int par1, EnumArmorMaterial par2EnumArmorMaterial,
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
		switch(slot) {
			case 2:
				return this.texturePath + "adv_spacesuit_2.png";
	//2 should be the slot for legs
			default:
				return this.texturePath + "adv_spacesuit_1.png";
			}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving,
	ItemStack itemStack, int armorSlot) {
	
		ModelBiped armorModel = null;
		if(itemStack != null) {
			if(itemStack.getItem() instanceof ItemStarboost) {
				int type = ((ItemArmor)itemStack.getItem()).armorType;

				if(type == 1 || type == 3 || type == 0){
				armorModel = SpaceAgeCore.proxy.getStarboostArmorModel(0);
				}else{
				armorModel = SpaceAgeCore.proxy.getStarboostArmorModel(1);
				}
			}
			if(armorModel != null){
				armorModel.bipedHead.showModel = armorSlot == 0;
				armorModel.bipedHeadwear.showModel = armorSlot == 0;
				armorModel.bipedBody.showModel = armorSlot == 1 || armorSlot == 2;
				armorModel.bipedRightArm.showModel = armorSlot == 1;
				armorModel.bipedLeftArm.showModel = armorSlot == 1;
				armorModel.bipedRightLeg.showModel = armorSlot == 2 || armorSlot == 3;
				armorModel.bipedLeftLeg.showModel = armorSlot == 2 || armorSlot == 3;

				armorModel.isSneak = entityLiving.isSneaking();
				armorModel.isRiding = entityLiving.isRiding();
				armorModel.isChild = entityLiving.isChild();
				armorModel.heldItemRight = entityLiving.getCurrentItemOrArmor(0) != null ? 1 :0;
				if(entityLiving instanceof EntityPlayer){
				armorModel.aimedBow =((EntityPlayer)entityLiving).getItemInUseDuration() > 2;
				}
				return armorModel;
				}
		}
		
		return null;
	}
	
	public void onArmorTickUpdate(World world, EntityPlayer player, ItemStack itemStack) {
		if ((player.getCurrentItemOrArmor(3) != null) && 
				(player.getCurrentItemOrArmor(4) != null) && 
				(player.getCurrentItemOrArmor(2) != null) && 
				(player.getCurrentItemOrArmor(1) != null)) {
			ItemStack chest = player.getCurrentItemOrArmor(3);
	      	ItemStack Helmet = player.getCurrentItemOrArmor(4);
	      	ItemStack Leggings = player.getCurrentItemOrArmor(2);
	      	ItemStack Boots = player.getCurrentItemOrArmor(1);

	      	if ((chest.getItem() == SpaceAgeCore.advancedSpacesuitChestplate) && 
	      			(Helmet.getItem() == SpaceAgeCore.advancedSpacesuitHelmet) && 
	      			(Leggings.getItem() == SpaceAgeCore.advancedSpacesuitLeggings) && 
	      			(Boots.getItem() == SpaceAgeCore.advancedSpacesuitBoots)) {
	      		//player.addPotionEffect(new PotionEffect(Potion.waterBreathing.id, 20, 0));
	      		//player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 20, 0));
	      		}
	      	}
		if (player.getCurrentItemOrArmor(3) != null) {
			ItemStack chest = player.getCurrentItemOrArmor(3);
			if ((chest.getItem() == SpaceAgeCore.advancedSpacesuitChestplate) && 
					(!player.isCollidedVertically)) {
				//world.spawnParticle("flame", player.posX, player.posY - 1.03D, player.posZ, 0.0D, 0.1D, 0.0D);
		        //world.spawnParticle("flame", player.posX, player.posY - 1.3D, player.posZ, 0.0D, 0.1D, 0.0D);
		        //world.spawnParticle("flame", player.posX, player.posY - 1.3D, player.posZ, 0.0D, 0.1D, 0.0D);
		        world.spawnParticle("flame", player.posX - 0.25D, player.posY - 1.5D, player.posZ, 0.0D, -0.1D, 0.0D);
		        world.spawnParticle("smoke", player.posX - 0.25D, player.posY - 1.5D, player.posZ, 0.0D, -0.1D, 0.0D);
		        world.spawnParticle("flame", player.posX + 0.25D, player.posY - 1.5D, player.posZ, 0.0D, -0.1D, 0.0D);
		        world.spawnParticle("smoke", player.posX + 0.25D, player.posY - 1.5D, player.posZ, 0.0D, -0.1D, 0.0D);
	        }
		}
	}
}