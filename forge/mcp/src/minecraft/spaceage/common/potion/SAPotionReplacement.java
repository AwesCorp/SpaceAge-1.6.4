package spaceage.common.potion;

import com.google.common.collect.Maps;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeInstance;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionAttackDamage;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHealth;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StringUtils;

public class SAPotionReplacement {
    /** The array of potion types. */
    public static final SAPotionReplacement[] potionTypes = new SAPotionReplacement[32];
    public static final SAPotionReplacement field_76423_b = null;
    //public static final SAPotionReplacement moveSpeed = (new SAPotionReplacement(1, false, 8171462)).setPotionName("potion.moveSpeed").setIconIndex(0, 0).func_111184_a(SharedMonsterAttributes.movementSpeed, "91AEAA56-376B-4498-935B-2F7F68070635", 0.20000000298023224D, 2);
    //public static final SAPotionReplacement moveSlowdown = (new SAPotionReplacement(2, true, 5926017)).setPotionName("potion.moveSlowdown").setIconIndex(1, 0).func_111184_a(SharedMonsterAttributes.movementSpeed, "7107DE5E-7CE8-4030-940E-514C1F160890", -0.15000000596046448D, 2);
    //public static final SAPotionReplacement digSpeed = (new SAPotionReplacement(3, false, 14270531)).setPotionName("potion.digSpeed").setIconIndex(2, 0).setEffectiveness(1.5D);
    //public static final SAPotionReplacement digSlowdown = (new SAPotionReplacement(4, true, 4866583)).setPotionName("potion.digSlowDown").setIconIndex(3, 0);
    //public static final SAPotionReplacement damageBoost = (new PotionAttackDamage(5, false, 9643043)).setPotionName("potion.damageBoost").setIconIndex(4, 0).func_111184_a(SharedMonsterAttributes.attackDamage, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 3.0D, 2);
    //public static final SAPotionReplacement heal = (new PotionHealth(6, false, 16262179)).setPotionName("potion.heal");
    //public static final SAPotionReplacement harm = (new PotionHealth(7, true, 4393481)).setPotionName("potion.harm");
    public static final SAPotionReplacement jump = (new SAPotionReplacement(8, false, 7889559)).setPotionName("potion.jump").setIconIndex(2, 1);
    //public static final SAPotionReplacement confusion = (new SAPotionReplacement(9, true, 5578058)).setPotionName("potion.confusion").setIconIndex(3, 1).setEffectiveness(0.25D);

    /** The regeneration Potion object. */
    public static final SAPotionReplacement regeneration = (new SAPotionReplacement(10, false, 13458603)).setPotionName("potion.regeneration").setIconIndex(7, 0).setEffectiveness(0.25D);
    public static final SAPotionReplacement resistance = (new SAPotionReplacement(11, false, 10044730)).setPotionName("potion.resistance").setIconIndex(6, 1);

    /** The fire resistance Potion object. */
    public static final SAPotionReplacement fireResistance = (new SAPotionReplacement(12, false, 14981690)).setPotionName("potion.fireResistance").setIconIndex(7, 1);

    /** The water breathing Potion object. */
    public static final SAPotionReplacement waterBreathing = (new SAPotionReplacement(13, false, 3035801)).setPotionName("potion.waterBreathing").setIconIndex(0, 2);

    /** The invisibility Potion object. */
    public static final SAPotionReplacement invisibility = (new SAPotionReplacement(14, false, 8356754)).setPotionName("potion.invisibility").setIconIndex(0, 1);

/*    /** The blindness Potion object. 
    //public static final SAPotionReplacement blindness = (new SAPotionReplacement(15, true, 2039587)).setPotionName("potion.blindness").setIconIndex(5, 1).setEffectiveness(0.25D);

    /** The night vision Potion object. */
    public static final SAPotionReplacement nightVision = (new SAPotionReplacement(16, false, 2039713)).setPotionName("potion.nightVision").setIconIndex(4, 1);

