package spaceage.planets;

import cr0s.WarpDrive.WarpDrive;
import cr0s.WarpDrive.WarpDriveConfig;
import cr0s.WarpDrive.space.SpaceTeleporter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

public class AtmosphereHandler {

	@ForgeSubscribe
	public void livingUpdate(LivingUpdateEvent event) {
		EntityLivingBase entity = event.entityLiving;
		
		if (entity.worldObj.provider.dimensionId == SpaceAgePlanets.instance.vulcanID || entity.worldObj.provider.dimensionId == SpaceAgePlanets.instance.hadesID) { //TODO more planets w/ breathable atmosphere
			boolean inVacuum = isEntityInVacuum(entity);

			// Damage entity if in vacuum without protection
			if (inVacuum) {
				if (entity instanceof EntityPlayerMP) {
					if (((EntityPlayerMP)entity).getCurrentArmor(3) != null && WarpDriveConfig.i.SpaceHelmets.contains(((EntityPlayerMP)entity).getCurrentArmor(3).itemID)) {
						//((EntityPlayerMP)entity).addChatMessage("");
					} else {
						entity.attackEntityFrom(DamageSource.drown, 1);
					}
				}
			}
		}
	}
	
	private boolean isEntityInVacuum(Entity e) {
		int x = MathHelper.floor_double(e.posX);
		int y = MathHelper.floor_double(e.posY);
		int z = MathHelper.floor_double(e.posZ);
		int id1 = e.worldObj.getBlockId(x, y, z);
		int id2 = e.worldObj.getBlockId(x, y + 1, z);

		if (id1 == WarpDriveConfig.i.airID || id2 == WarpDriveConfig.i.airID) {
			return false;
		}
		return true;
	}
}
