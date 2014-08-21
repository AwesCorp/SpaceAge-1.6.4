package spaceage.planets.aliens.entity;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.StepSound;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentThorns;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLivingData;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityBinaryFemale extends EntityHumanoid 
	implements IRangedAttackMob {

	public EntityBinaryFemale(World par1World) {
		super(par1World);
		
		this.isImmuneToFire = true;
	}
	
    /**
     * Returns true if the mob is armoured.
     */
    public boolean getArmoured() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
    }

    /**
     * Set or remove the armour of mobs.
     */
    public void setArmoured(boolean par1) {
        if (par1){
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)1));
        } else {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)0));
        }
    }

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float f) {
	    EntityArrow arrow = new EntityArrow(this.worldObj, this, target, 1.5F, 1.0F);
	    playSound("random.bow", 1.0F, 1.0F / (this.rand.nextFloat() * 0.4F + 0.8F));
	    this.worldObj.spawnEntityInWorld(arrow); //bone gun TODO
	}
	
	@Override
	public EntityAIBase getAttackAI() {
		return new EntityAIArrowAttack(this, 1.25D, 30, 50, 16.0F);
	}
	
	@Override
	protected void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		
		if(this.rand.nextBoolean()) {
			int j = this.rand.nextInt() + this.rand.nextInt(i + 1);
			for(int k = 0; k < j; k++) {
				dropItem(Item.arrow.itemID, 1); //bone gun TODO
			}
		}
	}
	
	@Override
	public EntityLivingData onSpawnWithEgg(EntityLivingData data) {
	    data = super.onSpawnWithEgg(data);
	    setCurrentItemOrArmor(0, new ItemStack(Item.bow)); //bone gun TODO
	    //setCurrentItemOrArmor(1, new ItemStack(SpaceAgeCore.organicHelmet));
	    //setCurrentItemOrArmor(1, new ItemStack(SpaceAgeCore.organicChestplate));
	    //setCurrentItemOrArmor(1, new ItemStack(SpaceAgeCore.organicLegs));
	    //setCurrentItemOrArmor(1, new ItemStack(SpaceAgeCore.organicBoots));
	    return data;
	}
}