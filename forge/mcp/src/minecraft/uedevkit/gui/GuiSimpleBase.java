package uedevkit.gui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.inventory.Container;

/** As an added bonus with UEDevKit, you receive this Gui base. It allows tooltips and a simpler version of drawing strings.
 * 
 * @author SkylordJoel
 *
 */

public abstract class GuiSimpleBase extends GuiContainer {
	
	protected Tessellator t = Tessellator.instance;

	/** Change these params to InventoryPlayer and your tile entity. 
	 * 
	 * @param InventoryPlayer
	 * player
	 * @param TileEntityWhateverYouWantToUse tile
	 * 
	 * @author SkylordJoel
	 *
	 */
	public GuiSimpleBase(Container xContainer) {
		super(xContainer);
	}

	/** Simpler version of drawing strings. Simply type write in your drawGuiContainerForegroundLayer and fill the parameters. 
	 * @author SkylordJoel
	 */
	public int write(String string, int coord0, int coord1, int colour) {
		return fontRenderer.drawString(string, coord0, coord1, colour);
	}
	
	/** Tooltip functionality. Use this in your drawGuiContainerForegroundLayer. An example is located below. 
	 * 
	 * @param x
	 * @param y
	 * 
	 * @author SkylordJoel
	 */
	public void drawTooltip(int x, int y, String... tooltips) {
        if (!GuiScreen.isShiftKeyDown()) {
            if (tooltips != null) {
                GL11.glDisable(GL12.GL_RESCALE_NORMAL);
                GL11.glDisable(GL11.GL_DEPTH_TEST);

                int var5 = 0;
                int var6;
                int var7;

                for (var6 = 0; var6 < tooltips.length; ++var6) {
                    var7 = this.fontRenderer.getStringWidth(tooltips[var6]);

                    if (var7 > var5) {
                        var5 = var7;
                    }
                }

                var6 = x + 12;
                var7 = y - 12;

                int var9 = 8;

                if (tooltips.length > 1) {
                    var9 += 2 + (tooltips.length - 1) * 10;
                }

                if (this.guiTop + var7 + var9 + 6 > this.height) {
                    var7 = this.height - var9 - this.guiTop - 6;
                }

                this.zLevel = 300;
                int var10 = -267386864;
                this.drawGradientRect(var6 - 3, var7 - 4, var6 + var5 + 3, var7 - 3, var10, var10);
                this.drawGradientRect(var6 - 3, var7 + var9 + 3, var6 + var5 + 3, var7 + var9 + 4, var10, var10);
                this.drawGradientRect(var6 - 3, var7 - 3, var6 + var5 + 3, var7 + var9 + 3, var10, var10);
                this.drawGradientRect(var6 - 4, var7 - 3, var6 - 3, var7 + var9 + 3, var10, var10);
                this.drawGradientRect(var6 + var5 + 3, var7 - 3, var6 + var5 + 4, var7 + var9 + 3, var10, var10);
                int var11 = 1347420415;
                int var12 = (var11 & 16711422) >> 1 | var11 & -16777216;
                this.drawGradientRect(var6 - 3, var7 - 3 + 1, var6 - 3 + 1, var7 + var9 + 3 - 1, var11, var12);
                this.drawGradientRect(var6 + var5 + 2, var7 - 3 + 1, var6 + var5 + 3, var7 + var9 + 3 - 1, var11, var12);
                this.drawGradientRect(var6 - 3, var7 - 3, var6 + var5 + 3, var7 - 3 + 1, var11, var11);
                this.drawGradientRect(var6 - 3, var7 + var9 + 2, var6 + var5 + 3, var7 + var9 + 3, var12, var12);

                for (int var13 = 0; var13 < tooltips.length; ++var13) {
                    String var14 = tooltips[var13];

                    this.fontRenderer.drawStringWithShadow(var14, var6, var7, -1);
                    var7 += 10;
                }

                this.zLevel = 0;

                GL11.glEnable(GL11.GL_DEPTH_TEST);
                GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            }
        }
    }
	
	/*Example of tooltip, for energy:
	if (this.isPointInRegion(161, 3, 8, 80, mouseX, mouseY)) {
		this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, "Energy " + String.valueOf(this.YOUR_TILE.getPowerRemainingScaled(100)) + " %");
	        } */
	
	public static String openURL(String url) {
		if(Desktop.isDesktopSupported()) {
			try {
				Desktop.getDesktop().browse(new URI(url));
			} catch(IOException e) {
				e.printStackTrace();
				return "Unable to open browser from IOException";
			} catch(URISyntaxException e) {
				e.printStackTrace();
				return "Invalid URL: Mod-related bug... PS: This should not happen: Alert SkylordJoel immediately";
			}
		} else {
			return "Can't open browser for unknown reason";
		}
		return null;
	}

	/**Needs to have a t.startDrawingQuads in the calling function - basically just enables code for a larger function
	 * @author SkylordJoel
	 */
/*	public void addPixelQuad(int x, int y, int u, int v) {
		//Tessellator t = Tessellator.instance;
		t.addVertexWithUV(x, 		y, 		0, u, 		v);
		t.addVertexWithUV(x, 		y + 1, 	0, u, 		v + 1);
		t.addVertexWithUV(x + 1, 	y + 1, 	0, u + 1, 	v + 1);
		t.addVertexWithUV(x + 1, 	y, 		0, u + 1, 	v);
	}*/
	
    /*public void drawTexturedModalRect(int x, int y, int u, int v, int width, int height) {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        
        tessellator.addVertexWithUV((double)(x + 0), (double)(y + height), (double)this.zLevel, (double)((float)(u + 0) * f), (double)((float)(v + height) * f1));
        tessellator.addVertexWithUV((double)(x + width), (double)(y + height), (double)this.zLevel, (double)((float)(u + width) * f), (double)((float)(v + height) * f1));
        tessellator.addVertexWithUV((double)(x + width), (double)(y + 0), (double)this.zLevel, (double)((float)(u + width) * f), (double)((float)(v + 0) * f1));
        tessellator.addVertexWithUV((double)(x + 0), (double)(y + 0), (double)this.zLevel, (double)((float)(u + 0) * f), (double)((float)(v + 0) * f1));
        tessellator.draw();
    }*/
}
