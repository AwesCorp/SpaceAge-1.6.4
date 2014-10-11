package cr0s.WarpDrive.client.gui;

import org.lwjgl.opengl.GL11;

import uedevkit.gui.GuiElectricBase;
import uedevkit.gui.GuiSimpleBase;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cr0s.WarpDrive.*;
import cr0s.WarpDrive.client.ClientProxy;
import cr0s.WarpDrive.common.container.ContainerPhotosynthesiser;
import cr0s.WarpDrive.tile.TilePhotosynthesiser;

	@SideOnly(Side.CLIENT)
	public class GUIPhotosynthesiser extends GuiSimpleBase {

		private static final ResourceLocation furnaceGuiTextures = new ResourceLocation(WarpDrive.modid + ":" + "textures/gui" + ClientProxy.guiPhotoSynth);
		private TilePhotosynthesiser furnaceInventory;
		
		public GUIPhotosynthesiser(InventoryPlayer player, TilePhotosynthesiser tile_entity) {
			super(new ContainerPhotosynthesiser(player, tile_entity));
			this.furnaceInventory = tile_entity;
		}
		
		@Override
		protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
	        String s = I18n.getString("photoSynth.name");
	        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
	        this.fontRenderer.drawString(I18n.getString("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	        
	        if (this.isPointInRegion(44, 10, 16, 58, mouseX, mouseY)) {
	            this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, "Oxygen " + String.valueOf(this.furnaceInventory.getOxygenRemainingScaled(100)) + " %");
	        }
	        
	        if (this.isPointInRegion(8, 10, 16, 58, mouseX, mouseY)) {
	            this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, "Water " + String.valueOf(this.furnaceInventory.getWaterRemainingScaled(100)) + " %");
	        }
		}

		@Override
		protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        this.mc.getTextureManager().bindTexture(furnaceGuiTextures);
	        int k = (this.width - this.xSize) / 2;
	        int l = (this.height - this.ySize) / 2;
	        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	        
	        int i1;
	        
	        int oxygen = this.furnaceInventory.getOxygenRemainingScaled(58);
	        this.drawTexturedModalRect(k + 44, l + 10, 192, 79, 16, 58 - oxygen);
	        
	        int water = this.furnaceInventory.getWaterRemainingScaled(58);
	        this.drawTexturedModalRect(k + 8, l + 10, 176, 79, 16, 58 - oxygen);
		}
	}
