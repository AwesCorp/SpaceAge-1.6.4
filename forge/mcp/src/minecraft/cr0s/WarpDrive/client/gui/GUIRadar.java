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
	    
	    public static GUIRadar access;

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

		public void drawTMR(int x, int z, int u, int v, int length, int height) {
			this.drawTexturedModalRect(x, z, u, v, length, height); 
		} 
		
		
	}