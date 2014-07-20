package spaceage.common.item;

import java.util.List;

import spaceage.common.block.BlockGenerator;
import spaceage.common.stuff.Utils;

import org.lwjgl.input.Keyboard;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ItemBlockGeneratorTooltip extends ItemBlock {

	public ItemBlockGeneratorTooltip(int id) {
		super(id);
	}
	
    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List info, boolean par4) {
        // Only displays tooltip information when SHIFT key is pressed.
        String tooltip = /*StatCollector.translateToLocal(getUnlocalizedName() + ".tooltip")*/"Type: Electrical Generator";
        String defaultTooltip = StatCollector.translateToLocal("Press shift for more information");
        boolean isShiftPressed = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);

        // Use LWJGL to detect what key is being pressed.
        if (tooltip != null && tooltip.length() > 0 && isShiftPressed) {
            info.addAll(Utils.splitStringPerWord(tooltip, 5));
        }
        else if (defaultTooltip != null && defaultTooltip.length() > 0 && !isShiftPressed) {
            info.addAll(Utils.splitStringPerWord(String.valueOf(defaultTooltip), 10));
        }
    }

}