    /* The hunger Potion object. 
    //public static final SAPotionReplacement hunger = (new SAPotionReplacement(17, true, 5797459)).setPotionName("potion.hunger").setIconIndex(1, 1);

    /* The weakness Potion object. 
   // public static final SAPotionReplacement weakness = (new PotionAttackDamage(18, true, 4738376)).setPotionName("potion.weakness").setIconIndex(5, 0).func_111184_a(SharedMonsterAttributes.attackDamage, "22653B89-116E-49DC-9B6B-9971489B5BE5", 2.0D, 0);

    /* The poison Potion object. 
    //public static final SAPotionReplacement poison = (new SAPotionReplacement(19, true, 5149489)).setPotionName("potion.poison").setIconIndex(6, 0).setEffectiveness(0.25D);

    //public static final SAPotionReplacement wither = (new SAPotionReplacement(20, true, 3484199)).setPotionName("potion.wither").setIconIndex(1, 2).setEffectiveness(0.25D);
    //public static final SAPotionReplacement field_76434_w = (new PotionHealthBoost(21, false, 16284963)).setPotionName("potion.healthBoost").setIconIndex(2, 2).func_111184_a(SharedMonsterAttributes.maxHealth, "5D6F0BA2-1186-46AC-B896-C61C5CEE99CC", 4.0D, 0);
    //public static final SAPotionReplacement field_76444_x = (new PotionAbsoption(22, false, 2445989)).setPotionName("potion.absorption").setIconIndex(2, 2);
    //public static final SAPotionReplacement field_76443_y = (new PotionHealth(23, false, 16262179)).setPotionName("potion.saturation");*/
    public static final SAPotionReplacement field_76442_z = null;
    public static final SAPotionReplacement field_76409_A = null;
    public static final SAPotionReplacement field_76410_B = null;
    public static final SAPotionReplacement field_76411_C = null;
    public static final SAPotionReplacement field_76405_D = null;
    public static final SAPotionReplacement field_76406_E = null;
    public static final SAPotionReplacement field_76407_F = null;
    public static final SAPotionReplacement field_76408_G = null;

    /** The Id of a Potion object. */
    public final int id;
    private final Map field_111188_I = Maps.newHashMap();

    /**
     * This field indicated if the effect is 'bad' - negative - for the entity.
     */
    private final boolean isBadEffect;

    /** Is the color of the liquid for this potion. */
    private final int liquidColor;

    /** The name of the Potion. */
    private String name = "";

    /** The index for the icon displayed when the potion effect is active. */
    private int statusIconIndex = -1;
    private double effectiveness;
    private boolean usable;

    protected SAPotionReplacement(int par1, boolean par2, int par3) {
        this.id = par1;
        potionTypes[par1] = this;
        this.isBadEffect = par2;

        if (par2) {
            this.effectiveness = 0.5D;
        } else {
            this.effectiveness = 1.0D;
        }

        this.liquidColor = par3;
    }

    /**
     * Sets the index for the icon displayed in the player's inventory when the status is active.
     */
    public SAPotionReplacement setIconIndex(int par1, int par2) {
        this.statusIconIndex = par1 + par2 * 8;
        return this;
    }

    /**
     * returns the ID of the potion
     */
    public int getId() {
        return this.id;
    }

    public void performEffect(EntityLivingBase par1EntityLivingBase, int par2) {
        if (this.id == regeneration.id) {
            if (par1EntityLivingBase.getHealth() < par1EntityLivingBase.getMaxHealth()) {
                par1EntityLivingBase.heal(1.0F);
            }
        } else {
            par1EntityLivingBase.heal((float)Math.max(4 << par2, 0));
        }
    }

    /**
     * Hits the provided entity with this potion's instant effect.
     */
    public void affectEntity(EntityLivingBase par1EntityLivingBase, EntityLivingBase par2EntityLivingBase, int par3, double par4) {
        int j;
            
        j = (int)(par4 * (double)(4 << par3) + 0.5D);
        par2EntityLivingBase.heal((float)j);
    }

