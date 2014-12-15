package spaceage.planets.aliens.entity;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingData;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class EntityFish extends EntityWaterMob {

	public EntityFish(World par1World) {
		super(par1World);
		
		//this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(0, new EntityAIAvoidEntity(this, EntityPlayer.class, 2F, 1.0D, 1.0D));
		this.tasks.addTask(1, new EntityAIWander(this, 0.5D));
	}
	
    @Override
    protected int getDropItemId() {
        return Item.fishRaw.itemID;
    }
    
	@Override
	public boolean isAIEnabled() {
		return true;
	}
	
	@Override
	public EntityLivingData onSpawnWithEgg(EntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		//Here set Current items, armour, etcertera
		return data;
	}
}