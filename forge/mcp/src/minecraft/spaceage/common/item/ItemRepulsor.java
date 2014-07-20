package spaceage.common.item;

import java.util.List;

import spaceage.common.SpaceAgeCore;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemRepulsor extends Item {

	public ItemRepulsor(int id) {
		super(id);
	}

	@Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer player) {
		if((player.getCurrentItemOrArmor(4) != null) && (player.getCurrentItemOrArmor(3) != null)) {
			ItemStack helmet = player.getCurrentItemOrArmor(4);
		    ItemStack plate = player.getCurrentItemOrArmor(3); 
		    
		    if (((plate.getItem() == SpaceAgeCore.advancedSpacesuitChestplate ? 1 : 0) | (helmet.getItem() == SpaceAgeCore.advancedSpacesuitHelmet ? 1 : 0)) != 0) {
		    	float f = (float)10.0F;
	            f = (f * f + f * 2.0F) / 3.0F;

	            if (f > 1.0F) {
	                f = 1.0F;
	            }
	            EntityArrow entityArrow = new EntityArrow(par2World, player, f * 2.0F);
		    
	            entityArrow.setDamage(entityArrow.getDamage() + (double)10.0F * 0.5D + 0.5D);
	            
        par2World.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!par2World.isRemote) {
            par2World.spawnEntityInWorld(entityArrow/*ELECTRIC TODO*/);
        		}
        	}
		}
        return par1ItemStack;
    }
	
	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		par3List.add("An energy weapon for use with the advanced spacesuit. Requires the chestplate for energy and the helmet for control.");
	}
	
}
