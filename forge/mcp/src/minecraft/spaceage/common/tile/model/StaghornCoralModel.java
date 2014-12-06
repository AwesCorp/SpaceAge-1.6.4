package spaceage.common.tile.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class StaghornCoralModel extends ModelBase {
	//fields
	ModelRenderer Main;
    ModelRenderer Secondary_Left;
    ModelRenderer Secondary_Right;
    ModelRenderer Secondary_Back;
    ModelRenderer Secondary_Front;
    
    public StaghornCoralModel() {
    	textureWidth = 64;
    	textureHeight = 32;
    
    	Main = new ModelRenderer(this, 0, 0);
    	Main.addBox(-0.5F, -16F, -0.5F, 1, 16, 1);
    	Main.setRotationPoint(0F, 24F, 0F);
    	Main.setTextureSize(64, 32);
    	Main.mirror = true;
    	setRotation(Main, 0F, 0F, 0F);
      
    	Secondary_Left = new ModelRenderer(this, 0, 17);
    	Secondary_Left.addBox(-7.5F, 0F, -0.5F, 8, 1, 1);
    	Secondary_Left.setRotationPoint(0F, 17F, 0F);
    	Secondary_Left.setTextureSize(64, 32);
    	Secondary_Left.mirror = true;
    	setRotation(Secondary_Left, 0F, 0F, 0.8726646F);
      
    	Secondary_Right = new ModelRenderer(this, 0, 17);
    	Secondary_Right.addBox(-0.5F, 0F, -0.5F, 8, 1, 1);
    	Secondary_Right.setRotationPoint(0F, 17F, 0F);
    	Secondary_Right.setTextureSize(64, 32);
    	Secondary_Right.mirror = true;
    	setRotation(Secondary_Right, 0F, 0F, -0.8726646F);
      
    	Secondary_Back = new ModelRenderer(this, 0, 17);
    	Secondary_Back.addBox(-7.5F, 0F, -0.5F, 8, 1, 1);
    	Secondary_Back.setRotationPoint(0F, 17F, 0F);
    	Secondary_Back.setTextureSize(64, 32);
    	Secondary_Back.mirror = true;
    	setRotation(Secondary_Back, 0F, 1.570796F, 0.8726646F);
      
    	Secondary_Front = new ModelRenderer(this, 0, 17);
    	Secondary_Front.addBox(-0.5F, 0F, -0.5F, 8, 1, 1);
    	Secondary_Front.setRotationPoint(0F, 17F, 0F);
    	Secondary_Front.setTextureSize(64, 32);
    	Secondary_Front.mirror = true;
    	setRotation(Secondary_Front, 0F, 1.570796F, -0.8726646F);
    }
  
    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    	super.render(entity, f, f1, f2, f3, f4, f5);
    	setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    	Main.render(f5);
    	Secondary_Left.render(f5);
    	Secondary_Right.render(f5);
    	Secondary_Back.render(f5);
    	Secondary_Front.render(f5);
    }
  
    private void setRotation(ModelRenderer model, float x, float y, float z) {
    	model.rotateAngleX = x;
    	model.rotateAngleY = y;
    	model.rotateAngleZ = z;
    }
  
    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
    	super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }
}