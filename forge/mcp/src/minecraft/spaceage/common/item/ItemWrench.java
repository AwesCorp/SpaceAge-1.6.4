package spaceage.common.item;

import spaceage.common.SpaceAgeCore;
import uedevkit.item.util.IWrench;
import com.google.common.collect.Multimap;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.StepSound;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class ItemWrench extends Item
  implements IWrench {
	
	@SideOnly(Side.CLIENT)
	private Icon[] icons;
	
	public ItemWrench(int id) {
		super(id);
		setMaxStackSize(1);
		setCreativeTab(SpaceAgeCore.tabSA);
	}

	@Override
	public boolean isFull3D() {
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
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register) {
		icons = new Icon[1];
		
		icons[0] = register.registerIcon("spaceage:wrench");
	}
}