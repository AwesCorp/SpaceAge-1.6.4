package spaceage.planets.technoorganic;

import spaceage.planets.SpaceAgePlanets;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.DimensionManager;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cr0s.WarpDrive.utils.CloudRenderBlank;

public class WorldProvider0011 extends WorldProvider {

	public void registerWorldChunkManager()
{
/** tells Minecraft to use our new WorldChunkManager **/
this.worldChunkMgr = new WorldChunkManger0011(worldObj.getSeed(), terrainType);
this.hasNoSky = false;
}

@Override
/** Dimension Name **/
public String getDimensionName()
{
return "0011";
}

/** Get Provider for dimension **/
public static WorldProvider getProviderForDimension(int id)
{
return DimensionManager.createProviderFor(SpaceAgePlanets.instance.T0011ID);
}

/** Welcome message **/
public String getWelcomeMessage()
{
return "<Nav Computer> Entering the atmosphere of 0011";
}


/** What chunk provider to use **/
public IChunkProvider createChunkGenerator()
{
return new ChunkProvider0011(worldObj, worldObj.getSeed(), true);
}

	/** Can player re-spawn here **/
	public boolean canRespawnHere() {
		return true;
	}

/** Determines the dimension the player will be respawned in **/
	public int getRespawnDimension(EntityPlayerMP player) {
		return SpaceAgePlanets.instance.T0011ID;
	}

	@Override
	public Vec3 getSkyColor(Entity cameraEntity, float partialTicks) {
		setCloudRenderer(new CloudRenderBlank());
		
		return this.worldObj.getWorldVec3Pool().getVecFromPool(0, 0, 0.5);
	}

	@Override
	public boolean isSkyColored() {
		return true;
	}
	
    @Override
    public boolean getWorldHasVoidParticles()
    {
        return false;
    }
}