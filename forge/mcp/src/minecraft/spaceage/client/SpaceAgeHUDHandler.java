//Credit to SHIM mod
package spaceage.client;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import java.util.EnumSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import spaceage.common.ServerTickHandler;
import spaceage.common.SpaceAgeCore;
import spaceage.common.stuff.Resources;

/**
 * The HUD handler for SpaceAgeCore.  
 * @author SkylordJoel, Colossali (for original HUD base in SHIM)
 */

public class SpaceAgeHUDHandler implements ITickHandler {
  public void tickStart(EnumSet<TickType> type, Object... tickData) {
  }

  public void tickEnd(EnumSet<TickType> type, Object[] tickData) {
    if (type.equals(EnumSet.of(TickType.RENDER))) {
      onRenderTick();
    }
    else if (type.equals(EnumSet.of(TickType.CLIENT))) {
      GuiScreen curScreen = Minecraft.getMinecraft().currentScreen;
      if (curScreen != null) {
        onTickInGui(FMLClientHandler.instance().getClient(), curScreen);
      }else {
        onTickInGame(FMLClientHandler.instance().getClient());
      }
    }
  }

  private void onTickInGame(Minecraft mc) {
  }

  private void onTickInGui(Minecraft mc, GuiScreen gui) {
  }

  private void onRenderTick() {
    Minecraft mc = FMLClientHandler.instance().getClient();

    if ((Minecraft.getMinecraft().thePlayer != null) && 
      (Minecraft.getMinecraft().thePlayer.inventory.armorItemInSlot(3) != null) && 
      (mc.currentScreen == null) && (
      (Minecraft.getMinecraft().thePlayer.inventory.armorItemInSlot(3).itemID == SpaceAgeCore.advancedSpacesuitHelmet.itemID)))
    {
      GL11.glPushAttrib(1048575);

      Tessellator t = Tessellator.instance;

      ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft().gameSettings, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);

      FontRenderer fontRender = Minecraft.getMinecraft().fontRenderer;
      int width = res.getScaledWidth();
      int height = res.getScaledHeight();
      Minecraft.getMinecraft().entityRenderer.setupOverlayRendering();

      GL11.glEnable(3553);

      String fuel = "Energy Levels: " + ServerTickHandler.timer;
      int fuelX = 25;
      int fuelY = 80;
      int fuelColor = 16777215;
      fontRender.drawStringWithShadow(fuel, fuelX, fuelY, fuelColor);

      Minecraft.getMinecraft().renderEngine.bindTexture(Resources.fuelIcon);

      t.startDrawingQuads();
      t.addVertexWithUV(0.0D, 95.0D, 90.0D, 0.0D, 1.0D);
      t.addVertexWithUV(25.0D, 95.0D, 90.0D, 1.0D, 1.0D);
      t.addVertexWithUV(25.0D, 70.0D, 90.0D, 1.0D, 0.0D);
      t.addVertexWithUV(0.0D, 70.0D, 90.0D, 0.0D, 0.0D);
      t.draw();

      GL11.glPopAttrib();
    }
  }

  public int getItemCount(EntityPlayer player, Item item) {
    int count = 0;

    for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
      ItemStack thisStack = player.inventory.getStackInSlot(i);
      if ((thisStack != null) && (thisStack.itemID == item.itemID)) {
        count += thisStack.stackSize;
      }
    }

    return count;
  }

  public EnumSet<TickType> ticks() {
    return EnumSet.of(TickType.RENDER);
  }

  public String getLabel() {
    return "Space_Age_HUD_Handler";
  }
}