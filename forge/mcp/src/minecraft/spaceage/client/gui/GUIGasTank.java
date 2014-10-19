package spaceage.client.gui;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import spaceage.common.SpaceAgeCore;
import spaceage.common.container.ContainerGasTank;
import spaceage.common.tile.TileHeatGenerator;
import spaceage.common.tile.TileGasTank;
import uedevkit.gui.GuiSimpleBase;

	@SideOnly(Side.CLIENT)
	public class GUIGasTank extends GuiSimpleBase {

		private static final ResourceLocation furnaceGuiTextures = new ResourceLocation(SpaceAgeCore.modid + ":" + "textures/gui/guiTank.png"/*"textures/gui/container/furnace.png"*/);
	    private TileGasTank furnaceInventory;

	    public GUIGasTank(InventoryPlayer par1InventoryPlayer, TileGasTank par2TileHeatGenerator) {
	        super(new ContainerGasTank(par1InventoryPlayer, par2TileHeatGenerator));
	        this.furnaceInventory = par2TileHeatGenerator;
	    }

	    /**
	     * Draw the foreground layer for the GuiContainer (everything in front of the items)
	     */
	    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
	        String s = I18n.getString("gasTank.name");
	        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
	        this.fontRenderer.drawString(I18n.getString("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	        
		 // Fluid level
	        if (this.isPointInRegion(8, 10, 16, 58, mouseX, mouseY)) {
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
	        
	        int fluidRemainingPercentage = this.furnaceInventory.getFluidRemainingScaled(58);
	        // Screen Coords: 8x10
	        // Filler Coords: 176x79
	        // Image Size WH: 16x58
	        this.drawTexturedModalRect(k + 8, l + 10, 176, 79, 16, 58 - fluidRemainingPercentage);
	    }
	  
}
