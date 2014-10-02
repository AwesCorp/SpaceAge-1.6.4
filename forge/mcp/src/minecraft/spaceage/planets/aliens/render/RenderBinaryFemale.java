package spaceage.planets.aliens.render;

import com.google.common.collect.Maps;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import spaceage.common.SpaceAgeCore;
import spaceage.planets.aliens.entity.EntityBinaryFemale;
import spaceage.planets.aliens.model.ModelBinaryFemaleTest;
import static net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED;
import static net.minecraftforge.client.IItemRenderer.ItemRendererHelper.BLOCK_3D;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;

@SideOnly(Side.CLIENT)
public class RenderBinaryFemale extends RenderLiving {
	
protected ModelBinaryFemaleTest modelFemale;
protected float field_77070_b;
//protected ModelBinaryFemaleTest modelArmourChestplate;
//protected ModelBinaryFemaleTest modelArmour;
private static final Map field_110859_k = Maps.newHashMap();

public Random random;

private static final ResourceLocation texture1 = new ResourceLocation(SpaceAgeCore.modid, "textures/entities/binary_female_1.png");
private static final ResourceLocation texture2 = new ResourceLocation(SpaceAgeCore.modid, "textures/entities/binary_female_2.png");
private static final ResourceLocation texture3 = new ResourceLocation(SpaceAgeCore.modid, "textures/entities/binary_female_3.png");

//private static final ResourceLocation organicArmour = new ResourceLocation(SpaceAgeCore.modid, "textures/armour/binary_armour_female_1.png");

/** List of binary armour texture filenames. */
//public static String[] bipedArmorFilenamePrefix = new String[] {"binary"}; //TODO binary armour

public RenderBinaryFemale(ModelBinaryFemaleTest par1ModelBinaryFemaleTest, float par2) {
	this(par1ModelBinaryFemaleTest, par2, 1.0F);
}

public RenderBinaryFemale(ModelBinaryFemaleTest par1ModelBinaryFemaleTest, float par2, float par3) {
	super(par1ModelBinaryFemaleTest, par2);
	this.modelFemale = par1ModelBinaryFemaleTest;
	this.field_77070_b = par3;
	//this.armourSizer();
}

/*protected void armourSizer() {
	this.modelArmourChestplate = new ModelBinaryFemaleTest(1.0F);
	this.modelArmour = new ModelBinaryFemaleTest(0.5F);
}*/

/*protected int renderArmouredMob(EntityBinaryFemale par1Entity, int par2, float par3) {
    if (par2 == 0 && par1Entity.getArmoured()) {
        this.bindTexture(organicArmour);
        return 1;
    } else {
        return -1;
    }
}*/

/*@Deprecated //Use the more sensitve version getArmorResource below
public static ResourceLocation func_110857_a(ItemArmor par0ItemArmor, int par1) {
return func_110858_a(par0ItemArmor, par1, (String)null);
}

@Deprecated //Use the more sensitve version getArmorResource below
public static ResourceLocation func_110858_a(ItemArmor par0ItemArmor, int par1, String par2Str) {
String s1 = String.format("textures/models/armor/%s_layer_%d%s.png", new Object[] {bipedArmorFilenamePrefix[par0ItemArmor.renderIndex], Integer.valueOf(par1 == 2 ? 2 : 1), par2Str == null ? "" : String.format("_%s", new Object[]{par2Str})});
ResourceLocation resourcelocation = (ResourceLocation)field_110859_k.get(s1);

if (resourcelocation == null) {
resourcelocation = new ResourceLocation(s1);
field_110859_k.put(s1, resourcelocation);
}

return resourcelocation;
}*/

/**
* More generic ForgeHook version of the above function, it allows for Items to have more control over what texture they provide.
*
* @param entity Entity wearing the armor
* @param stack ItemStack for the armor
* @param slot Slot ID that the item is in
* @param type Subtype, can be null or "overlay"
* @return ResourceLocation pointing at the armor's texture
*/
/*public static ResourceLocation getArmorResource(Entity entity, ItemStack stack, int slot, String type) {
	ItemArmor item = (ItemArmor)stack.getItem();
	String s1 = String.format(SpaceAgeCore.modid + ":" + "binary_armour_female_%d%s.png",
			(slot == 2 ? 2 : 1));
	//String s1 = String.format("textures/models/armor/%s_layer_%d%s.png",
			//bipedArmorFilenamePrefix[item.renderIndex], (slot == 2 ? 2 : 1), type == null ? "" : String.format("_%s", type));

	s1 = ForgeHooksClient.getArmorTexture(entity, stack, s1, slot, type);
	ResourceLocation resourcelocation = (ResourceLocation)field_110859_k.get(s1);

	if (resourcelocation == null) {
		resourcelocation = new ResourceLocation(s1);
		field_110859_k.put(s1, resourcelocation);
		}

	return resourcelocation;
	}*/

/*protected int setArmourModel(EntityBinaryFemale par1EntityLiving, int par2, float par3) {
	ItemStack itemstack = par1EntityLiving.func_130225_q(3 - par2);

	if (itemstack != null) {
		Item item = itemstack.getItem();

		if (item instanceof ItemArmor) {
			ItemArmor itemarmor = (ItemArmor)item;
			this.bindTexture(getArmorResource(par1EntityLiving, itemstack, par2, null));
			ModelBinaryFemaleTest modelFemale = par2 == 2 ? this.modelArmour : this.modelArmourChestplate;
			modelFemale.bipedHead.showModel = par2 == 0;
			modelFemale.bipedHeadwear.showModel = par2 == 0;
			modelFemale.bipedBody.showModel = par2 == 1 || par2 == 2;
			modelFemale.bipedRightArm.showModel = par2 == 1;
			modelFemale.bipedLeftArm.showModel = par2 == 1;
			modelFemale.bipedRightLeg.showModel = par2 == 2 || par2 == 3;
			modelFemale.bipedLeftLeg.showModel = par2 == 2 || par2 == 3;
			modelFemale = (ModelBinaryFemaleTest)ForgeHooksClient.getArmorModel(par1EntityLiving, itemstack, par2, modelFemale);
			this.setRenderPassModel(modelFemale);
			modelFemale.onGround = this.mainModel.onGround;
			modelFemale.isRiding = this.mainModel.isRiding;
			modelFemale.isChild = this.mainModel.isChild;
			float f1 = 1.0F;

			//Move out of if to allow for more then just CLOTH to have color
			int j = itemarmor.getColor(itemstack);
			if (j != -1) {
				float f2 = (float)(j >> 16 & 255) / 255.0F;
				float f3 = (float)(j >> 8 & 255) / 255.0F;
				float f4 = (float)(j & 255) / 255.0F;
				GL11.glColor3f(f1 * f2, f1 * f3, f1 * f4);

				if (itemstack.isItemEnchanted()) {
					return 31;
				}

				return 16;
			}

			GL11.glColor3f(f1, f1, f1);

			if (itemstack.isItemEnchanted()) {
				return 15;
			}

			return 1;
		}
	}

	return -1;//TODO
}*/

/*protected void func_130013_c(EntityBinaryFemale par1EntityLiving, int par2, float par3) {
	ItemStack itemstack = par1EntityLiving.func_130225_q(3 - par2);

	if (itemstack != null) {
		Item item = itemstack.getItem();

		if (item instanceof ItemArmor) {
			this.bindTexture(getArmorResource(par1EntityLiving, itemstack, par2, "overlay"));
			float f1 = 1.0F;
			GL11.glColor3f(f1, f1, f1);
		}//TODO
	}
}*/

public void doRenderLiving(EntityBinaryFemale par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
	float f2 = 1.0F;
	GL11.glColor3f(f2, f2, f2);
	ItemStack itemstack = par1EntityLiving.getHeldItem();
	this.renderOtherCrap(par1EntityLiving, itemstack);
	double d3 = par4 - (double)par1EntityLiving.yOffset;

	if (par1EntityLiving.isSneaking()) {
		d3 -= 0.125D;
	}

	super.doRenderLiving(par1EntityLiving, par2, d3, par6, par8, par9);
	this.modelFemale.aimedBow = false;
	this.modelFemale.isSneak = false;
	this.modelFemale.heldItemRight = 0;
	}

protected ResourceLocation func_110856_a(EntityBinaryFemale par1EntityBinary) {
	return null;
}

protected void renderOtherCrap(EntityBinaryFemale par1EntityLiving, ItemStack par2ItemStack) {
	this.modelFemale.heldItemRight = par2ItemStack != null ? 1 : 0;
	this.modelFemale.isSneak = par1EntityLiving.isSneaking();
}

protected void renderSpecials(EntityBinaryFemale par1EntityLiving, float par2) {
	float f1 = 1.0F;
	GL11.glColor3f(f1, f1, f1);
	super.renderEquippedItems(par1EntityLiving, par2);
	ItemStack itemstack = par1EntityLiving.getHeldItem();
	ItemStack itemstack1 = par1EntityLiving.func_130225_q(3);
	float f2;

	if (itemstack1 != null) {
		GL11.glPushMatrix();
		this.modelFemale.bipedHead.postRender(0.0625F);

		IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(itemstack1, EQUIPPED);
		boolean is3D = (customRenderer != null && customRenderer.shouldUseRenderHelper(EQUIPPED, itemstack1, BLOCK_3D));

		if (itemstack1.getItem() instanceof ItemBlock) {
			if (is3D || RenderBlocks.renderItemIn3d(Block.blocksList[itemstack1.itemID].getRenderType())) {
				f2 = 0.625F;
				GL11.glTranslatef(0.0F, -0.25F, 0.0F);
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
				GL11.glScalef(f2, -f2, -f2);
			}

			this.renderManager.itemRenderer.renderItem(par1EntityLiving, itemstack1, 0);
		}
		else if (itemstack1.getItem().itemID == Item.skull.itemID) {
			f2 = 1.0625F;
			GL11.glScalef(f2, -f2, -f2);
			String s = "";

			if (itemstack1.hasTagCompound() && itemstack1.getTagCompound().hasKey("SkullOwner")) {
				s = itemstack1.getTagCompound().getString("SkullOwner");
			}

			TileEntitySkullRenderer.skullRenderer.func_82393_a(-0.5F, 0.0F, -0.5F, 1, 180.0F, itemstack1.getItemDamage(), s);
		}

		GL11.glPopMatrix();
	}

	if (itemstack != null) {
		GL11.glPushMatrix();

		if (this.mainModel.isChild) {
			f2 = 0.5F;
			GL11.glTranslatef(0.0F, 0.625F, 0.0F);
			GL11.glRotatef(-20.0F, -1.0F, 0.0F, 0.0F);
			GL11.glScalef(f2, f2, f2);
		}

		this.modelFemale.bipedRightArm.postRender(0.0625F);
		GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);

		IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(itemstack, EQUIPPED);
		boolean is3D = (customRenderer != null && customRenderer.shouldUseRenderHelper(EQUIPPED, itemstack, BLOCK_3D));

		if (itemstack.getItem() instanceof ItemBlock && (is3D || RenderBlocks.renderItemIn3d(Block.blocksList[itemstack.itemID].getRenderType()))) {
			f2 = 0.5F;
			GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
			f2 *= 0.75F;
			GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(-f2, -f2, f2);
		}
		else if (itemstack.itemID == Item.bow.itemID) {
			f2 = 0.625F;
			GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
			GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(f2, -f2, f2);
			GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
		}
		else if (Item.itemsList[itemstack.itemID].isFull3D()) {
			f2 = 0.625F;

		if (Item.itemsList[itemstack.itemID].shouldRotateAroundWhenRendering()) {
			GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(0.0F, -0.125F, 0.0F);
		}

		this.translateCrap();
		GL11.glScalef(f2, -f2, f2);
		GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
		} else {
			f2 = 0.375F;
			GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
			GL11.glScalef(f2, f2, f2);
			GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
		}

		this.renderManager.itemRenderer.renderItem(par1EntityLiving, itemstack, 0);

		if (itemstack.getItem().requiresMultipleRenderPasses()) {
			for (int x = 1; x < itemstack.getItem().getRenderPasses(itemstack.getItemDamage()); x++) {
				this.renderManager.itemRenderer.renderItem(par1EntityLiving, itemstack, x);
			}
		}

		GL11.glPopMatrix();
	}
}

