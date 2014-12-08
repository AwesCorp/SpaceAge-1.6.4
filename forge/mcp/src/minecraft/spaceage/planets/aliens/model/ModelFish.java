package spaceage.planets.aliens.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelFish extends ModelBase {
	  //fields
	ModelRenderer midBodyPiece;
    ModelRenderer mainHead;
    ModelRenderer mainTailBack;
    ModelRenderer topHead;
    ModelRenderer bottomHead;
    ModelRenderer TAILMOVEMIDDLE;
    ModelRenderer TAILMOVETOP;
    ModelRenderer TAILMOVEBOTTOM;
    ModelRenderer finLeft;
    ModelRenderer finRight;
    ModelRenderer finTop;
  
    public ModelFish() {
    	textureWidth = 64;
    	textureHeight = 32;
    
    	midBodyPiece = new ModelRenderer(this, 0, 0);
    	midBodyPiece.addBox(-2F, 0F, -3F, 4, 6, 6);
    	midBodyPiece.setRotationPoint(0F, 13F, 0F);
    	midBodyPiece.setTextureSize(64, 32);
    	midBodyPiece.mirror = true;
    	setRotation(midBodyPiece, 0F, 0F, 0F);
      
    	mainHead = new ModelRenderer(this, 20, 0);
    	mainHead.addBox(-1.5F, 1.5F, -6F, 3, 3, 3);
    	mainHead.setRotationPoint(0F, 13F, 0F);
    	mainHead.setTextureSize(64, 32);
    	mainHead.mirror = true;
    	setRotation(mainHead, 0F, 0F, 0F);
    	
    	mainTailBack = new ModelRenderer(this, 32, 0);
    	mainTailBack.addBox(-1F, 1.5F, 3F, 2, 3, 2);
    	mainTailBack.setRotationPoint(0F, 13F, 0F);
    	mainTailBack.setTextureSize(64, 32);
    	mainTailBack.mirror = true;
    	setRotation(mainTailBack, 0F, 0F, 0F);
    	
    	topHead = new ModelRenderer(this, 40, 0);
    	topHead.addBox(-1.5F, -1F, -6.15F, 3, 2, 4);
    	topHead.setRotationPoint(0F, 13F, 0F);
    	topHead.setTextureSize(64, 32);
    	topHead.mirror = true;
    	setRotation(topHead, 0.4363323F, 0F, 0F);
    	
    	bottomHead = new ModelRenderer(this, 40, 6);
    	bottomHead.addBox(-1.5F, 4.4F, -3.8F, 3, 2, 4);
    	bottomHead.setRotationPoint(0F, 13F, 0F);
    	bottomHead.setTextureSize(64, 32);
    	bottomHead.mirror = true;
    	setRotation(bottomHead, -0.3839724F, 0F, 0F);
    	
    	TAILMOVEMIDDLE = new ModelRenderer(this, 0, 12);
    	TAILMOVEMIDDLE.addBox(-0.5F, -1F, 0F, 1, 2, 4);
    	TAILMOVEMIDDLE.setRotationPoint(0F, 16F, 4F);
    	TAILMOVEMIDDLE.setTextureSize(64, 32);
    	TAILMOVEMIDDLE.mirror = true;
      	setRotation(TAILMOVEMIDDLE, 0F, 0F, 0F);
      	
      	TAILMOVETOP = new ModelRenderer(this, 0, 18);
      	TAILMOVETOP.addBox(-0.5F, -1F, 0F, 1, 2, 4);
      	TAILMOVETOP.setRotationPoint(0F, 16F, 4F);
      	TAILMOVETOP.setTextureSize(64, 32);
      	TAILMOVETOP.mirror = true;
      	setRotation(TAILMOVETOP, 0.4712389F, 0F, 0F);
      	
      	TAILMOVEBOTTOM = new ModelRenderer(this, 0, 24);
      	TAILMOVEBOTTOM.addBox(-0.5F, -1F, 0F, 1, 2, 4);
      	TAILMOVEBOTTOM.setRotationPoint(0F, 16F, 4F);
      	TAILMOVEBOTTOM.setTextureSize(64, 32);
      	TAILMOVEBOTTOM.mirror = true;
      	setRotation(TAILMOVEBOTTOM, -0.4712389F, 0F, 0F);
      	
      	finLeft = new ModelRenderer(this, 10, 25);
      	finLeft.addBox(-0.5F, -1F, 0F, 1, 2, 3);
      	finLeft.setRotationPoint(1.5F, 17F, -2F);
      	finLeft.setTextureSize(64, 32);
      	finLeft.mirror = true;
      	setRotation(finLeft, -0.1396263F, 0.3839724F, 0F);
      	
      	finRight = new ModelRenderer(this, 10, 25);
      	finRight.addBox(-0.5F, -1F, 0F, 1, 2, 3);
      	finRight.setRotationPoint(-1.5F, 17F, -2F);
      	finRight.setTextureSize(64, 32);
      	finRight.mirror = true;
      	setRotation(finRight, -0.1396263F, -0.3839724F, 0F);
      	
      	finTop = new ModelRenderer(this, 18, 26);
      	finTop.addBox(-0.5F, 0F, -0.5F, 1, 1, 3);
      	finTop.setRotationPoint(0F, 13F, -1F);
      	finTop.setTextureSize(64, 32);
      	finTop.mirror = true;
      	setRotation(finTop, 0.3839724F, 0F, 0F);
    }
  
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    	super.render(entity, f, f1, f2, f3, f4, f5);
    	setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    	midBodyPiece.render(f5);
    	mainHead.render(f5);
    	mainTailBack.render(f5);
    	topHead.render(f5);
    	bottomHead.render(f5);
    	TAILMOVEMIDDLE.render(f5);
    	TAILMOVETOP.render(f5);
    	TAILMOVEBOTTOM.render(f5);
    	finLeft.render(f5);
    	finRight.render(f5);
    	finTop.render(f5);
    }
  
    private void setRotation(ModelRenderer model, float x, float y, float z) {
    	model.rotateAngleX = x;
    	model.rotateAngleY = y;
    	model.rotateAngleZ = z;
    }
  
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
    	super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }
}