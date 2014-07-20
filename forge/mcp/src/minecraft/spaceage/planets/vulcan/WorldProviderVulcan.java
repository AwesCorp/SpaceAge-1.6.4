package spaceage.planets.vulcan;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldProviderVulcan extends WorldProvider {
	
	@Override
	public void registerWorldChunkManager() {
		this.worldChunkMgr = new WorldChunkManagerVulcan(this.worldObj);
	}

	@Override
	public String getDimensionName() {
		return "Vulcan";
	}
	
	@Override
	public boolean canRespawnHere() {
		return true;
	}

	  public boolean darkenSkyDuringRain() {
	    return false;
	  }

	  public double getHorizon() {
	    return 0.0D;
	  }

	  @SideOnly(Side.CLIENT)
	  public boolean hasVoidParticles(boolean var1) {
	    return false;
	  }

	  @SideOnly(Side.CLIENT)
	  public boolean isSkyColored() {
	    return true;
	  }

	  public double getVoidFogYFactor() {
	    return 1.0D;
	  }

	  @SideOnly(Side.CLIENT)
	  public Vec3 getFogColor(float par1, float par2) {
	    float var3 = MathHelper.cos(par1 * 3.141593F * 2.0F) * 2.0F + 0.5F;

	    if (var3 < 0.0F)
	    {
	      var3 = 0.0F;
	    }

	    if (var3 > 1.0F)
	    {
	      var3 = 1.0F;
	    }

	    float var4 = 1.0F;
	    float var5 = 0.7372549F;
	    float var6 = 0.2588235F;
	    var4 *= (var3 * 3.94F + 0.06F);
	    var5 *= (var3 * 0.94F + 0.06F);
	    var6 *= (var3 * 0.91F + 0.09F);
	    return this.worldObj.getWorldVec3Pool().getVecFromPool(var4, var5, var6);
	  }

	  public void setAllowedSpawnTypes(boolean allowHostile, boolean allowPeaceful) {
	    allowPeaceful = true;
	  }

	  public String getWelcomeMessage() {
	    return "<Navigational Computer> Entering the atmosphere of Vulcan";
	  }

	  public String getDepartMessage() {
	    return "<Navigational Computer> Departing the atmosphere of Vulcan";
	  }

	  public IChunkProvider createChunkGenerator() {
	    return new ChunkProviderVulcan(this.worldObj, this.worldObj.getSeed());
	  }

	  public boolean isLightingDisabled() {
	    return false;
	  }
}