    /**
     * Returns true if the potion has an instant effect instead of a continuous one (eg Harming)
     */
    public boolean isInstant() {
        return false;
    }

    /**
     * checks if Potion effect is ready to be applied this tick.
     */
    public boolean isReady(int par1, int par2) {
        int k;

        if (this.id == regeneration.id) {
            k = 50 >> par2;
            return k > 0 ? par1 % k == 0 : true;
        }
        return false;
    }

    /**
     * Set the potion name.
     */
    public SAPotionReplacement setPotionName(String par1Str) {
        this.name = par1Str;
        return this;
    }

    /**
     * returns the name of the potion
     */
    public String getName() {
        return this.name;
    }

    protected SAPotionReplacement setEffectiveness(double par1) {
        this.effectiveness = par1;
        return this;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Returns true if the potion has a associated status icon to display in then inventory when active.
     */
    public boolean hasStatusIcon() {
        return this.statusIconIndex >= 0;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Returns the index for the icon to display when the potion is active.
     */
    public int getStatusIconIndex() {
        return this.statusIconIndex;
    }

    @SideOnly(Side.CLIENT)

    /**
     * This method returns true if the potion effect is bad - negative - for the entity.
     */
    public boolean isBadEffect() {
        return this.isBadEffect;
    }

    @SideOnly(Side.CLIENT)
    public static String getDurationString(PotionEffect par0PotionEffect) {
        if (par0PotionEffect.getIsPotionDurationMax()) {
            return "**:**";
        } else {
            int i = par0PotionEffect.getDuration();
            return StringUtils.ticksToElapsedTime(i);
        }
    }

    public double getEffectiveness() {
        return this.effectiveness;
    }

    public boolean isUsable() {
        return this.usable;
    }

    /**
     * Returns the color of the potion liquid.
     */
    public int getLiquidColor() {
        return this.liquidColor;
    }

    public SAPotionReplacement func_111184_a(Attribute par1Attribute, String par2Str, double par3, int par5) {
        AttributeModifier attributemodifier = new AttributeModifier(UUID.fromString(par2Str), this.getName(), par3, par5);
        this.field_111188_I.put(par1Attribute, attributemodifier);
        return this;
    }

    public void removeAttributesModifiersFromEntity(EntityLivingBase par1EntityLivingBase, BaseAttributeMap par2BaseAttributeMap, int par3) {
        Iterator iterator = this.field_111188_I.entrySet().iterator();

        while (iterator.hasNext()) {
            Entry entry = (Entry)iterator.next();
            AttributeInstance attributeinstance = par2BaseAttributeMap.getAttributeInstance((Attribute)entry.getKey());

            if (attributeinstance != null) {
                attributeinstance.removeModifier((AttributeModifier)entry.getValue());
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public Map func_111186_k() {
        return this.field_111188_I;
    }

    public void applyAttributesModifiersToEntity(EntityLivingBase par1EntityLivingBase, BaseAttributeMap par2BaseAttributeMap, int par3) {
        Iterator iterator = this.field_111188_I.entrySet().iterator();

        while (iterator.hasNext()) {
            Entry entry = (Entry)iterator.next();
            AttributeInstance attributeinstance = par2BaseAttributeMap.getAttributeInstance((Attribute)entry.getKey());

            if (attributeinstance != null) {
                AttributeModifier attributemodifier = (AttributeModifier)entry.getValue();
                attributeinstance.removeModifier(attributemodifier);
                attributeinstance.applyModifier(new AttributeModifier(attributemodifier.getID(), this.getName() + " " + par3, this.func_111183_a(par3, attributemodifier), attributemodifier.getOperation()));
            }
        }
    }

    public double func_111183_a(int par1, AttributeModifier par2AttributeModifier) {
        return par2AttributeModifier.getAmount() * (double)(par1 + 1);
    }
}