protected void translateCrap() {
	GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
}

/*protected void func_82439_b(EntityLivingBase par1EntityLivingBase, int par2, float par3) {
	this.func_130013_c((EntityBinaryFemale)par1EntityLivingBase, par2, par3);
}TODO*/

/**
* Queries whether should render the specified pass or not.
*/
protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3) {
	return -1;
}

protected void renderEquippedItems(EntityLivingBase par1EntityLivingBase, float par2) {
	this.renderSpecials((EntityBinaryFemale)par1EntityLivingBase, par2);
}

public void renderPlayer(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6, float par8, float par9) {
	this.doRenderLiving((EntityBinaryFemale)par1EntityLivingBase, par2, par4, par6, par8, par9);
}

/**
* Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
* handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
* (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
* double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
*/
public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
	this.doRenderLiving((EntityBinaryFemale)par1Entity, par2, par4, par6, par8, par9);
}

/**
 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
 */
protected ResourceLocation getEntityTexture(Entity par1Entity) {
    return this.getSubsetTextures((EntityBinaryFemale)par1Entity);
}

private ResourceLocation getSubsetTextures(EntityBinaryFemale par1Entity) {
	return (this.random.nextInt(5) != 0 ? texture1 : (this.random.nextInt(5) != 0 ? texture2 : texture3));
	}
}