package cr0s.serverMods;

import spaceage.common.SpaceAgeCore;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingFallEvent;

/**
 * Used for negating fall damage in space.
 * @author Cr0s, SkylordJoel
 */
public class AntiFallDamage {
    /*private final int JETPACK_ID = 29954;
    private final int ELECTRIC_JETPACK_ID = 29953;
    private final int QUANTUM_BOOTS_ID = 29915;*/ //IC2 Items
    
    private final int ADV_SPACESUIT_BOOTS_ID = SpaceAgeCore.advancedSpacesuitBootsID;
    private final int ADV_SPACESUIT_CHESTPLATE_ID = SpaceAgeCore.advancedSpacesuitChestplateID;

    @ForgeSubscribe
    public void livingFall(LivingFallEvent event) {
        EntityLivingBase entity = event.entityLiving;
        float distance = event.distance;

        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            int check = MathHelper.ceiling_float_int(distance - 3.0F);

            if (check > 0) {
                if ((player.getCurrentArmor(0) != null && player.getCurrentArmor(0).itemID == ADV_SPACESUIT_BOOTS_ID) &&
                        (player.getCurrentArmor(2) != null && player.getCurrentArmor(2).itemID == ADV_SPACESUIT_CHESTPLATE_ID) ||
                        (player.getCurrentArmor(2) != null && player.getCurrentArmor(2).itemID == ADV_SPACESUIT_CHESTPLATE_ID))
                {
                    event.setCanceled(true);
                }
            }
        }
    }
}
