package spaceage.planets.aliens.render;

import spaceage.common.SpaceAgeCore;
import spaceage.planets.aliens.entity.EntityBinaryFemale;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

	@SideOnly(Side.CLIENT)
	public class RenderBinaryFemaleTwo extends RenderLiving {
	
	private static final ResourceLocation skin = new ResourceLocation(SpaceAgeCore.modid, "textures/entities/binary_female_1.png");

	public RenderBinaryFemaleTwo(ModelBase modelbase, float shadowSize) {
		super(modelbase, shadowSize);
	}

	public void func_177_a(EntityBinaryFemale entity, double d, double d1,
			double d2, float f, float f1) {
		super.doRenderLiving(entity, d, d1, d2, f, f1);
	}

	public void doRenderLiving(EntityLivingBase entityliving, double d, double d1,
			double d2, float f, float f1) {
		func_177_a((EntityBinaryFemale) entityliving, d, d1, d2, f, f1);
	}

    @Override
    public void doRender(Entity entity, double d, double d1, double d2,
    		float f, float f1) {
    	func_177_a((EntityBinaryFemale) entity, d, d1, d2, f, f1);
    }

    @Override
	protected ResourceLocation getEntityTexture(Entity entity) {
    	return skin;
	}
}