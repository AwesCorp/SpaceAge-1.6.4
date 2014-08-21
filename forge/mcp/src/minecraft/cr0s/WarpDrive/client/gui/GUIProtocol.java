package cr0s.WarpDrive.client.gui;

import net.minecraft.block.BlockAnvil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import uedevkit.gui.GuiSimpleBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cr0s.WarpDrive.WarpDrive;
import cr0s.WarpDrive.WarpDriveConfig;
import cr0s.WarpDrive.client.ClientProxy;
import cr0s.WarpDrive.common.container.ContainerProtocol;
import cr0s.WarpDrive.tile.TileEntityProtocol;

	/**
	 *The GUI for the Protocol (Warp Interface) block. 
	 *@author SkylordJoel
	 */

	@SideOnly(Side.CLIENT)
	public class GUIProtocol extends GuiSimpleBase { 
		/*TODO
		 * List of stuff:
		 * dim_getP
		 * dim_setP - in progress w/ text input, might change to slider or +- buttons
		 * dim_getN
		 * dim_setN - in progress w/ text input, might change to slider or +- buttons
		 * setMode
		 * setDistance
		 * setDirection
		 * getAttachedPlayers
		 * summon
		 * summonAll
		 * getX
		 * getY
		 * getZ
		 * energyRemaining For Core
		 * doJump
		 * getShipSize
		 * setBeaconFrequency
		 * getDx
		 * getDz
		 * setCoreFrequency
		 * isInSpace - done
		 * isInHyperspace - done
		 * setTargetJumpgate*/
		
		int xSize = 256;
		int ySize = 256;
	   
		private static final ResourceLocation furnaceGuiTextures = new ResourceLocation(WarpDrive.modid + ":" + "textures/gui/" + ClientProxy.warpInterfaceGui/*"textures/gui/container/furnace.png"*/);
	    private TileEntityProtocol furnaceInventory;
	    
	    private GuiTextField front;
	    private GuiTextField back;
	    private GuiTextField left;
	    private GuiTextField right;
	    private GuiTextField up;
	    private GuiTextField down;
	    
	    private GuiTextField beaconInput;

	    public GUIProtocol(InventoryPlayer par1InventoryPlayer, TileEntityProtocol tile_entity)
	    {
	        super(new ContainerProtocol(par1InventoryPlayer, tile_entity));
	        this.furnaceInventory = tile_entity;
	    }
	    
	    int grey = 4210752;
	    
	    int sizex;
	    int sizey;
	    
	    int dimensionx;
	    int dimensiony;
	    
	    int blocksx;
	    int blocksy;
	    
	    int coord0x;
	    int coord0y;
	    
	    int pixelsPerWord = 10;
	    
	    String size0 = "Amount of blocks from Core to be moved: ";
	    String size1 = furnaceInventory.getFront() + ", Right: " + furnaceInventory.getRight() + ", Up: " + furnaceInventory.getUp();
	    String size2 = "Back: " + furnaceInventory.getBack() + ", Left: " + furnaceInventory.getLeft() + ", Down: " + furnaceInventory.getDown();

	    String dimension = furnaceInventory.currentDimension();
	    
	    String blocks = "Ship Size: " + String.valueOf(furnaceInventory.getShipSize());
	    
	    String coord0 = "Coordinates: ";
	    String coord1 = "X: " + String.valueOf(furnaceInventory.core.xCoord);
	    String coord2 = "Y: " + String.valueOf(furnaceInventory.core.yCoord);
	    String coord3 = "Z: " + String.valueOf(furnaceInventory.core.zCoord);
	    
	    /**
	     * Draw the foreground layer for the GuiContainer (everything in front of the items)
	     */
	    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	    {
	        String s = this.furnaceInventory.isInvNameLocalized() ? this.furnaceInventory.getInvName() : I18n.getString(this.furnaceInventory.getInvName());
	        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
	        this.fontRenderer.drawString(I18n.getString("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	        
	        write(size0, sizex, sizey, grey); //Title
	        write(size1, sizex + pixelsPerWord, sizey, grey); //setP
	        write(size2, sizex + (2 * pixelsPerWord), sizey, grey); //setN
	        
	        write(dimension, dimensionx, dimensiony, grey);
	        
	        write(coord0, coord0x, coord0y, grey);
	        write(coord1, coord0x + pixelsPerWord, coord0y + pixelsPerWord, grey);
	        write(coord2, coord0x + 2 * (pixelsPerWord), coord0y + 2 * (pixelsPerWord), grey);
	        write(coord3, coord0x + 3 * (pixelsPerWord), coord0y + 3 * (pixelsPerWord), grey);
	        
	        write(blocks, blocksx, blocksy, grey);
	        
	        front.drawTextBox();
	        back.drawTextBox();
	        left.drawTextBox();
	        right.drawTextBox();
	        up.drawTextBox();
	        down.drawTextBox();
	        
	        beaconInput.drawTextBox();
	    }

	    /**
	     * Draw the background layer for the GuiContainer (everything behind the items)
	     */
	    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	    {
	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        this.mc.getTextureManager().bindTexture(furnaceGuiTextures);
	        int k = (this.width - this.xSize) / 2;
	        int l = (this.height - this.ySize) / 2;
	        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	        
	        int i1;
	        
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
		    
		    int biX = 0; //TODO
		    int biY = 0;
	    	
		    int frontX = 0; //TODO
		    int frontY = 0;
		    
		    int inputLength = 16;
		    int inputWidth = 16;

	    	front = new GuiTextField(this.fontRenderer, frontX, frontY, inputLength, inputWidth);
	    	front.setFocused(false);
	    	front.setMaxStringLength(3);
	    	
	    	back = new GuiTextField(this.fontRenderer, frontX, frontY + pixelsPerWord, inputLength, inputWidth);
	    	back.setFocused(false);
	    	back.setMaxStringLength(3);
	    	
	    	left = new GuiTextField(this.fontRenderer, frontX, frontY + (2 * pixelsPerWord), inputLength, inputWidth);
	    	left.setFocused(false);
	    	left.setMaxStringLength(3);
	    	
	    	right = new GuiTextField(this.fontRenderer, frontX, frontY + (3 * pixelsPerWord), inputLength, inputWidth);
	    	right.setFocused(false);
	    	right.setMaxStringLength(3);
	    	
	    	up = new GuiTextField(this.fontRenderer, frontX, frontY + (4 * pixelsPerWord), inputLength, inputWidth);
	    	up.setFocused(false);
	    	up.setMaxStringLength(3);
	    	
	    	down = new GuiTextField(this.fontRenderer, frontX, frontY + (5 * pixelsPerWord), inputLength, inputWidth);
	    	down.setFocused(false);
	    	down.setMaxStringLength(3);
	    	
	    	beaconInput = new GuiTextField(this.fontRenderer, biX, biY, inputLength, inputWidth);
	    	beaconInput.setFocused(false);
	    	beaconInput.setMaxStringLength(2/*TODO*/);
	    	
	    	/**/buttonList.add(new GuiButton(0/*button number, maybe for mod, else gui*/, guiLeft + 100/*Location in relation to left in pixels*/, guiTop + 14/*Location in relation to top in pixels*/, 60/*Length in pixels*/, 20/*Height in pixels*/, "Jump"/*Text on button*/));
	    	/**/buttonList.add(new GuiButton(1/*button number, maybe for mod, else gui*/, guiLeft + 100/*Location in relation to left in pixels*/, guiTop + 14/*Location in relation to top in pixels*/, 60/*Length in pixels*/, 20/*Height in pixels*/, "Disable"/*Text on button*/));
	    }
	    
	    @Override
	    protected void keyTyped(char par1, int par2) {
    		super.keyTyped(par1, par2);
    		if(front.isFocused()) {
    			
    		} else if(back.isFocused()) {
    			
    		} else if(left.isFocused()) {
    			
    		} else if(right.isFocused()) {
    			
    		} else if(up.isFocused()) {
    			
    		} else if(down.isFocused()) {
    			
    		} else if(beaconInput.isFocused()) {
    			
    		}
	    }
	    
	    @Override
	    protected void mouseClicked(int x, int y, int buttonClicked) {
	    	super.mouseClicked(x, y, buttonClicked);
	    	
	    	this.front.mouseClicked(x, y, buttonClicked);
	    	this.back.mouseClicked(x, y, buttonClicked);
	    	this.left.mouseClicked(x, y, buttonClicked);
	    	this.right.mouseClicked(x, y, buttonClicked);
	    	this.up.mouseClicked(x, y, buttonClicked);
	    	this.down.mouseClicked(x, y, buttonClicked);
	    	
	    	this.beaconInput.mouseClicked(x, y, buttonClicked);
	    }
	    
	    @Override
	    public void updateScreen() {
	    	this.front.updateCursorCounter();
	    	this.back.updateCursorCounter();
	    	this.left.updateCursorCounter();
	    	this.right.updateCursorCounter();
	    	this.up.updateCursorCounter();
	    	this.down.updateCursorCounter();
	    	
	    	this.beaconInput.updateCursorCounter();
	    }
	    
	    @Override
	    protected void actionPerformed(GuiButton button) {
	    	switch(button.id) {
	    		case 0:
	    			furnaceInventory.doJump();
	    			System.out.println("Ship jumped from coordinates" + coord1 + ", " + coord2 + ", " + coord3);
	    		case 1:
	    			//System.out.println("Clicked!");//ACTION PERFORMED ON BUTTON CLICK
	    	}
	    }
	}

