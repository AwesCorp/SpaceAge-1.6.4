package spaceage.planets.aliens.render;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import spaceage.common.SpaceAgeCore;
import spaceage.planets.aliens.entity.EntityFish;
import spaceage.planets.aliens.model.ModelFish;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderFish extends RenderLiving {

	protected ModelFish modelFish;
	
	public Random random = new Random();
	
	private static final ResourceLocation fishTextures1 = new ResourceLocation("spaceage", "textures/entities/fish_1.png");
	private static final ResourceLocation fishTextures2 = new ResourceLocation("spaceage", "textures/entities/fish_2.png");
	//private static final ResourceLocation fishTextures3 = new ResourceLocation(SpaceAgeCore.modid, "textures/entities/fish_3.png");
	
	public RenderFish(ModelFish modelFish, float par2) {
		super(modelFish, par2);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		switch(this.random.nextInt(2)) {
			case 0:
				return fishTextures1;
			//case 1:
				//return fishTextures2;
			/*case 2:
				return fishTextures3;*/
		}
		return fishTextures1;
	}
	
	/**
	* Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
	* handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
	* (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
	* double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
	*/
	@Override
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
		this.doRenderLiving((EntityFish)par1Entity, par2, par4, par6, par8, par9);
	}
	
	public void doRenderLiving(EntityFish par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
		float f2 = 1.0F;
		GL11.glColor3f(f2, f2, f2);
		double d3 = par4 - (double)par1EntityLiving.yOffset;

		super.doRenderLiving(par1EntityLiving, par2, d3, par6, par8, par9);
	}
}