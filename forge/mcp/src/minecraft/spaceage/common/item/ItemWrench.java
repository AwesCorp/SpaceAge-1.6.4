package spaceage.common.item;

import spaceage.common.SpaceAgeCore;
import uedevkit.item.util.IWrench;
import com.google.common.collect.Multimap;
import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.block.Block;
import net.minecraft.block.StepSound;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class ItemWrench extends Item
  implements IWrench {
	
	public ItemWrench(int id) {
		super(id);
		setMaxStackSize(1);
		setCreativeTab(SpaceAgeCore.tabSA);
	}

	public boolean func_77662_d() {
		return true;
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int hitSide, float hitX, float hitY, float hitZ) {
		return true;
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int hitSide, float hitX, float hitY, float hitZ) {
    int bId = world.getBlockId(x, y, z);
    
    return false;
	}

	public boolean canWrench(EntityPlayer player, int x, int y, int z) {
		return true;
	}

	public void wrenchUsed(EntityPlayer player, int x, int y, int z) {
	}
}