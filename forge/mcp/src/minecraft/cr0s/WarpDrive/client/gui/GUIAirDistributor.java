package cr0s.WarpDrive.client.gui;

import org.lwjgl.opengl.GL11;

import uedevkit.gui.GuiElectricBase;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cr0s.WarpDrive.*;
import cr0s.WarpDrive.client.ClientProxy;
import cr0s.WarpDrive.common.container.ContainerAirDistributor;
import cr0s.WarpDrive.tile.TileEntityAirDistributor;

	@SideOnly(Side.CLIENT)
	public class GUIAirDistributor extends GuiElectricBase {

		private static final ResourceLocation furnaceGuiTextures = new ResourceLocation(WarpDrive.modid + ":" + "textures/gui" + ClientProxy.guiAirGen);
		private TileEntityAirDistributor furnaceInventory;
		
		public GUIAirDistributor(InventoryPlayer player, TileEntityAirDistributor tile_entity) {
			super(new ContainerAirDistributor(player, tile_entity));
			this.furnaceInventory = tile_entity;
		}
		
		@Override
		protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
	        String s = I18n.getString("airDistributor.name");
	        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
	        this.fontRenderer.drawString(I18n.getString("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	        
	        if (this.isPointInRegion(161, 3, 8, 58, mouseX, mouseY)) {
	            this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, "Energy " + String.valueOf(this.furnaceInventory.getPowerRemainingScaled(100)) + " %");
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
	        
	        int powerRemainingPercentage = this.furnaceInventory.getPowerRemainingScaled(58);
	        this.drawTexturedModalRect(k + 160, l + 10, 176, 0, 8, 58 - powerRemainingPercentage);
		}
	}
