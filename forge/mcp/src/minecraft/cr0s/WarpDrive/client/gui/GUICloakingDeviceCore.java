package cr0s.WarpDrive.client.gui;

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

import uedevkit.gui.GuiElectricBase;
import uedevkit.util.MathHelper;

import cpw.mods.fml.common.network.PacketDispatcher;
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
	public class GUICloakingDeviceCore extends GuiElectricBase { //TODO gui testing, button and text box placement
	   
		private static final ResourceLocation furnaceGuiTextures = new ResourceLocation(WarpDrive.modid + ":" + "textures/gui/" + "guiCloakingDevice_alternate.png");//ClientProxy.guiCloakingDeviceCore/*"textures/gui/container/furnace.png"*/);
	    private TileEntityCloakingDeviceCore furnaceInventory;
	    //private TileEntityParticleBooster particle;

	    //private GuiTextField setFieldTier;
	    //private GuiTextField setFrequency;
	    
	    //private char[] allowedChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	    //private char[] fieldChars = { '1', '2' };
	    
	    public static final GuiRectangle fieldTierSlider = new GuiRectangle(0, 0, 4, 6);
	    public static final GuiRectangle frequencySlider = new GuiRectangle(0, 0, 4, 6);
	    
	    private boolean tierIsDragging;
	    private boolean frequencyIsDragging;
	    
	    private int tempTierSetting = 1;
	    private int tempFrequencySetting = 1;
	    
	    public GUICloakingDeviceCore(InventoryPlayer par1InventoryPlayer, TileEntityCloakingDeviceCore tile_entity) {
	        super(new ContainerCloakingDeviceCore(par1InventoryPlayer, tile_entity));
	        this.furnaceInventory = tile_entity;
	    }
	    
	    int grey = 4210752;
	    
	    int tCoord0 = 3;
	    int tCoord1 = 37;
	    int aCoord0 = 3;
	    int aCoord1 = 37;

	    @Override
	    protected void mouseClickMove(int x, int y, int button, long timeSinceLastClick) {
	    	super.mouseClickMove(x, y, button, timeSinceLastClick);	
	    	
	    	if (tierIsDragging) {
	    		tempTierSetting = x - guiLeft - 116;
	    		
	    		if (tempTierSetting < 0) {
	    			tempTierSetting = 0;
	    		} else if (tempTierSetting > /*change*/1) {
	    			tempTierSetting = 1;/*change*/
	    		}
	    	} 
	    	if (frequencyIsDragging) {
	    		tempFrequencySetting = x - guiLeft - 116;
	    		
	    		if (tempFrequencySetting < 0) {
	    			tempFrequencySetting = 0;
	    		} else if (tempFrequencySetting > 128) {
	    			tempFrequencySetting = 128;
	    		}
	    	} 
	    }
	    
	    @Override
	    protected void mouseMovedOrUp(int x, int y, int button) {
	    	super.mouseMovedOrUp(x, y, button);
	    	
	    	if (tierIsDragging) {
	    		sendSliderPacket(9, tempTierSetting);
	    		furnaceInventory.setFieldTier(tempTierSetting);
		    	tierIsDragging = false;
	    	} 
	    	if (frequencyIsDragging) {
	    		sendSliderPacket(10, tempFrequencySetting);
	    		furnaceInventory.setFieldFrequency(tempFrequencySetting);
		    	frequencyIsDragging = false;
	    	} 
	    }
	    
	    public void sendSliderPacket(int identifier, int data) {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DataOutputStream output = new DataOutputStream(bos);
			
			try {
				output.writeInt(identifier);
				output.writeInt(data);
				
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

		private void updateTierSliderPosition() {
	        fieldTierSlider.setX(116 + getCurrentTier());
	    }
	    
	    private void updateFreqSliderPosition() {
	        frequencySlider.setX(116 + getCurrentFreq());
	    }
	    
	    public int getCurrentTier() {
	    	return tierIsDragging ? tempTierSetting : furnaceInventory.tier;
	    }
	    
	    public int getCurrentFreq() {
	    	return frequencyIsDragging ? tempFrequencySetting : furnaceInventory.frequency;
	    }
	    
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
	        if (this.isPointInRegion(161, 3, 8, 58, mouseX, mouseY)) { //TODO CHECK IF WORKS!!!
	            //String powerLevelLiteral = String.valueOf(this.ENTITY.getEnergy(ForgeDirection.UNKNOWN)) + "/" + String.valueOf(this.ENTITY.getEnergyCapacity(ForgeDirection.UNKNOWN));
	            this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, "Energy " + String.valueOf(this.furnaceInventory.getPowerRemainingScaled(100)) + " %");
	        }
	      
	        //setFieldTier.drawTextBox();
	        //setFrequency.drawTextBox();
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
	        
	        //int powerRemainingPercentage = this.furnaceInventory.getPowerRemainingScaled(58/*MAXWOLF = 32 - mine: 25 000 per pixel? So by 80?*/); //TODO CHECK IF WORKS!!!
	        // Screen Coords: 112x17 (161x3 if cursor is on (162x4 from absolute left and height))
	        // Filler Coords: 176x56 (176x0 if cursor is on)
	        // Image Size WH: 18x32 (8x80)
	        //this.drawTexturedModalRect(k/*screenX*/ + 160, l/*screenY*/ + 10, 176, 0, 8, 58 - powerRemainingPercentage);
	        drawTexturedModalRect(k + 160, l + 10, 184, 0, 8, 58);
	        
	        int qty = getEnergyScaled();
	        drawTexturedModalRect(k + 160, l + 68 - qty, 176, 58 - qty, 8, qty);
	        
	        //FLAME - DON'T NEED
	        /*if (this.furnaceInventory.isBurning())
	        {
	            i1 = this.furnaceInventory.getBurnTimeRemainingScaled(12);
	            this.drawTexturedModalRect(k + 56, l + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 2);
	        }*/
	        
	        //GIANT ARROW - DON'T NEED
	        /*i1 = this.furnaceInventory.getCookProgressScaled(24);
	        this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);*/
	        
	        fieldTierSlider.draw(this, 88, 174);
	        updateTierSliderPosition();
	        
	        frequencySlider.draw(this, 88, 174);
	        updateFreqSliderPosition();
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
	    	buttonList.add(new GuiButton(1/*button number, maybe for mod, else gui*/, guiLeft + 100/*Location in relation to left in pixels*/, guiTop + cloakY + 20/*Location in relation to top in pixels*/, 30/*Length in pixels*/, 20/*Height in pixels*/, "Wiki"/*Text on button*/));
	   
	    }
	    
	    @Override
	    protected void actionPerformed(GuiButton button) {
	    	switch(button.id) {
	    		case 0:
	    			furnaceInventory.cloak();
	    			break;
	    		case 1:
	    			openURL("https://sites.google.com/site/spaceageminecraft/wiki/blocks/cloaking-device-core");
	    			break;
	    	}
	    }
	    
		@Override
	    protected void mouseClicked(int x, int y, int buttonClicked) {
	    	super.mouseClicked(x, y, buttonClicked);
	    	
	    	updateTierSliderPosition();
	    	if (fieldTierSlider.isInRect(this, x, y)) {
	    		tierIsDragging = true;
	    		tempTierSetting = furnaceInventory.tier;
	    	}
	    	
	    	updateFreqSliderPosition();
	    	if (frequencySlider.isInRect(this, x, y)) {
	    		frequencyIsDragging = true;
	    		tempFrequencySetting = furnaceInventory.frequency;
	    	}
		}
		
		public int getEnergyScaled() {
		    if (this.furnaceInventory.getEnergyHandler().getEnergyCapacity() < 0) {
		    	return 58;
		    }
		    
		    return MathHelper.round(this.furnaceInventory.getEnergyHandler().getEnergy() * 58 / this.furnaceInventory.getEnergyHandler().getEnergyCapacity());
		}
	}