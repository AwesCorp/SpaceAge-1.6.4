package spaceage.planets.aliens.entity;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingData;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntitySentient extends EntityCreature {

	public EntitySentient(World par1World) {
		super(par1World);
		
		this.getNavigator().setAvoidsWater(true);
		
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIOpenDoor(this, true));
		this.tasks.addTask(2, getAttackAI());
		this.tasks.addTask(3, new EntityAIWander(this, 0.5D));
		this.tasks.addTask(4, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0F, 0.1F));
	    this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F, 0.02F));
	    this.tasks.addTask(6, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
	}
	
	public EntityAIBase getAttackAI() {
		return new EntityAIAttackOnCollide(this, 1.45D, false);
	}
	
	@Override
	public boolean isAIEnabled() {
		return true;
	}
	
	@Override
	protected void dropFewItems(boolean flag, int i) {
	}
	
	@Override
	public EntityLivingData onSpawnWithEgg(EntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		//Here set Current items, armour, etcertera
		return data;
	}
	
	@Override
	public boolean getCanSpawnHere() {
		return false;
	}
}
