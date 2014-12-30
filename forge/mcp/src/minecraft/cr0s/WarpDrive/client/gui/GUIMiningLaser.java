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
import cr0s.WarpDrive.common.container.ContainerMiningLaser;
import cr0s.WarpDrive.tile.TileEntityMiningLaser;
import cr0s.WarpDrive.tile.TileEntityParticleBooster;

	@SideOnly(Side.CLIENT)
	public class GUIMiningLaser extends GuiElectricBase { //TODO gui testing, string coords, button coords
	   
		private static final ResourceLocation furnaceGuiTextures = new ResourceLocation(WarpDrive.modid + ":" + "textures/gui/" + "guiMiningLaser_alternate.png");//ClientProxy.guiMiningLaser/*"textures/gui/container/furnace.png"*/);
	    private TileEntityMiningLaser furnaceInventory;
	    //private TileEntityParticleBooster particle;

	    public GUIMiningLaser(InventoryPlayer par1InventoryPlayer, TileEntityMiningLaser tile_entity) {
	        super(new ContainerMiningLaser(par1InventoryPlayer, tile_entity));
	        this.furnaceInventory = tile_entity;
	    }
	    
	    int mCoord0 = 3;
	    int mCoord1 = 37;
	    int qCoord0/* = */;
	    int qCoord1/* = */;
	    int eCoord0/* = */;
	    int eCoord1/* = */;
	    int lCoord0/* = */;
	    int lCoord1/* = */;
	    int omCoord0/* = */;
	    int omCoord1/* = */;
	    int olCoord0/* = */;
	    int olCoord1/* = */;

	    /**
	     * Draw the foreground layer for the GuiContainer (everything in front of the items)
	     */
	    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
	        String s = this.furnaceInventory.isInvNameLocalized() ? this.furnaceInventory.getInvName() : I18n.getString(this.furnaceInventory.getInvName());
	        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
	        this.fontRenderer.drawString(I18n.getString("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	        
	        String mining = this.furnaceInventory.isMining ? "Mining" : "Not Mining"; //TODO
	        this.fontRenderer.drawString(mining, mCoord0, mCoord1, 4210752);
	        
	        String quarry = this.furnaceInventory.isQuarry ? "Quarrying" : "Not Quarrying"; //TODO
	        this.fontRenderer.drawString(quarry, qCoord0, qCoord1, 4210752);
	        
	        /*int energyI = this.furnaceInventory.getBoosterEnergy();
	        String energyS = energyI;
	        this.fontRenderer.drawString(energy, eCoord0, eCoord1, 4210752);*/
	        
	        String layer = String.valueOf(this.furnaceInventory.getCurrentLayer()); //TODO
	        this.fontRenderer.drawString(layer, lCoord0, lCoord1, 4210752);
	        
	        if(furnaceInventory.isMining) { //TODO
				int valuablesInLayer, valuablesMined;
				valuablesInLayer = furnaceInventory.getValuablesInLayer().size();
				valuablesMined = furnaceInventory.getValuableIndex();
	        
	        String oresMined = String.valueOf(valuablesMined); //TODO
	        this.fontRenderer.drawString(oresMined, omCoord0, omCoord1, 4210752);
	        
	        String oresInLayer = String.valueOf(valuablesInLayer); //TODO
	        this.fontRenderer.drawString(oresInLayer, olCoord0, olCoord1, 4210752);
	        
	        }
	        
	        // Power level
	        if (this.isPointInRegion(161, 3, 8, 80, mouseX, mouseY)) { //TODO CHECK IF WORKS!!!
	            //String powerLevelLiteral = String.valueOf(this.ENTITY.getEnergy(ForgeDirection.UNKNOWN)) + "/" + String.valueOf(this.ENTITY.getEnergyCapacity(ForgeDirection.UNKNOWN));
	            this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, "Energy " + String.valueOf(this.furnaceInventory.getBoosterPowerRemainingScaled(100)) + " %");
	        }
	        
	        //Text to add:
	        //
	        //
	        //If it is mining
	        //
	        //All states, state (i/o), energy, current layer, amount of ores mined, amount of ores in layer
	        //Offset???
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
	        
	        int powerRemainingPercentage = this.furnaceInventory.getBoosterPowerRemainingScaled(80/*MAXWOLF = 32 - mine: 25 000 per pixel? So by 80?*/); //TODO CHECK IF WORKS!!!
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
	    	
	    	int mineX = 100;//TODO TEMP 
	    	int mineY = 14;//TODO TEMP  
	    	int mL = 60; //TODO TEMP 
	    	int mH = 20;//TODO TEMP 
	    	
	    	int quarryX = 100;//TODO TEMP 
	    	int quarryY = 14;//TODO TEMP  
	    	int qL = 60; //TODO TEMP 
	    	int qH = 20;//TODO TEMP 
	    	
	    	/*Start/stop mining*/buttonList.add(new GuiButton(0/*button number, maybe for mod, else gui*/, guiLeft + mineX/*Location in relation to left in pixels*/, guiTop + mineY/*Location in relation to top in pixels*/, mL/*Length in pixels*/, mH/*Height in pixels*/, "On/Off"/*Text on button*/));
	    	/*Start/stop quarrying*/buttonList.add(new GuiButton(1/*button number, maybe for mod, else gui*/, guiLeft + quarryX/*Location in relation to left in pixels*/, guiTop + quarryY/*Location in relation to top in pixels*/, qL/*Length in pixels*/, qH/*Height in pixels*/, "Quarry Engage/Disengage"/*Text on button*/));
	    	buttonList.add(new GuiButton(2, guiLeft + 100, guiTop + 14, 60, 20, "Wiki"));
	    	
	    	//Buttons to add: 
	    	//Start mining
	    	//Stop mining
	    	//
	    	//Start quarrying
	    	//
	    	//Offset??? TODO Ask dad how to do offset
	    }
	    
	    @Override
	    protected void actionPerformed(GuiButton button) {
	    	switch(button.id) {
	    		case 0:
	    			furnaceInventory.mine();
	    		case 1:
	    			furnaceInventory.quarry();
	    		case 2:
	    			openURL("https://sites.google.com/site/spaceageminecraft/wiki/blocks/mining-laser");
	    	}
	    }
	}