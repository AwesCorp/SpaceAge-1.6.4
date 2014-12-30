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

import cpw.mods.fml.common.network.PacketDispatcher;
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
	   
		private static final ResourceLocation furnaceGuiTextures = new ResourceLocation(WarpDrive.modid + ":textures/gui/" + "guiWarpInterface_alternate.png");//ClientProxy.warpInterfaceGui/*"textures/gui/container/furnace.png"*/);
	    private TileEntityProtocol furnaceInventory;
	    
	    //private GuiTextField front;
	    //private GuiTextField back;
	    //private GuiTextField left;
	    //private GuiTextField right;
	    //private GuiTextField up;
	    //private GuiTextField down;
	    
	    //private GuiTextField distance;
	    
	    //private GuiTextField mode;
	    
	    //private GuiTextField direction;
	    
	    private GuiTextField shipName;
	    //private GuiTextField beaconInput; UNUSED
	    
	    //private char[] allowedChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	    //private char[] modeChars = { '0'/*jump/long jump*/, '1'/*To hyperspace*/, '2'/*jumpgate*//*, '3'/*jump to different dimensions*/ };
	    //private char[] directionChars = { '0'/*up*/, '1'/*down*/, '2'/*left*/, '3'/*right*/, '4'/*forward*/, '5'/*back*/ };

	    //TEST
	    public static final GuiRectangle frontSlider = new GuiRectangle(116, 166, 4, 6);
	    public static final GuiRectangle backSlider = new GuiRectangle(116, 160, 4, 6);
	    public static final GuiRectangle leftSlider = new GuiRectangle(116, 154, 4, 6);
	    public static final GuiRectangle rightSlider = new GuiRectangle(116, 148, 4, 6);
	    public static final GuiRectangle upSlider = new GuiRectangle(116, 142, 4, 6);
	    public static final GuiRectangle bottomSlider = new GuiRectangle(116, 136, 4, 6);
	    
	    public static final GuiRectangle modeSlider = new GuiRectangle(161, 10, 4, 6);
	    
	    public static final GuiRectangle distanceSlider = new GuiRectangle(116, 118, 4, 6);
	    
	    public static final GuiRectangle directionSlider = new GuiRectangle(161, 16, 4, 6);
	    
	    //public static final GuiRectangle frontBehindSlider = new GuiRectangle(120, 168, 160, 2);
	    private boolean frontIsDragging;
	    private boolean backIsDragging;
	    private boolean leftIsDragging;
	    private boolean rightIsDragging;
	    private boolean upIsDragging;
	    private boolean bottomIsDragging;
	    
	    private boolean modeIsDragging;
	    
	    private boolean distanceIsDragging;
	    
	    private boolean directionIsDragging;
	    
	    //private int barFirst = 50;
	    private int tempFrontSetting = 25;
	    private int tempBackSetting = 25;
	    private int tempLeftSetting = 25;
	    private int tempRightSetting = 25;
	    private int tempUpSetting = 25;
	    private int tempBottomSetting = 25;
	    
	    private int tempModeSetting = 1;
	    
	    private int tempDistanceSetting = 25;
	    
	    private int tempDirectionSetting = 1;
	    
	    public GUIProtocol(InventoryPlayer par1InventoryPlayer, TileEntityProtocol tile_entity) {
	        super(new ContainerProtocol(par1InventoryPlayer, tile_entity));
	        this.furnaceInventory = tile_entity;
	        
	        this.xSize = 256;
	        this.ySize = 256;
	    }
	    
	    public void sendSliderPacket(int identifier, int tempHeightSetting) {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DataOutputStream output = new DataOutputStream(bos);
			
			try {
				output.writeInt(identifier);
				output.writeInt(tempHeightSetting);
				
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			Packet250CustomPayload packet = new Packet250CustomPayload();
			packet.channel = "WarpDriveGui";
			packet.data = bos.toByteArray();
			packet.length = bos.size();
			
			//this.mc.thePlayer.sendQueue.addToSendQueue(packet);
			PacketDispatcher.sendPacketToServer(packet);
	    }
	    
	    @Override
	    protected void mouseClickMove(int x, int y, int button, long timeSinceLastClick) {
	    	super.mouseClickMove(x, y, button, timeSinceLastClick);	
	    	
	    	if (frontIsDragging) {
	    		tempFrontSetting = x - guiLeft - 116;
	    		
	    		if (tempFrontSetting < 0) {
	    			tempFrontSetting = 0;
	    		} else if (tempFrontSetting > /*change*/128) {
	    			tempFrontSetting = 128;/*change*/
	    		}
	    	} 
	    	if (backIsDragging) {
	    		tempBackSetting = x - guiLeft - 116;
	    		
	    		if (tempBackSetting < 0) {
	    			tempBackSetting = 0;
	    		} else if (tempBackSetting > 128) {
	    			tempBackSetting = 128;
	    		}
	    	} 
	    	if (leftIsDragging) {
	    		tempLeftSetting = x - guiLeft - 116;
	    		
	    		if (tempLeftSetting < 0) {
	    			tempLeftSetting = 0;
	    		} else if (tempLeftSetting > 128) {
	    			tempLeftSetting = 128;
	    		}
	    		//System.out.println("left is dragging!");
	    	} 
	    	if (rightIsDragging) {
	    		tempRightSetting = x - guiLeft - 116;
	    		
	    		if (tempRightSetting < 0) {
	    			tempRightSetting = 0;
	    		} else if (tempRightSetting > 128) {
	    			tempRightSetting = 128;
	    		}
	    	} 
	    	if (upIsDragging) {
	    		tempUpSetting = x - guiLeft - 116;
	    		
	    		if (tempUpSetting < 0) {
	    			tempUpSetting = 0;
	    		} else if (tempUpSetting > 128) {
	    			tempUpSetting = 128;
	    		}
	    	}
	    	if (bottomIsDragging) {
	    		tempBottomSetting = x - guiLeft - 116;
	    		
	    		if (tempBottomSetting < 0) {
	    			tempBottomSetting = 0;
	    		} else if (tempBottomSetting > 128) {
	    			tempBottomSetting = 128;
	    		}
	    	}
	    	if (modeIsDragging) {
	    		tempModeSetting = x - guiLeft - 161;
	    		
	    		if (tempModeSetting < 0) {
	    			tempModeSetting = 0;
	    		} else if (tempModeSetting > 2) {
	    			tempModeSetting = 2;//6
	    		}
	    	}
	    	if (distanceIsDragging) {
	    		tempDistanceSetting = x - guiLeft - 116;
	    		
	    		if (tempDistanceSetting < 0) {
	    			tempDistanceSetting = 0;
	    		} else if (tempDistanceSetting > 128) {
	    			tempDistanceSetting = 128;
	    		}
	    	}
	    	if (directionIsDragging) {
	    		tempDirectionSetting = x - guiLeft - 161;
	    		
	    		if (tempDirectionSetting < 0) {
	    			tempDirectionSetting = 0;
	    		} else if (tempDirectionSetting > 5) {
	    			tempDirectionSetting = 5;//9
	    		}
	    	}
	    }
	    
	    @Override
	    protected void mouseMovedOrUp(int x, int y, int button) {
	    	super.mouseMovedOrUp(x, y, button);
	    	
	    	if (frontIsDragging) {
	    		sendSliderPacket(0, tempFrontSetting);
	    		furnaceInventory.setFront(tempFrontSetting);
		    	frontIsDragging = false;
	    	} 
	    	if (backIsDragging) {
	    		sendSliderPacket(1, tempBackSetting);
	    		furnaceInventory.setBack(tempBackSetting);
		    	backIsDragging = false;
	    	} 
	    	if (leftIsDragging) {
	    		sendSliderPacket(2, tempLeftSetting);
	    		furnaceInventory.setLeft(tempLeftSetting);
		    	leftIsDragging = false;
		    	//System.out.println("LEFT IS DRAGGING!");
	    	} 
	    	if (rightIsDragging) {
	    		sendSliderPacket(3, tempRightSetting);
	    		furnaceInventory.setRight(tempRightSetting);
		    	rightIsDragging = false;
	    	}
	    	if (upIsDragging) {
	    		sendSliderPacket(4, tempUpSetting);
	    		furnaceInventory.setUp(tempUpSetting);
		    	upIsDragging = false;
	    	}
	    	if (bottomIsDragging) {
	    		sendSliderPacket(5, tempBottomSetting);
	    		furnaceInventory.setDown(tempBottomSetting);
		    	bottomIsDragging = false;
	    	}
	    	if (modeIsDragging) {
	    		sendSliderPacket(6, tempModeSetting);
	    		
	    		furnaceInventory.setMode(evenMoreTempModeSetting(tempModeSetting));
	    		modeIsDragging = false;
	    	}
	    	if (distanceIsDragging) {
	    		sendSliderPacket(7, tempDistanceSetting);
	    		
	    		furnaceInventory.setJumpDistance(tempDistanceSetting);
	    		distanceIsDragging = false;
	    	}
	    	if (directionIsDragging) {
	    		sendSliderPacket(8, tempDirectionSetting);
	    		
	    		furnaceInventory.setDirection(transposeDirection(tempDirectionSetting));
	    		directionIsDragging = false;
	    	}
	    }
	    
	    public int transposeDirection(int tempDirectionSetting) {
	    	switch(tempDirectionSetting) {
	    		case 0://up
	    			return 1;
	    		case 1://down
	    			return 2;
	    		case 2://left
	    			return 90;
	    		case 3://right
	    			return 255;
	    		case 4://forward
	    			return 0;
	    		case 5://back
	    			return 180;
	    	}
			return 0;
		}
	    
	    public int antiTransposedDirection() {
	    	switch(furnaceInventory.getDirection()) {
	    		case 0://forward
	    			return 4;
	    		case 1://up
	    			return 0;
	    		case 2://down
	    			return 1;
	    		case 90://left
	    			return 2;
	    		case 180://back
	    			return 5;
	    		case 255://right
	    			return 3;
	    	}
	    	return 0;
	    }

		public int evenMoreTempModeSetting(int mode) {
	    	switch(mode) {
	    		case 0:	
	    			if(this.furnaceInventory.worldObj.provider.dimensionId == WarpDrive.instance.hyperSpaceDimID) {
	    				return 2;
	    			} else {
	    				return 1;
	    			}
	    		case 1:
	    			return 5;
	    		case 2:
	    			return 6;
	    	}
			return 1;
		}

		private void updateFrontSliderPosition() {
	        frontSlider.setX(116 + getCurrentFrontHeight());
	        //System.out.println("WD DEBUG: Just ran updateFrontSliderPosition");
	    }
	    
	    private void updateBackSliderPosition() {
	        backSlider.setX(116 + getCurrentBackHeight());
	        //System.out.println("WD DEBUG: Just ran updateFrontSliderPosition");
	    }
	    
	    private void updateLeftSliderPosition() {
	        leftSlider.setX(116 + getCurrentLeftHeight());
	        //System.out.println("WD DEBUG: Just ran updateLeftSliderPosition");
	    }
	    
	    private void updateRightSliderPosition() {
	        rightSlider.setX(116 + getCurrentRightHeight());
	        //System.out.println("WD DEBUG: Just ran updateRightSliderPosition");
	    }
	    
	    private void updateUpSliderPosition() {
	        upSlider.setX(116 + getCurrentUpHeight());
	        //System.out.println("WD DEBUG: Just ran updateFrontSliderPosition");
	    }
	    
	    private void updateBottomSliderPosition() {
	        bottomSlider.setX(116 + getCurrentBottomHeight());
	        //System.out.println("WD DEBUG: Just ran updateFrontSliderPosition");
	    }
	    
	    private void updateModeSliderPosition() {
	    	modeSlider.setX(161 + getCurrentModeHeight());
	    }
	    
	    private void updateDistanceSliderPosition() {
	    	distanceSlider.setX(116 + getCurrentDistance());
	    }
	    
	    private void updateDirectionSliderPosition() {
	    	directionSlider.setX(161 + getCurrentDirection());
	    }
	    
	    public int getCurrentFrontHeight() {
	    	return frontIsDragging ? tempFrontSetting : furnaceInventory.getFront();
	    }
	    
	    public int getCurrentBackHeight() {
	    	return backIsDragging ? tempBackSetting : furnaceInventory.getBack();
	    }
	    
	    public int getCurrentLeftHeight() {
	    	return leftIsDragging ? tempLeftSetting : furnaceInventory.getLeft();
	    }
	    
	    public int getCurrentRightHeight() {
	    	return rightIsDragging ? tempRightSetting : furnaceInventory.getRight();
	    }
	    
	    public int getCurrentUpHeight() {
	    	return upIsDragging ? tempUpSetting : furnaceInventory.getUp();
	    }
	    
	    public int getCurrentBottomHeight() {
	    	return bottomIsDragging ? tempBottomSetting : furnaceInventory.getDown();
	    }
	    
	    public int getCurrentModeHeight() {
	    	return modeIsDragging ? tempModeSetting : transcribedMode();
	    }
	    
	    public int getCurrentDistance() {
	    	return distanceIsDragging ? tempDistanceSetting : furnaceInventory.getDistance();
	    }
	    
	    public int getCurrentDirection() {
	    	return directionIsDragging ? tempDirectionSetting : antiTransposedDirection();
	    }
	    
	    public int transcribedMode() {
	    	switch(furnaceInventory.getMode()) {
	    		case 1:
	    			return 0;
	    		case 2:
	    			return 0;
	    		case 5:
	    			return 1;
	    		case 6:
	    			return 2;
	    	}
			return 0;
		}

		int grey = 4210752;
	    
	    int sizex = 26;//10-24
	    int sizey = 33;
	    
	    int dimensionx = 15;
	    int dimensiony = 177;
	    
	    int blocksx = 26;
	    int blocksy = 64;
	    
	    int coord0x = 26;
	    int coord0y = 75;
	    
	    int pixelsPerWord = 10;
	    
	    String size0 = "Amount of blocks from Core to be moved: ";
	    
	    
	    /**
	     * Draw the foreground layer for the GuiContainer (everything in front of the items)
	     */
	    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
	        String s = this.furnaceInventory.isInvNameLocalized() ? this.furnaceInventory.getInvName() : I18n.getString(this.furnaceInventory.getInvName());
	        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 1, 4210752);
	        this.fontRenderer.drawString(I18n.getString("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	        
	        String size1 = "Front: " + getCurrentFrontHeight() + ", Right: " + getCurrentRightHeight() + ", Up: " + getCurrentUpHeight();
		    String size2 = "Back: " + getCurrentBackHeight() + ", Left: " + getCurrentLeftHeight() + ", Down: " + getCurrentBottomHeight();

		    String dimension = furnaceInventory.currentDimension();
		    
		    String blocks = "Ship Size: " + String.valueOf(furnaceInventory.getShipSize());
		    
		    String coord0 = "Coordinates: ";
		    String coord1 = "X: " + String.valueOf(furnaceInventory.getCoreX());
		    String coord2 = "Y: " + String.valueOf(furnaceInventory.getCoreY());
		    String coord3 = "Z: " + String.valueOf(furnaceInventory.getCoreZ());
		    
		    String coord4 = "Destination Coordinates: ";
		    String coord5 = "X: " + String.valueOf(furnaceInventory.getDestX());
		    //String coord6 = "Y: " + String.valueOf(furnaceInventory.getDestY());
		    String coord7 = "Z: " + String.valueOf(furnaceInventory.getDestZ());
	        
	        write(size0, sizex, sizey, grey); //Title
	        write(size1, sizex, sizey + pixelsPerWord, grey); //setP
	        write(size2, sizex, sizey + (2 * pixelsPerWord), grey); //setN
	        
	        write(dimension, dimensionx, dimensiony, grey);
	        
	        write(coord0, coord0x, coord0y, grey);
	        write(coord1, coord0x, coord0y + pixelsPerWord, grey);
	        write(coord2, coord0x, coord0y + 2 * (pixelsPerWord), grey);
	        write(coord3, coord0x, coord0y + 3 * (pixelsPerWord), grey);
	        
	        write(coord4, coord0x, coord0y + 5 * (pixelsPerWord), grey);
	        write(coord5, coord0x, coord0y + 6 * (pixelsPerWord), grey);
	        write(coord7, coord0x, coord0y + 7 * (pixelsPerWord), grey);
	        
	        write(blocks, blocksx, blocksy, grey);
	        
	        //front.drawTextBox();
	        //back.drawTextBox();
	        //left.drawTextBox();
	        //right.drawTextBox();
	        //up.drawTextBox();
	        //down.drawTextBox();
	        
	        //distance.drawTextBox();
	        
	        //mode.drawTextBox();
	        
	        //direction.drawTextBox();
	        
	        shipName.drawTextBox();
	        
	        
	        //write("Front: " + getCurrentFrontHeight(), 0, 0, grey);
	        //write("Back: " + getCurrentBackHeight(), 0, 0, grey);
	        //write("Left: " + getCurrentLeftHeight(), 0, 0, grey);
	        //write("Right: " + getCurrentRightHeight(), 0, 0, grey);
	        //write("Up: " + getCurrentUpHeight(), 0, 0, grey);
	        //write("Down: " + getCurrentBottomHeight(), 0, 0, grey);
	        
	        write("Mode: " + getCurrentModeHeight(), 0, 0, grey);
	        
	        write("Distance: " + getCurrentDistance(), 0, 0, grey);
	        
	        write("Direction: " + getCurrentDirection(), 0, 0, grey);
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
	        
	        frontSlider.draw(this, 88, 174);
	        updateFrontSliderPosition();
	        
	        backSlider.draw(this, 88, 174);
	        updateBackSliderPosition();
	        
	        leftSlider.draw(this, 88, 174);
	        updateLeftSliderPosition();
	        //System.out.println("drew left slider");
	        
	        rightSlider.draw(this, 88, 174);
	        updateRightSliderPosition();
	        
	        upSlider.draw(this, 88, 174);
	        updateUpSliderPosition();
	        
	        bottomSlider.draw(this, 88, 174);
	        updateBottomSliderPosition();
	        
	        modeSlider.draw(this, 88, 174);
	        updateModeSliderPosition();
	        
	        distanceSlider.draw(this, 88, 174);
	        updateDistanceSliderPosition();
	        
	        directionSlider.draw(this, 88, 174);
	        updateDirectionSliderPosition();
	        
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
	    	
		    //int frontX = 0; //TODO
		    //int frontY = 0;
		    
		    //int diX = 0;
		    //int diY = 0;
		    
		    //int dirX = 0;
		    //int dirY = 0;
		    
		    //int moX = 0;
		    //int moY = 0;
		    
		    int sNX = 98;
		    int sNY = 12;
		    
		    int inputLength = 30;
		    int inputWidth = 20;
		    
		    //applyBasicAttributes(beaconInput); UNUSED
		    
		    shipName = new GuiTextField(this.fontRenderer, sNX, sNY, inputLength * 2, inputWidth);
		    shipName.setMaxStringLength(10);
	    	shipName.setEnableBackgroundDrawing(true);
	    	shipName.setFocused(false);
	    	shipName.setCanLoseFocus(true);
		    
		    //direction = new GuiTextField(this.fontRenderer, dirX, dirY, inputLength, inputWidth);
		    //direction.setMaxStringLength(1);
		    
		    //mode = new GuiTextField(this.fontRenderer, moX, moY, inputLength, inputWidth);
		    //mode.setMaxStringLength(1);

		    //distance = new GuiTextField(this.fontRenderer, diX, diY, inputLength, inputWidth);
		    //distance.setMaxStringLength(3);
		    
	    	//front = new GuiTextField(this.fontRenderer, frontX, frontY, inputLength, inputWidth);
	    	//front.setMaxStringLength(3);
	    	
	    	//back = new GuiTextField(this.fontRenderer, frontX, frontY + pixelsPerWord, inputLength, inputWidth);
	    	//back.setMaxStringLength(3);
	    	
	    	//left = new GuiTextField(this.fontRenderer, frontX, frontY + (2 * pixelsPerWord), inputLength, inputWidth);
	    	//left.setMaxStringLength(3);
	    	
	    	//right = new GuiTextField(this.fontRenderer, frontX, frontY + (3 * pixelsPerWord), inputLength, inputWidth);
	    	//right.setMaxStringLength(3);
	    	
	    	//up = new GuiTextField(this.fontRenderer, frontX, frontY + (4 * pixelsPerWord), inputLength, inputWidth);
	    	//up.setMaxStringLength(3);
	    	
	    	//down = new GuiTextField(this.fontRenderer, frontX, frontY + (5 * pixelsPerWord), inputLength, inputWidth);
	    	//down.setMaxStringLength(3);
	    	
		    //applyBasicAttributes(front);
		    //applyBasicAttributes(back);
		    //applyBasicAttributes(right);
		    //applyBasicAttributes(left);
		    //applyBasicAttributes(up);
		    //applyBasicAttributes(down);
		    
		    //applyBasicAttributes(distance);
		    
		    //applyBasicAttributes(mode);
		    
		    //applyBasicAttributes(direction);
		    
		    //applyBasicAttributes(shipName);
	    	
	    	//beaconInput = new GuiTextField(this.fontRenderer, biX, biY, inputLength, inputWidth); UNUSED
	    	//beaconInput.setMaxStringLength(2); UNUSED
	    	
	    	/**/buttonList.add(new GuiButton(0/*button number, maybe for mod, else gui*/, guiLeft + 14/*Location in relation to left in pixels*/, guiTop + 222/*Location in relation to top in pixels*/, 30/*Length in pixels*/, 20/*FrontHeight in pixels*/, "Jump"/*Text on button*/));
	    	/**/buttonList.add(new GuiButton(1/*button number, maybe for mod, else gui*/, guiLeft + 14/*Location in relation to left in pixels*/, guiTop + 202/*Location in relation to top in pixels*/, 60/*Length in pixels*/, 20/*FrontHeight in pixels*/, "Summon All"/*Text on button*/));
	    	buttonList.add(new GuiButton(2, guiLeft + 44/*217: -40*/, guiTop + 222/*227: -45*/, 30, 20, "Wiki"));
	    }
	    
	    /*public void applyBasicAttributes(GuiTextField field) {
	    	//if(field != null) {
		  //  	field.setEnableBackgroundDrawing(true);
		    //	field.setFocused(false);
		    	//field.setCanLoseFocus(true);
	    	//}
		//}*/

		@Override
	    protected void keyTyped(char par1, int par2) {
			if(par1 != 27) {
				if(shipName.textboxKeyTyped(par1, par2)) {
					shipName();
				}
				
					/*if(isValidChar(par1)) {
				//super.keyTyped(par1, par2);
						//if(front.textboxKeyTyped(par1, par2)) {
							//simpleTextPacket("WarpDrive_F", this.front, 4);
						//} else if(back.textboxKeyTyped(par1, par2)) {
							//simpleTextPacket("WarpDrive_B", this.back, 4);
						//if(left.textboxKeyTyped(par1, par2)) {
							//simpleTextPacket("WarpDrive_L", this.left, 4);
						//} else if(right.textboxKeyTyped(par1, par2)) {
						//	simpleTextPacket("WarpDrive_R", this.right, 4);
						//} else if(up.textboxKeyTyped(par1, par2)) {
							//simpleTextPacket("WarpDrive_U", this.up, 4);
						//} else if(down.textboxKeyTyped(par1, par2)) {
							//simpleTextPacket("WarpDrive_D", this.down, 4);
						////} else if(distance.textboxKeyTyped(par1, par2)) {
						//	simpleTextPacket("WarpDrive_Dis", this.distance, 4);
						//} else if(isValidModeChar(par1)) {
							if(mode.textboxKeyTyped(par1, par2)) {
								modeTextPacket("WarpDrive_M", this.mode, 4);
							} else if(isValidDirectionChar(par1)) {
								if(direction.textboxKeyTyped(par1, par2)) {
									directionTextPacket("WarpDrive_Dir", this.direction, 4);
								}
							}
				//} else if(beaconInput.textboxKeyTyped(par1, par2)) {UNUSED
					//simpleTextPacket("WarpDrive_Protocol", this.beaconInput.getText().getBytes())); UNUSED
						}
					} else {
						super.keyTyped(par1, par2);
					}
				}*/
			} else if (par1 == 27) {
				mc.thePlayer.closeScreen();
			}
		}

		public void shipName() {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DataOutputStream output = new DataOutputStream(bos);
			
			try {
				String f = shipName.getText();
				
				output.writeUTF(f);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			Packet250CustomPayload packet = new Packet250CustomPayload();
			packet.channel = "WarpDrive_SN";
			packet.data = bos.toByteArray();
			packet.length = bos.size();
			
			this.mc.thePlayer.sendQueue.addToSendQueue(packet);
		}

/*		public void directionTextPacket(String channel, GuiTextField field, int bytes) {
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
		}*/

		@Override
	    protected void mouseClicked(int x, int y, int buttonClicked) {
	    	super.mouseClicked(x, y, buttonClicked);
	    	
	    	//this.front.mouseClicked(x, y, buttonClicked);
	    	//this.back.mouseClicked(x, y, buttonClicked);
	    	//this.left.mouseClicked(x, y, buttonClicked);
	    	//this.right.mouseClicked(x, y, buttonClicked);
	    	//this.up.mouseClicked(x, y, buttonClicked);
	    	//this.down.mouseClicked(x, y, buttonClicked);
	    	
	    	//this.distance.mouseClicked(x, y, buttonClicked);
	    	
	    	//this.mode.mouseClicked(x, y, buttonClicked);
	    	
	    	//this.direction.mouseClicked(x, y, buttonClicked);
	    	
	    	this.shipName.mouseClicked(x, y, buttonClicked);
	    	
	    	updateFrontSliderPosition();
	    	if (frontSlider.isInRect(this, x, y)) {
	    		frontIsDragging = true;
	    		//System.out.println("REGISTERED FRONT DRAGGING");
	    		tempFrontSetting = furnaceInventory.getFront();
	    	}
	    	
	    	updateBackSliderPosition();
	    	if (backSlider.isInRect(this, x, y)) {
	    		backIsDragging = true;
	    		//System.out.println("REGISTERED BACK DRAGGING");
	    		tempBackSetting = furnaceInventory.getBack();
	    	}
	    	
	    	updateLeftSliderPosition();
	    	if (leftSlider.isInRect(this, x, y)) {
	    		leftIsDragging = true;
	    		//System.out.println("REGISTERED LefT DRAGGING");
	    		tempLeftSetting = furnaceInventory.getLeft();
	    	}
	    	
	    	updateRightSliderPosition();
	    	if (rightSlider.isInRect(this, x, y)) {
	    		rightIsDragging = true;
	    		//System.out.println("REGISTERED RIGHT DRAGGING");
	    		tempRightSetting = furnaceInventory.getRight();
	    	}
	    	
	    	updateUpSliderPosition();
	    	if (upSlider.isInRect(this, x, y)) {
	    		upIsDragging = true;
	    		//System.out.println("REGISTERED UP DRAGGING");
	    		tempUpSetting = furnaceInventory.getUp();
	    	}
	    	
	    	updateBottomSliderPosition();
	    	if (bottomSlider.isInRect(this, x, y)) {
	    		bottomIsDragging = true;
	    		//System.out.println("REGISTERED DOWN DRAGGING");
	    		tempBottomSetting = furnaceInventory.getDown();
	    	}
	    	
	    	updateModeSliderPosition();
	    	if (modeSlider.isInRect(this, x, y)) {
	    		modeIsDragging = true;
	    		tempModeSetting = transcribedMode();
	    	}
	    	
	    	updateDistanceSliderPosition();
	    	if (distanceSlider.isInRect(this, x, y)) {
	    		distanceIsDragging = true;
	    		tempDistanceSetting = furnaceInventory.getDistance();
	    	}
	    	
	    	updateDirectionSliderPosition();
	    	if (directionSlider.isInRect(this, x, y)) {
	    		directionIsDragging = true;
	    		tempDirectionSetting = this.antiTransposedDirection();
	    	}
	    	
	    	//this.beaconInput.mouseClicked(x, y, buttonClicked); UNUSED
	    }
	    
	   /* private boolean isValidChar(char par1) {
	    	for(int x = 0; x <= this.allowedChars.length; x++) {
	    		if(par1 == this.allowedChars[x]) {
	    			return true;
	    		}
	    	}
	    	return false;
	    }*/
	    
	    @Override
	    public void updateScreen() {
	    	//this.front.updateCursorCounter();
	    	//this.back.updateCursorCounter();
	    	//this.left.updateCursorCounter();
	    	//this.right.updateCursorCounter();
	    	//this.up.updateCursorCounter();
	    	//this.down.updateCursorCounter();
	    	
	    	//this.distance.updateCursorCounter();
	    	
	    	//this.mode.updateCursorCounter();
	    	
	    	//this.direction.updateCursorCounter();
	    	
	    	this.shipName.updateCursorCounter();
	    	
	    	//this.beaconInput.updateCursorCounter(); UNUSED
	    }
	    
	    @Override
	    protected void actionPerformed(GuiButton button) {
	    	switch(button.id) {
	    		case 0:
	    			furnaceInventory.doJump();
	    			System.out.println(furnaceInventory.getCoreFrequency() + " jumped from coordinates " + String.valueOf(furnaceInventory.getCoreX()) + ", " + String.valueOf(furnaceInventory.getCoreY()) + ", " + String.valueOf(furnaceInventory.getCoreZ()) + " to coordinates " + String.valueOf(furnaceInventory.getDestX()) + ", " + "UNCALCULATED Y, " + String.valueOf(furnaceInventory.getDestZ()));
	    			break;
	    		case 1:
	    			furnaceInventory.setSummonAllFlag(true);
	    			break;
	    			//System.out.println("Clicked!");//ACTION PERFORMED ON BUTTON CLICK
	    		case 2:
	    			openURL("https://sites.google.com/site/spaceageminecraft/wiki/blocks/warp-interface");
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
	    	//this.front.drawTextBox();
	    	//this.back.drawTextBox();
	    	//this.left.drawTextBox();
	    	//this.right.drawTextBox();
	    	//this.up.drawTextBox();
	    	//this.down.drawTextBox();
	    	
	    	//th/is.distance.drawTextBox();
	    	
	    	//this.mode.drawTextBox();
	    	
	    	//this.direction.drawTextBox();
	    	
	    	this.shipName.drawTextBox();
	    }
	}