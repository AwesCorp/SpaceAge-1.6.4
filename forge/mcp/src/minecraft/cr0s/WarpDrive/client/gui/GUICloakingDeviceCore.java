package cr0s.WarpDrive.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import uedevkit.gui.GuiElectricBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cr0s.WarpDrive.WarpDrive;
import cr0s.WarpDrive.client.ClientProxy;
import cr0s.WarpDrive.common.container.ContainerCloakingDeviceCore;
import cr0s.WarpDrive.common.container.ContainerMiningLaser;
import cr0s.WarpDrive.tile.TileEntityCloakingDeviceCore;
import cr0s.WarpDrive.tile.TileEntityMiningLaser;
import cr0s.WarpDrive.tile.TileEntityParticleBooster;

	@SideOnly(Side.CLIENT)
	public class GUICloakingDeviceCore extends GuiElectricBase { //TODO gui testing, 
		
		/*TODO list
		 * Field tier (1 or 2), checks automatically
		 * isAssemblyValid, checks automatically
		 * energy level
		 * enable/disable button
		 * setFieldFrequency button*/
	   
		private static final ResourceLocation furnaceGuiTextures = new ResourceLocation(WarpDrive.modid + ":" + "textures/gui/" + ClientProxy.guiCloakingDeviceCore/*"textures/gui/container/furnace.png"*/);
	    private TileEntityCloakingDeviceCore furnaceInventory;
	    //private TileEntityParticleBooster particle;

	    public GUICloakingDeviceCore(InventoryPlayer par1InventoryPlayer, TileEntityCloakingDeviceCore tile_entity) {
	        super(new ContainerCloakingDeviceCore(par1InventoryPlayer, tile_entity));
	        this.furnaceInventory = tile_entity;
	    }
	    
	    int grey = 4210752;
	    
	    int tCoord0 = 3;
	    int tCoord1 = 37;
	    int aCoord0 = 3;
	    int aCoord1 = 37;

	    /**
	     * Draw the foreground layer for the GuiContainer (everything in front of the items)
	     */
	    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
	        String s = this.furnaceInventory.isInvNameLocalized() ? this.furnaceInventory.getInvName() : I18n.getString(this.furnaceInventory.getInvName());
	        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
	        this.fontRenderer.drawString(I18n.getString("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	        
	        String tier = String.valueOf(this.furnaceInventory.tier);
	        write(tier, tCoord0, tCoord1, grey);
	        
	        String assembly = String.valueOf(this.furnaceInventory.validateAssembly());
	        write(assembly, aCoord0, aCoord1, grey);
	        
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
	    
	    @Override
	    public void initGui() {
	    	super.initGui();
	    	buttonList.clear();
	    	
	    	int cloakX = 100;//TODO TEMP 
	    	int cloakY = 14;//TODO TEMP  
	    	int cL = 60; //TODO TEMP 
	    	int cH = 20;//TODO TEMP 
	    	
	    	/*Turn on/off*/buttonList.add(new GuiButton(0/*button number, maybe for mod, else gui*/, guiLeft + cloakX/*Location in relation to left in pixels*/, guiTop + cloakY/*Location in relation to top in pixels*/, cL/*Length in pixels*/, cH/*Height in pixels*/, "On/Off"/*Text on button*/));
	    	buttonList.add(new GuiButton(1/*button number, maybe for mod, else gui*/, guiLeft + 100/*Location in relation to left in pixels*/, guiTop + 14/*Location in relation to top in pixels*/, 60/*Length in pixels*/, 20/*Height in pixels*/, "Wiki"/*Text on button*/));
	    	
	    	//Buttons to add: 
	    	//On/Off
	    	//Set field frequency: + and - buttons? Or text editing?
	    	//
	    	//
	    	//
	    	//Offset??? TODO Ask dad how to do offset
	    }
	    
	    @Override
	    protected void actionPerformed(GuiButton button) {
	    	switch(button.id) {
	    		case 0:
	    			furnaceInventory.cloak();
	    		case 1:
	    			openURL("https://sites.google.com/site/spaceageminecraft/wiki/blocks/cloaking-device-core");
	    		/*case 1:
	    			furnaceInventory.quarry();*/ //TODO
	    	}
	    }
	}

