package uedevkit.gui;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

/** If your GUI uses electric functionality, I recommend you extend this class. 
 * 
 * @author SkylordJoel
 *
 */

public abstract class GuiElectricBase extends GuiSimpleBase {

	/** Change these params to InventoryPlayer and your tile entity. 
	 * 
	 * @param InventoryPlayer
	 * player
	 * @param TileEntityWhateverYouWantToUse tile
	 * 
	 * @author SkylordJoel
	 *
	 */
	
	public GuiElectricBase(Container par1Container) {
		super(par1Container);
	}

}
