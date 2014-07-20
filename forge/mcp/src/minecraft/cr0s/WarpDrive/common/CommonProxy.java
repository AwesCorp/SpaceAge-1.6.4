package cr0s.WarpDrive.common;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.world.World;
import cpw.mods.fml.common.registry.EntityRegistry;
import cr0s.WarpDrive.Vector3;
import cr0s.WarpDrive.WarpDrive;
import cr0s.WarpDrive.entity.EntityCamera;
import cr0s.WarpDrive.entity.EntityJump;
import cr0s.WarpDrive.entity.EntitySphereGen;
import cr0s.WarpDrive.entity.EntityStarCore;

public class CommonProxy
{
    public void registerEntities()
    {
        EntityRegistry.registerModEntity(EntityJump.class, "EntityJump", 1, WarpDrive.instance, 80, 1, false);
        EntityRegistry.registerModEntity(EntitySphereGen.class, "EntitySphereGenerator", 1, WarpDrive.instance, 200, 1, false);
        EntityRegistry.registerModEntity(EntityStarCore.class, "EntityStarCore", 1, WarpDrive.instance, 300, 1, false);
        EntityRegistry.registerModEntity(EntityCamera.class, "EntityCamera", 1, WarpDrive.instance, 300, 1, false);
    }

    public void registerRenderers()
    {
    }

    public void renderBeam(World world, Vector3 position, Vector3 target, float red, float green, float blue, int age, int energy)
    {
    }
}