//Credit to Superheroes Unlimited Mod
package spaceage.common;

import java.util.EnumSet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class ServerTickHandler implements ITickHandler {
	private EnumSet<TickType> ticksToGet;
	
	  public void PlayerTickHandler(EnumSet<TickType> ticksToGet)
	  {
	    this.ticksToGet = ticksToGet;
	  }

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
			    if (type.equals(EnumSet.of(TickType.PLAYER))) {
			      onPlayerTick((EntityPlayer)tickData[0], null);
		    }
	  }

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.PLAYER, TickType.SERVER);
	}

	@Override
	public String getLabel() {
		return null;
	}
	
	private void onPlayerTick(EntityPlayer player, World world) {
	    if ((player.getCurrentItemOrArmor(4) != null) && (player.getCurrentItemOrArmor(3) != null) && (player.getCurrentItemOrArmor(2) != null) && (player.getCurrentItemOrArmor(1) != null))
	    {
	      ItemStack helmet = player.getCurrentItemOrArmor(4);
	      ItemStack plate = player.getCurrentItemOrArmor(3);
	      ItemStack legs = player.getCurrentItemOrArmor(2);
	      ItemStack boots = player.getCurrentItemOrArmor(1);

	      if (((boots.getItem() == SpaceAgeCore.advancedSpacesuitBoots ? 1 : 0) | (legs.getItem() == SpaceAgeCore.advancedSpacesuitLeggings ? 1 : 0) | (plate.getItem() == SpaceAgeCore.advancedSpacesuitChestplate ? 1 : 0) | (helmet.getItem() == SpaceAgeCore.advancedSpacesuitHelmet ? 1 : 0)) != 0) {
	        player.addPotionEffect(new PotionEffect(Potion.damageBoost.getId(), 2, 2));
	        player.fallDistance = 0.0F;
	      }
	    }

	    if ((player.getCurrentItemOrArmor(4) != null) && (player.getCurrentItemOrArmor(3) != null) && (player.getCurrentItemOrArmor(2) != null) && (player.getCurrentItemOrArmor(1) != null))
	    {
	      ItemStack helmet = player.getCurrentItemOrArmor(4);
	      ItemStack plate = player.getCurrentItemOrArmor(3);
	      ItemStack legs = player.getCurrentItemOrArmor(2);
	      ItemStack boots = player.getCurrentItemOrArmor(1);

	      if (((boots.getItem() == SpaceAgeCore.advancedSpacesuitBoots ? 1 : 0) | (legs.getItem() == SpaceAgeCore.advancedSpacesuitLeggings ? 1 : 0) | (plate.getItem() == SpaceAgeCore.advancedSpacesuitChestplate ? 1 : 0) | (helmet.getItem() == SpaceAgeCore.advancedSpacesuitHelmet ? 1 : 0)) != 0) {
	        player.addPotionEffect(new PotionEffect(Potion.waterBreathing.getId(), 2, 5));
	        player.fallDistance = 0.0F;
	      }

	    }
	    
	    if ((player.getCurrentItemOrArmor(4) != null) && (player.getCurrentItemOrArmor(3) != null) && (player.getCurrentItemOrArmor(2) != null) && (player.getCurrentItemOrArmor(1) != null))
	    {
	      ItemStack helmet = player.getCurrentItemOrArmor(4);
	      ItemStack plate = player.getCurrentItemOrArmor(3);
	      ItemStack legs = player.getCurrentItemOrArmor(2);
	      ItemStack boots = player.getCurrentItemOrArmor(1);

	      if (((boots.getItem() == SpaceAgeCore.advancedSpacesuitBoots ? 1 : 0) | (legs.getItem() == SpaceAgeCore.advancedSpacesuitLeggings ? 1 : 0) | (plate.getItem() == SpaceAgeCore.advancedSpacesuitChestplate ? 1 : 0) | (helmet.getItem() == SpaceAgeCore.advancedSpacesuitHelmet ? 1 : 0)) != 0) {
	        player.capabilities.allowFlying = true;
	        player.fallDistance = 0.0F;
	      }
	    }
	    else if (!player.capabilities.isCreativeMode) {
	      player.capabilities.allowFlying = false;
	    }
	    
	}

}
