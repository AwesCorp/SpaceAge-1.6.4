package uedevkit.item.util;

import net.minecraft.entity.player.EntityPlayer;

public abstract interface IWrench {
	
	public abstract boolean canWrench(EntityPlayer paramEntityPlayer, int paramInt1, int paramInt2, int paramInt3);

	public abstract void wrenchUsed(EntityPlayer paramEntityPlayer, int paramInt1, int paramInt2, int paramInt3);

}