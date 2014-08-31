package cr0s.WarpDrive.client.gui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Keyboard;
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
		 * dim_getP - done
		 * dim_setP - done
		 * dim_getN - done
		 * dim_setN - done
		 * setMode - done
		 * setDistance - done
		 * setDirection - done
		 * getAttachedPlayers - done
		 * summon
		 * summonAll - done
		 * getX - done
		 * getY - done
		 * getZ - done
		 * energyRemaining For Core
		 * doJump - done
		 * getShipSize - done
		 * setBeaconFrequency - unused
		 * getDx - done
		 * getDz - dome
		 * setCoreFrequency - more like ship name, done	
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
	    
	    private GuiTextField distance;
	    
	    private GuiTextField mode;
	    
	    private GuiTextField direction;
	    
	    private GuiTextField shipName;
	    //private GuiTextField beaconInput; UNUSED
	    
	    private char[] allowedChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	    private char[] modeChars = { '0'/*jump/long jump*/, '1'/*To hyperspace*/, '2'/*jumpgate*//*, '3'/*jump to different dimensions*/ };
	    private char[] directionChars = { '0'/*up*/, '1'/*down*/, '2'/*left*/, '3'/*right*/, '4'/*forward*/, '5'/*back*/ };

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
	    
	    String coord4 = "Destination Coordinates: ";
	    String coord5 = "X: " + String.valueOf(furnaceInventory.getDestX());
	    //String coord6 = "Y: " + String.valueOf(furnaceInventory.getDestY());
	    String coord7 = "Z: " + String.valueOf(furnaceInventory.getDestZ());
	    
	    /**
	     * Draw the foreground layer for the GuiContainer (everything in front of the items)
	     */
	    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
	        String s = this.furnaceInventory.isInvNameLocalized() ? this.furnaceInventory.getInvName() : I18n.getString(this.furnaceInventory.getInvName());
	        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
	        this.fontRenderer.drawString(I18n.getString("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	        
	        write(size0, sizex, sizey, grey); //Title
	        write(size1, sizex + pixelsPerWord, sizey, grey); //setP
	        write(size2, sizex + (2 * pixelsPerWord), sizey, grey); //setN
	        
	        write(dimension, dimensionx, dimensiony, grey);
	        
	        write(coord0, coord0x, coord0y, grey);
	        write(coord1, coord0x + pixelsPerWord, coord0y, grey);
	        write(coord2, coord0x + 2 * (pixelsPerWord), coord0y, grey);
	        write(coord3, coord0x + 3 * (pixelsPerWord), coord0y, grey);
	        
	        write(coord4, coord0x + 5 * (pixelsPerWord), coord0y, grey);
	        write(coord5, coord0x + 6 * (pixelsPerWord), coord0y, grey);
	        write(coord7, coord0x + 7 * (pixelsPerWord), coord0y, grey);
	        
	        write(blocks, blocksx, blocksy, grey);
	        
	        front.drawTextBox();
	        back.drawTextBox();
	        left.drawTextBox();
	        right.drawTextBox();
	        up.drawTextBox();
	        down.drawTextBox();
	        
	        distance.drawTextBox();
	        
	        mode.drawTextBox();
	        
	        direction.drawTextBox();
	        
	        shipName.drawTextBox();
	        
	        //beaconInput.drawTextBox(); UNUSED
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
	    	
	    	Keyboard.enableRepeatEvents(true);
		    
		    //int biX = 0; UNUSED
		    //int biY = 0; UNUSED
	    	
		    int frontX = 0; //TODO
		    int frontY = 0;
		    
		    int diX = 0;
		    int diY = 0;
		    
		    int dirX = 0;
		    int dirY = 0;
		    
		    int moX = 0;
		    int moY = 0;
		    
		    int sNX = 0;
		    int sNY = 0;
		    
		    int inputLength = 16;
		    int inputWidth = 16;
		    
		    applyBasicAttributes(front);
		    applyBasicAttributes(back);
		    applyBasicAttributes(right);
		    applyBasicAttributes(left);
		    applyBasicAttributes(up);
		    applyBasicAttributes(down);
		    
		    applyBasicAttributes(distance);
		    
		    applyBasicAttributes(mode);
		    
		    applyBasicAttributes(direction);
		    
		    applyBasicAttributes(shipName);
		    //applyBasicAttributes(beaconInput); UNUSED
		    
		    shipName = new GuiTextField(this.fontRenderer, sNX, sNY, inputLength, inputWidth);
		    shipName.setMaxStringLength(10);
		    
		    direction = new GuiTextField(this.fontRenderer, dirX, dirY, inputLength, inputWidth);
		    direction.setMaxStringLength(1);
		    
		    mode = new GuiTextField(this.fontRenderer, moX, moY, inputLength, inputWidth);
		    mode.setMaxStringLength(1);

		    distance = new GuiTextField(this.fontRenderer, diX, diY, inputLength, inputWidth);
		    distance.setMaxStringLength(3);
		    
	    	front = new GuiTextField(this.fontRenderer, frontX, frontY, inputLength, inputWidth);
	    	front.setMaxStringLength(3);
	    	
	    	back = new GuiTextField(this.fontRenderer, frontX, frontY + pixelsPerWord, inputLength, inputWidth);
	    	back.setMaxStringLength(3);
	    	
	    	left = new GuiTextField(this.fontRenderer, frontX, frontY + (2 * pixelsPerWord), inputLength, inputWidth);
	    	left.setMaxStringLength(3);
	    	
	    	right = new GuiTextField(this.fontRenderer, frontX, frontY + (3 * pixelsPerWord), inputLength, inputWidth);
	    	right.setMaxStringLength(3);
	    	
	    	up = new GuiTextField(this.fontRenderer, frontX, frontY + (4 * pixelsPerWord), inputLength, inputWidth);
	    	up.setMaxStringLength(3);
	    	
	    	down = new GuiTextField(this.fontRenderer, frontX, frontY + (5 * pixelsPerWord), inputLength, inputWidth);
	    	down.setMaxStringLength(3);
	    	
	    	//beaconInput = new GuiTextField(this.fontRenderer, biX, biY, inputLength, inputWidth); UNUSED
	    	//beaconInput.setMaxStringLength(2); UNUSED
	    	
	    	/**/buttonList.add(new GuiButton(0/*button number, maybe for mod, else gui*/, guiLeft + 100/*Location in relation to left in pixels*/, guiTop + 14/*Location in relation to top in pixels*/, 60/*Length in pixels*/, 20/*Height in pixels*/, "Jump"/*Text on button*/));
	    	/**/buttonList.add(new GuiButton(1/*button number, maybe for mod, else gui*/, guiLeft + 100/*Location in relation to left in pixels*/, guiTop + 14/*Location in relation to top in pixels*/, 60/*Length in pixels*/, 20/*Height in pixels*/, "Summon All"/*Text on button*/));
	    }
	    
	    public void applyBasicAttributes(GuiTextField field) {
	    	field.setEnableBackgroundDrawing(true);
	    	field.setFocused(false);
	    	field.setCanLoseFocus(true);
		}

		@Override
	    protected void keyTyped(char par1, int par2) {
			if(isValidChar(par1)) {
				//super.keyTyped(par1, par2);
				if(front.textboxKeyTyped(par1, par2)) {
					simpleTextPacket("WarpDrive_Protocol_Front", this.front, 3);
				} else if(back.textboxKeyTyped(par1, par2)) {
					simpleTextPacket("WarpDrive_Protocol_Back", this.back, 3);
				} else if(left.textboxKeyTyped(par1, par2)) {
					simpleTextPacket("WarpDrive_Protocol_Left", this.left, 3);
				} else if(right.textboxKeyTyped(par1, par2)) {
					simpleTextPacket("WarpDrive_Protocol_Right", this.right, 3);
				} else if(up.textboxKeyTyped(par1, par2)) {
					simpleTextPacket("WarpDrive_Protocol_Up", this.up, 3);
				} else if(down.textboxKeyTyped(par1, par2)) {
					simpleTextPacket("WarpDrive_Protocol_Down", this.down, 3);
				} else if(distance.textboxKeyTyped(par1, par2)) {
					simpleTextPacket("WarpDrive_Protocol_Distance", this.distance, 3);
				} else if(shipName.textboxKeyTyped(par1, par2)) {
					shipName();
				} else if(isValidModeChar(par1)) {
					if(mode.textboxKeyTyped(par1, par2)) {
						modeTextPacket("WarpDrive_Protocol_Mode", this.mode, 1);
				} else if(isValidDirectionChar(par1)) {
					if(direction.textboxKeyTyped(par1, par2)) {
						directionTextPacket("WarpDrive_Protocol_Direction", this.direction, 1);
					}
				}
				//} else if(beaconInput.textboxKeyTyped(par1, par2)) {UNUSED
					//simpleTextPacket("WarpDrive_Protocol", this.beaconInput.getText().getBytes())); UNUSED
				}
    		}/*etc*/ else {
    			super.keyTyped(par1, par2);
    		}
	    }
		
	    public void shipName() {
			ByteArrayOutputStream bos = new ByteArrayOutputStream(shipName.getText().length());
			DataOutputStream output = new DataOutputStream(bos);
			
			try {
				String f = shipName.getText();
				
				output.writeUTF(f);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			Packet250CustomPayload packet = new Packet250CustomPayload();
			packet.channel = "WarpDrive_ShipName";
			packet.data = bos.toByteArray();
			packet.length = bos.size();
		}

		public void directionTextPacket(String channel, GuiTextField field, int bytes) {
			ByteArrayOutputStream bos = new ByteArrayOutputStream(bytes);
			DataOutputStream output = new DataOutputStream(bos);
			
			try {
				String f = field.getText();
				int fI = Integer.parseInt(f);
				
				output.writeInt(fI);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			Packet250CustomPayload packet = new Packet250CustomPayload();
			packet.channel = channel;
			packet.data = bos.toByteArray();
			packet.length = bos.size();
			
			this.mc.thePlayer.sendQueue.addToSendQueue(packet);
		}

		private boolean isValidDirectionChar(char par1) {
	    	for(int x = 0; x <= this.directionChars.length; x++) {
	    		if(par1 == this.directionChars[x]) {
	    			return true;
	    		}
	    	}
			return false;
		}

		private boolean isValidModeChar(char par1) {
	    	for(int x = 0; x <= this.modeChars.length; x++) {
	    		if(par1 == this.modeChars[x]) {
	    			return true;
	    		}
	    	}
	    	return false;
	    }

		public void modeTextPacket(String channel, GuiTextField field, int lengthOfBytesInField) {
			ByteArrayOutputStream bos = new ByteArrayOutputStream(lengthOfBytesInField);
			DataOutputStream output = new DataOutputStream(bos);
			
			try {
				String f = field.getText();
				int fI = Integer.parseInt(f);
				
				output.writeInt(fI);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			Packet250CustomPayload packet = new Packet250CustomPayload();
			packet.channel = channel;
			packet.data = bos.toByteArray();
			packet.length = bos.size();
			
			this.mc.thePlayer.sendQueue.addToSendQueue(packet);
		}

		public void simpleTextPacket(String channel, GuiTextField field, int lengthOfBytesInField) {
			ByteArrayOutputStream bos = new ByteArrayOutputStream(lengthOfBytesInField);
			DataOutputStream output = new DataOutputStream(bos);
			
			try {
				String f = field.getText();
				int fI = Integer.parseInt(f);
				
				output.writeInt(fI);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			Packet250CustomPayload packet = new Packet250CustomPayload();
			packet.channel = channel;
			packet.data = bos.toByteArray();
			packet.length = bos.size();
			
			this.mc.thePlayer.sendQueue.addToSendQueue(packet);
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
	    	
	    	this.distance.mouseClicked(x, y, buttonClicked);
	    	
	    	this.mode.mouseClicked(x, y, buttonClicked);
	    	
	    	//this.beaconInput.mouseClicked(x, y, buttonClicked); UNUSED
	    }
	    
	    private boolean isValidChar(char par1) {
	    	for(int x = 0; x <= this.allowedChars.length; x++) {
	    		if(par1 == this.allowedChars[x]) {
	    			return true;
	    		}
	    	}
	    	return false;
	    }
	    
	    @Override
	    public void updateScreen() {
	    	this.front.updateCursorCounter();
	    	this.back.updateCursorCounter();
	    	this.left.updateCursorCounter();
	    	this.right.updateCursorCounter();
	    	this.up.updateCursorCounter();
	    	this.down.updateCursorCounter();
	    	
	    	this.distance.updateCursorCounter();
	    	
	    	this.mode.updateCursorCounter();
	    	
	    	//this.beaconInput.updateCursorCounter(); UNUSED
	    }
	    
	    @Override
	    protected void actionPerformed(GuiButton button) {
	    	switch(button.id) {
	    		case 0:
	    			furnaceInventory.doJump();
	    			System.out.println("Ship jumped from coordinates" + coord1 + ", " + coord2 + ", " + coord3);
	    		case 1:
	    			furnaceInventory.setSummonAllFlag(true);
	    			//System.out.println("Clicked!");//ACTION PERFORMED ON BUTTON CLICK
	    	}
	    }
	    
	    @Override
	    public void onGuiClosed() {
	    	super.onGuiClosed();
	    	Keyboard.enableRepeatEvents(false);
	    }
	    
	    @Override
	    public void drawScreen(int par1, int par2, float par3) {
	    	super.drawScreen(par1, par2, par3);
	    	this.front.drawTextBox();
	    	this.back.drawTextBox();
	    	this.left.drawTextBox();
	    	this.right.drawTextBox();
	    	this.up.drawTextBox();
	    	this.down.drawTextBox();
	    	
	    	this.distance.drawTextBox();
	    	
	    	this.mode.drawTextBox();
	    }
	}

