package spaceage.common.achievements;

import cpw.mods.fml.common.Loader;
import cr0s.WarpDrive.WarpDrive;
import spaceage.common.SpaceAgeCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityEvent;

public class SpaceAgeAchievements { //Future possible achievements: Magical Energy (dragon egg crafting), planets, 
	
	public static Achievement space;
	public static Achievement spaceDeath;
	
	public static AchievementPage spaceAge;
	static Achievement[] spaceAgeAchievementList;
	
	public static void init() {
		space = new Achievement(3099, "spaceage.achSpace", 0, 0, new ItemStack(SpaceAgeCore.spaceshipAlloyMeta, 1, 15), null).registerAchievement();
		
		spaceDeath = new Achievement(3100, "spaceage.achSpaceDeath", 2, -1, new ItemStack(SpaceAgeCore.spaceshipAlloyMeta, 1, 0), space).registerAchievement();
		
	    spaceAgeAchievementList = new Achievement[] { space, spaceDeath };

	    spaceAge = new AchievementPage("SpaceAge", spaceAgeAchievementList);

	    AchievementPage.registerAchievementPage(spaceAge);
	}
	
	@ForgeSubscribe
	public void chunkEntered(EntityEvent.EnteringChunk event) {
		if(event.entity != null) {
			if((event.entity instanceof EntityPlayer)) {
				EntityPlayer player = (EntityPlayer)event.entity;
				World world = player.worldObj;
				
				int x = MathHelper.floor_double(player.posX);
				int y = MathHelper.floor_double(player.boundingBox.minY);
				int z = MathHelper.floor_double(player.posZ);
				
				int i = 0;
				int biomeID = world.getBiomeGenForCoords(x, z).biomeID;
				
				if(Loader.isModLoaded("WarpDrive")) {
					if(biomeID == WarpDrive.spaceBiome.biomeID) {
						player.addStat(space, 1);
						
						if(player.getHealth() <= 0.0D) {
							player.addStat(spaceDeath, 1);
						}
						//player.onDeath(DamageSource.drown); {
							//player.addStat(spaceDeath, 1);
						//}
					}
				}
			}
		}
	}
}
