package cr0s.WarpDrive.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import uedevkit.gui.GuiElectricBase;
import uedevkit.util.MathHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cr0s.WarpDrive.WarpDrive;
import cr0s.WarpDrive.client.ClientProxy;
import cr0s.WarpDrive.common.container.ContainerMiningLaser;
import cr0s.WarpDrive.common.container.ContainerRadar;
import cr0s.WarpDrive.tile.TileEntityMiningLaser;
import cr0s.WarpDrive.tile.TileEntityParticleBooster;
import cr0s.WarpDrive.tile.TileEntityRadar;

	@SideOnly(Side.CLIENT)
	public class GUIRadar extends GuiElectricBase { //TODO gui testing, string coords, button coords
		/*TODO
		 * scanRay
		 * scanRadius
		 * getResultsCount
		 * getResult
		 * getEnergyLevel - done
		 * pos - done*/
	   
		private static final ResourceLocation furnaceGuiTextures = new ResourceLocation(WarpDrive.modid + ":" + "textures/gui/" + ClientProxy.guiRadar/*"textures/gui/container/furnace.png"*/);
	    private TileEntityRadar furnaceInventory;
	    //private TileEntityParticleBooster particle;

	    public GUIRadar(InventoryPlayer par1InventoryPlayer, TileEntityRadar tile_entity) {
	        super(new ContainerRadar(par1InventoryPlayer, tile_entity));
	        this.furnaceInventory = tile_entity;
	    }
	    
	    public int posXx;
	    public int posXy;
	    public int posYx;
	    public int posYy;
	    public int posZx;
	    public int posZy;

	    /**
	     * Draw the foreground layer for the GuiContainer (everything in front of the items)
	     */
	    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
	        String s = this.furnaceInventory.isInvNameLocalized() ? this.furnaceInventory.getInvName() : I18n.getString(this.furnaceInventory.getInvName());
	        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
	        this.fontRenderer.drawString(I18n.getString("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	        
	        String posX = String.valueOf(this.furnaceInventory.xCoord);
	        String posY = String.valueOf(this.furnaceInventory.yCoord);
	        String posZ = String.valueOf(this.furnaceInventory.zCoord);
	        
	        this.fontRenderer.drawString(posX, posXx, posXy, 4210752);
	        this.fontRenderer.drawString(posY, posYx, posYy, 4210752);
	        this.fontRenderer.drawString(posZ, posZx, posZy, 4210752);
	        
	        // Power level
	        if (this.isPointInRegion(161, 3, 8, 80, mouseX, mouseY)) { //TODO CHECK IF WORKS!!!
	            //String powerLevelLiteral = String.valueOf(this.ENTITY.getEnergy(ForgeDirection.UNKNOWN)) + "/" + String.valueOf(this.ENTITY.getEnergyCapacity(ForgeDirection.UNKNOWN));
	            this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, "Energy " + String.valueOf(this.furnaceInventory.getPowerRemainingScaled(100)) + " %");
	        }
	    }

	    /**
	     * Draw the background layer for the GuiContainer (everything behind the items)
	     */
	    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        this.mc.getTextureManager().bindTexture(furnaceGuiTextures);
	        int k = (this.width - this.xSize) / 2;
	        int l = (this.height - this.ySize) / 2;
	        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	        
	        int i1;
	        
	        int powerRemainingPercentage = this.furnaceInventory.getPowerRemainingScaled(80/*MAXWOLF = 32 - mine: 25 000 per pixel? So by 80?*/); //TODO CHECK IF WORKS!!!
	        // Screen Coords: 112x17 (161x3 if cursor is on (162x4 from absolute left and height))
	        // Filler Coords: 176x56 (176x0 if cursor is on)
	        // Image Size WH: 18x32 (8x80)
	        this.drawTexturedModalRect(k/*screenX*/ + 161, l/*screenY*/ + 3, 176, 0, 8, 80 - powerRemainingPercentage);
	        
	        //FLAME - DON'T NEED
	        /*if (this.furnaceInventory.isBurning())
	        {
	            i1 = this.furnaceInventory.getBurnTimeRemainingScaled(12);
	            this.drawTexturedModalRect(k + 56, l + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 2);
	        }*/
	        
	        //GIANT ARROW - DON'T NEED
	        /*i1 = this.furnaceInventory.getCookProgressScaled(24);
	        this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);*/
	    }
	    
	    int w = 142;
	    int h = 68;
	    		 
		/*public void colorScreen(int color) {
			for (int a = 2; a > w-1; a++) {
				for(int b = 1; b < h; b++) {
					paintutils.drawPixel(a,b,color);
				}
			}
		}*/
		
		int radius = 500;
		int scale = 25;
    	
    	public int translateX(int oldX) {
    		int x = furnaceInventory.xCoord - oldX;
    		
    		x = x / (radius / scale);
    		
    		x = x + (w / 2);
    		
    		x = MathHelper.floor(x);
    		
    		return x;
    	}
    	
    	public int translateZ(int oldZ) {
    		int x = furnaceInventory.yCoord - oldZ;
    		
    		x = x / (radius / scale);
    		
    		x = x + (w / 2);
    		
    		x = MathHelper.floor(x);
    		
    		return x;
    	}
	    		 
		public void drawContact(int x, int y, int z, String name, int color) {
			int newX = translateX(x);
			int newZ = translateZ(z);
			
			int contactX = 176;
			int contactZ = 80;
			
			this.drawTexturedModalRect(newX, newZ, contactX, contactZ, 1 + color, 1);
			//paintutils.drawPixel(newX, newZ, color);
			this.fontRenderer.drawString(name, newX - 3, newZ + 1, 4210752);
			//textOut(newX - 3, newZ + 1, "[" + name + "]", colors.white, colors.black);
		}
	    		 
		public int scanAndDraw() {
			if (furnaceInventory.getCurrentEnergyValue() < radius*radius) {
				int hh = MathHelper.floor(h / 2);
				int hw = MathHelper.floor(w / 2);
	    		   
				paintutils.drawLine(hw - 5, hh - 1, hw + 5, hh - 1, 9843760);
				paintutils.drawLine(hw - 5, hh, hw + 5, hh, 9843760);
		    
				textOut(hw - 4, hh, "LOW POWER", 14540253, 9843760);
		    
				paintutils.drawLine(hw - 5, hh + 1, hw + 5, hh + 1, 9843760);
	    		   
				return 0;
			}
			
			furnaceInventory.scanRadius(radius);
  		 
  		  redraw();
  		 
  		  int numResults = furnaceInventory.getResultCount();
  		 
  		  if ((numResults < 1) || (numResults > -1)) {
  		    for (int i = 0; i < numResults-1; i++) {
  		      //freq, cx, cy, cz = radar.getResult(i);
  		      String freq = furnaceInventory.getResultFrequency(i);
  		      int cx = Integer.parseInt(furnaceInventory.getResultX(i));
  		      int cy = Integer.parseInt(furnaceInventory.getResultY(i));
  		      int cz = Integer.parseInt(furnaceInventory.getResultZ(i));
  		     
  		      drawContact(cx, cy, cz, freq, 0);
  		  	}
  		  }
  		 
  		  drawContact(radarX, radarY, radarZ, "RAD", 1);
  		}
		
		public void drawLine(int startX, int startY, int endX, int endY, int nColour) {
			
			int startX2 = MathHelper.floor(startX);
			int startY2 = MathHelper.floor(startY);
			int endX2 = MathHelper.floor(endX);
			int endY2 = MathHelper.floor(endY);
			
			if(startX2 == endX2 && startY2 == endY2) {
				drawPixelInternal(startX2, startY2);
				return;
			}
			
			int minX = startX2 - endX2;
			if(minX == startX2) {
				int minY = startY2;
				int maxY = endY2;
				int maxX = endX2;
			} else {
				int minY = endY2;
				int maxY = startY2;
				int maxX = startX2;
			}
			
			int xDiff = maxX - minX;
			int yDiff = maxY - minY;
			
			if(xDiff > yDiff) {
				int y = minY;
				int dy = yDiff / xDiff;
				
				for(int x = minX; minX < maxX; minX++) {
					drawPixelInternal(x, MathHelper.floor(y + 0.5));
					int y2 = y + dy;
				}
			}
			int x = minX;
			int dx = xDiff / yDiff;
			
			if(maxY >= minY) {
				for(int y = minX; y < maxY; y++) {
					drawPixelInternal(MathHelper.floor(x + 0.5), y);
					int x2 = x + dx;
				}
			}
		}
	    		  
	    		 
	    		public void redraw() {
	    		   shell.run("clear");
	    		   //colorScreen(3491355);
	    		   
	    		   paintutils.drawLine(1, 1, w, 1, 1644054);
	    		   
	    		   textOut(h, 1, "= W-Radar v0.1 =", 14540253, 1644054);
	    		   
	    		   textOut(w - 3, 1, "[X]", 14540253, 9843760);
	    		   
	    		   paintutils.drawLine(1, h, w, h, 1644054);
	    		}
	    		   
	    		textOut(4, h, "Energy: " + furnaceInventory.getEnergyLevel() + " Eu | Scan radius: " + radius, colors.white, colors.black);
	    		int radarY = furnaceInventory.yCoord;
	    		int radarZ = furnaceInventory.zCoord;
	    		scanAndDraw();
	}

