package spaceage.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import spaceage.common.SpaceAgeCore;
import spaceage.common.container.ContainerHeatGenerator;
import spaceage.common.tile.TileHeatGenerator;
import spaceage.common.tile.TileSolarPanel;
import uedevkit.gui.GuiElectricBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * The GUI of the {@link TileHeatGenerator}. 
 * @author SkylordJoel
 */

	@SideOnly(Side.CLIENT)
	public class GUIHeatGenerator extends GuiElectricBase {
	   
		private static final ResourceLocation furnaceGuiTextures = new ResourceLocation(SpaceAgeCore.modid + ":" + "textures/gui/guiGeothermalTurbine.png"/*"textures/gui/container/furnace.png"*/);
	    private TileHeatGenerator furnaceInventory;

	    public GUIHeatGenerator(InventoryPlayer par1InventoryPlayer, TileHeatGenerator par2TileHeatGenerator) {
	        super(new ContainerHeatGenerator(par1InventoryPlayer, par2TileHeatGenerator));
	        this.furnaceInventory = par2TileHeatGenerator;
	    }

	    /**
	     * Draw the foreground layer for the GuiContainer (everything in front of the items)
	     */
	    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
	        String s = this.furnaceInventory.isInvNameLocalized() ? this.furnaceInventory.getInvName() : I18n.getString(this.furnaceInventory.getInvName());
	        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
	        this.fontRenderer.drawString(I18n.getString("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	        
	     // Power level
	        if (this.isPointInRegion(161, 3, 8, 80, mouseX, mouseY)) {
	            //String powerLevelLiteral = String.valueOf(this.ENTITY.getEnergy(ForgeDirection.UNKNOWN)) + "/" + String.valueOf(this.ENTITY.getEnergyCapacity(ForgeDirection.UNKNOWN));
	            this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, "Energy " + String.valueOf(this.furnaceInventory.getPowerRemainingScaled(100)) + " %");
	        }
	        
		 // Water level
	        if (this.isPointInRegion(8, 10, 16, 58, mouseX, mouseY)) {
	            //String powerLevelLiteral = String.valueOf(this.ENTITY.getEnergy(ForgeDirection.UNKNOWN)) + "/" + String.valueOf(this.ENTITY.getEnergyCapacity(ForgeDirection.UNKNOWN));
	            this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, "Fluid " + String.valueOf(this.furnaceInventory.getFluidRemainingScaled(100)) + " %");
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
	        // Screen Coords: 161x3 (161x3 if cursor is on (162x4 from absolute left and height))
	        // Filler Coords: 176x0 (176x0 if cursor is on)
	        // Image Size WH: 8x80 (8x80)
	        this.drawTexturedModalRect(k/*screenX*/ + 161, l/*screenY*/ + 3, 176, 0, 8, 80 - powerRemainingPercentage); //TODO CHECK IF WORKS!!!
	        
	        int fluidRemainingPercentage = this.furnaceInventory.getFluidRemainingScaled(58/*mine: by 58?*/);
	        // Screen Coords: 8x10
	        // Filler Coords: 176x79
	        // Image Size WH: 16x58
	        this.drawTexturedModalRect(k + 8, l + 10, 176, 79, 16, 58 - fluidRemainingPercentage);
	        
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
	  
	}

