package uedevkit.helper;

import net.minecraft.world.World;

public class RandomHelpers {
	
	public static final boolean isServerWorld(World world) {

		return !world.isRemote;
	}
}
